
package projetAscenseur.factories.concreteFactories;

import projetAscenseur.personne.concretePersonne.PersonneSeule;
import projetAscenseur.personne.Personnes;
import projetAscenseur.*;
import projetAscenseur.factories.PersonneFactory;

/**
 * cree des personnes uniques partant de l'etage 0 jusuq'a un etage au hasard
 * @author Checconi maxime, Pilot guillaume et Canessa Marine
 */
public class PersonneSeuleMonteImpl implements PersonneFactory{
    
    private Etage etageDepart;
    private Etage etageArrive;
    int numEtageArrive = 0;
    
    public PersonneSeuleMonteImpl(){}
    public Personnes getPersonne(int num, int nbMax){
        calculEtage();
        PersonneSeule p = null;
        p = new PersonneSeule(etageDepart,etageArrive,num);
        numEtageArrive = 0;
        return p;
    }
    
     public Etage getEtageArrive() {   return etageArrive;   }

    public Etage getEtageDepart() {   return etageDepart;   }
    
    /**
     * calcule l'etage d'arrivee aleatoirement
     */
    public void calculEtage(){
        while(numEtageArrive == 0){
            numEtageArrive = (int)(Math.random() * (Immeuble.getNBEtage()));
        }
        etageDepart = Etage.chercherEtageDsListe(0);
        etageArrive =Etage.chercherEtageDsListe(numEtageArrive);
    } 

}
