/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projetAscenseur;

import java.util.*;
import java.beans.XMLEncoder;
import java.beans.XMLDecoder;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author fabrice.bourgeon
 */
public class Enregistrement extends javax.swing.JFrame {

    // Variables declaration - do not modify
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButtonValiderNuitWeek;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButtonValiderJournee;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField20Prem;
    private javax.swing.JTextField jTextField20PremNuit;
    private javax.swing.JTextField jTextField5Der;
    private javax.swing.JTextField jTextField5DerNuit;
    private javax.swing.JTextField jTextFieldAutre;
    private javax.swing.JTextField jTextFieldAutreNuit;
    private javax.swing.JTextField jTextFieldRed;
    private javax.swing.JTextField jTextFieldRedNuit;
    private javax.swing.JTextField jTextFieldSous;
    private javax.swing.JTextField jTextFieldSousNuit;
    //variable de logique
    private String ConfFile = "ConfigurationAscenseur.xml";
    private String StatsFile = "Statistiques.xml";
    private FichierConfiguration ClassFichierConfiguration = null;
    private Statistiques stats = null;
    static private Enregistrement _instance = null;

    /**
     * Constructeur de Enregistrement
     * Permet la lecture dans le fichier de configuration
     */
    public Enregistrement() {
        System.out.println("Debut du Enregistrement");
        try {
            //initialise les composant le la fenetre
            initEnregistrement();
            //Initilialisation des Statistiques
            stats = new Statistiques();

            //Initialisation du decoder
            XMLDecoder decoder = new XMLDecoder(new FileInputStream(ConfFile));
            //Si on a pas généré d'exeption, c'est que le fichier existe déja
            //On peut en extraire les données
            ClassFichierConfiguration = (FichierConfiguration) decoder.readObject();
        } catch (Exception es) {
            //Initialisation de l'encoder
            try {

                System.out.println("Erreur dans l'encodage");
                XMLEncoder encoder = new XMLEncoder(new FileOutputStream(ConfFile));

                //On intilialise l'objet
                ClassFichierConfiguration = new FichierConfiguration();
                ClassFichierConfiguration.setDefaultParamsJournee();
                ClassFichierConfiguration.setDefaultParamsNuitWeekEnd();
                ClassFichierConfiguration.setDefaultLogin();
                //On ecrit l'objet dans le fichier
                encoder.writeObject(ClassFichierConfiguration);
                encoder.flush();
                encoder.close();

            } catch (Exception exception) {
                System.out.printf("Erreur dans l'encodage");
            }
        }
    }

    public void setVisible(boolean affi) {
        this.initValues();
        super.setVisible(affi);
    }

    private void initValues() {
        //recup des configs journee
        HashMap configJ = this.getConfigJournee();
        //recup des configs nuit et weekend
        HashMap configN = this.getConfigNuitWeekEnd();
        //on parcour le hashmap de journee
        String RdC = configJ.get(new String("RdC")).toString();
        String vingtPremiersEtages = configJ.get(new String("vingtPremiersEtages")).toString();
        String cinqDerniersEtages = configJ.get(new String("cinqDerniersEtages")).toString();
        String sousSols = configJ.get(new String("sousSols")).toString();
        String autresEtages = configJ.get(new String("autresEtages")).toString();
        //on parcour le hashmap de nuit week
        String RdCN = configJ.get(new String("RdC")).toString();
        String vingtPremiersEtagesN = configJ.get(new String("vingtPremiersEtages")).toString();
        String cinqDerniersEtagesN = configJ.get(new String("cinqDerniersEtages")).toString();
        String sousSolsN = configJ.get(new String("sousSols")).toString();
        String autresEtagesN = configJ.get(new String("autresEtages")).toString();

        //on met les valeurs de la journée dans l'interface
        jTextField20Prem.setText(vingtPremiersEtages);
        jTextField5Der.setText(cinqDerniersEtages);
        jTextFieldSous.setText(sousSols);
        jTextFieldRed.setText(RdC);
        jTextFieldAutre.setText(autresEtages);

        //on met les valeurs de la nuit et wek dans l'interface
        jTextField20PremNuit.setText(vingtPremiersEtagesN);
        jTextField5DerNuit.setText(cinqDerniersEtagesN);
        jTextFieldSousNuit.setText(sousSolsN);
        jTextFieldRedNuit.setText(RdCN);
        jTextFieldAutreNuit.setText(autresEtagesN);
    }

