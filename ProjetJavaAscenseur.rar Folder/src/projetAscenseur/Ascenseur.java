package projetAscenseur;

import java.util.logging.Level;
import java.util.logging.Logger;
import projetAscenseur.personne.Personnes;
import projetAscenseur.strategy.ComportementAbstrait;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.System;
import java.net.URL;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;

import projetAscenseur.personne.concretePersonne.Groupe;
/**
 * classe representant l'ascenseur dans l'immeuble
 * @author Checconi maxime, Pilot guillaume et Canessa Marine
 */

public class Ascenseur extends JFrame implements Runnable {
    
    //***********************ATTRIBUTS**************************************
    private Dimension dimension;
    private int numAscenseur=0;
    private int etageCourant = 0;
    private JPanel ascenseurPanel = new JPanel();
    private ArrayList<Personnes> listePersonne;
    private ArrayList<Appel> listeAppels;
    private int nbPersonneMax;
    private int nbPersonneActuel;
    private int nbPlacesRestantes;
    private ComportementAbstrait comportement;
    private Thread threadAscenseur;
    private boolean portesOuvertes = false;
    private boolean monte = true;
    private boolean arrete = false;
    private Immeuble immeuble;
    private int temporisation;
    private boolean maintenance = false;


    // Rajout de la parite et de l'étage par defaut

    private int parite;             //todo // mode de fonctionnement pair si vaut 0 et impair si vaut 1
    private Etage EtageDefaut;      //todo


    
    //****************************CONSTRUCTEURS**********************************
    /**
     * constructeur pour les tests, independant du graphique
     */
    public Ascenseur(){
        super("Ascenseur");
        nbPersonneActuel = 0;
        nbPersonneMax = 15;
        listePersonne = new ArrayList<Personnes>();
        listeAppels = new ArrayList<Appel>();
    }
    
    /**
     * constructeur d'ascenseur
     * @param nbPersonneMax capacite maximale de l'ascenseur
     * @param numAscenseur le numero d'ascenseur
     * @param comportement le futur comportement
     */
    public Ascenseur(int nbPersonneMax, int numAscenseur, ComportementAbstrait comportement){
        super("Ascenseur");
        this.numAscenseur = numAscenseur;
        System.out.print(numAscenseur+" ");
        setTemporisation(500);
        nbPersonneActuel = 0;
        setComportement(comportement);
        this.nbPersonneMax = nbPersonneMax;
        initAscenseur();
        threadAscenseur = new Thread(this);
        threadAscenseur.start();
    }
    
    public void paint(Graphics g)
    {
    	try {
    		super.paint(g);
    	}
    	catch (ArrayIndexOutOfBoundsException e)
    	{
            e.printStackTrace();
    	}
    }
    
    //****************************GETTERS AND SETTERS*********************************
    
    public int getNbPlacesRestantes() { return nbPlacesRestantes;    }

    public void setNbPlacesRestantes(int nbPlacesRestantes) { this.nbPlacesRestantes = nbPlacesRestantes;}
    
    public ArrayList<Personnes> getListePersonne(){return listePersonne;}
    
    public int getNbPersonne(){return listePersonne.size();}
    
    public int getEtageCourant(){return etageCourant;}
    
    public int getNbPersActuel(){return nbPersonneActuel;}
    
    public int getNbPersonneMax(){return nbPersonneMax;}
    
    public void setEtageCourant(int nb){etageCourant=nb;}
    
    public void setNbPersActuel(int nb){nbPersonneActuel=nb;}
    
    public Thread getThread(){return this.threadAscenseur;}
    
    public int getNumAscenseur(){return numAscenseur;}
    
    public JPanel getJPanel(){return ascenseurPanel;}
        
    public boolean isPortesOuvertes() {  return portesOuvertes; }

    public void setPortesOuvertes(boolean portesOuvertes) {   this.portesOuvertes = portesOuvertes;  }
    
    public boolean isMonte() {   return monte; }

    public void setMonte(boolean monte) {  this.monte = monte;   }
    
    public ComportementAbstrait getComportement() {   return comportement; }
    public void setComportement(ComportementAbstrait a) {   comportement=a; comportement.setAscenseur(this); }
    
    public boolean isArrete() {  return arrete;  }    
        
    public void setArrete(boolean arrete) {
        this.arrete = arrete;
    }
    
    public boolean estArrete(){ return arrete;}
    
    public void setImmeuble(Immeuble immeuble) {this.immeuble = immeuble;}

