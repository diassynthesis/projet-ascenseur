package projetAscenseur;

import fr.unice.plugin.Plugin;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import projetAscenseur.personne.Personnes;
import projetAscenseur.factories.concreteFactories.GroupeAleatoireImpl;
import projetAscenseur.factories.concreteFactories.GroupeMonteImpl;
import projetAscenseur.factories.concreteFactories.PersGroupeFactory;
import projetAscenseur.factories.PersonneFactory;
import projetAscenseur.factories.PersonneMaker;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import projetAscenseur.factories.concreteFactories.PersonneSeuleAleatoireImpl;
import projetAscenseur.factories.concreteFactories.PersonneSeuleMonteImpl;
import projetAscenseur.strategy.concreteStrategy.ComportementArretEtage;
import projetAscenseur.strategy.ComportementAbstrait;
import fr.unice.plugin.Plugin;
import fr.unice.plugin.PluginLoader;
/**
 * classe representant l'interface graphique de la simulation
 * @author Checconi maxime, Pilot guillaume et Canessa Marine
 */
public class Simulateur extends JFrame{
    
    //********************ATTRIBUTS*****************************
    private Immeuble immeuble;
    private PersonneFactory fabrique;
    private static boolean enCreation = true;
    private Ascenseur ascenseur;
    private int nbAsc;
    private int nbPersChoisi;
    private int capacite;
    
    private JPanel panelControle;
    private JPanel panelButtons;
    private JPanel panelSlider;
    private JButton start;
    private JButton stats;
    private JButton stop;
    private JButton rafraichir;
    private JButton exit;
    private JButton chargerPlugins;
    
    private JPanel modifPanel;
    private JPanel modifPanelGauche;
    private JPanel modifPanelDroit;
    private JButton ajouterAscenseur;
    private JButton ajouterPersonne;
    private JComboBox nbPersAjout;
    
    private JPanel paramPanel;
    private JPanel paramPanelHaut;
    private JPanel paramPanelBas;
    private JLabel nbEtages;
    private JLabel nbPers;
    private JLabel nbAscenseur;
    private JComboBox nbAscenseurBox;
    private JLabel typeDePersonne;
    private JComboBox typeDePersonneBox;
    private JComboBox listNbEtages;
    private JTextField nbPersText;
    
    private JLabel capaciteAscLabel;
    private JTextField capaciteAscText;
    
    private JPanel resultat;
    private JPanel resultatVisitor;
    private JPanel resultatButton;
    private JPanel resultatTpsVisitor;
    private JPanel resultatHumeurVisitor;
    private JButton sauvegarder;
    private JButton ouvrirFicher;
    
    private JLabel nbContente;
    private JLabel nbNormal;
    private JLabel nbEnerve;
    private JLabel nbContenteVisitor;
    private JLabel nbNormalVisitor;
    private JLabel nbEnerveVisitor;
    
    private JLabel totalTime;
    private JLabel nbVisited;
    private JLabel moyenne;
    private JLabel totalTimeVisitor;
    private JLabel nbVisitedVisitor;
    private JLabel moyenneVisitor;
    
    private JTabbedPane onglets;
    private JSlider tempsSimulation;
    
    private PluginLoader PluginLoader;
    private PluginMenuItemFactory pluginMenuItemFactory;
    private Plugin[] plugins;
    private JMenuBar mb = new JMenuBar();
    private JMenu menuPlugin2;
    private ComportementAbstrait comportement;
    
    
    
    //************************ACCESSEURS*****************************
   
    public static boolean isEnCreation() {    return enCreation;   }

    public void setEnCreation(boolean enCreation) {   this.enCreation = enCreation;   }
   
    
    //******************************METHODES*********************************
    
