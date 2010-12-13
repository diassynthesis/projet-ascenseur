/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projetAscenseur;

import projetAscenseur.Immeuble;
import projetAscenseur.Etage;
import projetAscenseur.personne.Personnes;
import projetAscenseur.Ascenseur;
import projetAscenseur.strategy.concreteStrategy.ComportementArretEtage;
import projetAscenseur.personne.concretePersonne.PersonneSeule;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author buny
 */
public class AscenseurTest {

    public AscenseurTest() {
    }


    /**
     * Test of calculerPlacesRestantes method, of class Ascenseur.
     */
    @Test
    public void testCalculerPlacesRestantes() {
        System.out.println("calculerPlacesRestantes");
        Ascenseur instance = new Ascenseur();
        int expResult = 15;
        int result = instance.calculerPlacesRestantes();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of calculerPlacesRestantes method, of class Ascenseur.
     */
    @Test
    public void testCalculerPlacesRestantes2() {
        System.out.println("calculerPlacesRestantes2");
        Ascenseur instance = new Ascenseur();
        instance.setNbPersActuel(4);
        int expResult = 11;
        int result = instance.calculerPlacesRestantes();
        assertEquals(expResult, result);
        boolean expResultb = false;
        boolean resultb = instance.isPlein();
        assertEquals(expResultb, resultb);
    }

    /**
     * Test of isPlein method, of class Ascenseur.
     */
    @Test
    public void testIsPlein() {
        System.out.println("isPlein");
        Ascenseur instance = new Ascenseur();
        boolean expResult = false;
        boolean result = instance.isPlein();
        assertEquals(expResult, result);
    }

   
    /**
     * Test of quelqunVeutDessendreDeAsc method, of class Ascenseur.
     */
    @Test
    public void testQuelqunVeutDessendreDeAscNull() {
        System.out.println("quelqunVeutDessendreDeAscNull");
        Ascenseur instance = new Ascenseur();
        Personnes expResult = null;
        Personnes result = instance.quelqunVeutDessendreDeAsc();
        assertEquals(expResult, result);
    }
    
     /**
     * Test of quelqunVeutDessendreDeAsc method, of class Ascenseur.
     */
    @Test
    public void testQuelqunVeutDessendreDeAsc() {
        System.out.println("quelqunVeutDessendreDeAsc");
        Ascenseur instance = new Ascenseur();
        Etage etageDepart = new Etage(1);
        Etage etageArrive = new Etage(3);
        Personnes p = new PersonneSeule(etageDepart, etageArrive, 1);
        
        p.setEtageDepart(etageDepart);
        p.setEtageArrive(etageArrive);
        instance.ajouterPersonneAscenseur(p);
        instance.setEtageCourant(3);
        
        Personnes expResult = p;
        Personnes result = instance.quelqunVeutDessendreDeAsc();
        assertEquals(expResult, result);
    }

    /**
     * Test of quelqunVeutMonterDansAsc method, of class Ascenseur.
     */
    @Test
    public void testQuelqunVeutMonterDansAscOK() {
        System.out.println("quelqunVeutMonterDansAscOK");
        Ascenseur instance = new Ascenseur();
        instance.setComportement(new ComportementArretEtage());
        Immeuble im = new Immeuble(2,"");
        Etage etageDepart = new Etage(1);
        Etage etageArrive = new Etage(2);
        Personnes p = new PersonneSeule(etageDepart, etageArrive, 1);
        p.setEtageDepart(etageDepart);
        p.setEtageArrive(etageArrive);
        Personnes expResult = p;
        etageDepart.getListePersonne().add(p);
        im.getListeEtage().add(etageDepart);
        im.getListeEtage().add(etageArrive);
        instance.setEtageCourant(1);
        Personnes result = instance.quelqunVeutMonterDansAsc();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of quelqunVeutMonterDansAsc method, of class Ascenseur.
     */
    @Test
    public void testQuelqunVeutMonterDansAscKO() {
        System.out.println("quelqunVeutMonterDansAscK0");
        Ascenseur instance = new Ascenseur();
        instance.setComportement(new ComportementArretEtage());
        Immeuble im = new Immeuble(2,"");
        Etage etage = new Etage(0);
        Etage etageDepart = new Etage(1);
        Etage etageArrive = new Etage(2);
        Personnes p = new PersonneSeule(etageDepart, etageArrive, 1);
        
        p.setEtageDepart(etageDepart);
        p.setEtageArrive(etageArrive);
        etageDepart.getListePersonne().add(p);
        im.getListeEtage().add(etage);
        im.getListeEtage().add(etageDepart);
        im.getListeEtage().add(etageArrive);
        instance.setEtageCourant(0);
        
        Personnes expResult = null;
        Personnes result = instance.quelqunVeutMonterDansAsc();
        assertEquals(expResult, result);
    }

    /**
     * Test of monter method, of class Ascenseur.
     */
    @Test
    public void testMonter() {
        System.out.println("monter");
        Ascenseur instance = new Ascenseur();
        Immeuble i = new Immeuble(3,"");
        int expResult =instance.getEtageCourant()+1;
        instance.monter();
        int result=instance.getEtageCourant();
        assertEquals(expResult, result);

    }

    /**
     * Test of descendre method, of class Ascenseur.
     */
    @Test
    public void testDescendre() {
        System.out.println("descendre");
        Ascenseur instance = new Ascenseur();
        instance.setEtageCourant(2);
        Immeuble i = new Immeuble(3,"");
        int expResult =instance.getEtageCourant()-1;
        instance.descendre();
        int result=instance.getEtageCourant();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of ouvrirPorte method, of class Ascenseur.
     */
    @Test
    public void testOuvrirPorte() {
        System.out.println("ouvrirPorte");
        Ascenseur instance = new Ascenseur();
        instance.ouvrirPorte();
        boolean expResult =true;
        boolean result=instance.isPortesOuvertes();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of fermerPorte method, of class Ascenseur.
     */
    @Test
    public void testFermerPorte() {
        System.out.println("fermerPorte");
        Ascenseur instance = new Ascenseur();
        Immeuble im = new Immeuble(1,"");
        Etage et = new Etage(0);
        im.getListeEtage().add(et);
        instance.fermerPorte();
        boolean expResult =false;
        boolean result=instance.isPortesOuvertes();
        assertEquals(expResult, result);
    }


    /**
     * Test of chercherPersonneDansListe method, of class Ascenseur.
     */
    @Test
    public void testChercherPersonneDansListe() {
        System.out.println("chercherPersonneDansListe");
        Personnes p = null;
        Ascenseur instance = new Ascenseur();
        instance.getListePersonne().add(p);
        boolean expResult = true;
        boolean result = instance.chercherPersonneDansListe(p);
        assertEquals(expResult, result);
    }

    /**
     * Test of ajouterPersonneAscenseur method, of class Ascenseur.
     */
    @Test
    public void testAjouterPersonneAscenseur() {
        System.out.println("ajouterPersonneAscenseur");
        Personnes P = null;
        Ascenseur instance = new Ascenseur();
        int v1 = instance.getNbPersActuel()+1;
        //code contenu dans la methode, car on peut pas tester la methode directement a cause des graphiques
        instance.getListePersonne().add(P);
        instance.setNbPersActuel(instance.getNbPersActuel()+1);
        instance.calculerPlacesRestantes();
        int v2 = instance.getNbPersActuel();
        assertEquals(v1, v2);
    }

    /**
     * Test of supprimerPersonneAscenseur method, of class Ascenseur.
     */
    @Test
    public void testSupprimerPersonneAscenseur() {
        System.out.println("supprimerPersonneAscenseur");
        Personnes P = null;
        Ascenseur instance = new Ascenseur();
        instance.getListePersonne().add(P);
        int v1 = instance.getNbPersActuel()-1;
        //code contenu dans la methode, car on peut pas tester la methode directement a cause des graphiques
       instance.getListePersonne().remove(P);
        instance.setNbPersActuel(instance.getNbPersActuel()-1);
        instance.calculerPlacesRestantes();
        int v2 = instance.getNbPersActuel();
        assertEquals(v1, v2);
    }

}