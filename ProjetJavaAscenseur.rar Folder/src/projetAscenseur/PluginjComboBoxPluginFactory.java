/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projetAscenseur;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.*;

import fr.unice.plugin.Plugin;
import fr.unice.plugin.PluginLoader;
/**
 *
 * @author max06
 */
public class PluginjComboBoxPluginFactory {

    /**
     * Le menu g?r? par cette instance.
     */
    private JComboBox combo;
    /**
     * Le chargeur de classes charge les plugins.
     */
    private PluginLoader loader;
    /**
     * L'actionListener qui va ?couter les entr?es du menu des plugins.
     */
    private ActionListener listener;
    private static Logger logger = Logger.getLogger("player.test.PluginMenu");

    /**
     * Construit une instance qui concerne un certain menu. Ce menu aura des choix qui permettront de
     * s?lectionner un plugin ou un autre.
     *
     * @param menu
     *           le menu g?r? par cette instance.
     * @param loader
     *           le chargeur de classes des plugins.
     * @param listener
     *           l'actionDeListener qui va ?couter les entr?es du menu.
     */
    public PluginjComboBoxPluginFactory(JComboBox combo, PluginLoader loader, ActionListener listener){ //ActionListener listener) {
        this.combo = combo;
        this.loader = loader;
        this.listener = listener;
    }

    /**
     * Construit le menu des plugins.
     *
     * @param type type des plugins utilis�s pour construire le menu. Si null, tous les types de plugin
     *           seront utilis�s pour construire le menu.
     */
    public void buildMenu(Class type) {

        if (loader == null) {
            return;
        }
        logger.info("Construction du menu des PLUGINS");

        // Enl?ve les entr?es pr?c?dentes s'il y en avait
       // combo.removeAll();

        Plugin[] instancesPlugins = loader.getPluginInstances(type);
        logger.info("Nombre de plugins trouves :" + instancesPlugins.length);
        PluginComboString item;
        // On ajoute une entr?e par instance de plugin
        for (int i = 0; i < instancesPlugins.length; i++) {
            Plugin plugin = instancesPlugins[i];

            String mi = plugin.getName();
            item = new PluginComboString(mi);
            item.setPlugin(instancesPlugins[i]);

            combo.addItem(item);
        }
        combo.addActionListener(listener);

    }

    /**
     * l'accesseur de la donnee JMenu
     *
     * @return le JMenu
     */
    public JComboBox getCombo() {
        return combo;
    }
}

