package projetAscenseur;

import fr.unice.plugin.Plugin;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import projetAscenseur.personne.Personnes;
import projetAscenseur.factories.concreteFactories.GroupeAleatoireImpl;
import projetAscenseur.factories.concreteFactories.GroupeMonteImpl;
import projetAscenseur.factories.concreteFactories.PersGroupeFactory;
import projetAscenseur.factories.PersonneFactory;
import projetAscenseur.factories.PersonneMaker;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import projetAscenseur.factories.concreteFactories.PersonneSeuleAleatoireImpl;
import projetAscenseur.factories.concreteFactories.PersonneSeuleMonteImpl;
import projetAscenseur.strategy.concreteStrategy.ComportementArretEtage;
import projetAscenseur.strategy.ComportementAbstrait;
import fr.unice.plugin.Plugin;
import fr.unice.plugin.PluginLoader;
//import de la classe qui gere l'édition des variables!
import java.util.Date;
import projetAscenseur.EnregistrementConf;



import fr.unice.plugin.Plugin;
import fr.unice.plugin.PluginLoader;

/**
 * classe representant l'interface graphique de la simulation
 * @author Checconi maxime, Pilot guillaume et Canessa Marine
 */
public class Manager extends JFrame {

    //********************ATTRIBUTS*****************************
    private Simulateur simulateur;
    private static boolean enCreation = true;
    private EnregistrementConf enregistrementConf = null;
    private EnregistrementStat enregistrementStat = null;
    private EnregistrementStat enregistrementStatTempsMoyen = null;

    //***INTERFACE******//
    // Variables declaration - do not modify
    private javax.swing.JButton jButtonDemarrerGene;
    private javax.swing.JButton jButtonDemarrer1;
    private javax.swing.JButton jButtonDemarrer2;
    private javax.swing.JButton jButtonDemarrer3;
    private javax.swing.JButton jButtonDemarrer4;
    private javax.swing.JButton jButtonDemarrer5;
    private javax.swing.JButton jButtonDemarrer6;
    private javax.swing.JButton jButtonMaintenance1;
    private javax.swing.JButton jButtonMaintenance2;
    private javax.swing.JButton jButtonMaintenance3;
    private javax.swing.JButton jButtonMaintenance4;
    private javax.swing.JButton jButtonMaintenance5;
    private javax.swing.JButton jButtonMaintenance6;
    private javax.swing.JButton jButtonMaintenanceGene;
    private javax.swing.JComboBox jComboBoxPlugin;
    private javax.swing.JLabel jLabelConso1;
    private javax.swing.JLabel jLabelConso2;
    private javax.swing.JLabel jLabelConso3;
    private javax.swing.JLabel jLabelConso4;
    private javax.swing.JLabel jLabelConso5;
    private javax.swing.JLabel jLabelConso6;
    private javax.swing.JLabel jLabelEtage1;
    private javax.swing.JLabel jLabelEtage2;
    private javax.swing.JLabel jLabelEtage3;
    private javax.swing.JLabel jLabelEtage4;
    private javax.swing.JLabel jLabelEtage5;
    private javax.swing.JLabel jLabelEtage6;
    private javax.swing.JLabel jLabelEtat1;
    private javax.swing.JLabel jLabelEtat2;
    private javax.swing.JLabel jLabelEtat3;
    private javax.swing.JLabel jLabelEtat4;
    private javax.swing.JLabel jLabelEtat5;
    private javax.swing.JLabel jLabelEtat6;
    private javax.swing.JLabel jLabelLabConso1;
    private javax.swing.JLabel jLabelLabConso2;
    private javax.swing.JLabel jLabelLabConso3;
    private javax.swing.JLabel jLabelLabConso4;
    private javax.swing.JLabel jLabelLabConso5;
    private javax.swing.JLabel jLabelLabConso6;
    private javax.swing.JLabel jLabelLabEtage1;
    private javax.swing.JLabel jLabelLabEtage2;
    private javax.swing.JLabel jLabelLabEtage3;
    private javax.swing.JLabel jLabelLabEtage4;
    private javax.swing.JLabel jLabelLabEtage5;
    private javax.swing.JLabel jLabelLabEtage6;
    private javax.swing.JLabel jLabelLabEtat1;
    private javax.swing.JLabel jLabelLabEtat2;
    private javax.swing.JLabel jLabelLabEtat3;
    private javax.swing.JLabel jLabelLabEtat4;
    private javax.swing.JLabel jLabelLabEtat5;
    private javax.swing.JLabel jLabelLabEtat6;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenu jMenuEdit;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenuItem jMenuItemAlgorithme;
    private javax.swing.JMenuItem jMenuItemConso;
    private javax.swing.JMenuItem jMenuItemTmoyen;
    private javax.swing.JMenu jMenuStatistiques;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;