    public Immeuble getImmeuble() {return immeuble;} //todo
    
    public int getTemporisation(){ return temporisation;}
    
    public  void setTemporisation(int temporisation){ this.temporisation = temporisation;}

    public void setMaintenance(boolean bool){
        this.maintenance = bool;
    }

    /**
     * todo getMaintenance
     */
    public boolean getMaintenance(){
        return this.maintenance;
    }
    
    //********************************FONCTIONS***********************************//
        
  
    /**
     * initialise l'ascenseur
     */
    public void initAscenseur(){
    	
    	listePersonne = new ArrayList<Personnes>();
        dimension = new Dimension(100, Math.round(Immeuble.getTailleEtage()));
        ascenseurPanel.setBackground(Color.BLACK);
        ascenseurPanel.setPreferredSize(dimension);
        this.setBackground(new Color(0,255,255));
        this.setLocation((320+(numAscenseur*110)),Math.round(Immeuble.getDimension().height)-Math.round(Immeuble.getTailleEtage()));
        this.add(ascenseurPanel );
        //enlever les bordures de la fenetre
        setUndecorated(true);
        this.setResizable(false);
        pack();
        setVisible(true);
    }
    
    /**
     * methode calculant le nombre de places restantes dans l'ascenseur
     * @return le nombre de places restantes
     */
    public int calculerPlacesRestantes()
    {
        int result = this.getNbPersonneMax() - this.getNbPersActuel();
        setNbPlacesRestantes(result);
        return result;
    }
    
    /**
     * methode qui calcule si l'ascenseur est plein
     * @return vrai si l'ascenseur est plein
     */
    public boolean isPlein(){
        boolean result=true;
        if(calculerPlacesRestantes()==0){result = true;}
        else{result = false;}
        return result;
    }
    
    /**
     * methode principale du thread, qui s'execute quand le thread est lancé
     */
    public void run(){
        while(true){
            //tant qu'il y aura des personnes en mouvement 
            while(Immeuble.getListeAppel().size() > 0 || nbPersonneActuel > 0){
                    comportement.seDeplacer();
                try {
                    validate(); repaint();
                    for(Etage e : immeuble.getListeEtage()) 
                    {
                            e.updateJLabel();
                            e.validate();
                            e.repaint();
                            e.updateJLabelBouton();
                    }
                    Thread.sleep(temporisation);

                } catch (InterruptedException e) {  
                    e.printStackTrace();  }
                    
            }
            fermerPorte();
            setArrete(true);
        }
    }

    /**
     * permet de savoir si une personne veut descendre de l'ascenseur a l'etage courant
     * @return la personne qui veut dessendre
     */
    public Personnes quelqunVeutDessendreDeAsc()
    {
        Personnes result = null;
        for(int i =0; i<this.getListePersonne().size();i++)
        {
            Personnes pCourante = this.getListePersonne().get(i);
            if(pCourante.getEtageArrive().getNumEtage() == this.getEtageCourant())
            {
                result = pCourante;
                break;
            } 
        }
        return result;
    }
    
    /**
     * permet de savoir si une personne veut monter dans l'ascenseur si celui-ci n'est pas plein
     * @return la personne qui veut monter
     */
    public Personnes quelqunVeutMonterDansAsc()
    {
        Etage et = Etage.chercherEtageDsListe(this.getEtageCourant());
        Personnes result = null;
        if(!this.isPlein()){ 
            for(int i =0; i<et.getListePersonne().size();i++)
            {
                Personnes pCourante = et.getListePersonne().get(i);      
                if(pCourante.getEtageDepart().getNumEtage() == this.getEtageCourant() 
                && getComportement().accepterPersonne(pCourante))
                {
                    result = pCourante;
                    break;
                } 
            }
        }
        return result;
    }
    
   
    /**
     * fait monter l'ascenseur
     */
    public void monter()
    {  	
        if(!isPortesOuvertes())
        {
            Point a = new Point(0, -(int)Immeuble.getTailleEtage());
            Point b = this.getLocation();
            Point c = new Point((int)b.getX(), (int)(a.getY() + b.getY()));
            this.setLocation(c);
            int value = this.getEtageCourant()+1;
            this.setEtageCourant(value);
            //todo change le libel du manager
            Manager mana = this.immeuble.getManager();
            mana.setValuesAsc(this.numAscenseur, this.etageCourant, 1500);

            // enregistrement du deplacement
            Date maDate = new Date();
            mana.saveStatistique(maDate, new Integer(this.numAscenseur), new Integer(1));

        }
    }

/**
 * fait dessendre l'ascenseur
 */
    public void descendre()
    {
        if(!isPortesOuvertes())
        {
            Point a = new Point(0, (int)Immeuble.getTailleEtage());
            Point b = this.getLocation();
            Point c = new Point((int)b.getX(), (int)(a.getY() + b.getY()));
            this.setLocation(c);
            int value = this.getEtageCourant()-1;
            this.setEtageCourant(value);
            Manager mana = this.immeuble.getManager();
            mana.setValuesAsc(this.numAscenseur, this.etageCourant, 750);
            
            // enregistrement du deplacement
            Date maDate = new Date();
            mana.saveStatistique(maDate, new Integer(this.numAscenseur), new Integer(-1));
        }
    }
    
