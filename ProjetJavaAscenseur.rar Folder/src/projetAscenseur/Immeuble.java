package projetAscenseur;

import java.util.logging.Level;
import java.util.logging.Logger;
import projetAscenseur.personne.Personnes;
import projetAscenseur.Appel;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import projetAscenseur.visitor.concreteVisitor.HumeurVisitor;
import projetAscenseur.visitor.concreteVisitor.TpsVisitor;
import java.util.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;

/**
 * classe immeuble composee d'etage
 * @author Checconi maxime, Pilot guillaume et Canessa Marine
 */
public class Immeuble extends JFrame implements Runnable{
    
    //**************************ATTRIBUTS*****************************************
    private static Dimension dimension; //taille immeuble
    private final Dimension dimensionDefaut = new Dimension(300,600);
    private static int NBEtage;
    protected Boolean estCree = false;
    private JPanel immeublePanel = new JPanel();
    private static float tailleEtage;
    private static ArrayList<Etage> listeEtage;
    private static ArrayList<Ascenseur> listeAscenseur;
    private static ArrayList<Etage> listeAppel;
    private static ArrayList<Integer> listeDestinationAscenseur;
    private static ArrayList<Integer> listeReplacementJour;
    private static ArrayList<Integer> listeReplacementNuit;
    
    //Rajouter pour nouveau comportement
    private static ArrayList<Appel> listeAppel_Dest;
    private static ArrayList<Appel> listeAppel_Attente;
    //private static ArrayList<Etage> retourAppel_Dest;
    //Fin du rajout

    private static ArrayList<Personnes> listePersonneArrivee;
    private static ArrayList<Personnes> listePersonnesCrees;
    private Thread thread;
    private TpsVisitor tpsVisitor = new TpsVisitor();
    private HumeurVisitor humeurVisitor = new HumeurVisitor();
    private Manager manager;
    
    //**************************CONSTRUCTEURS**********************************
    public Immeuble(){}
    
    /**
     * constructeur pour les tests
     * @param NBEtages le nombre d'etages dans l'immeuble
     */
    public Immeuble(int NBEtages,String test){
    	super("Immeuble");
    	NBEtage = NBEtages;
        listeAppel = new ArrayList();                       // Rajouter pour nouveau comportement
        listeEtage = new ArrayList<Etage>();
        listeAscenseur = new ArrayList<Ascenseur>();
        listePersonneArrivee =  new ArrayList<Personnes>();
        listePersonnesCrees =  new ArrayList<Personnes>();
        listeAppel_Dest = new ArrayList<Appel>();
        listeAppel_Attente = new ArrayList<Appel>();
        listeDestinationAscenseur = new ArrayList<Integer>();
        tailleEtage = this.dimensionDefaut.height/getNBEtage();
        estCree = true;
    }
    
    /**
     * constructeur utilisé prenant un etage et un nombre de personnes
     * @param NBEtages le nombre d'etages dans l'immeuble
     */
    public Immeuble(int NBEtages, Manager manager){
    	super("Immeuble");
    	NBEtage = NBEtages;
        this.manager = manager;
        initImmeuble();
        estCree = true;
        
    }

    synchronized public Manager getManager(){
        return manager;
    }
    
    //*************************GETTERS AND SETTERS******************************************
    public static int getNBEtage(){return NBEtage;}
    
    public static float getTailleEtage(){return tailleEtage;}
    
    public static Dimension getDimension(){return dimension;}
    
    public static ArrayList<Etage> getListeEtage(){return listeEtage;}
    
    public static ArrayList<Ascenseur> getListeAscenseur(){return listeAscenseur;}
    
    public JPanel getPanel(){return immeublePanel;}
    
    public static ArrayList<Etage> getListeAppel() {  return listeAppel;   }
    
    public static ArrayList<Personnes> getListePersonneArrivee() { return listePersonneArrivee;  }

    public static void setListePersonneArrivee(ArrayList<Personnes> listePersonneArrivee) {Immeuble.listePersonneArrivee = listePersonneArrivee;  }
   

    public static ArrayList<Personnes> getListePersonnesCrees() {return listePersonnesCrees;}

    public static void setListePersonnesCrees(ArrayList<Personnes> listePersonnesCrees) {Immeuble.listePersonnesCrees = listePersonnesCrees;} 

    public Thread getThread() {return thread;}

    public void setThread(Thread thread) {	this.thread = thread;}
        public HumeurVisitor getHumeurVisitor() {  return humeurVisitor;   }

    public void setHumeurVisitor(HumeurVisitor humeurVisitor) {   this.humeurVisitor = humeurVisitor;   }

    public TpsVisitor getTpsVisitor() {    return tpsVisitor;  }

    public void setTpsVisitor(TpsVisitor tpsVisitor) {    this.tpsVisitor = tpsVisitor;   }
    