    private boolean activity = true;
    private long debutSessionInactivity;
    private PluginjComboBoxPluginFactory pluginMenuItemFactory;
    private PluginLoader PluginLoader;
    private JMenu menuPlugin2;
    private Plugin[] plugins;

    //************************ACCESSEURS*****************************
    public static boolean isEnCreation() {
        return enCreation;
    }

    public void setEnCreation(boolean enCreation) {
        this.enCreation = enCreation;
    }

    public boolean isFocused() {
        if(simulateur != null)
            return simulateur.isFocused();
        return false;
    }

    public boolean hasFocus() {
        return rootPane.hasFocus();
    }

    public JComboBox getjComboBoxPlugin(){
        return jComboBoxPlugin;
    }
    //******************************METHODES*********************************

    /**
     * Permet de déconnecter l'application au bout de 10 minutes
     * Fait disparaitre uniquement le manager car le simulateur ne fait pas parti de l'appli
     * Un formulaire de connexion s'affiche
     */
    public void session(){
        debutSessionInactivity = System.currentTimeMillis();
        while(true){
            if(this.isActive() || isFocused()){
                debutSessionInactivity = System.currentTimeMillis();
                //System.out.println("Focus");
            }
            if(System.currentTimeMillis()-this.debutSessionInactivity > 600000){
                //System.out.println("Focus perdu");
                this.setVisible(false);
                //simulateur.setVisible(false);
                Identification auth = new Identification();
                auth.setVisible(true);
                while(!auth.isLogged());
                this.setVisible(true);
                simulateur.setVisible(true);
                debutSessionInactivity = System.currentTimeMillis();
            }
        }

        
    }


