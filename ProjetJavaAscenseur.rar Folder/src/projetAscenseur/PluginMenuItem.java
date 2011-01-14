/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projetAscenseur;
import javax.swing.Icon;
import javax.swing.JRadioButtonMenuItem;

import fr.unice.plugin.Plugin;
/**
 *
 * @author max06
 */
public class PluginMenuItem extends JRadioButtonMenuItem {

    private Plugin plugin;

    public PluginMenuItem() {
        super();
    }

    public PluginMenuItem(String text) {
        super(text);
    }

    public PluginMenuItem(String text, Plugin plugin) {
        super(text);
        this.plugin = plugin;
    }

    public PluginMenuItem(String text, Icon icon) {
        super(text, icon);
    }

    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }

    public Plugin getPlugin() {
        return plugin;
    }
}

