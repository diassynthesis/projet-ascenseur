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
public class Statistiques implements Serializable{

    private HashMap stats = null;

    private java.util.Date uDate = new java.util.Date();
    private java.sql.Date sDate = new java.sql.Date(System.currentTimeMillis());
    public Statistiques(){
        System.out.println(uDate);
        System.out.println(sDate);
    }

    public synchronized  void addStatistiques(java.util.Date date,Ascenseur asc, int trajet ){
        

        //Initilialisation si le tableau n'existe pas
        if(stats == null ){
            stats = new HashMap();
        }
        stats.put(date, trajet);
    }

    /**
     * @Override
     */
    @Override public void finalize()
     {
        
     }
}
