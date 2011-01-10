
package projetAscenseur.strategy.concreteStrategy;

import projetAscenseur.personne.Personnes;
import projetAscenseur.*;
import projetAscenseur.strategy.ComportementAbstrait;

/**
 * comportement intelligent qui recupere les personnes en fonction de l'ordre d'appel
 * @author Checconi maxime, Pilot guillaume et Canessa Marine
 */
public class ComportementOptimisationEnergie implements ComportementAbstrait{

    private Ascenseur asc;
    private int parite =0;

    public int getParite() {
        return parite;
    }

    public void setParite(int parite) {
        this.parite = parite;
    }


    public ComportementOptimisationEnergie(){}
    public ComportementOptimisationEnergie(Ascenseur asc)
    {
        this.asc=asc;
    }


    public void setAscenseur(Ascenseur asc){  this.asc = asc;  }

     /**
     * l'ascenseur se deplace dans les etages
     */
    public void seDeplacer()
    {

    	//Si il y a personne dans l'ascenseur
    	 if(asc.getListePersonne().size()==0){
            System.out.println("Je teste si je peux etre sélectionné : " + asc.getNumAscenseur());
            if(Immeuble.ascenseurLePlusProcheEnergiquement(asc)!=null){
                int etageAppelant = Immeuble.ascenseurLePlusProcheEnergiquement(asc).getNumEtage();
                System.out.println("L'ascenseur est sélectionné : " + asc.getNumAscenseur());
                //L'ascenseur doit aller a cet etage
                if(asc.getEtageCourant()<etageAppelant){
                    asc.monter();
                }
                else if(asc.getEtageCourant()>etageAppelant){
                    asc.descendre();
                }
                else if (asc.getEtageCourant()==etageAppelant && !Immeuble.ascenseurLePlusProcheEnergiquement(asc).getListePersonne().isEmpty()){

                    // on arrive a l'etage de la personne on regle l'ascenseur en fonction de la personne
                    if(Immeuble.ascenseurLePlusProche(asc).getListePersonne().get(0).veutMonter() == true && asc.isMonte()){
                        attendre();
                    }
                    else if(Immeuble.ascenseurLePlusProche(asc).getListePersonne().get(0).veutMonter() == false && !asc.isMonte()){
                        attendre();
                    }
                    else if(Immeuble.ascenseurLePlusProche(asc).getListePersonne().get(0).veutMonter() == true && !asc.isMonte()){
                        asc.setMonte(true);
                        attendre();
                    }
                    else if(Immeuble.ascenseurLePlusProche(asc).getListePersonne().get(0).veutMonter() == false && asc.isMonte()){
                        asc.setMonte(false);
                        attendre();
                    }
                }
            }
    }
    //Si il y a qq1 dans l'ascenseur
    else{
            int etageDestPers0 = asc.getListePersonne().get(0).getEtageArrive().getNumEtage();//Etage de la 1ere pers de la liste!

            if(etageDestPers0<asc.getEtageCourant()){
                asc.descendre();
                if(asc.quelqunVeutDessendreDeAsc()!=null){
                        attendre();
                }
            }
            else if(etageDestPers0>asc.getEtageCourant()){
                asc.monter();
                if(asc.quelqunVeutDessendreDeAsc()!=null){
                        attendre();
                }
            }
            else if(etageDestPers0==asc.getEtageCourant()){
                attendre();
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
        while(asc.quelqunVeutMonterDansAsc()!= null ){
             asc.reveillerPersonne(asc.quelqunVeutMonterDansAsc());
        }
         try {
                Thread.sleep(asc.getTemporisation());
        } catch (InterruptedException e) {
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
         * recherche si un ascenseur est deja là pour recuperer les gens
         * @return vrai ou faux
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

    public String getName() {
        return "Optimisation d'énergie";
    }

    public Class getType() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