    public boolean getEstCree(){ return estCree;}

    public static ArrayList<Appel> getListeAppels_Dest() {  return listeAppel_Dest;   }

    public static ArrayList<Appel> getListeAppel_Attente() {
        return listeAppel_Attente;
    }

    public static void setListeAppel_Attente(ArrayList<Appel> listeAppel_Attente) {
        Immeuble.listeAppel_Attente = listeAppel_Attente;
    }



    //*******************************Comportement Optimisation**************************************
    // Rajouter pour nouveau comportement


    /*public static ArrayList<Appel> getListeAppel_Dest() {
        return listeAppel_Dest;
    }

    public static ArrayList<Etage> getListeAppel_V2_src() {

        int i =0;


        while(!listeAppel_Dest.isEmpty())
        {
            retourAppel_Dest.add((listeAppel_Dest.get(i)).getDest());
            i++;

        }


        return retourAppel_Dest;
    }

    public static int getListeAppel_V2_monte(Etage src)
    {



    }*/

    public static Etage AscLePlusProcheTemporellement(Ascenseur asc)
    {
        ArrayList<Ascenseur> listeAsc = getListeAscenseur();
    	//ArrayList<Etage> etageAppelant = getListeAppel();
    	int val = Immeuble.NBEtage; //le nombre max d'etage
    	Ascenseur ascSelect = asc;
    	int valTest = Immeuble.NBEtage;
    	Etage etageRenvoye = null;
    	//pour chaque ascenseur on va tester si c lui le plus proche
    	for(int i = 0; i< listeAsc.size();i++){                                 //On parcours tout les ascenseurs
            if(listeAsc.get(i).getListePersonne().size()==0){                   //On test si ils sont vide
                for(int j =0; j<listeAppel.size();j++){
                    //A traiter a partir d'ici todo
                    
                    if(estInterrompable(asc, listeAppel_Dest.get(j).getDest()))     //Test pour savoir si l'ascenseur à le droit de s'arreter
                    {
                        //Test à faire pour savoir si la personne va dans le meme sens que l'ascenseur

                        valTest = Math.abs(listeAppel_Dest.get(j).getDest().getNumEtage() - listeAsc.get(i).getEtageCourant());

                        if(valTest<val){//normalement avec cette condition un seul ascenseur prend la main pas de conflit
                            ascSelect = listeAsc.get(i);
                            etageRenvoye = listeAppel.get(j);
                            val = valTest;
                        }

                    }
                    // Sinon rien car ce n'est pas a lui de s'arreter donc pas de else

                      
                    //Fin traitement todo
                }
            }
    	}
    	if(ascSelect == asc){
    		return etageRenvoye;
    	}
    	return null;
    }



    // Fin du rajout
    //*******************************Fin Comportement Optimisation**************************************





    //*******************************METHODES**************************************
    
    /**
     * methode principale le thread qui met a jour la simulation
     */
        public void run(){
            int value = 1;  
            while(true){
                //!!!!!!!!!!!!!!!!!!!!!personnes !!!!!!!!!!!!!!!!!!!!!
                if(!Simulateur.isEnCreation())
                {
                    for(int i=0; i<listePersonnesCrees.size();i++)
                    {
                       Personnes pers = listePersonnesCrees.get(i);
                       pers.commencerAttente();
                       long test = System.currentTimeMillis()-pers.getDebut();
                       pers.setTpsArrive(System.currentTimeMillis()-pers.getDebut());

                       if(listePersonneArrivee.contains(pers))
                       {
                           long time = System.currentTimeMillis()-pers.getDebut();
                           pers.setTpsArrive(time);
                           //met a jout les visitor
                           if(listePersonneArrivee.size() >= value)
                           {
                                Personnes l = listePersonneArrivee.get(value-1);
                                if(l!=null){
                                    l.accept(tpsVisitor);
                                    l.accept(humeurVisitor);
                                }
                                //System.out.println(tpsVisitor.result());
                                //System.out.println(humeurVisitor.result());
                                value++;
                            }  
                       }
                    }
                }
            }
        }
        
        
        /**
     * initialise l'immeuble et cree les etages
     */
    public void initImmeuble(){
        listeAppel = new ArrayList();
        listeAscenseur = new ArrayList<Ascenseur>();
        listePersonneArrivee = new ArrayList<Personnes>(); 
        listePersonnesCrees =  new ArrayList<Personnes>();
        listeAppel_Dest = new ArrayList<Appel>();
        listeAppel_Attente = new ArrayList<Appel>();
        listeDestinationAscenseur = new ArrayList<Integer>();
        thread = new Thread(this);
        thread.start();
        tailleEtage = this.dimensionDefaut.height/getNBEtage();
        float reste = dimensionDefaut.height - tailleEtage*getNBEtage();
        if(reste >0)
        {
               dimension = new Dimension(this.dimensionDefaut.width,dimensionDefaut.height-(int)reste); 
        }
        else
        {
            dimension = dimensionDefaut;
        }
        this.setBackground(new Color(0,255,255));
        immeublePanel.setBackground(new Color(255,0,0));
        immeublePanel.setLayout(new GridLayout(getNBEtage(),1));
        immeublePanel.setPreferredSize(dimension);

        //on cree les etages
        listeEtage = new ArrayList<Etage>();
        for(int i = NBEtage-1; i>=0;i--)
        {
            int j = i % 2;
            Etage ei = new Etage(dimension.width,tailleEtage,i,new Color(200-j*10,200-j*10,200-j*10));
            listeEtage.add(ei);
            immeublePanel.add(ei);
        }
        
        this.add(immeublePanel);
        //enlever les bordures de la fenetre
        setUndecorated(true);
        this.setResizable(false);
        setSize(dimension);
        setVisible(true);
        pack();
    }
    

