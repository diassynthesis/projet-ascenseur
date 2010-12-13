/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import projetAscenseur.*;
import projetAscenseur.visitor.Visitor;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.io.*;

/**
 * classe abstraite representant les AppelDest (groupe et AppelDesteule
 * @author Checconi maxime, Pilot guillaume et Canessa Marine
 */
public abstract class AppelDest extends JLabel{

    //************************ATTRIBUTS***************************

    private Etage source;
    private Etage dest;


    //*************************CONSTRUCTEURS***************************

    /**
     *premier constructeur
     */
    public AppelDest()
    {


    }
}




    //*********************ACCESSEURS************************************



    //************************METHODES****************************


