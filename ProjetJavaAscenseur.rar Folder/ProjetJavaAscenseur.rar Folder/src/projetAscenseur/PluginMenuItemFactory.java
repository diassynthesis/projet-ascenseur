/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projetAscenseur;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JMenu;

import fr.unice.plugin.Plugin;
import fr.unice.plugin.PluginLoader;
/**
 *
 * @author max06
 */
public class PluginMenuItemFactory {

    /**
     * Le menu g?r? par cette instance.
     */
    private JMenu menu;
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
    public PluginMenuItemFactory(JMenu menu, PluginLoader loader, ActionListener listener){ //ActionListener listener) {
        this.menu = menu;
        this.loader = loader;
        this.listener = listener;
    }

    /**
     * Construit le menu des plugins.
     * 
     * @param type type des plugins utilisés pour construire le menu. Si null, tous les types de plugin
     *           seront utilisés pour construire le menu.
     */
    public void buildMenu(Class type) {
        if (loader == null) {
            return;
        }
        logger.info("Construction du menu des PLUGINS");

        // Enl?ve les entr?es pr?c?dentes s'il y en avait
        menu.removeAll();

        // R?cup?re les instances d?j? charg?es

        Plugin[] instancesPlugins = loader.getPluginInstances(type);
        logger.info("Nombre de plugins trouv?s :" + instancesPlugins.length);
        PluginMenuItem item;
        // On ajoute une entr?e par instance de plugin
        for (int i = 0; i < instancesPlugins.length; i++) {
             Plugin plugin = instancesPlugins[i];

            String mi = plugin.getName();
            item = new PluginMenuItem(mi);
            item.setPlugin(instancesPlugins[i]);
            menu.add(item);

            // menu.add(mi);
            item.addActionListener(listener);

        }
    }

    /**
     * l'accesseur de la donnee JMenu
     * 
     * @return le JMenu
     */
    public JMenu getMenu() {
        return menu;
    }
}

