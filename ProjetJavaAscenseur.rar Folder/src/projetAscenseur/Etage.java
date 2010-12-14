package projetAscenseur;

import projetAscenseur.personne.Personnes;
import java.awt.Color;
import java.awt.Graphics;

import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import projetAscenseur.personne.concretePersonne.Groupe;

/**
 * classe Etage de l'immeuble
 * @author Checconi maxime, Pilot guillaume et Canessa Marine
 */
public class Etage extends JPanel{

    //***************************ATTRIBUTS**********************************
    private float largeur;
    private float hauteur;
    private int numEtage;
    private Color couleur;
    private ArrayList<Personnes> listePersonne;
    private JLabel nbPers;
    private boolean boutonAscenseur;
    private JLabel appel;
    
    //*******************************CONSTRUCTEURS*********************************
    public Etage(){}
    
    /**
     * constructeur pour les tests
     * @param num
     */
    public Etage( int num)
    {
        this.numEtage = num;
        this.listePersonne = new ArrayList<Personnes>();
        boutonAscenseur = false;
    }
    
    public Etage(float larg, float haut, int num, Color couleur){
        super(null);
        this.numEtage = num;
        this.couleur = couleur;
        this.largeur = larg;
        this.hauteur = haut;
        this.setSize(Math.round(larg), Math.round(haut));
        this.setBackground(couleur); 
        this.listePersonne = new ArrayList<Personnes>();
        this.setLayout(new FlowLayout());
        nbPers = new JLabel();
        appel = new JLabel();
        appel.setText("ap");
        appel.setOpaque(true);
        this.add(nbPers);
        this.add(appel);
        boutonAscenseur = false;
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
    
   
    //****************************GETTERS AND SETTERS*********************************************
    public int getNumEtage(){return numEtage;}
    
    public Color getCouleur(){return couleur;}
    
    public float getLargeur(){return largeur;}
    
    public float getHauteur(){return hauteur;}
    
    public ArrayList<Personnes> getListePersonne(){return listePersonne;}
    
   // public int getNbPersonne(){return listePersonne.size();}
    
    public boolean isBoutonAscenseur() {  return boutonAscenseur;  }

    public void setBoutonAscenseur(boolean boutonAscenseur) {	this.boutonAscenseur = boutonAscenseur; }
    
    
    //*****************************METHODES***********************************
    
    /**
     * met a jour le JLabel de l'etage pour connaitre le nombre de personnes dans l'etage
     */
    public void updateJLabel(){
        int nbP=0;
    	for(int i=0;i<getListePersonne().size();i++)
        {
            nbP += getListePersonne().get(i).getTaille();
        }
        String nbPersEtage = String.valueOf(nbP);
    	nbPers.setForeground(Color.red);
        nbPers.setText(nbPersEtage);  
    }
    
    /**
     * met a jour le label representant le bouton appuye ou pas a l'etage
     */
    public void updateJLabelBouton()
    {
        if(isBoutonAscenseur())
        {
            appel.setBackground(Color.blue);
        }
        else appel.setBackground(Color.black);
    }
    
    /**
     * ajouter une personne a l'etage
     * @param P la personne a ajouter
     */
    public synchronized void listPersonneAdd(Personnes P){
    	getListePersonne().add(P);
        //graphiquement
        if(P instanceof Groupe){
            Groupe g = (Groupe)P;
            g.afficherGroupe(this);
        }
        else this.add(P);
    }
    
    /**
     * supprimer une personne de l'etage
     * @param P la personne a supprimer
     */
    public synchronized void listPersonneRem(Personnes P){
        if(listePersonne.contains(P)){
            getListePersonne().remove(P);
            if(P instanceof Groupe){
            Groupe g = (Groupe)P;
            g.effacerGroupe(this);
        }
        else this.remove(P);
        }
    }


     
    /**
     * permet de chercher un etage dans la liste d'etages
     * @param numEtage le numero de l'etage cherche
     * @return l'etage
     */
     public static Etage chercherEtageDsListe(int numEtage)
    {
        Etage result = null;
        int nbEtage = Immeuble.getNBEtage();
        int value = nbEtage - (1+numEtage);
        result = Immeuble.getListeEtage().get(value);
        return result;
    }
    
     /**
      * cherche une personne dans la liste de l'etage
      * @param p la personne recherchee
      * @return vrai si la personne est trouvee
      */
    public boolean chercherPersonneDansListe(Personnes p)
    {
       boolean pResult = false;
       for(int i=0;i<this.getListePersonne().size();i++)
       {
           if(this.getListePersonne().get(i) == p)
           {
                   pResult = true;
           }
       }
       return pResult;
    }
    
    
    /**
     * permet de savoir si au moins une personne de l'etage veut prendre l'ascenseur
     * utile pour appeler l'ascenseur
     * @return la personne
     */
    public Personnes personneMobileDsEtage()
    {
        Personnes result = null;
        for (int i=0; i<this.getListePersonne().size();i++)
        {
            if(getListePersonne().get(i).getEtageArrive().getNumEtage()!=this.getNumEtage())
            {
                result = getListePersonne().get(i);
                break;
            }
        }
        return result;
    }
    
    /**
     * aoute une nouvelle personne pendant l'execution de la simulation
     * @param p la personne a ajouter
     */
    public void ajouterNewPers(Personnes p){
    	int val=0;
    	if(this.getListePersonne().size()!=0)
    	{
            for(int i = 0;i<this.getListePersonne().size();i++){
                if(this.getListePersonne().get(i).isintentionDeDeplacement()==true){
                        val++;
                }else{
                        break;

                }
            }
            this.getListePersonne().add(val, p);
            Immeuble.getListePersonnesCrees().add(p);
            this.add(p, val+2);		
    	}
    	else{
            this.getListePersonne().add(0,p);
            Immeuble.getListePersonnesCrees().add(p);
            this.add(p,2);
    	}
    }
   
  
    /**
     * supprime les personnes de l'etage lors du rafraichissement
     */
     public void supprimerPersonneListe(){
      int debut = listePersonne.size() -1 ;
        for(int i =debut; i>=0; i--){
            listPersonneRem(listePersonne.get(i));
        }
    }

}