    private void setValuesJournee(){
        HashMap HashConfigJournee = ClassFichierConfiguration.getParamsJournee();

        HashConfigJournee.put("RdC", Integer.parseInt(jTextFieldRed.getText()));
        HashConfigJournee.put("vingtPremiersEtages", Integer.parseInt(jTextField20Prem.getText()));
        HashConfigJournee.put("cinqDerniersEtages", Integer.parseInt(jTextField5Der.getText()));
        HashConfigJournee.put("sousSols", Integer.parseInt(jTextFieldSous.getText()));
        HashConfigJournee.put("autresEtages", Integer.parseInt(jTextFieldAutre.getText()));

        this.setConfigJournee(HashConfigJournee);
    }

    private void setValuesNuitWeek(){
         HashMap HashConfigNuitWeek = ClassFichierConfiguration.getParamsNuitWeekEnd();

        HashConfigNuitWeek.put("RdC", Integer.parseInt(jTextFieldRedNuit.getText()));
        HashConfigNuitWeek.put("vingtPremiersEtages", Integer.parseInt(jTextField20PremNuit.getText()));
        HashConfigNuitWeek.put("cinqDerniersEtages", Integer.parseInt(jTextField5DerNuit.getText()));
        HashConfigNuitWeek.put("sousSols", Integer.parseInt(jTextFieldSousNuit.getText()));
        HashConfigNuitWeek.put("autresEtages", Integer.parseInt(jTextFieldAutreNuit.getText()));

        this.setConfigNuitWeekEnd(HashConfigNuitWeek);
    }


    /**
     * @return On renvoit toujours la même instance de Enregistrement
     */
    static public Enregistrement instance() {
        if (null == _instance) {
            _instance = new Enregistrement();
        }
        return _instance;
    }

    /**
     * Permet de vérifier l'authenticité du login et mot de passe dans un fichier
     * @param login
     * @param mdp
     * @return boolean
     */
    public String isLogin(String login, String mdp) {

        HashMap HashLogin = ClassFichierConfiguration.getParamsLogin();

        if (HashLogin.get("login") == login) {
            if (HashLogin.get("mdp") == mdp) {
                return "Success";
            } else {
                return "Mot de passe incorrect !";
            }
        } else {
            return "Identifiant incorrect !";
        }
    }

    /**
     * Permet de charger la configuration de la Journée
     */
    public HashMap getConfigJournee() {
        HashMap HashConfigJournee = ClassFichierConfiguration.getParamsJournee();

        return HashConfigJournee;
    }

    /**
     * Permet de charger la configuration de la Journée
     */
    public HashMap getConfigNuitWeekEnd() {
        HashMap HashConfigNuitWeekEnd = ClassFichierConfiguration.getParamsNuitWeekEnd();
        return HashConfigNuitWeekEnd;
    }

    /**
     * Récupère les configurations de l'application 
     */
    public HashMap chargeConfigurationParams() {
        HashMap HashConfigJournee = ClassFichierConfiguration.getParamsJournee();

        return HashConfigJournee;
    }

    public boolean setConfigJournee(HashMap HashConfigJournee) {

        //Si le tableau n'est pas vide
        if (!HashConfigJournee.isEmpty()) {
            //On vérifie que les données sont cohérentes
            if (this.verifyConfigJournee(HashConfigJournee)) {
                ClassFichierConfiguration.setParamsJournee(HashConfigJournee);

            } else {
                return false;
            }
        }
        return true;
    }