    /**
     * ajoute un appel a la liste d'appel
     * @param etage l'etage appelant
     */
    public static void ajouterAppel(Etage etageDepart,Etage etageArrivee,boolean monte){

        //On passe en mode appel pour le manager
        Appel ap = new Appel(etageDepart,etageArrivee,monte,null);
        //System.out.println("Appel de :"+etageDepart.getNumEtage());
        //listeAppel_Dest.add(ap);
                //On ajoute l'appel une seule fois - on vérifie donc si il n'existe pas d'appel à cet étage
        if(containsAppelAttente(etageDepart,etageArrivee,monte,null) == null)
           listeAppel_Attente.add(ap);
        //System.out.println("Nombre d'appels " + listeAppel_Attente.size());
        //Pour le simulateur
    	listeAppel.add(etageDepart);
        etageDepart.setBoutonAscenseur(true);
        etageDepart.updateJLabelBouton();
    }
    
    /**
     * supprime un appel de la liste
     * @param etage etage a supprimer
     */
    public static void supprimerAppel(Etage etage){
        if(listeAppel.contains(etage))
        {
           listeAppel.remove(etage);
            etage.setBoutonAscenseur(false);
            
            etage.updateJLabelBouton();
        }
        //On passe en mode appel pour le manager
        /*Appel ap = new Appel(etageDepart,etageArrivee,monte);
        if(listeAppel_Dest.contains(ap)){
            listeAppel_Dest.remove(ap);
        }*/
    }

    /**
     * ajoute un ascennseur dans l'immeuble
     * @param asc l'ascenseur a ajouter
     */
     synchronized public static void ajouterAscenseur(Ascenseur asc){
    	listeAscenseur.add(asc);
        //Ajout de la destination null pour l'ascenseur ajouté
        listeDestinationAscenseur.add(asc.getNumAscenseur(), -1);
    }


     public boolean allInMaintenance(){
         int size = listeAscenseur.size();
         for(int i=0; i<size; i++){
             if(!listeAscenseur.get(i).getMaintenance())
                 return false;
         }
         return true;
     }

      public boolean allNotInMaintenance(){
         int size = listeAscenseur.size();
         for(int i=0; i<size; i++){
             if(listeAscenseur.get(i).getMaintenance())
                 return false;
         }
         return true;
     }
     /**
      * supprime un ascenseur 
      * @param asc l'ascenseur a supprimer
      */
     public static void SupprimerAscenseur(Ascenseur asc){
    	listeAscenseur.remove(asc);
    }
    
     /**
      * ajoute les personnes dans la listre des personnes arrivees a destination
      * @param pers le personne a ajouter
      */
    public static void addPersArrivee(Personnes pers){
    	listePersonneArrivee.add(pers);
    }
     
    /**
     * permet de savoir si un ascenseur est le plus proche d'un des etages appelants
     * @param asc l'ascenseur concernee
     * @return l'etage duquel il est le plus pres ou null si il est le plus eloigne
     */
    public static Etage ascenseurLePlusProche(Ascenseur asc)
    {
    	ArrayList<Ascenseur> listeAsc = getListeAscenseur();
    	//ArrayList<Etage> etageAppelant = getListeAppel();
    	int val = Immeuble.NBEtage; //le nombre max d'etage
    	Ascenseur ascSelect = asc;
    	int valTest = Immeuble.NBEtage;
    	Etage etageRenvoye = null; 
    	//pour chaque ascenseur on va tester si c lui le plus proche
    	for(int i = 0; i< listeAsc.size();i++){
            if(listeAsc.get(i).getListePersonne().size()==0){	
                for(int j =0; j<listeAppel.size();j++){
                    valTest = Math.abs(listeAppel.get(j).getNumEtage() - listeAsc.get(i).getEtageCourant());
                    if(valTest<val){//normalement avec cette condition un seul ascenseur prend la main pas de conflit
                        ascSelect = listeAsc.get(i);
                        etageRenvoye = listeAppel.get(j);
                        val = valTest;
                    }
                }
            }
    	}
    	if(ascSelect == asc){
    		return etageRenvoye;
    	}
    	return null;
    }

