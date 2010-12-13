/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projetAscenseur;

import projetAscenseur.Immeuble;
import projetAscenseur.Etage;
import projetAscenseur.personne.Personnes;
import projetAscenseur.personne.concretePersonne.PersonneSeule;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author buny
 */
public class EtageTest {


    /**
     * Test of chercherEtageDsListe method, of class Etage.
     */
    @Test
    public void testChercherEtageDsListe() {
        System.out.println("chercherEtageDsListe");
        Immeuble i = new Immeuble(2,"");
        Etage e1 = new Etage(0);
        Etage e2 = new Etage(1);
        i.getListeEtage().add(e1);
        i.getListeEtage().add(e2);
        int numEtage = 0;
        Etage expResult = e2;
        Etage result = Etage.chercherEtageDsListe(numEtage);
        assertEquals(expResult, result);
    }

    /**
     * Test of chercherPersonneDansListe method, of class Etage.
     */
    @Test
    public void testChercherPersonneDansListe() {
        System.out.println("chercherPersonneDansListe");
        Etage instance = new Etage(2);
        Personnes p = new PersonneSeule(instance, instance, 1);
        
        instance.getListePersonne().add(p);
        boolean expResult = true;
        boolean result = instance.chercherPersonneDansListe(p);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of personneMobileDsEtage method, of class Etage.
     */
    @Test
    public void testPersonneMobileDsEtage() {
        System.out.println("personneMobileDsEtage");
        Etage instance = new Etage(2);
        Etage e2 = new Etage(3);
        Personnes expResult = new PersonneSeule(instance, e2, 1);
        
        expResult.setEtageDepart(instance);
        expResult.setEtageArrive(e2);
        instance.getListePersonne().add(expResult);
        Personnes result = instance.personneMobileDsEtage();
        assertEquals(expResult, result);

    }

}