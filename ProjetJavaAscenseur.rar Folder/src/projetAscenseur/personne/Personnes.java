package projetAscenseur.personne;

import projetAscenseur.*;
import projetAscenseur.visitor.Visitor;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.io.*;
/**
 * classe abstraite representant les personnes (groupe et personneSeule
 * @author Checconi maxime, Pilot guillaume et Canessa Marine
 */
public abstract class Personnes extends JLabel{
    
    //************************ATTRIBUTS***************************
    private Etage etageDepart;
    private Etage etageArrive;
    private int num;
    private long tpsAttente ;
    private long tpsArrive ;
    private Color color;
    private boolean estDsAsc = false;
    private int taille;
    private String etat;
    private boolean intentionDeDeplacement = true;
    private long debut;
    private ImageIcon image;
    private String texte;



    //*************************CONSTRUCTEURS***************************
    
    /**
     *premier constructeur 
     */
    public Personnes(){
        super();
        this.setOpaque(true);
        this.setVisible(true);
        this.debut = System.currentTimeMillis();
    }
    
    /**
     * constructeur avec etages
     * @param dep etage de depart
     * @param arr etage d'arrivee
     */
    public Personnes(Etage dep,Etage arr)
    {
        super();
        this.etageDepart = dep;
        this.etageArrive = arr;
        this.setOpaque(true);
        this.setVisible(true);
        this.debut = System.currentTimeMillis();
    }
    
