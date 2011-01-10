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

    public Manager getManager(){
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
                                System.out.println(tpsVisitor.result());
                                System.out.println(humeurVisitor.result());
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
        System.out.println("Appel de :"+etageDepart.getNumEtage());
        listeAppel_Dest.add(ap);
                //On ajoute l'appel une seule fois - on vérifie donc si il n'existe pas d'appel à cet étage
        if(containsAppelAttente(etageDepart,etageArrivee,monte,null) == null)
           listeAppel_Attente.add(ap);
        System.out.println("Nombre d'appels " + listeAppel_Attente.size());
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
     public static void ajouterAscenseur(Ascenseur asc){
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
                        System.out.println("On cherche un objet dans AppelAttente");
                        if(getListeAppel_Attente().get(j).getSource() == etageDepart && getListeAppel_Attente().get(j).getAsc() == null)
                            return getListeAppel_Attente().get(j);

           }
        return null;
    }
    //********************************* Methodes pour la gestion d'économie d'énergie ************************
    /**
     * permet de savoir si un ascenseur est le plus proche d'un des etages appelants
     * @param asc l'ascenseur concernee
     * @return l'etage duquel il est le plus pres ou null si il est le plus eloigne
     */
        public static Etage InterogeImmeublePourDeplacement(Ascenseur asc){

        //Etage d'appel est-il supérieur ?


        ArrayList<Ascenseur> listeAsc = getListeAscenseur();
    	//ArrayList<Etage> etageAppelant = getListeAppel();
    	int val = 1500*Immeuble.NBEtage; //le nombre max d'etage
    	Ascenseur ascenseurQuiMonte = null;
        Ascenseur ascenseurQuiDescend = null;
        //Etage EtageLePlusHaut = new Etage(Immeuble.NBEtage);
        Appel appelLePlushaut = AppelLePlusHaut();
    	int valTest = 1500*Immeuble.NBEtage;
    	Etage etageRenvoye = null;
        int index = -1;
    	//pour chaque ascenseur on va tester si c lui le plus proche
    	//for(int i = 0; i< listeAsc.size();i++){
        if(appelLePlushaut.getSource().getNumEtage() != -1){
            //Si l'ascenseur courant est vide et qu'il n'a pas de destination
            if(asc.getListePersonne().isEmpty() && listeDestinationAscenseur.get(asc.getNumAscenseur()) == -1){

                ascenseurQuiMonte = unDesAscenseurMonte();
                ascenseurQuiDescend = unDesAscenseurdescend();
                 System.out.println("Le plus proche : ");
                //Si c'est un appel pour monter
                if(appelLePlushaut.getMonte() ){
                    //Si un ascenseur est déja sur la route
                    if( ascenseurQuiMonte!= null && ascenseurQuiMonte.getEtageCourant() < appelLePlushaut.getSource().getNumEtage()){
                        //On ne fait rien
                    }
                    //On exclue les différents ascenseurs
                    else if(jeSuisLePlusProcheVide(asc,appelLePlushaut)){
                        etageRenvoye = appelLePlushaut.getSource();
                    }
                }else{//L'appel le plus haut veut descendre
                    //Si un ascenseur est déja entrain de descendre et est au dessus de l'étage appelant
                    if( ascenseurQuiDescend != null && ascenseurQuiDescend.getEtageCourant() > appelLePlushaut.getSource().getNumEtage()){
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
/*
                for(int j =0; j<listeAppel_Attente.size();j++){
                    System.out.println("Ascenseur vide en recherche de personnes. Qqun attend du  "+ listeAppel_Attente.get(j).getSource().getNumEtage());
                    //On calcule la distance
                    //On calcule dans le sens de la monté
                    if(listeAppel_Attente.get(j).getSource().getNumEtage() > listeAsc.get(i).getEtageCourant())
                        valTest = 1500 * (listeAppel_Attente.get(j).getSource().getNumEtage()- listeAsc.get(i).getEtageCourant());
                    //En descente
                    else if(listeAppel_Attente.get(j).getSource().getNumEtage() < listeAsc.get(i).getEtageCourant())
                        valTest = 749 * (listeAsc.get(i).getEtageCourant() - listeAppel_Attente.get(j).getSource().getNumEtage() );
                    else{
                        valTest = 0 ;
                    }
                    if(valTest<val){//normalement avec cette condition un seul ascenseur prend la main pas de conflit
                        ascSelect = listeAsc.get(i);
                        etageRenvoye = listeAppel_Attente.get(j).getSource();
                        index = j;
                        val = valTest;
                    }

                    if(listeAppel_Attente.get(j).getSource().getNumEtage() > EtageLePlusHaut.getNumEtage() )
                        EtageLePlusHaut = listeAppel_Attente.get(j).getSource();
                }
            }else{//Sinon on a deja quelqun
                return null;
               /* System.out.println("L'ascenseur est plein");
               for(int j =0; j<listeAppel_Attente.size();j++){
                    //System.out.println("Ascenseur vide en recherche de personnes. Qqun attend du  "+ listeAppel_Dest.get(j).getSource().getNumEtage());
                    //On calcule la distance
                    //On calcule dans le sens de la monté
                    if(listeAsc.get(i).getListeAppels().contains(listeAppel_Attente.get(j)) && listeAppel_Attente.get(j).getSource().getNumEtage() == listeAsc.get(i).getEtageCourant()){

                        ascSelect = listeAsc.get(i);
                        listeAppel_Attente.get(i).setAsc(asc);
                        return listeAsc.get(i).getListePersonne().get(0).getEtageArrive();
                        //etageRenvoye = listeAppel_Dest.get(j).getSource();
                        //index = j;
                    }
                }
            }
    	}
    	if(ascSelect == asc && index !=-1){
                listeAppel_Attente.get(index).setAsc(asc);
    		return EtageLePlusHaut;

    	}
*/
    	return etageRenvoye;
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
            System.out.println("val : " + val +"Etage  : "+etageNum);
            if(val < valTest){
                ascLePlusProcheVide = listeAscenseur.get(j);
            }
        }
        if(asc.getNumAscenseur() == ascLePlusProcheVide.getNumAscenseur()){
            return true;
        }
        return false;
    }

    private static Appel AppelLePlusHaut(){
        Appel EtageLePlusHaut = new Appel(new Etage(-1),new Etage(0),true,null);
        for(int j =0; j<listeAppel_Attente.size();j++){
            if(listeAppel_Attente.get(j).getDest().getNumEtage() > EtageLePlusHaut.getSource().getNumEtage() && !listeDestinationAscenseur.contains(listeAppel_Attente.get(j).getDest().getNumEtage()))
                EtageLePlusHaut = listeAppel_Attente.get(j);
        }

        return EtageLePlusHaut;

    }

    /**
     * Retourne le premier ascenseur qui est entrain de monter
     * @return
     */
    private static Ascenseur unDesAscenseurMonte(){
        Ascenseur asc = null;
        for(int j =0; j<listeAscenseur.size();j++){
            if(listeAscenseur.get(j).isMonte() && listeDestinationAscenseur.get(j) != -1){
                asc = listeAscenseur.get(j);
                break;
            }
        }
        return asc;
    }

    private static Ascenseur unDesAscenseurdescend(){
        Ascenseur asc = null;
        for(int j =0; j<listeAscenseur.size();j++){
            if(!listeAscenseur.get(j).isMonte() && listeDestinationAscenseur.get(j) != -1){
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
