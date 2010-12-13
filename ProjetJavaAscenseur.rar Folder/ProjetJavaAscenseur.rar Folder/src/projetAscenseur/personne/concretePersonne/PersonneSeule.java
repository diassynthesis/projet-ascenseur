
package projetAscenseur.personne.concretePersonne;

import java.awt.Color;
import projetAscenseur.*;
import projetAscenseur.personne.Personnes;

/**
 * classe representant une personne unique
 * @author Checconi maxime, Pilot guillaume et Canessa Marine
 */
public class PersonneSeule extends Personnes{
    
    public PersonneSeule(Etage dep,Etage arr, int num)
    {
        super(dep, arr, num);
        this.setColor(Color.yellow);        
        setTaille(1);
    }

    /**
     * methode qui fait descendre une personne de l'ascenseur, la fait monter ou la fait appeler l'ascenseur
     * @param asc l'ascenseur destine
     */
    public void chercheASeDeplacer(Ascenseur asc){
        //si la personne est en mouvement
        if(this.isintentionDeDeplacement()){ 
            if(asc.chercherPersonneDansListe(this))  //si elle est dans l'asc
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
                else appuyerBoutonAscenseur();
            }
        }
    }
    
    /**
     * met a jour la couleur du label representant une personne
     */
    public void updateJLabelPers()
    {
        this.setBackground(getColor());
    }
    
}
