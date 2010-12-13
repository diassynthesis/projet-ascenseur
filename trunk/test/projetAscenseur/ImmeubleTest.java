/*
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projetAscenseur;

import projetAscenseur.Immeuble;
import projetAscenseur.Etage;
import projetAscenseur.Ascenseur;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author buny
 */
public class ImmeubleTest {

    /**
     * Test of ajouterAscenseur method, of class Immeuble.
     */
    @Test
    public void testAjouterAscenseur() {
        System.out.println("ajouterAscenseur");
        Immeuble im = new Immeuble(2,"");
        Ascenseur asc = new Ascenseur();
        im.ajouterAscenseur(asc);
        assertTrue(im.getListeAscenseur().contains(asc));
    }

    /**
     * Test of SupprimerAscenseur method, of class Immeuble.
     */
    @Test
    public void testSupprimerAscenseur() {
         Immeuble im = new Immeuble(2,"");
        Ascenseur asc = new Ascenseur();
        im.ajouterAscenseur(asc);
        Immeuble.SupprimerAscenseur(asc);
        assertFalse(im.getListeAscenseur().contains(asc));
    }
    
     /**
     * Test of SupprimerAscenseur method, of class Immeuble.
     */
    @Test
    public void testAscenseurLePlusProche() 
    {
        Immeuble im = new Immeuble(4,"");
        Ascenseur asc = new Ascenseur();      
        Ascenseur asc2 = new Ascenseur();
        Etage etageDepart = new Etage(1);
        Etage etageArrive = new Etage(2);
        Etage etageArrive2 = new Etage(3);
        Etage etageArrive3 = new Etage(4);
        asc.setEtageCourant(etageDepart.getNumEtage());
        asc.setEtageCourant(etageArrive3.getNumEtage());
        im.getListeEtage().add(etageDepart);
        im.getListeEtage().add(etageArrive);
        im.getListeEtage().add(etageArrive2);
        im.getListeEtage().add(etageArrive3);
        im.getListeAscenseur().add(asc);
        im.getListeAscenseur().add(asc2);
        im.getListeAppel().add(etageArrive);
        
        Etage etExpect = etageArrive;
        Etage result = im.ascenseurLePlusProche(asc);
        assertEquals(etExpect, result);
    }
 

}