
package projetAscenseur.visitor;

import projetAscenseur.personne.Personnes;
import projetAscenseur.*;

/**
 * interface de visitor sur les personnes
 * @author Checconi maxime, Pilot guillaume et Canessa Marine
 */
public interface Visitor {
	public void visit(Personnes p) ;
	public void reset();
	public String result();
}