    public Appel getAppelDest(Etage etageDepart,Etage etageArrivee,boolean monte,Ascenseur asc,int indiceDebut){

        Appel appel = new Appel(etageDepart, etageArrivee, monte,asc);

         for (int j = getListeAppels_Dest().size()-1;j>=0; j--){
                        //System.out.println("On cherche un objet dans AppelDest");
                        if(getListeAppels_Dest().get(j).equals(appel))
                            return getListeAppels_Dest().get(j);

           }
        return null;
    }

    public static Appel containsAppelAttente(Etage etageDepart,Etage etageArrivee,boolean monte,Ascenseur asc){

        Appel appel = new Appel(etageDepart, etageArrivee, monte,asc);

         for (int j = getListeAppel_Attente().size()-1;j>=0; j--){
                        //System.out.println("On cherche un objet dans AppelAttente");
                        if(getListeAppel_Attente().get(j).getSource() == etageDepart && getListeAppel_Attente().get(j).getAsc() == null)
                            return getListeAppel_Attente().get(j);

           }
        return null;
    }
    //********************************* Methodes pour la gestion d'économie de temps ************************
    synchronized public static Etage InterogeImmeublePourDeplacementTemporel(Ascenseur asc){

        
        if(listeAppel_Attente.isEmpty()){
            //On ve raplacer les différents ascenseurs
            return replacementAscenseur(asc);
        }

        //Etage d'appel est-il supérieur ?


        ArrayList<Ascenseur> listeAsc = getListeAscenseur();
    	//ArrayList<Etage> etageAppelant = getListeAppel();
    	Ascenseur ascenseurQuiMonte = null;
        Ascenseur ascenseurQuiDescend = null;
        //Etage EtageLePlusHaut = new Etage(Immeuble.NBEtage);

        //On récupere l'appel le plus haut - si = -1 => il n'y a pas d'appel donc personne veut bouger
        Appel dernierAppel ;
        //System.out.println("Etage le plus haut : " + appelLePlushaut.getSource().getNumEtage());
    	Etage etageRenvoye = null;


    	//pour chaque ascenseur on va tester si c lui le plus proche
    	for(int i = 0; i< listeAppel_Attente.size() && etageRenvoye == null ;i++){
            dernierAppel = listeAppel_Attente.get(i);
            //On regarde l'appel le plus haut
            if(dernierAppel.getSource().getNumEtage() != -1){
                //Si l'ascenseur courant est vide et qu'il n'a pas de destination
                if(asc.getListePersonne().isEmpty() && listeDestinationAscenseur.get(asc.getNumAscenseur()) == -1){

                    //On récupere les ascenseurs en cours de déplacement
                    ascenseurQuiMonte = unDesAscenseurMonte();
                    ascenseurQuiDescend = unDesAscenseurdescend();

                    //Si c'est un appel pour monter
                    if(dernierAppel.getMonte() ){
                        //Si un ascenseur est déja sur la route
                        if( ascenseurQuiMonte!= null && ascenseurQuiMonte.getEtageCourant() <= dernierAppel.getSource().getNumEtage()){
                            //On ne fait rien

                        }
                        //On exclue les différents ascenseurs
                        else if(jeSuisLePlusProcheVide(asc,dernierAppel)){
                            etageRenvoye = dernierAppel.getSource();
                        }
                    }else{//L'appel le plus haut veut descendre
                        //Si un ascenseur est déja entrain de descendre et est au dessus de l'étage appelant
                        if( ascenseurQuiDescend != null && ascenseurQuiDescend.getEtageCourant() >= dernierAppel.getSource().getNumEtage() && ascenseurQuiMonte != null && ascenseurQuiMonte.getEtageCourant() == Immeuble.getNBEtage() ){
                            //on ne fait rien
                        }
                        //On exclue les différents ascenseurs
                        else if(jeSuisLePlusProcheVide(asc,dernierAppel)){
                            etageRenvoye = dernierAppel.getSource();
                        }
                    }

                 //Sinon l'ascenseur est vide mais va déja quelquepart
                }else if(asc.getListePersonne().isEmpty() && listeDestinationAscenseur.get(asc.getNumAscenseur()) != -1){
                    int numEtageRenvoye = listeDestinationAscenseur.get(asc.getNumAscenseur());
                    etageRenvoye = new Etage(numEtageRenvoye);


                }else{
                    //Si on est arrivé à l'étage du premier appelant ou d'un second
                    if(listeDestinationAscenseur.get(asc.getNumAscenseur()) == asc.getEtageCourant()){
                        //Si l'ascenseur est vide et a nulle part ou aller après que sa dernière personne soit sortie
                        if(1 == asc.getListePersonne().size()){
                            listeDestinationAscenseur.remove(asc.getNumAscenseur());
                            listeDestinationAscenseur.add(asc.getNumAscenseur(), -1);
                        }
                        //Sinon on va à la destination de la premiere autre personne
                        else{
                            listeDestinationAscenseur.remove(asc.getNumAscenseur());
                            listeDestinationAscenseur.add(asc.getNumAscenseur(), asc.getListePersonne().get(1).getEtageArrive().getNumEtage());
                        }
                    }
                }

           }
        }
        if(asc.getMaintenance())
            return null;
    	return etageRenvoye;
    }


    
    //********************************* Methodes pour la gestion d'économie d'énergie ************************
    /**
     * permet de savoir si un ascenseur est le plus proche d'un des etages appelants
     * @param asc l'ascenseur concernee
     * @return l'etage duquel il est le plus pres ou null si il est le plus eloigne
     */
        synchronized public static Etage InterogeImmeublePourDeplacement(Ascenseur asc){

        
        /*if(listeAppel_Attente.isEmpty()){
            //On ve raplacer les différents ascenseurs
            Etage eta =  new Etage(2);
            return eta;
        }*/

        //Etage d'appel est-il supérieur ?


        ArrayList<Ascenseur> listeAsc = getListeAscenseur();
    	//ArrayList<Etage> etageAppelant = getListeAppel();
    	Ascenseur ascenseurQuiMonte = null;
        Ascenseur ascenseurQuiDescend = null;
        //Etage EtageLePlusHaut = new Etage(Immeuble.NBEtage);

        //On récupere l'appel le plus haut - si = -1 => il n'y a pas d'appel donc personne veut bouger
        Appel appelLePlushaut = AppelLePlusHaut();
        //System.out.println("Etage le plus haut : " + appelLePlushaut.getSource().getNumEtage());
    	Etage etageRenvoye = null;

        
    	//pour chaque ascenseur on va tester si c lui le plus proche
    	//for(int i = 0; i< listeAsc.size();i++){

        //On regarde l'appel le plus haut 
        if(appelLePlushaut.getSource().getNumEtage() != -1){
            //Si l'ascenseur courant est vide et qu'il n'a pas de destination
            if(asc.getListePersonne()!= null && asc.getListePersonne().isEmpty() &&
                     !listeDestinationAscenseur.isEmpty()
                    && listeDestinationAscenseur.get(asc.getNumAscenseur()) == -1){

                //On récupere les ascenseurs en cours de déplacement
                ascenseurQuiMonte = unDesAscenseurMonte();
                ascenseurQuiDescend = unDesAscenseurdescend();
                
                //Si c'est un appel pour monter
                if(appelLePlushaut.getMonte() ){
                    //Si un ascenseur est déja sur la route
                    if( ascenseurQuiMonte!= null && ascenseurQuiMonte.getEtageCourant() <= appelLePlushaut.getSource().getNumEtage()){
                        //On ne fait rien
                        
                    }
                    //On exclue les différents ascenseurs
                    else if(jeSuisLePlusProcheVide(asc,appelLePlushaut)){
                        etageRenvoye = appelLePlushaut.getSource();
                    }
                }else{//L'appel le plus haut veut descendre
                    //Si un ascenseur est déja entrain de descendre et est au dessus de l'étage appelant
                    if( ascenseurQuiDescend != null && ascenseurQuiDescend.getEtageCourant() >= appelLePlushaut.getSource().getNumEtage() && ascenseurQuiMonte != null && ascenseurQuiMonte.getEtageCourant() == Immeuble.getNBEtage() ){
                        //on ne fait rien
                    }
                    //On exclue les différents ascenseurs
                    else if(jeSuisLePlusProcheVide(asc,appelLePlushaut)){
                        etageRenvoye = appelLePlushaut.getSource();
                    }
                }

             //Sinon l'ascenseur est vide mais va déja quelquepart
            }else if(asc.getListePersonne().isEmpty() && listeDestinationAscenseur.get(asc.getNumAscenseur()) != -1){
                int numEtageRenvoye = listeDestinationAscenseur.get(asc.getNumAscenseur());
                etageRenvoye = new Etage(numEtageRenvoye);


            }else{
                //Si on est arrivé à l'étage du premier appelant ou d'un second
                if(listeDestinationAscenseur.get(asc.getNumAscenseur()) == asc.getEtageCourant()){
                    //Si l'ascenseur est vide et a nulle part ou aller après que sa dernière personne soit sortie
                    if(1 == asc.getListePersonne().size()){
                        listeDestinationAscenseur.remove(asc.getNumAscenseur());
                        listeDestinationAscenseur.add(asc.getNumAscenseur(), -1);
                    }
                    //Sinon on va à la destination de la premiere autre personne
                    else{
                        listeDestinationAscenseur.remove(asc.getNumAscenseur());
                        listeDestinationAscenseur.add(asc.getNumAscenseur(), asc.getListePersonne().get(1).getEtageArrive().getNumEtage());
                    }
                }
            }

       }
        //System.out.println("Ascenseur " + asc.getNumAscenseur() + "Plus proche ? " + jeSuisLePlusProcheVide(asc,appelLePlushaut) + " de " + appelLePlushaut.getSource().getNumEtage());

        //Si l'ascensceur est en maintenance on ne bouge pas
        if(asc.getMaintenance())
            return null;
    	return etageRenvoye;
    }

