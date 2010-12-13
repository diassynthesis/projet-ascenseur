
package projetAscenseur.strategy.concreteStrategy;

import projetAscenseur.personne.concretePersonne.Groupe;
import projetAscenseur.personne.Personnes;
import projetAscenseur.*;
import projetAscenseur.strategy.ComportementAbstrait;

/**
 * comportement de l'ascenseur qui s'arrete a chaque etage
 * @author Checconi maxime, Pilot guillaume et Canessa Marine
 */
public class ComportementArretEtage implements ComportementAbstrait{
    
    private Ascenseur asc;
    
    
    public ComportementArretEtage(){}
    
    public ComportementArretEtage(Ascenseur asc)
    {
        this.asc=asc;
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
                attendre();
                if(!asc.getListePersonne().isEmpty()||!Immeuble.getListeAppel().isEmpty()){
                   asc.monter(); 
                }
            }
            else{   //sinon faut qu'il redessende
                asc.setMonte(false);
                attendre();
                if(!asc.getListePersonne().isEmpty()||!Immeuble.getListeAppel().isEmpty()){
                   asc.descendre(); 
                }
            }
         }
        else if((!asc.isMonte())&&(!asc.isPortesOuvertes())) //si l'ascenseur dessend
        {
            if(asc.getEtageCourant()>0)  //s'il est pas arrivé en bas
            {
                attendre();
                if(!asc.getListePersonne().isEmpty()||!Immeuble.getListeAppel().isEmpty()){
                  asc.descendre();  
                }
            }
            else{   //sinon faut qu'il monte
                asc.setMonte(true);
                attendre();
                if(!asc.getListePersonne().isEmpty()||!Immeuble.getListeAppel().isEmpty()){
                   asc.monter(); 
                }
            }
        }
    }
    
    
    /**
     * simule l'attente de l'ascenseur pendant que les personnes montent dedans ou en descendent 
     * quand tout le monde est monté il ferme ses portes
     */
    public void attendre(){
    	while(ascenseurDejaLa()){
            try {
                    Thread.sleep(asc.getTemporisation());
            } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            }
    	}
        
        asc.ouvrirPorte();
        while(asc.quelqunVeutDessendreDeAsc()!=null)
        {
            asc.reveillerPersonne(asc.quelqunVeutDessendreDeAsc());
        }
         while(asc.quelqunVeutMonterDansAsc()!= null){
            asc.reveillerPersonne(asc.quelqunVeutMonterDansAsc());
        } 
        asc.fermerPorte();
    }
    
    /**
     * indique si l'ascenseur accepte de prendre une personne ou pas
     * @return vrai si l'ascenseur accepte quelqu'un
     */
    public boolean accepterPersonne(Personnes p)
    {
        if(p instanceof Groupe)
        {
            if(p.getTaille()<=asc.getNbPlacesRestantes())
            return true;
            else return false;
        }
        else return true;
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
        return "Arret Etage";
    }

    public Class getType() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
  
  
}
