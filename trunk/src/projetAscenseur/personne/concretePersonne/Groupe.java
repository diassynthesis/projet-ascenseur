
package projetAscenseur.personne.concretePersonne;

import java.awt.Color;
import projetAscenseur.*;
import projetAscenseur.personne.Personnes;
import java.util.ArrayList;
import javax.swing.JLabel;

/**
 * Groupe de personnes
 * @author Checconi maxime, Pilot guillaume et Canessa Marine
 */
public class Groupe extends Personnes{
    
    //***********************************ATTRIBUTS ************************************
    private ArrayList<PersonneSeule> liste ;
    private static int numGroupe;
    private int numeroDuGroupe;



    //**************************CONSTRUCTEURS ************************************
    public Groupe(Etage dep,Etage arr, int num, int nb)
    {
        super(dep, arr,   num);
        setNumeroDuGroupe(getNumGroupe());
        setNumGroupe(getNumGroupe()+1);
        liste = new ArrayList<PersonneSeule>();
        String texte = "G"+getNumeroDuGroupe();
        for(int j=0;j<nb;j++){
            //créé nb personnes par groupe
            int value = num;
            PersonneSeule sj = new PersonneSeule(dep, arr,  value);
            this.ajouterSeule(sj); 
            num++;
        }
        this.setColor(Color.yellow);
        setTexte(texte);
        setTaille(nb);
    }
    
        //***********************ACCESSEURS*************************************
    public static int getNumGroupe() {  return numGroupe;   }

    public static void setNumGroupe(int numGroupe) {  Groupe.numGroupe = numGroupe;  }

    public int getNumeroDuGroupe() {    return numeroDuGroupe;  }

    public void setNumeroDuGroupe(int numeroDuGroupe) {  this.numeroDuGroupe = numeroDuGroupe;   }
    
    public ArrayList<PersonneSeule> getListe() { return liste;  }
   
    public void setListe(ArrayList<PersonneSeule> liste) {    this.liste = liste;   }
    
    //***************************METHODES **************************************
    /**
     * ajoute une personne seule dans le groupe
     * @param s la nouvelle personne du groupe
     */
    public void ajouterSeule(PersonneSeule s )
    {
        liste.add(s);
    }
    
    public void effacerGroupe(Etage o) {
        for(int i=0;i<this.getTaille();i++){
            o.remove(this.getListe().get(i));
        } 
    }

    
    public void afficherGroupe(Etage  o)
    {
        for(int i=0;i<this.getTaille();i++){
            JLabel jgroupe = this.getListe().get(i);
            jgroupe.setText(getTexte());
            o.add(jgroupe,i+2);
        } 
    }
    
    public void effacerGroupe(Ascenseur as) {
        for(int i=0;i<this.getTaille();i++){
            as.getJPanel().remove(this.getListe().get(i));
        } 
    }

    
    public void afficherGroupe(Ascenseur as)
    {
        for(int i=0;i<this.getTaille();i++){
            JLabel ji = this.getListe().get(i);
            as.getJPanel().add(ji,i);
        } 
    }
    

    
    /**
     * met a jour la couleur du label representant une personne
     */
    public void updateJLabelPers()
    {
        for(int i=0;i<this.getTaille();i++){
            this.getListe().get(i).setColor(getColor());
            this.getListe().get(i).updateJLabelPers();
        } 
    }
    
    /**
     * methode qui fait descendre une personne de l'ascenseur, la fait monter ou la fait appeler l'ascenseur
     * @param asc l'ascenseur destine
     */
    public void chercheASeDeplacer(Ascenseur asc){
        //si la personne est en mouvement
        if(this.isintentionDeDeplacement()){ 
            if(asc.chercherPersonneDansListe(this))  //si elle est dans l'ascenseur
            {
                //faire descendre
                if( asc.getEtageCourant() == this.getEtageArrive().getNumEtage())
                {
                    descendreDeAscenseur(asc);
                }
            }
            else if(this.getEtageDepart().chercherPersonneDansListe(this)) 
            {
                if(asc.getNbPlacesRestantes()>= this.getTaille() && asc.getComportement().accepterPersonne(this)==true){ 
                    monterDansAscenseur(asc);
              }
                else {
                    this.appuyerBoutonAscenseur();
              }
            }
        }
    }

   
}
