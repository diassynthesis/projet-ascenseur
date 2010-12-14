/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projetAscenseur;

import java.util.*;
import java.io.Serializable;

/**
 *
 * @author fabrice.bourgeon
 */
public class FichierConfiguration implements Serializable{

    /**
     * Tableaux pour les différents paramètres
     */
    HashMap paramsJournee ;

    public void setParamsJournee(HashMap paramsJournee) {
        this.paramsJournee = paramsJournee;
    }

    public void setParamsLogin(HashMap paramsLogin) {
        this.paramsLogin = paramsLogin;
    }

    public void setParamsNuitWeekEnd(HashMap paramsNuitWeekEnd) {
        this.paramsNuitWeekEnd = paramsNuitWeekEnd;
    }
    HashMap paramsNuitWeekEnd ;
    HashMap paramsLogin ;

    static private FichierConfiguration _instance = null;


    static public FichierConfiguration instance(){
            if(_instance == null){
                _instance = new FichierConfiguration();                
            }
            return _instance;
    }

    public FichierConfiguration(){

    }
    /*public boolean getEstInitialise(){return this.estInitialise;}
    public void setEstInitialise(boolean valeur){this.estInitialise = valeur;}*/

    
    /**
     * Initialise les valeurs par défaut des fréquentation en Journée
     */
    public boolean setDefaultParamsJournee(){

        //Initialisation
        paramsJournee = new HashMap();
        //Insertion des valeurs par défaut dans le HASH
        paramsJournee.put("RdC", new Integer(3434));
        paramsJournee.put("vingtPremiersEtages", new Integer(123));
        paramsJournee.put("cinqDerniersEtages", new Integer(1200));
        paramsJournee.put("sousSols", new Integer(99));
        paramsJournee.put("autresEtages", new Integer(-19));

        return true;
    }

    /**
     * Initialise les valeurs par défaut des fréquentation en Journée
     */
    public boolean setDefaultParamsNuitWeekEnd(){

        //Initialisation
        paramsNuitWeekEnd = new HashMap();
        //Insertion des valeurs par défaut dans le HASH
        paramsNuitWeekEnd.put("RdC", new Integer(3434));
        paramsNuitWeekEnd.put("vingtPremiersEtages", new Integer(123));
        paramsNuitWeekEnd.put("cinqDerniersEtages", new Integer(1200));
        paramsNuitWeekEnd.put("sousSols", new Integer(99));
        paramsNuitWeekEnd.put("autresEtages", new Integer(-19));
        return true;
    }

     public boolean setDefaultLogin(){

        //Insertion des valeurs par défaut dans le HASH
        paramsNuitWeekEnd.put("Login", new String("admin"));
        paramsNuitWeekEnd.put("mdp", new String("123123"));
        return true;
    }

    public HashMap getParamsJournee(){
        return this.paramsJournee;
    }
    public HashMap getParamsNuitWeekEnd(){
        return this.paramsNuitWeekEnd;
    }
    public HashMap getParamsLogin(){
        return this.paramsLogin;
    }
    
}