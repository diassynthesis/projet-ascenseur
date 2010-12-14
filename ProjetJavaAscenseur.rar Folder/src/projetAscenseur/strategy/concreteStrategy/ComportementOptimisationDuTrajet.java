
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
    //private int parite;             //todo // mode de fonctionnement pair si vaut 0 et impair si vaut 1
    //private Etage EtageDefaut;      //todo
    private int NextHop;


    public ComportementOptimisationDuTrajet(){}
    public ComportementOptimisationDuTrajet(Ascenseur asc ) //, int parite, Etage defaut) //todo
    {
        this.asc=asc;
        //this.parite=parite;           //todo
        //this.EtageDefaut=defaut;      //todo

    }

     /**
     * l'ascenseur se deplace dans les etages
     */
    public void seDeplacer()
    {
        if(!asc.getMaintenance())                            // Si l'ascenseur n'est pas en maintenance
        {

            if( Immeuble.AscLePlusProcheTemporellement(asc) != null)            // Si il existe un étage pour la prochaine étape
            {
               NextHop = Immeuble.AscLePlusProcheTemporellement(asc).getNumEtage();
            }
            else
            {
               NextHop = EtageDefaut.getNumEtage();
            }

            //L'ascenseur réagit en fonction du NextHop
            if(asc.getEtageCourant() == NextHop)
            {
                attendre();
            }
            if(asc.getEtageCourant()<NextHop)
            {
                asc.monter();
            }
            else if(asc.getEtageCourant()>NextHop)
            {
                asc.descendre();
            }

        }
        else
        {
            // Faire le cas Maintenance: retour à l'étage 0 et on ouvre les portes quelques secondes puis on ferme les portes.
            System.out.println("L'ascenseur n°" + asc.getNumAscenseur() + "est en maintenance");

            while(asc.getEtageCourant()>0)              //Tant que l'ascenseur n'est pas en bas
            {
                asc.descendre();
            }
            asc.ouvrirPorte();
            try {                                       // Permet de laisser la porte ouverte pour faire descendre les personnes avant la fermerture de la porte
                    Thread.sleep(500);
            } catch (InterruptedException e) {
                    e.printStackTrace();
            }
            asc.fermerPorte();

        }

    }


    /**
     * simule l'attente de l'ascenseur pendant que les personnes montent dedans ou en descendent
     * quand tout le monde est monté il ferme ses portes
     */
    public void attendre()
    {
        System.out.println("L'ascenseur doit récupérer une personne");
        // Faire attention de prendre en compte qu'il est possible que l'ascenseur se soit juste replacé

        // Test d'un copie colle de l'autre programme todo

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




        // Fin Test todo


    }

    /**
     * indique si l'ascenseur accepte de prendre une personne ou pas
     * @return vrai si l'ascenseur accepte quelqu'un
     */
    public boolean accepterPersonne(Personnes p)
    {
        boolean value = false;
        if(asc.getMaintenance())                                                // Si l'ascenseur est en maintenance il ne va accepter personne
        {
            value = false;
        }
        else
        {
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
    public int getParite(){return -1;}
}
