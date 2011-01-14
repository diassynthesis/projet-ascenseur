/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projetAscenseur;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.event.ActionEvent;
import javax.swing.Icon;
import javax.swing.JRadioButtonMenuItem;

import fr.unice.plugin.Plugin;
import java.awt.event.ActionListener;
/**
 *
 * @author aymeric
 */
public class PluginComboString implements ActionListener{


    private Plugin plugin;
    private String text;

    public PluginComboString() {
        super();
    }

    public PluginComboString(String text) {
        this.text = text;
    }

    public PluginComboString(String text, Plugin plugin) {
        this.text = text;
        this.plugin = plugin;
    }

    public PluginComboString(String text, Icon icon) {
        this.text = text;
    }

    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public String toString() {
        return this.text;
    }

    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

