
package projetAscenseur.factories;

import projetAscenseur.personne.Personnes;
import projetAscenseur.*;

/**
 * interface des factory de personnes
 * @author Checconi maxime, Pilot guillaume et Canessa Marine
 */
public interface PersonneFactory {
    
    public Personnes getPersonne(int n, int nbMax);

}
