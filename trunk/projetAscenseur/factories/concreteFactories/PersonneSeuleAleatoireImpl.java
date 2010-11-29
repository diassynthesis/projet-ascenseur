/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projetAscenseur.factories.concreteFactories;

import projetAscenseur.personne.concretePersonne.PersonneSeule;
import projetAscenseur.personne.Personnes;
import projetAscenseur.*;
import projetAscenseur.factories.PersonneFactory;

/**
 * cree les personnes uniques avec des etages de depart et d'arrivee aleatoire
 * @author Checconi maxime, Pilot guillaume et Canessa Marine
 */
public class PersonneSeuleAleatoireImpl implements PersonneFactory{
    
    private Etage etageDepart;
    private Etage etageArrive;
    int numEtageDepart = 0;
    int numEtageArrive = 0;
    
    public PersonneSeuleAleatoireImpl(){}
    public Personnes getPersonne(int num, int nbMax){
        calculEtage();
        PersonneSeule p = null;
        p = new PersonneSeule(etageDepart,etageArrive,num);
        numEtageDepart = 0;
        numEtageArrive = 0;
        return p;
    }
    
     public Etage getEtageArrive() {
        return etageArrive;
    }

    public Etage getEtageDepart() {
        return etageDepart;
    }
    
    /**
     * calcule aleatoirement les etages de depart et d'arrivee
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
