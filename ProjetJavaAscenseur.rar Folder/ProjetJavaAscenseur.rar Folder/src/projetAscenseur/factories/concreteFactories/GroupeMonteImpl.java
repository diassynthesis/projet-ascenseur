
package projetAscenseur.factories.concreteFactories;

import projetAscenseur.personne.concretePersonne.Groupe;
import projetAscenseur.personne.Personnes;
import projetAscenseur.*;
import projetAscenseur.factories.PersonneFactory;

/**
 *cree des groupes qui partent du rez de chaussee jusqu'à un etage au hasard
 * @author Checconi maxime, Pilot guillaume et Canessa Marine
 */
public class GroupeMonteImpl implements PersonneFactory{
    
    private Etage etageDepart;
    private Etage etageArrive;
    private int numEtageArrive = 0;
    private Groupe grp;
    private static int nbCree =0;
    
    public GroupeMonteImpl(){}
    
     public Personnes getPersonne(int num, int nbMax){
        calculEtage();
        int capacite =0;
        int nbReste = nbMax-nbCree;
        if (nbReste > 5)
        {
            capacite = (int)(Math.random() * (3))+2;
        }
        else if(nbReste % 2 !=0) {
            
            capacite = 3;
        }
        else if(nbReste < 5 && nbReste>3)
        {
            capacite = 4;
        }
        else {capacite = 2;}
        
        grp = new Groupe(etageDepart, etageArrive, num, capacite);
        nbCree += capacite;
        numEtageArrive = 0;
        return grp;
     }
    
     public Etage getEtageArrive() {   return etageArrive;   }

    public Etage getEtageDepart() {  return etageDepart;  }
    
    /**
     * calcule aleatoiremement les etages de depart et d'arrivee
     */
    public void calculEtage(){
       while(numEtageArrive == 0){
            numEtageArrive = (int)(Math.random() * (Immeuble.getNBEtage()));
        }
        etageDepart = Etage.chercherEtageDsListe(0);
        etageArrive =Etage.chercherEtageDsListe(numEtageArrive);
    }

}
