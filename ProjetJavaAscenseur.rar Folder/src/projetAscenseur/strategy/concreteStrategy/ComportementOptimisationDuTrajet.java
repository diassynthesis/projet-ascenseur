
package projetAscenseur.strategy.concreteStrategy;

import projetAscenseur.personne.Personnes;
import projetAscenseur.*;
import projetAscenseur.strategy.ComportementAbstrait;

/**
 * comportement qui gère l'optimisation du tajet
 * @author A. COTTEN, A. MORTEMOUSQUE, F. BOURGEON et C. FAUCHER
 */
public class ComportementOptimisationDuTrajet implements ComportementAbstrait{

    private Ascenseur asc;
    private int parite;             // mode de fonctionnement pair si vaut 0 et impair si vaut 1
    private Etage EtageDefaut;


    public ComportementOptimisationDuTrajet(){}
    public ComportementOptimisationDuTrajet(Ascenseur asc, int parite, Etage defaut)
    {
        this.asc=asc;
        this.parite=parite;
        this.EtageDefaut=defaut;

    }

     /**
     * l'ascenseur se deplace dans les etages
     */
    public void seDeplacer()
    {
        if((asc.isMonte())&&(!asc.isPortesOuvertes())) //si l'asc est en mode "monte"
        {
            if(asc.getEtageCourant()<Immeuble.getNBEtage()-1) //si il est pas arrivé en haut
            {

                if(asc.getEtageCourant()% 2 == parite)
                {
                    if(asc.quelqunVeutDessendreDeAsc()!=null ||( (Immeuble.getListeAppel()).contains(asc.getEtageCourant())   ))
                    {
            		attendre();
                    }
                }
                else
                {
                    if(asc.quelqunVeutDessendreDeAsc()!=null || (Immeuble.getListeAppel()).contains(asc.getEtageCourant()))                                
                    {
            		attendre();
                    }
                }
                


            	// Faire le if si quelqu'un veut descendre ou monter dans l'ascenseur

                asc.monter();
            }
            else                //sinon faut qu'il redessende
            {   
            	asc.setMonte(false);

                
                
                
                
                
                // Faire le if si quelqu'un veut descendre ou monter dans l'ascenseur

            	asc.descendre();
            }
        }
        else if((!asc.isMonte())&&(!asc.isPortesOuvertes())) //si l'ascenseur dessend
        {
            if(asc.getEtageCourant()>0)  //s'il est pas arrivé en bas
            {
            	// Faire le if si quelqu'un veut descendre ou monter dans l'ascenseur

            	asc.descendre();
            }
            else{   //sinon faut qu'il monte
                asc.setMonte(true);


            	// Faire le if si quelqu'un veut descendre ou monter dans l'ascenseur



            	asc.monter();
            }
        }



    }


    /**
     * simule l'attente de l'ascenseur pendant que les personnes montent dedans ou en descendent
     * quand tout le monde est monté il ferme ses portes
     */
    public void attendre()
    {

    }

    /**
     * indique si l'ascenseur accepte de prendre une personne ou pas
     * @return vrai si l'ascenseur accepte quelqu'un
     */
    public boolean accepterPersonne(Personnes p)
    {
        boolean value = false;
        if(p.getTaille()<=asc.getNbPlacesRestantes())
        {
            if(p.veutMonter() && asc.isMonte() || asc.getNbPersActuel()==0){
                value= true;
            }
            else if(!p.veutMonter() && !asc.isMonte()|| asc.getNbPersActuel()==0){
                value= true;
            }
            else{
                value= false;
            }
        }
        return value;
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
        return "Optimisation du trajet";
    }

    public Class getType() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
