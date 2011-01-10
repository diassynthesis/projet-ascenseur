/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projetAscenseur;

import projetAscenseur.*;
import projetAscenseur.visitor.Visitor;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.io.*;
import java.util.ArrayList;

/**
 * classe abstraite representant les AppelDest (groupe et AppelDesteule
 * @author Checconi maxime, Pilot guillaume et Canessa Marine
 */
public class Appel extends JLabel{

    //************************ATTRIBUTS***************************

    private Etage source;
    private Etage dest;
    private boolean monte;              //Si vaut 1 l'utilisateur
    private Ascenseur asc;

    private ArrayList<Appel> listeAppel;


    //*************************CONSTRUCTEURS***************************

    /**
     *premier constructeur
     */
    public Appel()
    {

    }

    public Appel(Etage src, Etage dst, boolean monte,Ascenseur asc)
    {
        this.source=src;
        this.dest=dst;
        this.monte=monte;
        this.asc = asc;

    }


    //*********************ACCESSEURS************************************

    public Etage getDest() {
        return dest;
    }

    public void setDest(Etage dest) {
        this.dest = dest;
    }

    public boolean getMonte() {
        return monte;
    }

    public void setMonte(boolean monte) {
        this.monte = monte;
    }

    public Etage getSource() {
        return source;
    }

    public void setSource(Etage source) {
        this.source = source;
    }

    public ArrayList<Appel> getListeAppel() {
        return listeAppel;
    }


    //************************METHODES****************************


    /**
     * ajouter un appel a la liste
     * @param l'appel a ajouter
     */
    public synchronized void listAppelAdd(Appel call)
    {
    	getListeAppel().add(call);
       
    }

    /**
     * supprimer un appel a la liste
     * @param l'appel a supprimer
     */
    public synchronized void listAppelRem(Appel call)
    {
        if(listeAppel.contains(call))
        {
            getListeAppel().remove(call);
          
        }
    }

   public Ascenseur getAsc() {
        return asc;
    }

    public void setAsc(Ascenseur asc) {
        this.asc = asc;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Appel other = (Appel) obj;
        if (this.source != other.source && (this.source == null || !this.source.equals(other.source))) {
            return false;
        }
        if (this.dest != other.dest && (this.dest == null || !this.dest.equals(other.dest))) {
            return false;
        }
        if (this.monte != other.monte) {
            return false;
        }
        return true;
    }







}