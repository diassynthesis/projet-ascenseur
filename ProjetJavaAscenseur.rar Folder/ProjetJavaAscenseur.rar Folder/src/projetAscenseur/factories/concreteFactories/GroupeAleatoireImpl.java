
package projetAscenseur.factories.concreteFactories;

import projetAscenseur.personne.concretePersonne.Groupe;
import projetAscenseur.personne.Personnes;
import projetAscenseur.*;
import projetAscenseur.factories.PersonneFactory;

/**
 * cree des groupes partant et arrivant à des etages aux hasard
 * @author Checconi maxime, Pilot guillaume et Canessa Marine
 */
public class GroupeAleatoireImpl implements PersonneFactory{
    
    private Etage etageDepart;
    private Etage etageArrive;
    private int numEtageDepart = 0;
    private int numEtageArrive = 0;
    private Groupe grp;
    private static int nbCree =0;
    
    public GroupeAleatoireImpl(){}
    
     public Personnes getPersonne(int num, int nbMax)
     {
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
        numEtageDepart = 0;
        numEtageArrive = 0;
        nbCree += capacite;
        return grp;
     }
    
     public Etage getEtageArrive() {  return etageArrive;  }

    public Etage getEtageDepart() {   return etageDepart;  }
    
    /**
     * calcule des etages de depart et d'arrivee aleatoirement
     */
    public void calculEtage(){
       while(numEtageDepart == numEtageArrive){
           
            numEtageDepart = (int)(Math.random() * (Immeuble.getNBEtage()));
            numEtageArrive = (int)(Math.random() * (Immeuble.getNBEtage()));
        }
        etageDepart = Etage.chercherEtageDsListe(numEtageDepart);
        etageArrive =Etage.chercherEtageDsListe(numEtageArrive);
    }

}
