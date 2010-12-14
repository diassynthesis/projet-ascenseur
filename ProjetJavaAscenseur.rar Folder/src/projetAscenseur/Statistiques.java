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

    private HashMap HashStatistiques = null;

    public HashMap getHashStatistiques() {
        return HashStatistiques;
    }

    public void setHashStatistiques(HashMap HashStatistiques) {
        this.HashStatistiques = HashStatistiques;
    }

    private java.util.Date uDate = new java.util.Date();
    private java.sql.Date sDate = new java.sql.Date(System.currentTimeMillis());
    public Statistiques(){
        System.out.println(uDate);
        System.out.println(sDate);
    }

    /**
     * On ajoute une nouvelle statistique dans le HasMap
     * @param date
     * @param NumAsc
     * @param monte
     */
    public synchronized  void addStatistiques(java.util.Date date,int NumAsc, int monte ){
        

        //Initilialisation si le tableau n'existe pas
        if(HashStatistiques == null ){
            HashStatistiques = new HashMap();
        }
        if(monte>0)
            HashStatistiques.put(date, NumAsc);
        else
            HashStatistiques.put(date, -NumAsc);

    }

    
    /**
     * @Override
     */
    @Override public void finalize()
     {
        
     }
}
