
package projetAscenseur.strategy.concreteStrategy;

import projetAscenseur.personne.Personnes;
import projetAscenseur.*;
import projetAscenseur.strategy.ComportementAbstrait;

/**
 * comportement de l'ascenseur qui monte et dessend en fonction de la personne qu'il recupere dans la liste d'appel
 * @author Checconi maxime, Pilot guillaume et Canessa Marine
 */
public class ComportementIntelligent2 implements ComportementAbstrait{
    
    private Ascenseur asc;
    
    
    public ComportementIntelligent2(){}
    public ComportementIntelligent2(Ascenseur asc, int parite, Etage defaut)
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
            	if(asc.quelqunVeutDessendreDeAsc()!=null || Immeuble.getListeAppel().contains(Etage.chercherEtageDsListe(asc.getEtageCourant()))){
            		attendre();
            	}
                asc.monter();
            }
            else{   //sinon faut qu'il redessende
            	asc.setMonte(false);
            	if(asc.quelqunVeutDessendreDeAsc()!=null || Immeuble.getListeAppel().contains(Etage.chercherEtageDsListe(asc.getEtageCourant()))){
            		attendre();
            	}
            	asc.descendre();
            }
        }
        else if((!asc.isMonte())&&(!asc.isPortesOuvertes())) //si l'ascenseur dessend
        {
            if(asc.getEtageCourant()>0)  //s'il est pas arrivé en bas
            {
            	if(asc.quelqunVeutDessendreDeAsc()!=null || Immeuble.getListeAppel().contains(Etage.chercherEtageDsListe(asc.getEtageCourant()))){
            		attendre();
            	}
            	asc.descendre();
            }
            else{   //sinon faut qu'il monte
                asc.setMonte(true);
            	if(asc.quelqunVeutDessendreDeAsc()!=null || Immeuble.getListeAppel().contains(Etage.chercherEtageDsListe(asc.getEtageCourant()))){
            		attendre();
            	}
            	asc.monter();
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
            e.printStackTrace();
        }
        }
		
        asc.ouvrirPorte();
        while(asc.quelqunVeutDessendreDeAsc()!=null)
        {
            asc.reveillerPersonne(asc.quelqunVeutDessendreDeAsc());
        }
         while(asc.quelqunVeutMonterDansAsc()!= null){
             if(accepterPersonne(asc.quelqunVeutMonterDansAsc())==true){
                 asc.reveillerPersonne(asc.quelqunVeutMonterDansAsc());
             }
        }
         
         try {
            Thread.sleep(asc.getTemporisation());
        } catch (InterruptedException e) {
                // TODO Auto-generated catch block
            e.printStackTrace();
        }
        asc.fermerPorte();
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
        return "Simplement Intelligent II";
    }

    public Class getType() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public int getParite(){return -1;}
}