    /**
     * constructeur pour les personnes avec numero
     * @param dep etage de depart
     * @param arr etage d'arrivee
     * @param num numero de la personne ou du groupe
     */
    public Personnes(Etage dep,Etage arr,  int num)
    {
        super();
        debut = System.currentTimeMillis();
        this.etageDepart = dep;
        this.etageArrive = arr;
        this.etat = "cool";
        this.num = num;
        String texte = String.valueOf(num);
        //this.setBackground(Color.yellow);
        setImage(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/ressources/b2.gif"))));
        this.setIcon(getImage()); 
        setTexte(texte);
        this.setOpaque(true);
        this.setVisible(true);
    }
    

    //*********************ACCESSEURS************************************
    public long getTpsArrive() {   return tpsArrive;  }

    public void setTpsArrive(long tpsArrive) {   this.tpsArrive = tpsArrive;   }
    
    public int getNum() {  return num; }

    public int getTaille() {  return taille; }

    public void setTaille(int taille) {   this.taille = taille;  }
   
    public boolean isintentionDeDeplacement() {return intentionDeDeplacement;}
    
    public void setintentionDeDeplacement(boolean enMouvement) {this.intentionDeDeplacement = enMouvement;}

    public Etage getEtageDepart(){return etageDepart;}
    
    public Etage getEtageArrive(){return etageArrive;}
      
     public void setEtageArrive(Etage etageArrive) { this.etageArrive = etageArrive; }

    public void setEtageDepart(Etage etageDepart) {   this.etageDepart = etageDepart;   }
    
    public long getTpsAttente() {  return tpsAttente; }

    public void setTpsAttente(long tpsAttente) {   this.tpsAttente = tpsAttente;  }
    
    public Color getColor() {   return color;  }

    public void setColor(Color color) {  this.color = color;   }

    public String getEtat() {   return etat;  }

    public void setEtat(String etat) {    this.etat = etat;  }
    
    public boolean isEstDsAsc() {	return estDsAsc;	}

    public void setEstDsAsc(boolean estDsAsc) {	this.estDsAsc = estDsAsc;}
    
    public long getDebut() {	return debut;}

    public void setDebut(long debut) {	this.debut = debut;}

    public String getTexte() {    return texte;   }

    public void setTexte(String texte) {    this.texte = texte;  }

    public ImageIcon getImage() {     return image;   }

    public void setImage(ImageIcon image) {  this.image = image;   }
    
    //************************METHODES****************************
    
    
    /**
     * ajoute une personne soit dans l'ascenseur soit dans l'etage
     * @param obj l'objet ou la personne est ajoutee
     */
    public void ajouterDansListe(Object obj)
    {   
    	if (obj instanceof Ascenseur) {
            Ascenseur asc = (Ascenseur)obj;
            asc.ajouterPersonneAscenseur(this);	
	}
    	else if(obj instanceof Etage)
    	{
            Etage eta = (Etage)obj;
            eta.listPersonneAdd(this);
    	}

    }
    
    
    
    /**
     * supprime la personne de la liste ou elle se trouvait, soit dans l'ascenseur, soit dans l'etage
     * @param obj l'objet d'ou la personne doit etre supprimee
     */
    public void supprimerDansListe(Object obj)
    {   
        if (obj instanceof Ascenseur) 
        {
            Ascenseur asc = (Ascenseur)obj;
            asc.supprimerPersonneAscenseur(this);
            asc.supprimerAppel(this);
	}
    	else if(obj instanceof Etage)
    	{
            Etage eta = (Etage)obj;
            eta.listPersonneRem(this);
    	}
    }
    
    /**
     * pour savoir si la personne veut monter ou descendre dans l'immeuble
     * @return vrai si elle veut monter
     */
    public boolean veutMonter()
    {
       boolean result = false;
       if(this.getEtageDepart().getNumEtage()<this.getEtageArrive().getNumEtage())
       {
           return result = true;
       }
       else if(this.getEtageDepart().getNumEtage()>this.getEtageArrive().getNumEtage())
       {
           return result = false;
       }
       return result;
    }
    
    
   /**
    * fait monter la personne dans l'ascenseur
    * @param asc l'ascenseur concerne
    */
    public void monterDansAscenseur(Ascenseur asc){
        supprimerDansListe(this.getEtageDepart());
        ajouterDansListe(asc);
        this.setEstDsAsc(true);
    }
    
    /**
     * fait descendre la personne de l'ascenseur
     * @param asc l'ascenseur concerne
     */
    public void descendreDeAscenseur(Ascenseur asc){

        supprimerDansListe(asc);
    	ajouterDansListe(this.getEtageArrive());
        Immeuble.addPersArrivee(this); 
        setintentionDeDeplacement(false);
        this.setEstDsAsc(false);
        setColor(Color.gray);
        updateJLabelPers();

        System.out.println("Descend de l'ascenseur"); //todo
    }
    
 
    /**
     * methode abstraite a definir dans les classes heritees
     * @param asc l'ascenseur pour se deplacer
     */
    public abstract void chercheASeDeplacer(Ascenseur asc);

    
    /**
     * pour accepter un visitor
     * @param v
     */
    public void accept(Visitor v) 
    {
            v.visit(this);
    }
    
    /**
     * met a jour la couleur du label representant une personne
     */
    public abstract void updateJLabelPers();
    
    

    /**
     * controle l'etat de la personne en fonction de son temps d'attente
     */
    public void controlerEtatSatisfaction()
    {
        if(this.getTpsAttente()>6000 && this.getTpsAttente()<12000)
        {
            setEtat("normal");
            setColor(Color.orange);
            
        }
        if( this.getTpsAttente()>12000)
        {
            setEtat("enerve");
            setColor(Color.red);
        }
        else setEtat("cool"); 
        updateJLabelPers();
    }
    
    /**
     * compte le temps d'attente avec un timer
     */
   public void commencerAttente()
   {
       if(!this.isEstDsAsc()&& this.isintentionDeDeplacement()){ 
            long calcul = System.currentTimeMillis()-this.getDebut();
            this.setTpsAttente(calcul);
            this.controlerEtatSatisfaction(); 	
       }
   }

    /**
     * appel de l'ascenseur pour se deplacer dans l'immeuble, l'etage est ajoute dans les appels
     */
    public void appuyerBoutonAscenseur(){
        //la personne veut prendre l'ascenseur, on met en route le temps d'attente
    	//Si le bouton n'est pas appuye, la personne appuie
    	Etage etageCourant = this.getEtageDepart();
        if(!etageCourant.isBoutonAscenseur()){
            Immeuble.ajouterAppel(etageCourant,this.etageArrive,this.veutMonter());

        }
    }

    
}