    public Simulateur(){
        super("Interface de controle du simulateur");
        try {
            setLocation(600, 0);
            initSimulateur();
        } catch (MalformedURLException ex) {
            Logger.getLogger(Simulateur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void initMenuPlugin(){
        try {
            PluginLoader = new PluginLoader("plugins");
            PluginLoader.loadPlugins();
            plugins = PluginLoader.getPluginInstances();
            System.out.println(plugins.length);
            setJMenuBar(mb);
            buildPluginMenu();
        } catch (MalformedURLException ex) {
            Logger.getLogger(Simulateur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void buildPluginMenu() {
        menuPlugin2 = new JMenu("Plugins");
        if (pluginMenuItemFactory == null) {
            pluginMenuItemFactory = new PluginMenuItemFactory(menuPlugin2, PluginLoader, listener);
        }
        buildPluginMenuEntries();
        mb.add(menuPlugin2);
        menuPlugin2.setEnabled(false);
    }
     private void buildPluginMenuEntries() {

        pluginMenuItemFactory.buildMenu(null);
    }
     
    ActionListener listener = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        for(Ascenseur asc : immeuble.getListeAscenseur()){
            comportement = (ComportementAbstrait)((PluginMenuItem)e.getSource()).getPlugin();
            try {
                ComportementAbstrait c = comportement.getClass().newInstance();
                asc.setComportement(c);
            } catch (InstantiationException ex) {
                Logger.getLogger(Simulateur.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Simulateur.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
      }
    };
    
    /**
     * initialise l'interface du simulateur
     */
    public void initSimulateur() throws MalformedURLException{
        
        //composants graphiques de l'onglet Contrôle
        onglets = new JTabbedPane(SwingConstants.TOP);
        panelControle = new JPanel();
        panelButtons = new JPanel();
        panelSlider = new JPanel();
        start = new JButton("Start");
        stop = new JButton("Stop");
        stats = new JButton("Voir stats");
        stats.setEnabled(false);
        stop.setEnabled(false);
        rafraichir = new JButton("Rafraichir");
        rafraichir.setEnabled(false);
        exit = new JButton("Exit");
        chargerPlugins = new JButton("Charger plugins");
        
        //composants graphiques de l'onglet Modification
        modifPanel = new JPanel();
        modifPanelGauche = new JPanel();
        modifPanelDroit = new JPanel();
        ajouterAscenseur = new JButton("Ajouter Ascenseur");
        ajouterAscenseur.setEnabled(false);
        ajouterPersonne = new JButton("Ajouter Personne");
        ajouterPersonne.setEnabled(false);
        
        //composants graphiques de l'onglet Paramètres
        paramPanel = new JPanel();
        paramPanelHaut = new JPanel();
        paramPanelBas = new JPanel();
        paramPanel.setLayout(new GridLayout(2,0));
        nbEtages = new JLabel("Nombres d'etages : ");
        nbPers = new JLabel("Nombres de personnes : ");
        nbAscenseur = new JLabel("Nombres d'Ascenseur :");
        nbAscenseurBox = new JComboBox();
        typeDePersonne = new JLabel("Type de personne");
        typeDePersonneBox = new JComboBox();
        nbPersText = new JTextField(3);
        nbPersText.setText(""+10);
        
        capaciteAscText = new JTextField(3);
        capaciteAscText.setText(""+5);
        capaciteAscLabel = new JLabel("Capacité Ascenseur : ");
        
        //composants graphiques de l'onglet Resultats
        resultat = new JPanel();
        resultat.setLayout(new BorderLayout());
        resultatVisitor = new JPanel();
        resultatVisitor.setLayout(new GridLayout(2,0));
        resultatButton = new JPanel();
        resultatTpsVisitor = new JPanel();
        resultatHumeurVisitor = new JPanel();
        sauvegarder = new JButton("Sauvegarder");
        sauvegarder.setEnabled(false);
        ouvrirFicher = new JButton("Ouvrir fichier");
        ouvrirFicher.setEnabled(false);
        
        //label des visitors
        totalTime = new JLabel("temps total : ");
        nbVisited = new JLabel("nombre visites : ");
        moyenne = new JLabel("temps moyen : ");
        nbContente = new JLabel("personnes contentes : ");
        nbNormal = new JLabel("personnes normales : ");
        nbEnerve = new JLabel("personnes enervees : ");
        
        //label des visitors
        totalTimeVisitor = new JLabel("0");
        nbVisitedVisitor = new JLabel("0");
        moyenneVisitor = new JLabel("0");
        nbContenteVisitor = new JLabel("0");
        nbNormalVisitor = new JLabel("0");
        nbEnerveVisitor = new JLabel("0");
        
        totalTimeVisitor.setForeground(Color.red);
        nbVisitedVisitor.setForeground(Color.red);
        moyenneVisitor.setForeground(Color.red);
        nbContenteVisitor.setForeground(Color.red);
        nbNormalVisitor.setForeground(Color.red);
        nbEnerveVisitor.setForeground(Color.red);
        
        //ajout des composants graphiques du panel resultatTpsVisitor
        resultatTpsVisitor.add(totalTime);
        resultatTpsVisitor.add(totalTimeVisitor);
        resultatTpsVisitor.add(nbVisited);
        resultatTpsVisitor.add(nbVisitedVisitor);
        resultatTpsVisitor.add(moyenne);
        resultatTpsVisitor.add(moyenneVisitor);
        
        //ajout des composants graphiques du panel resultatHumeurVisitor
        resultatHumeurVisitor.add(nbContente);
        resultatHumeurVisitor.add(nbContenteVisitor);
        resultatHumeurVisitor.add(nbNormal);
        resultatHumeurVisitor.add(nbNormalVisitor);
        resultatHumeurVisitor.add(nbEnerve);
        resultatHumeurVisitor.add(nbEnerveVisitor);
        
        //ajout des panels resultatTpsVisitor et resultatHumeurVisitor au panel resultatVisitor
        resultatVisitor.add(resultatTpsVisitor);
        resultatVisitor.add(resultatHumeurVisitor);
        resultatButton.add(sauvegarder);
        resultatButton.add(ouvrirFicher);
        
        //ajout des panels resultatVisitor et resultatButton au panel resultat
        resultat.add(resultatVisitor, BorderLayout.NORTH);
        resultat.add(resultatButton, BorderLayout.CENTER);
        
        
        
        //slider permettant le contrôle de la vitesse de la simulation
        tempsSimulation = new JSlider(JSlider.HORIZONTAL, 200, 1000, 500);
        tempsSimulation.addChangeListener(new ChangeListener()
            {
               public void stateChanged(ChangeEvent e)
               {
                  
                   JSlider target = (JSlider) e.getSource(); 	   

                    int temporisation = (int) target.getValue();
                    ArrayList<Ascenseur> listAscenseur = immeuble.getListeAscenseur();
                    for(int i = 0; i<listAscenseur.size();i++)
                        listAscenseur.get(i).setTemporisation(temporisation);

               }
            });

    	tempsSimulation.setMajorTickSpacing(200);
    	tempsSimulation.setPaintTicks(true);
    	tempsSimulation.setPaintLabels(true);
        tempsSimulation.setEnabled(false);
        
        //creation de la liste deroulante du nombres d'etages
        listNbEtages = new JComboBox();
        for (int i = 0; i<9;i++){
    		listNbEtages.addItem(i+2);
        }
        listNbEtages.setSelectedIndex(4);
        //creation de la liste deroulante du nombres de personnes à ajouter en cours de simulation(onglet Modification)
        nbPersAjout = new JComboBox();
        for (int i = 2; i<11;i++){
    		nbPersAjout.addItem(i);
        }
        //creation de la liste deroulante du type de personnes voulue dans la simulation(Personne, Groupe...)
        typeDePersonneBox.addItem("SeuleAleatoire");
        typeDePersonneBox.addItem("SeuleQuiMonte");
        typeDePersonneBox.addItem("GroupeQuiMonte");
        typeDePersonneBox.addItem("GroupeAleatoire");
        typeDePersonneBox.addItem("GroupeEtPersonne");
        
        for(int i = 1; i<=8;i++){
            nbAscenseurBox.addItem(i);
        }
        
        //ajout des composants graphique du panelContrôle
        panelControle.setLayout(new BorderLayout());
        panelSlider.add(new JLabel("Vitesse : "));
        panelSlider.add(tempsSimulation);
        panelButtons.add(start);
        panelButtons.add(stop);
        panelButtons.add(rafraichir);
        panelButtons.add(stats);
        panelButtons.add(chargerPlugins);
        panelButtons.add(exit);
        panelControle.add(panelButtons, BorderLayout.NORTH);
        panelControle.add(panelSlider, BorderLayout.CENTER);
        
        //ajout des composants graphique du modifPanel
        modifPanelGauche.setLayout(new GridLayout(0,1));
        modifPanelDroit.setLayout(new GridLayout(1,0));
        modifPanelGauche.add(ajouterAscenseur);
        modifPanelDroit.add(ajouterPersonne);
        modifPanelDroit.add(nbPersAjout);
        modifPanel.add(modifPanelGauche);
        modifPanel.add(modifPanelDroit);
        
        //ajout des composants graphique du paramPanel
        paramPanelHaut.add(nbEtages);
        paramPanelHaut.add(listNbEtages);
        paramPanelHaut.add(nbPers);
        paramPanelHaut.add(nbPersText);
        paramPanelHaut.add(typeDePersonne);
        paramPanelHaut.add(typeDePersonneBox);
        paramPanelBas.add(nbAscenseur);
        paramPanelBas.add(nbAscenseurBox);
        paramPanelBas.add(capaciteAscLabel);
        paramPanelBas.add(capaciteAscText);
        paramPanel.add(paramPanelHaut);
        paramPanel.add(paramPanelBas);
        
        //ajout des composants graphique à la fenêtre principal sous forme d'onglets
        setLayout(new GridLayout(1,0));
        initMenuPlugin();
        comportement = (ComportementAbstrait)plugins[0];
        onglets.addTab("Controle",panelControle);
        onglets.addTab("Parametres",paramPanel);
        onglets.addTab("Modifications",modifPanel);
        onglets.setOpaque(true);
        add(onglets);
        pack();
        this.setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        //**************************creation des ecouteurs sur les boutons*************************
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                
                if(verifChampNbPersonne() && verifChampCapacite()){
                    miseAjourBoutonCreation();
                    //creation immeuble
                    int tmp = (Integer)listNbEtages.getSelectedItem();
                    immeuble = new Immeuble(tmp);  
                
                    //creation des personnes 
                    fabriquePersonne();
                
                    //creation des ascenseurs 
                    creationAscenseurs(nbAscenseurBox.getSelectedIndex());
                    
   
                    setEnCreation(false);
                    
                    menuPlugin2.setEnabled(true);
                    }
                 else return;
            }
            });
            
        stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
              
                PersGroupeFactory.setNbCree(0);
                resetStats();
                ArrayList<Ascenseur> listAscenseur = immeuble.getListeAscenseur();
                int nbAscenseur = listAscenseur.size();
                immeuble.dispose();
                for(int i = 0; i<nbAscenseur; i++){
                    Ascenseur asc = listAscenseur.get(i);
                    asc.dispose();
                    asc.getThread().stop();
                }
                miseAjourBoutonStop();
                    
                menuPlugin2.setEnabled(false);
            }
        });
        
         rafraichir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                boolean ascenseursArrete = verifAscenseursArrete();
                if(ascenseursArrete){
                    if(verifChampCapacite() && verifChampNbPersonne()){
                        PersGroupeFactory.setNbCree(0);
                        resetStats();
                        ArrayList<Etage> listEtage = immeuble.getListeEtage();
                        ArrayList<Ascenseur> listAscenseur = immeuble.getListeAscenseur();
                        for(int i = 0; i<listEtage.size(); i++){
                            listEtage.get(i).supprimerPersonneListe();
                        }
                        for(int i = 0; i<listAscenseur.size(); i++){
                            listAscenseur.get(i).supprimerPersonneListe();
                        }
                   
                        fabriquePersonne();
                        }
                     else{
                        return;
                         }
                    }
                        else{
                            afficheMessage("Vous devez attendre que les ascenseurs soient à l'arrêt pour rafraichir");
                        }
                }
                 
        });
        
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
        
        ouvrirFicher.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                ouvrirFichier();
            }     
        });
        
        
         stats.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
               miseAjourStats();
            }
        });
        
        chargerPlugins.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
               PluginLoader.reloadPlugins();
               buildPluginMenuEntries();
            }
        });
        
        sauvegarder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                immeuble.ecrireStatistiques();
            }
        });
        
        ajouterAscenseur.addActionListener(new ActionListener() {
            public  void actionPerformed(ActionEvent e){
                   if(verifChampCapacite()){
                        creationAscenseurs(0);
                   }
                   else return;
            };
        });
        
        ajouterPersonne.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                int nbPersAjouter = nbPersAjout.getSelectedIndex() + 2;
                int nbPersExistante=0;
                if(Immeuble.getListePersonnesCrees().size()<300){
	                for(int y =0;y<Immeuble.getListePersonnesCrees().size();y++)
	                {
	                    nbPersExistante +=  Immeuble.getListePersonnesCrees().get(y).getTaille();
	                }
	                int personneTotal = nbPersAjouter + nbPersExistante;
	                for(int i=0;i<nbPersAjouter;i++)
	                { 
	                   Personnes p =null;
	                   if(fabrique instanceof GroupeMonteImpl || fabrique instanceof GroupeAleatoireImpl ||fabrique instanceof PersGroupeFactory)
	                    {
	                         p =fabrique.getPersonne(nbPersExistante,personneTotal);
	                         p.ajouterDansListe(p.getEtageDepart());
	                         Immeuble.getListePersonnesCrees().add(p);
	                         i+=p.getTaille()-1;
	                         p.appuyerBoutonAscenseur();
	                    }
	                    else {
	                        p =fabrique.getPersonne(nbPersExistante,personneTotal);
	                        p.ajouterDansListe(p.getEtageDepart());
	                        Immeuble.getListePersonnesCrees().add(p);
	                        p.appuyerBoutonAscenseur();
	                    }
	                   nbPersExistante += p.getTaille();
	                    for(Etage et : Immeuble.getListeEtage()) 
	                    {
	                        et.updateJLabel();
	                        et.validate();
	                        et.repaint();
	                        et.updateJLabelBouton();
	                    }
	                }
                }else{
                	afficheMessage("Vous avez rentrer un nombre de personnes trop �lev�" +
                    ", le maximum est de 300 personnes");
            }
            }
            	
        });
    }//fin de initSimulateur
   /**
    * réinitilise les statistiques lorsque l'on appui sur le bouton stop
    */
    public void resetStats(){
        immeuble.getHumeurVisitor().reset();
        immeuble.getTpsVisitor().reset();
        if(onglets.getTabCount() == 4)
            onglets.removeTabAt(3);
        moyenneVisitor.setText("0");
        nbVisitedVisitor.setText("0");
        totalTimeVisitor.setText("0");
        nbContenteVisitor.setText("0");
        nbNormalVisitor.setText("0");
        nbEnerveVisitor.setText("0");
        
    }
    /**
    * fait apparaître l'onglet resultat dans l'interface avec mise à jour des statistiques de la simulation
    */
    public void miseAjourStats(){
        onglets.addTab("Resultats",resultat);
        moyenneVisitor.setText(String.valueOf(immeuble.getTpsVisitor().getMoyenne()));
        nbVisitedVisitor.setText(String.valueOf(immeuble.getTpsVisitor().getNbVisited()));
        totalTimeVisitor.setText(String.valueOf(immeuble.getTpsVisitor().getTotalTime()));
        nbContenteVisitor.setText(String.valueOf(immeuble.getHumeurVisitor().getNbContente()));
        nbNormalVisitor.setText(String.valueOf(immeuble.getHumeurVisitor().getNbNormal()));
        nbEnerveVisitor.setText(String.valueOf(immeuble.getHumeurVisitor().getNbEnerve()));
    }
    
    /**
    * permet d'afficher un message sous forme de boite de dialogue d'erreur
    */
    private void afficheMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "",
                JOptionPane.ERROR_MESSAGE);
    }
    
    /**
    * met à jour les boutons de l'interface lorsque l'on appui sur le bouton start
    */
    public void miseAjourBoutonCreation(){
        
        stop.setEnabled(true);
        ajouterPersonne.setEnabled(true);
        rafraichir.setEnabled(true);
        sauvegarder.setEnabled(true);
        ajouterAscenseur.setEnabled(true);
        ouvrirFicher.setEnabled(true);
        start.setEnabled(false);
        stats.setEnabled(true);
        tempsSimulation.setEnabled(true);
        
    }
    
    /**
    * met à jour les boutons de l'interface lorsque l'on appui sur le bouton stop
    */
    public void miseAjourBoutonStop(){
        stop.setEnabled(false);
        ajouterAscenseur.setEnabled(false);
        ajouterPersonne.setEnabled(false);
        rafraichir.setEnabled(false);
        start.setEnabled(true);
        stats.setEnabled(false);
        sauvegarder.setEnabled(false);
        ouvrirFicher.setEnabled(false);
        tempsSimulation.setEnabled(false);
    }
    
    /**
    * permet d'ouvrir un fichier en utilisant un JFileChooser(ouvrir le fichier de statistiques)
    */
    public void ouvrirFichier(){
        JFileChooser fileChooser = new JFileChooser(".");
        int valeur = fileChooser.showOpenDialog(null);
        if (valeur == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (Desktop.isDesktopSupported()){
                try {
                        Desktop desktop = Desktop.getDesktop();
                        desktop.open(selectedFile);
                        } catch (IOException ex) {
                            Logger.getLogger(Simulateur.class.getName()).log(Level.SEVERE, null, ex);
                        }
            }
        }
    }
    /**
    * fabrique des personnes ou des groupes de personnes
    */
    public void fabriquePersonne(){
            fabrique = PersonneMaker.getInstance().getPersonneFactory((String)typeDePersonneBox.getSelectedItem());
            for(int i=0;i<nbPersChoisi;i++){
                if(fabrique instanceof GroupeMonteImpl || fabrique instanceof GroupeAleatoireImpl ||fabrique instanceof PersGroupeFactory){
                    Personnes p =fabrique.getPersonne(i,nbPersChoisi);
                    p.ajouterDansListe(p.getEtageDepart());
                    Immeuble.getListePersonnesCrees().add(p);
                    i+=p.getTaille()-1;
                    p.appuyerBoutonAscenseur();     
                }
                else {
                     Personnes p =fabrique.getPersonne(i,nbPersChoisi);
                    p.ajouterDansListe(p.getEtageDepart());
                    Immeuble.getListePersonnesCrees().add(p);
                    p.appuyerBoutonAscenseur();    
                }
            }
    }
    /**
    * fabrique des ascenseurs
    */
    public void creationAscenseurs(int nb){
        for(int i = 0; i<=nb; i++ ){
            nbAsc = immeuble.getListeAscenseur().size();
            if(nbAsc <8){
                if(capaciteAscText.getText().equals("") ||  Integer.valueOf(capaciteAscText.getText())>15 
                        || Integer.valueOf(capaciteAscText.getText()) <= 0){
                    try {
                        ComportementAbstrait c = comportement.getClass().newInstance();
                    ascenseur =new Ascenseur(15,nbAsc, c);
                    ascenseur.setImmeuble(immeuble);
                    immeuble.ajouterAscenseur(ascenseur);
                    } catch (InstantiationException ex) {
                        Logger.getLogger(Simulateur.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(Simulateur.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                 }
                else{
                    if(Integer.valueOf(capaciteAscText.getText()) <= 15){
                        try {
                            ComportementAbstrait c = comportement.getClass().newInstance();
                        capacite = Integer.valueOf(capaciteAscText.getText());
                        ascenseur = new Ascenseur(capacite,nbAsc, c);
                        ascenseur.setImmeuble(immeuble);
                        immeuble.ajouterAscenseur(ascenseur);
                        } catch (InstantiationException ex) {
                            Logger.getLogger(Simulateur.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IllegalAccessException ex) {
                            Logger.getLogger(Simulateur.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
            else afficheMessage("Vous avez atteint le nombre maximal d'ascenseur");
        }
     }
    
    /**
    * permet de savoir si tous les ascenseurs de la simulation sont arrétés
    */
    public boolean verifAscenseursArrete(){
        boolean result =true;
        ArrayList<Ascenseur> listAscenseur = immeuble.getListeAscenseur();
        for(int i = 0; i<listAscenseur.size(); i++){
            if(listAscenseur.get(i).estArrete()){
                result = true;
            }
            else{
                result = false;
            }
        }
        return result;
    }
    
    /**
    * permet de savoir si le champ nbPersonnes est bien un nombre
    */
    public boolean verifChampNbPersonne(){
        if(nbPersText.getText().equals("")){
            nbPersChoisi = 1;
            return true;
        }
        else{
        try{
            nbPersChoisi = Integer.valueOf(nbPersText.getText());
            if(nbPersChoisi == 0){
                nbPersChoisi = 1;
            }
            if(nbPersChoisi > 300){
                afficheMessage("Vous avez rentrer un nombre de personnes trop �lev�" +
                        ", le maximum est de 300 personnes");
                return false;
            }
            }catch (NumberFormatException nf){
                afficheMessage("Veuillez entrer un nombre correcte de personnes");
                return false; 
            }
        }
        return true;
    }
    
    /**
    * permet de savoir si le champ capacite est bien un nombre
    */
    public boolean verifChampCapacite(){
        try{
            capacite = Integer.valueOf(capaciteAscText.getText());
           }catch (NumberFormatException nf){
                afficheMessage("Veuillez entrer une capacite d'ascenseur correcte");
                return false; 
            }
        return true;
    }
        
    

}//fin de la classe
