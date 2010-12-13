
package projetAscenseur.visitor.concreteVisitor;

import projetAscenseur.personne.Personnes;
import projetAscenseur.*;
import projetAscenseur.visitor.Visitor;

/**
 * classe pour voir l'humeur des personnes en fonctions de leurs temps d'attente
 * @author Checconi maxime, Pilot guillaume et Canessa Marine
 */
public class HumeurVisitor implements Visitor{
    
    private Personnes pers;
    private String humeurPersonnes = null;
    private int nbContente = 0;
    private int nbNormal = 0;
    private int nbEnerve = 0;

    public int getNbContente() {return nbContente;}

    public int getNbNormal() {return nbNormal;}
    
    public int getNbEnerve(){ return nbEnerve;}
    
    public String getHumeurPersonnes(){return humeurPersonnes;}
    
    /**
     * met a jour les donnees lorsqu'il "visite" les personnes
     * @param p la personne visitee
     */
    public void visit(Personnes p) {
        pers = p;
        humeurPersonnes = p.getEtat();
        if(humeurPersonnes.equals("cool")){
            nbContente +=1;
        }
        else if(humeurPersonnes.equals("normal")){
            nbNormal+=1;
        }
        else nbEnerve +=1;
    }

    /**
     * remet a zero les donnees du visitor
     */
    public void reset() {
        humeurPersonnes = null;
        nbContente = 0;
        nbNormal = 0;
        nbEnerve = 0;  
    }

    /**
     * affiche le resultat sous forme de chaine de caracteres
     * @return la chaine resultat
     */
    public String result() {
        String result = "[Le nombre de personnes contente de "+nbContente+"]";
        result += "[Le nombre de personnes normal est de "+nbNormal+"]";
        result += "[Le nombre de personnes enerv� est de "+nbEnerve+"]";
        return result;
    }

}
