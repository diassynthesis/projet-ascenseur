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
 * @author aymeric
 */
public class StatistiquesTempsMoyen implements Serializable {

    //variable de logique
    private ArrayList arrayTemps = new ArrayList();
    private ArrayList arrayDates = new ArrayList();

    // End of variables declaration
    /**
     * @Override
     */
    public ArrayList getArrayTemps() {
        return arrayTemps;
    }

    public void setArrayTemps(ArrayList hashDa) {
        this.arrayTemps = hashDa;
    }

    public ArrayList getArrayDates() {
        return arrayDates;
    }

    public void setArrayDates(ArrayList hashDa) {
        this.arrayDates = hashDa;
    }

    public StatistiquesTempsMoyen() {
        arrayTemps = new ArrayList();
        arrayDates = new ArrayList();
    }

    public void setName(String str) {
    }

    public void setTitle(String str) {
    }

    public void test() {
        Date date = new Date();
        addStatistiquesTempsMoyen(date, new Long(1000));
    }

    /**
     * On ajoute une nouvelle statistique dans le HasMap
     * @param date
     * @param NumAsc
     * @param monte
     */
    public synchronized void addStatistiquesTempsMoyen(Date date, Long temps) {
        System.out.println("Temps d'attente : "+temps);
        this.arrayTemps.add(temps);
        this.arrayDates.add(date);
    }

    //calcule de les trajet en monté et en descente
    public ArrayList getTempsMoyenJour(Date dateDeb, Date dateFin) {

        // on parcour chaque numero d'ascenseur
        Date date;
        Long temp,e;
        ArrayList tempsMoyen = new ArrayList();
        tempsMoyen.add(new Long(0));
        tempsMoyen.add(new Long(0));
        tempsMoyen.add(new Long(0));
        tempsMoyen.add(new Long(0));
        tempsMoyen.add(new Long(0));
        tempsMoyen.add(new Long(0));
        tempsMoyen.add(new Long(0));
       // tempsMoyen.add(new Long(0));

        int size = arrayTemps.size(), i = 0, d=0,j=1,dBefore=0;
        while (i < size) { // personne
            
            date = (Date) arrayDates.get(i);
            d = date.getDay();
            if (i==0)
                dBefore = d;
            System.out.println(d);
            if (date.compareTo(dateDeb) > 0 && date.compareTo(dateFin) < 0) {
                //on prend le temps du jour
                e = (Long)tempsMoyen.get(d);
                //on ajoute au temps du jour le temps de la liste
                temp = e + (Long)this.arrayTemps.get(i);
                tempsMoyen.set(d, temp);
                //nb personne
                j++;

            }
            if (d!=dBefore) {
                temp = (Long)tempsMoyen.get(d);
                tempsMoyen.set(dBefore, temp/j);
                j=1;
            }
            i++;
            //jour d'avant
            dBefore = d;

        }
        temp = (Long)tempsMoyen.get(d);
        tempsMoyen.set(dBefore, temp/j);
        j=1;
        return tempsMoyen;
    }
}
