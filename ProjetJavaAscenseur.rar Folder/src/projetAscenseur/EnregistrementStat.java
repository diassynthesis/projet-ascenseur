/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projetAscenseur;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;
import java.io.Serializable;
import javax.swing.ButtonGroup;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.util.Rotation;

/**
 *
 * @author fabrice.bourgeon
 */
public class EnregistrementStat extends ApplicationFrame {

    // Variables d'interface
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemAsc1;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemAsc2;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemAsc3;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemAsc4;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemAsc5;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemAsc6;
    private javax.swing.JMenu jMenuAscenseur;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuGraphique;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItemBarre;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItemCourbe;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItemHistogramme;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItemSecteur;
    private JFreeChart chart;
    private ChartPanel chartPanel;
    private PieDataset datasetPie;
    private DefaultCategoryDataset datasetBar = new DefaultCategoryDataset();
    //variable de logique
    private String StatsFile = "Statistiques.xml";
    private int typeGraphe = 1; //1 = barre
    private int ascenseurs[] = {1, 1, 1, 1, 1, 1, 1, 1, 1}; //0 = assenseur non affiché
    private Statistiques stats = null;
    static private EnregistrementStat _instance = null;
    // End of variables declaration

    /**
     * @return On renvoit toujours la même instance de Enregistrement
     */
    static public EnregistrementStat instance() {
        if (null == _instance) {
            _instance = new EnregistrementStat();
        }
        return _instance;
    }

    /**
     * @Override
     */
    public EnregistrementStat() {
        super("Statistique");

        initComponents();
        initStats();
        initChart();

    }

    private void initStats() {
        if (stats == null) {
            try {
                XMLDecoder decoder = new XMLDecoder(new FileInputStream(StatsFile));

                stats = (Statistiques) decoder.readObject();
            } catch (Exception IException) {
                System.out.println("Pas encore de fichier de statistiques créé. On le créé");
                stats = new Statistiques();
                sauvegarde();
            }
        }
    }

    /**
     * Creates a sample dataset for the demo.
     *
     * @return A sample dataset.
     */
    private PieDataset createDatasetPieChart3D() {
        //test();
        Date dateDeb = new Date("8/1/1989"), dateFin = new Date("8/1/2030");
        HashMap sta = this.stats.calculeDeplacement(dateDeb, dateFin);
        ArrayList arr = new ArrayList();
        DefaultPieDataset result = new DefaultPieDataset();
        for (int i = 0; i < this.stats.getNbAsc(); i++) {
            arr = (ArrayList) sta.get(new Integer(i));
            System.out.println("ascenseur " + i);

            System.out.print(arr.toString());
            if (arr != null) {
                if (ascenseurs[i] == 1) {

                    result.setValue("Ascenseur " + (i + 1), new Double(arr.get(0).toString()));
                } else {
                    result.setValue("Ascenseur " + (i + 1), new Double(0));

                }
            }
        }
        return result;
    }

    private void createDatasetBarChart3D() {

        int nbAsc = this.stats.getNbAsc();

        Date dateDeb = new Date("8/1/1989"), dateFin = new Date("8/1/2030");
        HashMap sta = this.stats.calculeDeplacement(dateDeb, dateFin);
        ArrayList arr = new ArrayList();
        //DefaultPieDataset dataset = new DefaultPieDataset();

        //  DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        this.datasetBar = new DefaultCategoryDataset();
        for (int i = 0; i < nbAsc; i++) {
            System.out.println("ascenseur " + i);
            arr = (ArrayList) sta.get(new Integer(i));
            //System.out.print(arr.toString());

            if (arr != null) {
                if (ascenseurs[i] == 1) {

                    this.datasetBar.addValue(Float.parseFloat(arr.get(0).toString()) * 1500, "Ascenseur " + (i + 1), "Montée");
                    this.datasetBar.addValue(Float.parseFloat(arr.get(1).toString()) * 750, "Ascenseur " + (i + 1), "Descente");
                } else {

                    this.datasetBar.addValue(0, "Ascenseur " + (i + 1), "Montée");
                    this.datasetBar.addValue(0, "Ascenseur " + (i + 1), "Descente");

                }
            }
        }
    }

    /**
     * Creates a sample chart.
     *
     * @param dataset  the dataset.
     *
     * @return A chart.
     */
    private JFreeChart createPieChart3D(PieDataset dataset) {

        JFreeChart chart = ChartFactory.createPieChart3D(
                "Consommation", // chart title
                dataset, // data
                true, // include legend
                true,
                false);

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        plot.setNoDataMessage("No data to display");
        return chart;

    }

    private JFreeChart createBarChart3D(CategoryDataset dataset) {

        JFreeChart chart = ChartFactory.createBarChart(
                "Consommation", // chart title
                "Ascenseurs", // domain axis label
                "Kilowatt", // range axis label
                dataset, // data
                PlotOrientation.VERTICAL, // orientation
                true, // include legend
                true, // tooltips?
                false // URLs?
                );


        return chart;

    }