    private static boolean isNight(){
        long dateactuelle = System.currentTimeMillis();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        Date d = new Date();  // convert string to date
        
        if(d.getHours() > 19 || d.getHours() < 7)
            return true;
        else
            return false;

    }
    public static Etage replacementAscenseur(Ascenseur asc){
        int numAscenseur = asc.getNumAscenseur();
        int poids = 0;
        Etage eta = null;
        //Etage etageRenvoye = null;

        
            
        
        EnregistrementConf conf = new EnregistrementConf();

        //Si c'est la nuit et que le tableau n'est pas encore initialisé
        if(isNight() && listeReplacementNuit == null){
            listeReplacementNuit = new ArrayList<Integer>();

            HashMap listePoids = new HashMap();
            HashMap configJ =  conf.getConfigNuitWeekEnd();
            float rdc = (  (1 / ((Integer)Immeuble.getNBEtage()).floatValue())   ) *100;
            float poidsRDC =  (((Integer)configJ.get(new String("RdC"))).floatValue())/rdc ;
            listePoids.put(4, poidsRDC);
            //System.out.println(rdc);
            float vingtPremiersEtages = (20 /  ((Integer)Immeuble.getNBEtage()).floatValue() ) *100;
            float poidsVingtPremiersEtages = (((Integer)configJ.get(new String("vingtPremiersEtages"))).floatValue()) /vingtPremiersEtages ;
            listePoids.put(14, poidsVingtPremiersEtages);

            float cinqDerniersEtages = (5 /  ((Integer)Immeuble.getNBEtage()).floatValue()) *100;
            float poidsCinqDerniersEtages =  (((Integer)configJ.get(new String("cinqDerniersEtages"))).floatValue())/ cinqDerniersEtages ;
            listePoids.put(40, poidsCinqDerniersEtages);

            float sousSols = (4/ ((Integer)Immeuble.getNBEtage()).floatValue())*100;
            float poidsSousSols =  (((Integer)configJ.get(new String("sousSols"))).floatValue())/sousSols;
            listePoids.put(0, poidsSousSols);

            float autreEtages = (15/  ((Integer)Immeuble.getNBEtage()).floatValue())*100;
            float poidsAutreEtages =  (((Integer)configJ.get(new String("autresEtages"))).floatValue())/autreEtages ;
            listePoids.put(24, poidsAutreEtages);
            
            Float max = 0.00f;
                       
            Map map = new HashMap();
            map = listePoids;
            Object cleMax = null;
           //System.out.println("Init Nuit");
            for(int i = 0 ; i< Immeuble.getListeAscenseur().size();i++){
                //On recherche l'element de poids inferieur
                Set cles = map.keySet();
                Iterator it = cles.iterator();
                max = 0.00f;
                while (it.hasNext()){
                    
                    Object cle = it.next(); // tu peux typer plus finement ici
                    System.out.println((Float)map.get(cle));
                    Float valeur = (Float)map.get(cle); // tu peux typer plus finement ici
                    if(valeur > max){
                        max = valeur ;
                        cleMax = cle;
                    }
                        
                }
                if(cleMax != null){
                    System.out.println("Ajout");
                    listeReplacementNuit.add(Immeuble.getListeAscenseur().get(i).getNumAscenseur(), (Integer)cleMax);
                    listePoids.remove(cleMax);
                    if(max > 1 && Immeuble.getListeAscenseur().size() > i+1){
                        listeReplacementNuit.add(Immeuble.getListeAscenseur().get(i+1).getNumAscenseur(), (Integer)cleMax);
                        listePoids.remove(cleMax);
                        i++;
                    }
                }
                
            }
            

            
        }
        //Si c'est la journée et que le tableau n'est pas initialisé
        else if (!isNight() && listeReplacementJour == null ){

            listeReplacementJour = new ArrayList<Integer>();

            HashMap listePoids = new HashMap();
            HashMap configJ =  conf.getConfigJournee();
            float rdc = (  (1 / ((Integer)Immeuble.getNBEtage()).floatValue())   ) *100;
            float poidsRDC =  (((Integer)configJ.get(new String("RdC"))).floatValue())/rdc ;
            listePoids.put(4, poidsRDC);
            //System.out.println(rdc);
            float vingtPremiersEtages = (20 /  ((Integer)Immeuble.getNBEtage()).floatValue() ) *100;
            float poidsVingtPremiersEtages = (((Integer)configJ.get(new String("vingtPremiersEtages"))).floatValue()) /vingtPremiersEtages ;
            listePoids.put(14, poidsVingtPremiersEtages);

            float cinqDerniersEtages = (5 /  ((Integer)Immeuble.getNBEtage()).floatValue()) *100;
            float poidsCinqDerniersEtages =  (((Integer)configJ.get(new String("cinqDerniersEtages"))).floatValue())/ cinqDerniersEtages ;
            listePoids.put(40, poidsCinqDerniersEtages);

            float sousSols = (4/ ((Integer)Immeuble.getNBEtage()).floatValue())*100;
            float poidsSousSols =  (((Integer)configJ.get(new String("sousSols"))).floatValue())/sousSols;
            listePoids.put(0, poidsSousSols);

            float autreEtages = (15/  ((Integer)Immeuble.getNBEtage()).floatValue())*100;
            float poidsAutreEtages =  (((Integer)configJ.get(new String("autresEtages"))).floatValue())/autreEtages ;
            listePoids.put(24, poidsAutreEtages);

            Float max = 0.00f;

            Map map = new HashMap();
            map = listePoids;
            Object cleMax = null;
           //System.out.println("Init Nuit");
            for(int i = 0 ; i< Immeuble.getListeAscenseur().size();i++){
                //On recherche l'element de poids inferieur
                Set cles = map.keySet();
                Iterator it = cles.iterator();
                max = 0.00f;
                while (it.hasNext()){

                    Object cle = it.next(); // tu peux typer plus finement ici
                    System.out.println((Float)map.get(cle));
                    Float valeur = (Float)map.get(cle); // tu peux typer plus finement ici
                    if(valeur > max){
                        max = valeur ;
                        cleMax = cle;
                    }

                }
                if(cleMax != null){
                    //System.out.println("Ajout");
                    listeReplacementJour.add(Immeuble.getListeAscenseur().get(i).getNumAscenseur(), (Integer)cleMax);
                    listePoids.remove(cleMax);
                    if(max > 1 && Immeuble.getListeAscenseur().size() > i+1){
                        listeReplacementJour.add(Immeuble.getListeAscenseur().get(i+1).getNumAscenseur(), (Integer)cleMax);
                        listePoids.remove(cleMax);
                        i++;
                    }
                }

            }
        }
        //le tableau est déja initialisé et c'est la nuit
        if (isNight() && listeReplacementNuit != null){
            return new Etage(listeReplacementNuit.get(asc.getNumAscenseur()));
        }
        //le tableau est déja initialisé et c'est la journée
        if (!isNight() && listeReplacementJour != null){
            return new Etage(listeReplacementJour.get(asc.getNumAscenseur()));
        }


        
        return null;
    }

