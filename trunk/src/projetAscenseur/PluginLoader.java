/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projetAscenseur;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import projetAscenseur.strategy.ComportementAbstrait;

/**
 *
 * @author max06
 */
public class PluginLoader {
    
     public ComportementAbstrait loadPlugin(String className) {
        Class c = null;
        URLClassLoader cl = null;
         Object o = null;
        try {
            //c = Class.forName(className);
            cl = new URLClassLoader(new URL[]{new URL("file:/home/max06/ProjetJavaAscenseur/build/plugins/")});
//getClass().getClassLoader()+
            c = cl.loadClass(className);

           o = c.newInstance();
            

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClassLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ClassLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException e) {
            System.err.println("Erreur dans l'instantiation de la classe " + className);
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            System.err.println("Erreur dans l'instantiation de la classe " + className);
            e.printStackTrace();
            return null;
        }
        return (ComportementAbstrait) o;

    }

}
