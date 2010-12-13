
package projetAscenseur.factories.concreteFactories;

import projetAscenseur.personne.concretePersonne.PersonneSeule;
import projetAscenseur.personne.concretePersonne.Groupe;
import projetAscenseur.personne.Personnes;
import projetAscenseur.*;
import projetAscenseur.factories.PersonneFactory;

/**
 * cree des personnes et des groupes aleatoirement partant et arrivant à n'importe quel etage
 * @author Checconi maxime, Pilot guillaume et Canessa Marine
 */
public class PersGroupeFactory implements PersonneFactory{

    private Etage etageDepart;
    private Etage etageArrive;
    private int numEtageDepart = 0;
    private int numEtageArrive = 0;
    private Groupe grp;
    private int random; 
    private static int nbCree =0;


    
    public PersGroupeFactory(){}
    
     public Personnes getPersonne(int num, int nbMax){
        calculEtage();
        Personnes pReturn = null;                
        int capacite =0;
        int nbReste = nbMax-nbCree;
        random = (int)(Math.random() * (6));
        if(random <=3 && nbReste>1)
        {
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
            pReturn = grp;
        }
        else 
        {
            PersonneSeule p = null;
            p = new PersonneSeule(etageDepart,etageArrive,num);
            pReturn = p;
            nbCree += 1;
        }
        numEtageDepart = 0;
        numEtageArrive = 0;
        return pReturn;
     }
    
     public Etage getEtageArrive() {
        return etageArrive;
    }

    public Etage getEtageDepart() {
        return etageDepart;
    }
    
    public static void setNbCree(int a) {  nbCree=a;  }

    
    
    /**
     * calcule aleatoirement les etage de depart et d'arrivee
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