    public static boolean quelquunAprendreIci(Ascenseur asc){
        boolean retour = false;
        Personnes p = asc.quelqunVeutMonterDansAsc();
        while(p !=null){
            if(asc.isMonte() && p.veutMonter() && p.getEtageArrive().getNumEtage() <= listeDestinationAscenseur.get(asc.getNumAscenseur()))
                    retour = true;
            p = asc.quelqunVeutMonterDansAsc();
        }

        /*if(!asc.isMonte() && asc.quelqunVeutMonterDansAsc()!= null && asc.quelqunVeutMonterDansAsc().getEtageArrive().getNumEtage() <= listeDestinationAscenseur.get(asc.getNumAscenseur()))
            return true;*/
        return retour;
    }

    private static boolean jeSuisLePlusProcheVide(Ascenseur asc,Appel appel){

        int etageNum = appel.getSource().getNumEtage();
        int valTest = Immeuble.NBEtage;
        int val = valTest;
        Ascenseur ascLePlusProcheVide = null;
        for(int j =0; j<listeAscenseur.size();j++){
            val = Math.abs(etageNum - listeAscenseur.get(j).getEtageCourant());
            //System.out.println("val : " + val +"Etage  : "+etageNum);
            //On change la valeur de valTest si on a trouvé plus petit
            if(val < valTest && listeAscenseur.get(j).getNbPersonne() == 0 && !asc.getMaintenance()){
                valTest = val;
                ascLePlusProcheVide = listeAscenseur.get(j);
            }
        }
        if(ascLePlusProcheVide == null){
            return false;
        }
        else if(asc.getNumAscenseur() == ascLePlusProcheVide.getNumAscenseur())
        {
            //System.out.println("L'ascenseur le plus proche de " + etageNum + "est :" +ascLePlusProcheVide.getNumAscenseur());
            return true;
        }
        return false;
    }