    public Manager(Simulateur sim) {
        super("Interface du Manager");
        simulateur = sim;

        try {
            // init fenetre du manager
            initManager();
            this.setVisible(true);
            //init fenetre de variables
            enregistrementConf = new EnregistrementConf();
            enregistrementStat = new EnregistrementStat();
            enregistrementStatTempsMoyen = new EnregistrementStat(3);

            enregistrementConf.setVisible(false);
            enregistrementStat.setVisible(false);
            enregistrementStatTempsMoyen.setVisible(false);

        } catch (MalformedURLException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean checkAuthentification(String login, String mdp){

        return false;
    }
    /**
     * initialise l'interface du simulateur
     */
    public void initManager() throws MalformedURLException {


        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu4 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        jPanel4 = new javax.swing.JPanel();
        jLabelLabEtat4 = new javax.swing.JLabel();
        jLabelLabEtage4 = new javax.swing.JLabel();
        jLabelLabConso4 = new javax.swing.JLabel();
        jButtonMaintenance4 = new javax.swing.JButton();
        jButtonDemarrer4 = new javax.swing.JButton();
        jLabelEtat4 = new javax.swing.JLabel();
        jLabelEtage4 = new javax.swing.JLabel();
        jLabelConso4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabelLabEtat1 = new javax.swing.JLabel();
        jLabelLabEtage1 = new javax.swing.JLabel();
        jLabelLabConso1 = new javax.swing.JLabel();
        jButtonMaintenance1 = new javax.swing.JButton();
        jButtonDemarrer1 = new javax.swing.JButton();
        jLabelEtat1 = new javax.swing.JLabel();
        jLabelEtage1 = new javax.swing.JLabel();
        jLabelConso1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabelLabEtat3 = new javax.swing.JLabel();
        jLabelLabEtage3 = new javax.swing.JLabel();
        jLabelLabConso3 = new javax.swing.JLabel();
        jButtonMaintenance3 = new javax.swing.JButton();
        jButtonDemarrer3 = new javax.swing.JButton();
        jLabelEtat3 = new javax.swing.JLabel();
        jLabelEtage3 = new javax.swing.JLabel();
        jLabelConso3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabelLabEtat2 = new javax.swing.JLabel();
        jLabelLabEtage2 = new javax.swing.JLabel();
        jLabelLabConso2 = new javax.swing.JLabel();
        jButtonMaintenance2 = new javax.swing.JButton();
        jButtonDemarrer2 = new javax.swing.JButton();
        jLabelEtat2 = new javax.swing.JLabel();
        jLabelEtage2 = new javax.swing.JLabel();
        jLabelConso2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabelLabEtat5 = new javax.swing.JLabel();
        jLabelLabEtage5 = new javax.swing.JLabel();
        jLabelLabConso5 = new javax.swing.JLabel();
        jButtonMaintenance5 = new javax.swing.JButton();
        jButtonDemarrer5 = new javax.swing.JButton();
        jLabelEtat5 = new javax.swing.JLabel();
        jLabelEtage5 = new javax.swing.JLabel();
        jLabelConso5 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabelLabEtat6 = new javax.swing.JLabel();
        jLabelLabEtage6 = new javax.swing.JLabel();
        jLabelLabConso6 = new javax.swing.JLabel();
        jButtonMaintenance6 = new javax.swing.JButton();
        jButtonDemarrer6 = new javax.swing.JButton();
        jLabelEtat6 = new javax.swing.JLabel();
        jLabelEtage6 = new javax.swing.JLabel();
        jLabelConso6 = new javax.swing.JLabel();
        jButtonMaintenanceGene = new javax.swing.JButton();
        jButtonDemarrerGene = new javax.swing.JButton();
        jComboBoxPlugin = new javax.swing.JComboBox();
        jButtonDemarrerGene = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuEdit = new javax.swing.JMenu();
        jMenuItemAlgorithme = new javax.swing.JMenuItem();
        jMenuStatistiques = new javax.swing.JMenu();
        jMenuItemConso = new javax.swing.JMenuItem();
        jMenuItemTmoyen = new javax.swing.JMenuItem();

        jMenu4.setText("File");
        jMenuBar2.add(jMenu4);

        jMenu5.setText("Edit");
        jMenuBar2.add(jMenu5);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Ascenseur");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Ascenseur"));
        jPanel4.setMinimumSize(new java.awt.Dimension(221, 200));
        jPanel4.setPreferredSize(new java.awt.Dimension(250, 200));

        jLabelLabEtat4.setText("Etat :");

        jLabelLabEtage4.setText("Etage :");

        jLabelLabConso4.setText("Conso du jour :");

        jButtonMaintenance4.setText("Maintenance");
        jButtonMaintenance4.setEnabled(false);
        jButtonMaintenance4.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMaintenance4ActionPerformed(evt);
            }
        });

        jButtonDemarrer4.setText("Démarrer");
        jButtonDemarrer4.setEnabled(false);
        jButtonDemarrer4.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDemarrer4ActionPerformed(evt);
            }
        });

        jLabelEtat4.setText("non utilisé");

        jLabelEtage4.setText("0");

        jLabelConso4.setText("0");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addContainerGap().addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addComponent(jLabelLabEtat4).addGap(18, 18, 18).addComponent(jLabelEtat4)).addGroup(jPanel4Layout.createSequentialGroup().addComponent(jLabelLabEtage4).addGap(18, 18, 18).addComponent(jLabelEtage4)).addGroup(jPanel4Layout.createSequentialGroup().addComponent(jLabelLabConso4).addGap(18, 18, 18).addComponent(jLabelConso4)).addGroup(jPanel4Layout.createSequentialGroup().addComponent(jButtonMaintenance4).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jButtonDemarrer4))).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addContainerGap().addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabelLabEtat4).addComponent(jLabelEtat4)).addGap(18, 18, 18).addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabelLabEtage4).addComponent(jLabelEtage4)).addGap(18, 18, 18).addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabelLabConso4).addComponent(jLabelConso4)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE).addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jButtonMaintenance4).addComponent(jButtonDemarrer4)).addContainerGap()));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Ascenseur"));
        jPanel1.setMinimumSize(new java.awt.Dimension(250, 200));
        jPanel1.setPreferredSize(new java.awt.Dimension(250, 200));

        jLabelLabEtat1.setText("Etat :");

        jLabelLabEtage1.setText("Etage :");

        jLabelLabConso1.setText("Conso du jour :");

        jButtonMaintenance1.setText("Maintenance");
        jButtonMaintenance1.setEnabled(false);
        jButtonMaintenance1.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMaintenance1ActionPerformed(evt);
            }
        });

        jButtonDemarrer1.setText("Démarrer");
        jButtonDemarrer1.setEnabled(false);
        jButtonDemarrer1.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDemarrer1ActionPerformed(evt);
            }
        });

        jLabelEtat1.setText("non utilisé");

        jLabelEtage1.setText("0");

        jLabelConso1.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(jLabelLabEtat1).addGap(18, 18, 18).addComponent(jLabelEtat1)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(jLabelLabEtage1).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jLabelEtage1)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(jLabelLabConso1).addGap(18, 18, 18).addComponent(jLabelConso1)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(jButtonMaintenance1).addGap(6, 6, 6).addComponent(jButtonDemarrer1))).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabelLabEtat1).addComponent(jLabelEtat1)).addGap(18, 18, 18).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabelLabEtage1).addComponent(jLabelEtage1)).addGap(18, 18, 18).addComponent(jLabelLabConso1).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(jLabelConso1).addGap(18, 18, 18))).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jButtonMaintenance1).addComponent(jButtonDemarrer1)).addContainerGap()));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Ascenseur"));
        jPanel3.setMinimumSize(new java.awt.Dimension(221, 200));
        jPanel3.setPreferredSize(new java.awt.Dimension(221, 200));

        jLabelLabEtat3.setText("Etat :");

        jLabelLabEtage3.setText("Etage :");

        jLabelLabConso3.setText("Conso du jour :");

        jButtonMaintenance3.setText("Maintenance");
        jButtonMaintenance3.setEnabled(false);
        jButtonMaintenance3.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMaintenance3ActionPerformed(evt);
            }
        });

        jButtonDemarrer3.setText("Démarrer");
        jButtonDemarrer3.setEnabled(false);
        jButtonDemarrer3.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDemarrer3ActionPerformed(evt);
            }
        });

        jLabelEtat3.setText("non utilisé");

        jLabelEtage3.setText("0");

        jLabelConso3.setText("0");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addComponent(jLabelLabEtat3).addGap(18, 18, 18).addComponent(jLabelEtat3)).addGroup(jPanel3Layout.createSequentialGroup().addComponent(jLabelLabConso3).addGap(18, 18, 18).addComponent(jLabelConso3)).addGroup(jPanel3Layout.createSequentialGroup().addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup().addComponent(jLabelLabEtage3).addGap(18, 18, 18).addComponent(jLabelEtage3)).addComponent(jButtonMaintenance3, javax.swing.GroupLayout.Alignment.LEADING)).addGap(6, 6, 6).addComponent(jButtonDemarrer3))).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabelLabEtat3).addComponent(jLabelEtat3)).addGap(18, 18, 18).addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabelLabEtage3).addComponent(jLabelEtage3)).addGap(18, 18, 18).addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabelLabConso3).addComponent(jLabelConso3)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE).addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jButtonMaintenance3).addComponent(jButtonDemarrer3)).addContainerGap()));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Ascenseur"));
        jPanel2.setMinimumSize(new java.awt.Dimension(250, 200));
        jPanel2.setPreferredSize(new java.awt.Dimension(250, 200));

        jLabelLabEtat2.setText("Etat :");

        jLabelLabEtage2.setText("Etage :");

        jLabelLabConso2.setText("Conso du jour :");

        jButtonMaintenance2.setText("Maintenance");
        jButtonMaintenance2.setEnabled(false);
        jButtonMaintenance2.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMaintenance2ActionPerformed(evt);
            }
        });

        jButtonDemarrer2.setText("Démarrer");
        jButtonDemarrer2.setEnabled(false);
        jButtonDemarrer2.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDemarrer2ActionPerformed(evt);
            }
        });

        jLabelEtat2.setText("non utilisé");

        jLabelEtage2.setText("0");

        jLabelConso2.setText("0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addComponent(jLabelLabEtat2).addGap(18, 18, 18).addComponent(jLabelEtat2)).addGroup(jPanel2Layout.createSequentialGroup().addComponent(jLabelLabEtage2).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jLabelEtage2)).addGroup(jPanel2Layout.createSequentialGroup().addComponent(jLabelLabConso2).addGap(18, 18, 18).addComponent(jLabelConso2)).addGroup(jPanel2Layout.createSequentialGroup().addComponent(jButtonMaintenance2).addGap(6, 6, 6).addComponent(jButtonDemarrer2))).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabelLabEtat2).addComponent(jLabelEtat2)).addGap(18, 18, 18).addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabelLabEtage2).addComponent(jLabelEtage2)).addGap(18, 18, 18).addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabelLabConso2).addComponent(jLabelConso2)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE).addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jButtonMaintenance2).addComponent(jButtonDemarrer2)).addContainerGap()));

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Ascenseur"));
        jPanel5.setMinimumSize(new java.awt.Dimension(250, 200));
        jPanel5.setPreferredSize(new java.awt.Dimension(250, 200));

        jLabelLabEtat5.setText("Etat :");

        jLabelLabEtage5.setText("Etage :");

        jLabelLabConso5.setText("Conso du jour :");

        jButtonMaintenance5.setText("Maintenance");
        jButtonMaintenance5.setEnabled(false);
        jButtonMaintenance5.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMaintenance5ActionPerformed(evt);
            }
        });

        jButtonDemarrer5.setText("Démarrer");
        jButtonDemarrer5.setEnabled(false);
        jButtonDemarrer5.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDemarrer5ActionPerformed(evt);
            }
        });

        jLabelEtat5.setText("non utilisé");

        jLabelEtage5.setText("0");

        jLabelConso5.setText("0");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel5Layout.createSequentialGroup().addContainerGap().addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel5Layout.createSequentialGroup().addComponent(jLabelLabEtat5).addGap(18, 18, 18).addComponent(jLabelEtat5)).addGroup(jPanel5Layout.createSequentialGroup().addComponent(jLabelLabConso5).addGap(18, 18, 18).addComponent(jLabelConso5)).addGroup(jPanel5Layout.createSequentialGroup().addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup().addComponent(jLabelLabEtage5).addGap(27, 27, 27).addComponent(jLabelEtage5)).addComponent(jButtonMaintenance5, javax.swing.GroupLayout.Alignment.LEADING)).addGap(6, 6, 6).addComponent(jButtonDemarrer5))).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel5Layout.createSequentialGroup().addContainerGap().addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabelLabEtat5).addComponent(jLabelEtat5)).addGap(18, 18, 18).addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabelLabEtage5).addComponent(jLabelEtage5)).addGap(18, 18, 18).addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabelLabConso5).addComponent(jLabelConso5)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE).addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jButtonMaintenance5).addComponent(jButtonDemarrer5)).addContainerGap()));

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Ascenseur"));
        jPanel6.setMinimumSize(new java.awt.Dimension(200, 200));
        jPanel6.setPreferredSize(new java.awt.Dimension(200, 200));

        jLabelLabEtat6.setText("Etat :");

        jLabelLabEtage6.setText("Etage :");

        jLabelLabConso6.setText("Conso du jour :");

        jButtonMaintenance6.setText("Maintenance");
        jButtonMaintenance6.setEnabled(false);
        jButtonMaintenance6.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMaintenance6ActionPerformed(evt);
            }
        });

        jButtonDemarrer6.setText("Démarrer");
        jButtonDemarrer6.setEnabled(false);
        jButtonDemarrer6.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDemarrer6ActionPerformed(evt);
            }
        });

        jLabelEtat6.setText("non utilisé");

        jLabelEtage6.setText("0");

        jLabelConso6.setText("0");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel6Layout.createSequentialGroup().addContainerGap().addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel6Layout.createSequentialGroup().addComponent(jLabelLabEtat6).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jLabelEtat6)).addGroup(jPanel6Layout.createSequentialGroup().addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel6Layout.createSequentialGroup().addComponent(jLabelLabEtage6).addGap(18, 18, 18).addComponent(jLabelEtage6)).addComponent(jButtonMaintenance6).addComponent(jLabelLabConso6)).addGap(6, 6, 6).addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabelConso6).addComponent(jButtonDemarrer6)))).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel6Layout.createSequentialGroup().addContainerGap().addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabelLabEtat6).addComponent(jLabelEtat6)).addGap(18, 18, 18).addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabelLabEtage6).addComponent(jLabelEtage6)).addGap(18, 18, 18).addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabelLabConso6).addComponent(jLabelConso6)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE).addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jButtonMaintenance6).addComponent(jButtonDemarrer6)).addContainerGap()));

        jButtonMaintenanceGene.setText("Maintenance générale");
        jButtonMaintenanceGene.setEnabled(false);
        jButtonMaintenanceGene.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMaintenanceGeneActionPerformed(evt);
            }
        });

        jButtonDemarrerGene.setText("Démarrage générale");
        jButtonDemarrerGene.setEnabled(false);
        jButtonDemarrerGene.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDemarrerGeneActionPerformed(evt);
            }
        });

       // jComboBoxPlugin.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Trajet", "Economie d'énergie"}));
