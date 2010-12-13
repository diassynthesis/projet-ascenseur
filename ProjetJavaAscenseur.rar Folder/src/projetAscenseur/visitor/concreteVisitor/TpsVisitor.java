
package projetAscenseur.visitor.concreteVisitor;

import projetAscenseur.personne.Personnes;
import projetAscenseur.*;
import projetAscenseur.visitor.Visitor;

/**
 * visitor sur le temps d'attente des personnes
 * @author Checconi maxime, Pilot guillaume et Canessa Marine
 */
public class TpsVisitor  implements Visitor{
    
        Personnes pers;
        long tpsAttentePerso = 0;
        long tpsAttentePerso2 =0;
    	long totalTime = 0;
	int nbVisited = 0;
        long moyenne =0;
        
    public long getMoyenne() {return moyenne;}

    public int getNbVisited() {return nbVisited;}

    public long getTotalTime() {return totalTime;}

    
    /**
     * met a jour les donnees en fonction de la personne visitee
     * @param p la personne visitee
     */
    public void visit(Personnes p) {
        pers = p;
        tpsAttentePerso = p.getTpsAttente();
        tpsAttentePerso2 = p.getTpsArrive();
        totalTime += tpsAttentePerso;
	nbVisited += 1;
        moyenne = totalTime/nbVisited;       
    }
    
    /**
     * met a zero les donnes du visitor
     */
    public void reset() {
        tpsAttentePerso = 0;
        tpsAttentePerso2 =0;
    	totalTime = 0;
	nbVisited = 0;
        moyenne =0;
    }

    /**
     * affiche le resultat du visitor
     * @return le resultat
     */
    public String result() {
        String result = "[le temps d'attente moyen est : "+moyenne+" millisecondes ]";
        result += " [ nb personnes = "+nbVisited+" ]";
        result += " [ personne "+pers.getNum()+" a attendu "+tpsAttentePerso+" millisecondes avant de prendre l'ascenseur]";
        result += " [ et mis "+tpsAttentePerso2+" millisecondes pour aller du" +pers.getEtageDepart().getNumEtage()+" au "+pers.getEtageArrive().getNumEtage()+"]";

        return result;
    }
    
    

}