    private static Appel AppelLePlusHaut(){
        Appel EtageLePlusHautMonte = new Appel(new Etage(-1),new Etage(0),true,null);
        Appel EtageLePlusHautDescend = new Appel(new Etage(-1),new Etage(0),false,null);

        //System.out.println("Il y a x appels : " + listeDestinationAscenseur.size());
        //On recherche dans les appels montants ceux qui sont le plus haut
        for(int j =0; j<listeAppel_Attente.size();j++){
            if(listeAppel_Attente.get(j).getSource().getNumEtage() > EtageLePlusHautMonte.getSource().getNumEtage() && !listeDestinationAscenseur.contains(listeAppel_Attente.get(j).getDest().getNumEtage()))
                EtageLePlusHautMonte = listeAppel_Attente.get(j);
        }

        //On recherche dans les appels descendants ceux qui sont le plus haut
        for(int j =0; j<listeAppel_Attente.size();j++){
            if(listeAppel_Attente.get(j).getSource().getNumEtage() < EtageLePlusHautDescend.getSource().getNumEtage() && !listeDestinationAscenseur.contains(listeAppel_Attente.get(j).getDest().getNumEtage()))
                EtageLePlusHautDescend = listeAppel_Attente.get(j);
        }

        //On renvoie l'étage le plus haut
        if(EtageLePlusHautDescend.getSource().getNumEtage() >= EtageLePlusHautMonte.getSource().getNumEtage() )
            return EtageLePlusHautDescend;
        else
            return EtageLePlusHautMonte;

    }