    /**
     * ouvre les portes de l'ascenseur et desactive l'appel partant de cet etage
     */
    public void ouvrirPorte()
    {
        this.getJPanel().setBackground(Color.GRAY);
        /*URL chemin =  getClass().getResource("/ressources/dingdong.wav");
        String s=chemin.toString();
        String cheminValide = s.substring(10); 
        Sound player = new Sound(cheminValide);
	InputStream stream = new ByteArrayInputStream(player.getSamples());
	player.play(stream);*/
        setPortesOuvertes(true);
        if(Immeuble.getListeAppel().size() != 0)
        {
            Etage et = Etage.chercherEtageDsListe(this.getEtageCourant());
            //Appel ap = chercherAppelDsListe(this.getEtageCourant());
            Immeuble.supprimerAppel(et);

        }

        try {
            Thread.sleep(temporisation);
        } catch (InterruptedException e) {  
            e.printStackTrace();    }
    }

    /**
     * ferme les portes de l'ascenseur
     */
    public void fermerPorte()
    {
        setPortesOuvertes(false);
        
        try {
            Thread.sleep(temporisation);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        //si ya des gens qui ont pas pu monter , rappuie sur le bouton par la premiere personne!!!
        Etage et = Etage.chercherEtageDsListe(this.getEtageCourant());
        Personnes p = et.personneMobileDsEtage();
        if(p != null)
        {
            p.appuyerBoutonAscenseur();
        }
        this.getJPanel().setBackground(Color.BLACK);
    }
    

    /**
     * indique aux personnes de l'etage qu'il est arrivé Ding Dong
     * @param p la personne a reveiller
     */
    public void reveillerPersonne(Personnes p){
        p.chercheASeDeplacer(this);
    }
   
    /**
     * indique si une personne donné est dans l'ascenseur
     * @param p la personne cherchee
     * @return vrai ou faux
     */
   public boolean chercherPersonneDansListe(Personnes p)
   {
       boolean pResult = false;
       for(int i=0;i<this.getListePersonne().size();i++)
       {
           if(this.getListePersonne().get(i) == p)
           {
               pResult = true;
               break;
           }
       }
       return pResult;
   }

   /**
    * ajoute une personne dans l'ascenseur
    * @param P la personne a ajouté
    */
     public synchronized void ajouterPersonneAscenseur(Personnes P){
        //Pour le Manager
        Appel ap = new Appel(P.getEtageDepart(),P.getEtageArrive(),P.veutMonter());
        listeAppels.add(ap);
        
        //Pour le simulateur
    	listePersonne.add(P);
        int nb = P.getTaille();
        this.setNbPersActuel(this.getNbPersActuel()+nb);
        calculerPlacesRestantes();
        if(P instanceof Groupe){
            Groupe g = (Groupe)P;
            g.afficherGroupe(this);
        }
        else this.getJPanel().add(P);
    }
    
    /**
     * supprime une personne de l'ascenseur
     * @param P la personne qui dessend
     */
    public synchronized void supprimerPersonneAscenseur(Personnes P){
        if(listePersonne.contains(P)){
            listePersonne.remove(P);
            int nb = P.getTaille();
            this.setNbPersActuel(this.getNbPersActuel()-nb);
            calculerPlacesRestantes();
             if(P instanceof Groupe){
                Groupe g = (Groupe)P;
                g.effacerGroupe(this);
            }
             else this.getJPanel().remove(P);
        }
    }


    /**
     * supprime les personnes presentes dans l'ascenseur lors du rafraichissement
     */
     public void supprimerPersonneListe(){
        int debut = listePersonne.size() - 1;
        for(int i = debut; i>=0;i--)
            supprimerPersonneAscenseur(listePersonne.get(i));
    }


	
}//fin de la classe