    public void initChart() {
        //datasetPie = createDatasetPieChart3D();
        if (this.typeGraphe == 1) {
            createDatasetBarChart3D();
            chart = createBarChart3D(this.datasetBar);
        } else if (this.typeGraphe == 2) {
            PieDataset paset = createDatasetPieChart3D();
            chart = createPieChart3D(paset);
        }
        // create the chart...
        // add the chart to a panel...

        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));

        setContentPane(chartPanel);
    }

    private void initComponents() {

        // CREATION DE CHART
        // create a dataset...

        jPanel1 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuAscenseur = new javax.swing.JMenu();
        jCheckBoxMenuItemAsc1 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItemAsc2 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItemAsc3 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItemAsc4 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItemAsc5 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItemAsc6 = new javax.swing.JCheckBoxMenuItem();
        jMenuGraphique = new javax.swing.JMenu();

        jRadioButtonMenuItemHistogramme = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItemBarre = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItemCourbe = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItemSecteur = new javax.swing.JRadioButtonMenuItem();



        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 190, Short.MAX_VALUE));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 71, Short.MAX_VALUE));

        jMenuAscenseur.setText("Ascenseur");


        jCheckBoxMenuItemAsc1.setSelected(true);
        jCheckBoxMenuItemAsc1.setText("Ascenseur 1");
        jCheckBoxMenuItemAsc1.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (jCheckBoxMenuItemAsc1.getState() == true) {
                    ascenseurs[0] = 1;

                } else {
                    ascenseurs[0] = 0;

                }
                initChart();
                setVisible(true);
            }
        });
        jMenuAscenseur.add(jCheckBoxMenuItemAsc1);

        jCheckBoxMenuItemAsc2.setSelected(true);
        jCheckBoxMenuItemAsc2.setText("Ascenseur 2");
        jCheckBoxMenuItemAsc2.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (jCheckBoxMenuItemAsc2.getState() == true) {
                    ascenseurs[1] = 1;

                } else {
                    ascenseurs[1] = 0;

                }
                initChart();
                setVisible(true);
            }
        });
        jMenuAscenseur.add(jCheckBoxMenuItemAsc2);

        jCheckBoxMenuItemAsc3.setSelected(true);
        jCheckBoxMenuItemAsc3.setText("Ascenseur 3");
        jCheckBoxMenuItemAsc3.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (jCheckBoxMenuItemAsc3.getState() == true) {
                    ascenseurs[2] = 1;

                } else {
                    ascenseurs[2] = 0;

                }
                initChart();
                setVisible(true);
            }
        });
        jMenuAscenseur.add(jCheckBoxMenuItemAsc3);

        jCheckBoxMenuItemAsc4.setSelected(true);
        jCheckBoxMenuItemAsc4.setText("Ascenseur 4");
        jCheckBoxMenuItemAsc4.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (jCheckBoxMenuItemAsc4.getState() == true) {
                    ascenseurs[3] = 1;

                } else {
                    ascenseurs[3] = 0;

                }
                initChart();
                setVisible(true);
            }
        });
        jMenuAscenseur.add(jCheckBoxMenuItemAsc4);

        jCheckBoxMenuItemAsc5.setSelected(true);
        jCheckBoxMenuItemAsc5.setText("Ascenseur 5");
        jCheckBoxMenuItemAsc5.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (jCheckBoxMenuItemAsc5.getState() == true) {
                    ascenseurs[4] = 1;

                } else {
                    ascenseurs[4] = 0;

                }
                initChart();
                setVisible(true);
            }
        });
        jMenuAscenseur.add(jCheckBoxMenuItemAsc5);

        jCheckBoxMenuItemAsc6.setSelected(true);
        jCheckBoxMenuItemAsc6.setText("Ascenseur 6");
        jCheckBoxMenuItemAsc6.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (jCheckBoxMenuItemAsc6.getState() == true) {
                    ascenseurs[5] = 1;

                } else {
                    ascenseurs[5] = 0;

                }
                initChart();
                setVisible(true);
            }
        });
        jMenuAscenseur.add(jCheckBoxMenuItemAsc6);

        jMenuBar1.add(jMenuAscenseur);

        jMenuGraphique.setText("Graphique");


        jRadioButtonMenuItemBarre.setSelected(true);
        jRadioButtonMenuItemBarre.setText("Barre");
        jMenuGraphique.add(jRadioButtonMenuItemBarre);

//        jRadioButtonMenuItemHistogramme.setSelected(false);
//        jRadioButtonMenuItemHistogramme.setText("Histogramme");
//        jMenuGraphique.add(jRadioButtonMenuItemHistogramme);
//
//
//
//        jRadioButtonMenuItemCourbe.setSelected(false);
//        jRadioButtonMenuItemCourbe.setText("Courbe");
//        jMenuGraphique.add(jRadioButtonMenuItemCourbe);

        jRadioButtonMenuItemSecteur.setSelected(false);
        jRadioButtonMenuItemSecteur.setText("Secteur");
        jMenuGraphique.add(jRadioButtonMenuItemSecteur);

        jMenuBar1.add(jMenuGraphique);
        setJMenuBar(jMenuBar1);


        jRadioButtonMenuItemSecteur.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeGraphe = 2;
                jRadioButtonMenuItemBarre.setSelected(false);
                initChart();
                setVisible(true);
            }
        });

        jRadioButtonMenuItemBarre.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeGraphe = 1;
                jRadioButtonMenuItemSecteur.setSelected(false);
                initChart();
                setVisible(true);

            }
        });

        pack();


    }// </editor-fold>

    /**
     * @sauvegarde
     */
    public void sauvegarde() {
        //System.out.println("Sauvegarde des statistiques");
        try {
            XMLEncoder encoderStats = new XMLEncoder(new FileOutputStream(StatsFile));

            //On intilialise l'objet
            //On ecrit l'objet dans le fichier
            encoderStats.writeObject(this.stats);

            //On vide les buffers et on ferme les fichiers
            encoderStats.close();
        } catch (FileNotFoundException e) {
        }
    }

    public void addStatistiques(Date date, Integer NumAsc, Integer monte) {
        this.stats.addStatistiques(date, NumAsc, monte);
        this.sauvegarde();
        // System.out.println("repaint");

        // this.repaint();

    }
}