    /**
     * Retourne le premier ascenseur qui est entrain de monter
     * @return
     */
    private static Ascenseur unDesAscenseurMonte(){
        Ascenseur asc = null;
        for(int j =0; j<listeAscenseur.size();j++){
            if(listeAscenseur.get(j).isMonte() && listeDestinationAscenseur.get(j) != -1 && !listeAscenseur.get(j).getMaintenance()){
                asc = listeAscenseur.get(j);
                break;
            }
        }
        return asc;
    }

    private static Ascenseur unDesAscenseurdescend(){
        Ascenseur asc = null;
        for(int j =0; j<listeAscenseur.size();j++){
            if(!listeAscenseur.get(j).getMaintenance() && (!listeAscenseur.get(j).isMonte() || (listeAscenseur.get(j).getNbPersonne() != 0 && !listeAscenseur.get(j).getListePersonne().get(0).veutMonter() )  )&& listeDestinationAscenseur.get(j) != -1){
                asc = listeAscenseur.get(j);
                break;
            }
        }
        return asc;
    }
    public static boolean estInterrompable(Ascenseur asc, Etage dest){  //Appel ap){

        if( /*ap.getDest()*/dest.getNumEtage() % 2 == asc.getComportement().getParite())
        {
            return true;
        }   
        else
        {
            return false;
        }
            

    }
    
    //********************************* FIN Methodes pour la gestion d'économie d'énergie ************************

    /**
     * methode qui ecrit un fichier texte contenant les statistiques de la simulation
     */
   public void ecrireStatistiques() {

        Date date = new Date();
        //a completer avec le humeurVisitor!!!!
        File fic = new File("statistiques.txt");
        StringBuffer sb = new StringBuffer();
        sb.append("Ecrit le : " +date.toString() + "\n");
        sb.append("Le nombre de personnes visit�s lors de cette simulation est de : " + getTpsVisitor().getNbVisited() + "\n");
        sb.append("Le temps moyen d'attente des personnes lors de cette simulation est de : "+ getTpsVisitor().getMoyenne() + "\n");
        sb.append("Le temp total de cette simulation est de : "+getTpsVisitor().getTotalTime() + "\n");
        sb.append("***************************************************************************************************\n");
        sb.append("Le nombre de personnes contente dans cette simulation est de : "+getHumeurVisitor().getNbContente() + "\n");
        sb.append("Le nombre de personnes normal dans cette simulation est de : "+getHumeurVisitor().getNbNormal() + "\n");
        sb.append("Le nombre de personnes enerv� dans cette simulation est de : "+getHumeurVisitor().getNbEnerve() + "\n");
        sb.append("***************************************************************************************************\n\n");
        String resultat = sb.toString();
        try {
            FileWriter fw = new FileWriter(fic, true);
            fw.write(resultat);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'ecriture");
        }

    }

}