//        jComboBoxPlugin.addActionListener(new java.awt.event.ActionListener() {
//
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                jComboBoxPluginActionPerformed(evt);
//            }
//        });

        jButtonDemarrerGene.setText("Démarrage ");

        jMenuFile.setText("File");
        jMenuBar1.add(jMenuFile);

        jMenuEdit.setText("Edit");

        jMenuItemAlgorithme.setText("Algorithme");
        jMenuItemAlgorithme.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAlgorithmeActionPerformed(evt);
            }
        });
        jMenuEdit.add(jMenuItemAlgorithme);

        jMenuBar1.add(jMenuEdit);

        jMenuStatistiques.setText("Statistiques");

        jMenuItemConso.setText("Consommation");


        jMenuItemConso.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemConsoActionPerformed(evt);
            }
        });

        jMenuStatistiques.add(jMenuItemConso);

        jMenuItemTmoyen.setText("Temps moyen");

        jMenuItemTmoyen.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTempsMoyenActionPerformed(evt);
            }
        });
        jMenuStatistiques.add(jMenuItemTmoyen);

        jMenuBar1.add(jMenuStatistiques);



        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jComboBoxPlugin, javax.swing.GroupLayout.Alignment.TRAILING, 0, 258, Short.MAX_VALUE).addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE).addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE).addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE).addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE))).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(194, 194, 194).addComponent(jButtonMaintenanceGene, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jButtonDemarrerGene, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))).addContainerGap()));



        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addGap(21, 21, 21).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jButtonDemarrerGene).addComponent(jComboBoxPlugin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jButtonMaintenanceGene)).addContainerGap()));

        pack();
        //initialisation de la comboBox des plugins
        initMenuPlugin();
    }// </editor-fold>

    private void jMenuItemAlgorithmeActionPerformed(java.awt.event.ActionEvent evt) {
        enregistrementConf.setVisible(true);
    }

    private void jButtonMaintenance4ActionPerformed(java.awt.event.ActionEvent evt) {
        jButtonMaintenanceActionPerformed(4);
    }

    private void jButtonMaintenance1ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        Immeuble immeuble = simulateur.getImmeuble();
        ArrayList<Ascenseur> listeAscenseur = immeuble.getListeAscenseur();
        Ascenseur asc = listeAscenseur.get(0);
        if (asc.getMaintenance()) {
            this.setMaintenance(0, false);
        } else {
            this.setMaintenance(0, true);
        }
    }

    private void jButtonMaintenance2ActionPerformed(java.awt.event.ActionEvent evt) {
        jButtonMaintenanceActionPerformed(2);
    }

    private void jButtonMaintenance3ActionPerformed(java.awt.event.ActionEvent evt) {
        jButtonMaintenanceActionPerformed(3);
    }

    private void jButtonMaintenance5ActionPerformed(java.awt.event.ActionEvent evt) {
        jButtonMaintenanceActionPerformed(5);
    }

    private void jButtonMaintenance6ActionPerformed(java.awt.event.ActionEvent evt) {
        jButtonMaintenanceActionPerformed(6);
    }

    private void jButtonDemarrer1ActionPerformed(java.awt.event.ActionEvent evt) {
        jButtonMaintenanceActionPerformed(1);
    }

    private void jButtonDemarrer2ActionPerformed(java.awt.event.ActionEvent evt) {
        jButtonMaintenanceActionPerformed(2);
    }

    private void jButtonDemarrer3ActionPerformed(java.awt.event.ActionEvent evt) {
        jButtonMaintenanceActionPerformed(3);
    }

    private void jButtonDemarrer6ActionPerformed(java.awt.event.ActionEvent evt) {
        jButtonMaintenanceActionPerformed(6);
    }

    private void jButtonDemarrer5ActionPerformed(java.awt.event.ActionEvent evt) {
        jButtonMaintenanceActionPerformed(5);
    }

    private void jButtonDemarrer4ActionPerformed(java.awt.event.ActionEvent evt) {
        jButtonMaintenanceActionPerformed(4);
    }

    private void jMenuItemConsoActionPerformed(java.awt.event.ActionEvent evt) {
        //this.enregistrementStat.initChart();
        enregistrementStat.setVisible(true);
    }
    private void jMenuItemTempsMoyenActionPerformed(java.awt.event.ActionEvent evt) {
        //this.enregistrementStat.initChart();
        enregistrementStatTempsMoyen.setVisible(true);
    }

    private void jButtonMaintenanceGeneActionPerformed(java.awt.event.ActionEvent evt) {
        Immeuble immeuble = simulateur.getImmeuble();
        ArrayList<Ascenseur> listeAscenseur = immeuble.getListeAscenseur();
        int size = listeAscenseur.size();
        Ascenseur asc;
        for (int i = 0; i < size; i++) {
            asc = listeAscenseur.get(i);

            this.setMaintenance(i, true);
            // donc bouton generale devient active
            this.jButtonMaintenanceGene.setEnabled(false);
            // on met de le bouton de demarrage gene en active
            this.jButtonDemarrerGene.setEnabled(true);
        }
        this.jButtonMaintenanceGene.setEnabled(false);
        this.jButtonDemarrerGene.setEnabled(true);
    }

    private void jButtonDemarrerGeneActionPerformed(java.awt.event.ActionEvent evt) {
        Immeuble immeuble = simulateur.getImmeuble();
        ArrayList<Ascenseur> listeAscenseur = immeuble.getListeAscenseur();
        int size = listeAscenseur.size();

        for (int i = 0; i < size; i++) {
            this.setMaintenance(i, false);
            // donc bouton generale devient active
            this.jButtonMaintenanceGene.setEnabled(true);
            // on met de le bouton de demarrage gene en active
            this.jButtonDemarrerGene.setEnabled(true);
        }
        this.jButtonDemarrerGene.setEnabled(false);
        this.jButtonMaintenanceGene.setEnabled(true);
    }

    private void jComboBoxPluginActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jButtonMaintenanceActionPerformed(int numAsc) {

        numAsc = numAsc - 1;
        Immeuble immeuble = simulateur.getImmeuble();
        ArrayList<Ascenseur> listeAscenseur = immeuble.getListeAscenseur();
        Ascenseur asc = listeAscenseur.get(numAsc);
        if (asc.getMaintenance()) { // je le sort de la maintenance
            this.setMaintenance(numAsc, false);
            // on met les deux labels en acifs
            this.jButtonMaintenanceGene.setEnabled(true);
            this.jButtonDemarrerGene.setEnabled(true);
            // si tt les ascensseurs sont demarrés
            if (immeuble.allNotInMaintenance()) {
                this.jButtonDemarrerGene.setEnabled(false);
            }
        } else { //Je le met en maintenance
            this.setMaintenance(numAsc, true);
            // // on met les deux labels en acifs
            this.jButtonMaintenanceGene.setEnabled(true);
            this.jButtonDemarrerGene.setEnabled(true);
            // si tt les ascensseurs sont en maintenance
            if (immeuble.allInMaintenance()) {
                this.jButtonMaintenanceGene.setEnabled(false);
            }
        }

    }

    public void initValues() {
        Immeuble immeuble = simulateur.getImmeuble();
        Ascenseur asc;
        ArrayList<Ascenseur> listeAscenseur = immeuble.getListeAscenseur();
        int i = 0;
        int taille = listeAscenseur.size();
        while (i < taille) {
            asc = listeAscenseur.get(i);
            if (asc.getMaintenance()) {
                this.setMaintenance(i, true);
            } else {
                this.setMaintenance(i, false);
            }
            i++;
        }

        while (i < 6) {
            this.initValueBoutonAsc(i);
            i++;
        }
        // on active les bouton apres le start
        this.jButtonMaintenanceGene.setEnabled(false);
        this.jComboBoxPlugin.setEnabled(false);
        //this.jButtonDemarrerGene.setEnabled(true);

    }

    /**
     * Rafraichit le numero d'ascenseur et la conso
     * @param numAscenseur
     * @param numEtage
     * @param conso
     */
    public void setValuesAsc(int numAscenseur, int numEtage, int conso) {
        Integer et = new Integer(numEtage);

        Integer cons = new Integer(0);
        switch (numAscenseur) {
            case 0:
                jLabelEtage1.setText(et.toString());
                cons = new Integer(Integer.parseInt(jLabelConso1.getText()) + conso);
                //System.out.println(cons.toString());
                jLabelConso1.setText(cons.toString());
                break;
            case 1:
                jLabelEtage2.setText(et.toString());
                cons = new Integer(Integer.parseInt(jLabelConso2.getText()) + conso);
                jLabelConso2.setText(cons.toString());
                break;
            case 2:
                jLabelEtage3.setText(et.toString());
                cons = new Integer(Integer.parseInt(jLabelConso3.getText()) + conso);
                jLabelConso3.setText(cons.toString());
                break;
            case 3:
                jLabelEtage4.setText(et.toString());
                cons = new Integer(Integer.parseInt(jLabelConso4.getText()) + conso);
                jLabelConso4.setText(cons.toString());
                break;
            case 4:
                jLabelEtage5.setText(et.toString());
                cons = new Integer(Integer.parseInt(jLabelConso5.getText()) + conso);
                jLabelConso5.setText(cons.toString());
                break;
            case 5:
                jLabelEtage6.setText(et.toString());
                cons = new Integer(Integer.parseInt(jLabelConso6.getText()) + conso);
                jLabelConso6.setText(cons.toString());
                break;
        }
    }

    /**
     * Met un ascenseur en maintenance dans l'interface
     * @param numAscenseur
     * @param maintenance
     */
    private void setMaintenance(int numAscenseur, boolean maintenance) {
        String etat = new String();
        Integer cons = new Integer(0);
        ArrayList<Ascenseur> listeAscenseur = simulateur.getImmeuble().getListeAscenseur();
        listeAscenseur.get(numAscenseur).setMaintenance(maintenance);
        if (maintenance) {
            etat = new String("maintenance");
        } else {
            etat = new String("marche");
        }

        //System.out.print(maintenance);
        switch (numAscenseur) {
            case 0:
                jLabelEtat1.setText(etat);
                jButtonMaintenance1.setEnabled(!maintenance);
                jButtonDemarrer1.setEnabled(maintenance);

                break;
            case 1:
                jLabelEtat2.setText(etat);
                jButtonMaintenance2.setEnabled(!maintenance);
                jButtonDemarrer2.setEnabled(maintenance);

                break;
            case 2:
                jLabelEtat3.setText(etat);
                jButtonMaintenance3.setEnabled(!maintenance);
                jButtonDemarrer3.setEnabled(maintenance);

                break;
            case 3:
                jLabelEtat4.setText(etat);
                jButtonMaintenance4.setEnabled(!maintenance);
                jButtonDemarrer4.setEnabled(maintenance);

                break;
            case 4:
                jLabelEtat5.setText(etat);
                jButtonMaintenance5.setEnabled(!maintenance);
                jButtonDemarrer5.setEnabled(maintenance);

                break;
            case 5:
                jLabelEtat6.setText(etat);
                jButtonMaintenance6.setEnabled(!maintenance);
                jButtonDemarrer6.setEnabled(maintenance);
                break;
        }
    }

    public void initValueBoutonAsc(int numAsc) {
        String etat = new String("non utilisé");

        switch (numAsc) {
            case 0:
                jLabelEtat1.setText(etat);
                jButtonMaintenance1.setEnabled(false);
                jButtonDemarrer1.setEnabled(false);

                break;
            case 1:
                jLabelEtat2.setText(etat);
                jButtonMaintenance2.setEnabled(false);
                jButtonDemarrer2.setEnabled(false);

                break;
            case 2:
                jLabelEtat3.setText(etat);
                jButtonMaintenance3.setEnabled(false);
                jButtonDemarrer3.setEnabled(false);

                break;
            case 3:
                jLabelEtat4.setText(etat);
                jButtonMaintenance4.setEnabled(false);
                jButtonDemarrer4.setEnabled(false);

                break;
            case 4:
                jLabelEtat5.setText(etat);
                jButtonMaintenance5.setEnabled(false);
                jButtonDemarrer5.setEnabled(false);

                break;
            case 5:
                jLabelEtat6.setText(etat);
                jButtonMaintenance6.setEnabled(false);
                jButtonDemarrer6.setEnabled(false);
                break;
        }
    }

    //+1 monte -1 descent
    public void saveStatistique(Date date, Integer numAsc, Integer direction) {
        System.out.print(date.toString() + " -- " + numAsc.toString() + " -- " + direction.toString());
        this.enregistrementStat.addStatistiques(date, numAsc, direction);
        this.enregistrementStat.initChart();
        //enregistrementStat.setVisible(true);
    }

    public void saveStatistiqueTempsMoyen(Date date, Long time) {
        this.enregistrementStatTempsMoyen.addStatistiquesTempsMoyen(date, time);
        this.enregistrementStatTempsMoyen.initChart();

    }

    ///menu des plugin
    public void buildPluginMenu() {
        //menuPlugin2 = new JMenu("Plugins");
        if (pluginMenuItemFactory == null) {
            pluginMenuItemFactory = new PluginjComboBoxPluginFactory(jComboBoxPlugin, PluginLoader, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ComportementAbstrait comportement = simulateur.getComportement();
                    Immeuble imm  = simulateur.getImmeuble();
                    for (Ascenseur asc : imm.getListeAscenseur()) {
                                            
                         //on recupere le JComboBox;
                         JComboBox  aa = (JComboBox)e.getSource();
                         //on recupere l'item selectionné dans la liste
                         PluginComboString bb = (PluginComboString)aa.getSelectedItem();
                        //on regarde quel comportement à été selectionné
                        comportement = (ComportementAbstrait) bb.getPlugin();
                        System.out.println(comportement.getClass());

                        try {
                            ComportementAbstrait c = comportement.getClass().newInstance();
                            asc.setComportement(c);
                            simulateur.setComportement(comportement);
                        } catch (InstantiationException ex) {
                            Logger.getLogger(Simulateur.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IllegalAccessException ex) {
                            Logger.getLogger(Simulateur.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                }
            });
        }
        buildPluginMenuEntries();
        //mb.add(menuPlugin2);
        //menuPlugin2.setEnabled(false);
    }

    public void initMenuPlugin() {
        try {
            PluginLoader = new PluginLoader("plugins");
            PluginLoader.loadPlugins();
            plugins = PluginLoader.getPluginInstances();
            System.out.println(plugins.length);
            //setJMenuBar(mb);
            buildPluginMenu();
        } catch (MalformedURLException ex) {
            Logger.getLogger(Simulateur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void buildPluginMenuEntries() {

        pluginMenuItemFactory.buildMenu(null);
    }
}//fin de la classe

