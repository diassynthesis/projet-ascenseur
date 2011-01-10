/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projetAscenseur;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;
import java.io.Serializable;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.util.Rotation;

/**
 *
 * @author fabrice.bourgeon
 */
public class Statistiques implements Serializable {

    //variable de logique
    private HashMap hashDate = new HashMap();
    private HashMap hashDirection = new HashMap();
    private int nbAsc = 6;

    // End of variables declaration

    /**
     * @Override
     */
    public HashMap getHashDate() {
        return hashDate;
    }

    public HashMap getHashDirection() {
        return hashDirection;
    }

    public void setHashDate(HashMap hashDa) {
        this.hashDate = hashDa;
    }

    public void setHashDirection(HashMap hashDir) {
        this.hashDirection = hashDir;
    }

    public Statistiques() {
         for(int i=0; i<nbAsc; i++){
            hashDate.put(new Integer(i), new ArrayList());
            hashDirection.put(new Integer(i), new ArrayList());
        }

    }

    public void setName(String str){
        
    }
    public void setTitle(String str){
        
    }

    public int getNbAsc(){
        return nbAsc;
    }

    public void setNbAsc(int nbAsc){
        this.nbAsc = nbAsc;
    }

    //calcule de les trajet en monté et en descente
    public HashMap calculeDeplacement(Date dateDeb, Date dateFin) {
        HashMap date = this.getHashDate();
        HashMap direction = this.getHashDirection();
        HashMap retour = new HashMap();
        ArrayList arrDate;
        ArrayList arrDirection;
        int nbMonte = 0;
        int nbDescente = 0;
        int size;
        Date dateDep;
        Integer dirDep;
        // on parcour chaque numero d'ascenseur
        for (int i = 0; i < this.nbAsc; i++) {
            //on recupere la liste des dates pour l'ascenseur I
            arrDate = (ArrayList) date.get(new Integer(i));
            //on recupere la liste des directions pour l'ascenseur I
            arrDirection = (ArrayList) direction.get( new Integer(i));
            //on regarde si l'index de l'escenseur existe bien dans les deux listes
            if (arrDate != null && arrDirection != null) {
                //on prend la taille des listes
                size = arrDate.size();
                //on parcour la liste des dates de l'ascenseur
                for (int j = 0; j < size; j++) {
                    // on récupere la date et la direction
                    dateDep = (Date) arrDate.get(j);
                    dirDep = (Integer) arrDirection.get(j);
                    // on vérifie que la date est bien comprise entre les dates indiquées en params
                    if (dateDep.compareTo(dateDeb) > 0 && dateDep.compareTo(dateFin) < 0) {
                        if (dirDep > 0) {
                            nbMonte++;
                        } else {
                            nbDescente++;
                        }
                    }
                }

                // apres le parcour de la liste on met le calcule dans une arrayListe puis un HachMap
                ArrayList arrRetour = new ArrayList() ;
                arrRetour.add(nbMonte);
                arrRetour.add(nbDescente);
                retour.put(i, arrRetour);
                nbMonte = 0;
                nbDescente = 0;
            }
        }

        return retour;
    }

    public void test(){
        Date date = new Date();
        addStatistiques(date, new Integer(1), new Integer(1));
        addStatistiques(date, new Integer(2), new Integer(2));
        addStatistiques(date, new Integer(3), new Integer(3));
    }


    /**
     * On ajoute une nouvelle statistique dans le HasMap
     * @param date
     * @param NumAsc
     * @param monte
     */
    public synchronized void addStatistiques(Date date, Integer NumAsc, Integer monte) {
        ArrayList arrDate = (ArrayList) hashDate.get(NumAsc);
        ArrayList arrDirection = (ArrayList) hashDirection.get(NumAsc);
        arrDate.add(date);
        arrDirection.add(monte);
    }

 
}
