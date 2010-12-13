/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projetAscenseur;

import projetAscenseur.Etage;
import projetAscenseur.personne.Personnes;
import projetAscenseur.personne.concretePersonne.PersonneSeule;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author buny
 */
public class PersonneTest {

    /**
     * Test of veutMonter method, of class Personne.
     */
    @Test
    public void testVeutMonter() {
        System.out.println("veutMonter");
        Etage etageDepart = new Etage(1);
        Etage etageArrive = new Etage(2);
        Personnes instance = new PersonneSeule(etageDepart,etageArrive,2);
        
        instance.setEtageDepart(etageDepart);
        instance.setEtageArrive(etageArrive);
        boolean result = instance.veutMonter();
        assertTrue(result);
    }
}