    public boolean setConfigNuitWeekEnd(HashMap HashConfigNuitWeekEnd) {

        //Si le tableau n'est pas vide
        if (!HashConfigNuitWeekEnd.isEmpty()) {
            //On vérifie que les données sont cohérentes
            if (this.verifyConfigNuitWeekEnd(HashConfigNuitWeekEnd)) {
                ClassFichierConfiguration.setParamsNuitWeekEnd(HashConfigNuitWeekEnd);

            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * On vérifie les éléments de la configuration de la journeee
     * On verifie que le total des pourcentages fassent 100
     * 
     * @param HashConfigJournee
     * @return
     */
    public boolean verifyConfigJournee(HashMap HashConfigJournee) {

        /* Set cles = HashConfigJournee.keySet();
        Iterator it = cles.iterator();
        while (it.hasNext()){
        Object cle = it.next(); // tu peux typer plus finement ici
        Object valeur = HashConfigJournee.get(cle); // tu peux typer plus finement ici
        if(valeur > 100){
        HashConfigJournee.put(cle, 100)
        }elseif(valeur < 100){
        HashConfigJournee.put(cle, 0)
        }
        }*/

        ArrayList valeurs = (ArrayList) HashConfigJournee.values();
        int sum = 0;

        //On effectue la somme des valeurs du tableaux
        //Elles doivent être égale à 100%
        for (int i = 0; i < valeurs.size(); i++) {
            sum = sum + (Integer) valeurs.get(i);
        }
        if (sum == 100) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verifyConfigNuitWeekEnd(HashMap HashConfigNuitWeekEnd) {

        /* Set cles = HashConfigJournee.keySet();
        Iterator it = cles.iterator();
        while (it.hasNext()){
        Object cle = it.next(); // tu peux typer plus finement ici
        Object valeur = HashConfigJournee.get(cle); // tu peux typer plus finement ici
        if(valeur > 100){
        HashConfigJournee.put(cle, 100)
        }elseif(valeur < 100){
        HashConfigJournee.put(cle, 0)
        }
        }*/

        ArrayList valeurs = (ArrayList) HashConfigNuitWeekEnd.values();
        int sum = 0;

        //On effectue la somme des valeurs du tableaux
        //Elles doivent être égale à 100%
        for (int i = 0; i < valeurs.size(); i++) {
            sum = sum + (Integer) valeurs.get(i);
        }
        if (sum == 100) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList getStatistiques(Date dateDebut, Date dateFin) {

        ArrayList resultats = new ArrayList();

        return resultats;

    }

    public boolean setStatistiques() {

        return true;

    }

    /**
     * @Override
     */
    @Override
    public void finalize() {
        System.out.println("Destructeur");
        try {
            XMLEncoder encoderConf = new XMLEncoder(new FileOutputStream(ConfFile));
            XMLEncoder encoderStats = new XMLEncoder(new FileOutputStream(StatsFile));

            //On intilialise l'objet            
            //On ecrit l'objet dans le fichier
            encoderConf.writeObject(ClassFichierConfiguration);
            stats.addStatistiques(new java.util.Date(), new Ascenseur(), 5);
            encoderStats.writeObject(stats);

            //On vide les buffers
            encoderStats.close();
            encoderConf.close();
        } catch (FileNotFoundException e) {
        }
    }

    // interface
    public void initEnregistrement() {
        this.initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jTextFieldSous = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jTextField5Der = new javax.swing.JTextField();
        jTextField20Prem = new javax.swing.JTextField();
        jTextFieldAutre = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jTextFieldRed = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButtonValiderJournee = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButtonValiderNuitWeek = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jTextFieldSousNuit = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField5DerNuit = new javax.swing.JTextField();
        jTextField20PremNuit = new javax.swing.JTextField();
        jTextFieldAutreNuit = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTextFieldRedNuit = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextFieldSous.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldSousActionPerformed(evt);
            }
        });

        jLabel21.setText("Sous-sols & Parking :");

        jLabel22.setText("Rez-de-chaussée :");

        jLabel23.setText("Autre étages :");

        jLabel24.setText("20 premiers étages :");

        jLabel25.setText("5 derniers étages :");

        jTextField5Der.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5DerActionPerformed(evt);
            }
        });

