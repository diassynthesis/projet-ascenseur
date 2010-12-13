
package projetAscenseur.strategy.concreteStrategy;

//import projetAscenseur.personne.concretePersonne.Groupe;
import projetAscenseur.personne.Personnes;
import projetAscenseur.*;
import projetAscenseur.strategy.ComportementAbstrait;

/**
 * comportement de l'ascenseur qui s'arrete a chaque etage
 * @author A. Mortemousque, A. Cotten, F. Bourgeon, C. Faucher
 */
public class ComportementAlerte implements ComportementAbstrait{

    private Ascenseur asc;


    public ComportementAlerte(){}

    public ComportementAlerte(Ascenseur asc)
    {
        this.asc=asc;
    }

     /**
     * l'ascenseur se deplace dans les etages
     */
    public void seDeplacer()
    {
        

	asc.setArrete(true);							// On arrete l'ascenceur
	//asc.ouvrirPorte();							// On lui fait ouvrir les portes
        if(asc.getListePersonne().size() != 0)
        {
            attendre();
        }


    }


    /**
     * simule l'attente de l'ascenseur pendant que les personnes montent dedans ou en descendent
     * quand tout le monde est monté il ferme ses portes
     */
    public void attendre(){
    	
        asc.ouvrirPorte();							// On ouvre les portes pour laisser sortir les gens et on ne les referment pas

        Etage Etage_alerte = new Etage(asc.getEtageCourant());


        for(int i=0;i<asc.getListePersonne().size();i++)
       {
           //asc.supprimerPersonneAscenseur(asc.getListePersonne().get(i));
           asc.getListePersonne().get(i).setEtageArrive(Etage_alerte);
           asc.reveillerPersonne(asc.getListePersonne().get(i));

           System.out.println("Liste de personne arrivé"); //todo

           System.out.println((asc.getImmeuble()).getListePersonneArrivee()); //todo


        }

        
       asc.fermerPorte();
    }

    /**
     * indique si l'ascenseur accepte de prendre une personne ou pas
     * @return vrai si l'ascenseur accepte quelqu'un
     */
    public boolean accepterPersonne(Personnes p)
    {
        return false; 								// Mode alerte, nous n'acceptons personne
    }

    /**
     * recherche si un ascenseur est deja present à l'etage
     * @return vrai si il y a deja un ascenseur
     */
    public boolean ascenseurDejaLa()
    {
    	for(Ascenseur a : Immeuble.getListeAscenseur())
    	{
    		int numEt = a.getEtageCourant();
    		if(numEt == asc.getEtageCourant() && a.isPortesOuvertes())
    		{
    			return true;
    		}
    	}
    	return false;
    }

     public void setAscenseur(Ascenseur asc){
         this.asc = asc;
     }

    public String getName() {
        return "Mode alerte";
    }

    public Class getType() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}