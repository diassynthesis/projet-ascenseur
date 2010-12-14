package projetAscenseur.strategy;

import fr.unice.plugin.Plugin;
import projetAscenseur.personne.Personnes;
import projetAscenseur.*;

/**
 *interface des comportements de l'ascenseur
 * @author Checconi maxime, Pilot guillaume et Canessa Marine
 */
public interface ComportementAbstrait extends Plugin {

    //methode a definir
    public void seDeplacer();
    
    public void attendre();
    
    public boolean accepterPersonne(Personnes p);
    
    public boolean ascenseurDejaLa();
    
    public void setAscenseur(Ascenseur asc);

    public int getParite();
    
}