        jTextField20Prem.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField20PremActionPerformed(evt);
            }
        });

        jTextFieldAutre.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldAutreActionPerformed(evt);
            }
        });

        jLabel26.setText("%");

        jLabel27.setText("%");

        jLabel28.setText("%");

        jLabel29.setText("%");

        jLabel30.setText("%");

        jTextFieldRed.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldRedActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel5Layout.createSequentialGroup().addContainerGap().addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false).addGroup(jPanel5Layout.createSequentialGroup().addComponent(jLabel24).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jTextField20Prem)).addGroup(jPanel5Layout.createSequentialGroup().addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(jLabel21, javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel23, javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel22, javax.swing.GroupLayout.Alignment.LEADING)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false).addComponent(jTextFieldSous, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE).addComponent(jTextFieldRed, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE).addComponent(jTextFieldAutre, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE).addComponent(jTextField5Der, javax.swing.GroupLayout.Alignment.LEADING))).addComponent(jLabel25)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel26).addComponent(jLabel27).addComponent(jLabel30).addComponent(jLabel29).addComponent(jLabel28)).addGap(122, 122, 122)));
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel5Layout.createSequentialGroup().addGap(12, 12, 12).addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(jLabel29).addComponent(jLabel24).addComponent(jTextField20Prem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addGap(18, 18, 18).addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addGroup(jPanel5Layout.createSequentialGroup().addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel5Layout.createSequentialGroup().addGap(5, 5, 5).addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel25).addComponent(jTextField5Der, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel30)).addGap(34, 34, 34).addComponent(jLabel23)).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup().addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(jLabel26).addComponent(jTextFieldAutre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addGap(1, 1, 1))).addGap(23, 23, 23).addComponent(jLabel22).addGap(29, 29, 29).addComponent(jLabel21)).addGroup(jPanel5Layout.createSequentialGroup().addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(jLabel27).addComponent(jTextFieldRed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addGap(18, 18, 18).addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jTextFieldSous, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel28)))).addContainerGap()));

        jButton5.setText("Annuler");

        jButtonValiderJournee.setText("Valider");
        jButtonValiderJournee.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonValiderJourneeActionPerformed(evt);
            }
        });

        jButton2.setText("Réinitialiser");
        jButton2.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel6Layout.createSequentialGroup().addGap(24, 24, 24).addComponent(jButtonValiderJournee, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(95, 95, 95).addComponent(jButton2).addGap(21, 21, 21)));
        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel6Layout.createSequentialGroup().addGap(29, 29, 29).addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jButtonValiderJournee).addComponent(jButton5).addComponent(jButton2)).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 476, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(312, 312, 312)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(21, 21, 21).addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(32, 32, 32).addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        jTabbedPane1.addTab("Journée", jPanel1);

        jPanel2.setPreferredSize(new java.awt.Dimension(600, 380));

        jButton3.setText("Annuler");

        jButtonValiderNuitWeek.setText("Valider");
        jButtonValiderNuitWeek.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonValiderNuitWeekActionPerformed(evt);
            }
        });

        jButton1.setText("Réinitialiser");
        jButton1.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addGap(24, 24, 24).addComponent(jButtonValiderNuitWeek, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(95, 95, 95).addComponent(jButton1).addGap(21, 21, 21)));
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addGap(29, 29, 29).addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jButtonValiderNuitWeek).addComponent(jButton3).addComponent(jButton1)).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        jTextFieldSousNuit.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldSousNuitActionPerformed(evt);
            }
        });

        jLabel19.setText("Sous-sols & Parking :");

        jLabel18.setText("Rez-de-chaussée :");

        jLabel15.setText("Autre étages :");

        jLabel14.setText("20 premiers étages :");

        jLabel11.setText("5 derniers étages :");

        jTextField5DerNuit.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5DerNuitActionPerformed(evt);
            }
        });

        jTextField20PremNuit.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField20PremNuitActionPerformed(evt);
            }
        });

        jTextFieldAutreNuit.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldAutreNuitActionPerformed(evt);
            }
        });

        jLabel16.setText("%");

        jLabel17.setText("%");

        jLabel20.setText("%");

        jLabel13.setText("%");

        jLabel12.setText("%");

        jTextFieldRedNuit.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldRedNuitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addContainerGap().addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addComponent(jLabel14).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jTextField20PremNuit, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jLabel13)).addGroup(jPanel4Layout.createSequentialGroup().addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(jLabel19, javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false).addComponent(jTextFieldSousNuit, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE).addComponent(jTextFieldRedNuit, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE).addComponent(jTextFieldAutreNuit, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE).addComponent(jTextField5DerNuit, javax.swing.GroupLayout.Alignment.LEADING))).addComponent(jLabel11)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel17).addComponent(jLabel16).addComponent(jLabel20).addComponent(jLabel12)))).addGap(95, 95, 95)));

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[]{jTextFieldAutreNuit, jTextFieldRedNuit, jTextFieldSousNuit});

        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addContainerGap().addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(jLabel14).addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jTextField20PremNuit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel13))).addGap(18, 18, 18).addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addGroup(jPanel4Layout.createSequentialGroup().addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addGap(5, 5, 5).addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel11).addComponent(jTextField5DerNuit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel12)).addGap(34, 34, 34).addComponent(jLabel15)).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup().addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel16).addComponent(jTextFieldAutreNuit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addGap(1, 1, 1))).addGap(23, 23, 23).addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup().addComponent(jLabel18).addGap(29, 29, 29).addComponent(jLabel19)))).addGroup(jPanel4Layout.createSequentialGroup().addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(jLabel17).addComponent(jTextFieldRedNuit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addGap(18, 18, 18).addComponent(jTextFieldSousNuit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))).addContainerGap()));

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[]{jTextField20PremNuit, jTextFieldAutreNuit, jTextFieldRedNuit, jTextFieldSousNuit});

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 476, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup().addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(312, 312, 312)));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addGap(21, 21, 21).addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(32, 32, 32).addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        jTabbedPane1.addTab("Nuit et Weekend", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap()));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE));

        pack();
    }// </editor-fold>

// </editor-fold>  
    private void jButtonValiderNuitWeekActionPerformed(java.awt.event.ActionEvent evt) {
            this.setValuesNuitWeek();
    }

    private void jTextFieldSousNuitActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jTextFieldRedNuitActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jTextFieldAutreNuitActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jTextField20PremNuitActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jTextField5DerNuitActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jTextFieldSousActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jTextField5DerActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jTextField20PremActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jTextFieldAutreActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jTextFieldRedActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jButtonValiderJourneeActionPerformed(java.awt.event.ActionEvent evt) {
        this.setValuesJournee();
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }
}
