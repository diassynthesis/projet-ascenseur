/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projetAscenseur;


import java.util.*;
import java.beans.XMLEncoder;
import java.beans.XMLDecoder;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author fabrice.bourgeon
 */
public class Enregistrement  {


    private String ConfFile = "ConfigurationAscenseur.xml";
    private String StatsFile = "Statistiques.xml";
    private FichierConfiguration ClassFichierConfiguration = null;
    private Statistiques stats = null;

    static private Enregistrement _instance = null;
    /**
     * Constructeur de Enregistrement
     * Permet la lecture dans le fichier de configuration
     */


    public Enregistrement(){
        System.out.println("Debut du Enregistrement");
        try{

            //Initilialisation des Statistiques
            stats = new Statistiques();

            //Initialisation du decoder
            XMLDecoder decoder = new XMLDecoder(new FileInputStream(ConfFile));
            //Si on a pas généré d'exeption, c'est que le fichier existe déja
            //On peut en extraire les données
            ClassFichierConfiguration = (FichierConfiguration)decoder.readObject();
        }catch(Exception es){
            //Initialisation de l'encoder
            try{

                System.out.println("Erreur dans l'encodage");
                XMLEncoder encoder = new XMLEncoder(new FileOutputStream(ConfFile));

                //On intilialise l'objet
                ClassFichierConfiguration = new FichierConfiguration();
                ClassFichierConfiguration.setDefaultParamsJournee();
                ClassFichierConfiguration.setDefaultParamsNuitWeekEnd();
                ClassFichierConfiguration.setDefaultLogin();
                //On ecrit l'objet dans le fichier
                encoder.writeObject(ClassFichierConfiguration);
                encoder.flush();
                encoder.close();
                
            }catch(Exception exception){
                System.out.printf("Erreur dans l'encodage");
            }
        }
    }

   /**
    * @return On renvoit toujours la même instance de Enregistrement
    */
    static public Enregistrement instance() {
      if(null == _instance) {
         _instance = new Enregistrement();
      }
      return _instance;
     }



    /**
     * Permet de vérifier l'authenticité du login et mot de passe dans un fichier
     * @param login
     * @param mdp
     * @return boolean
     */
    public String isLogin(String login,String mdp){

        HashMap HashLogin = ClassFichierConfiguration.getParamsLogin();

        if(HashLogin.get("login") == login){
            if(HashLogin.get("mdp") == mdp){
                return "Success";
            }else{
                return "Mot de passe incorrect !";
            }
        }else{
            return "Identifiant incorrect !";
        }
    }

    /**
     * Permet de charger la configuration de la Journée
     */
    public HashMap getConfigJournee(){
        HashMap HashConfigJournee = ClassFichierConfiguration.getParamsJournee();

        return HashConfigJournee;
    }

    /**
     * Récupère les configurations de l'application 
     */
    public HashMap chargeConfigurationParams(){
        HashMap HashConfigJournee = ClassFichierConfiguration.getParamsJournee();

        return HashConfigJournee;
    }

    public boolean setConfigJournee(HashMap HashConfigJournee){

        //Si le tableau n'est pas vide
        if(!HashConfigJournee.isEmpty()){
            //On vérifie que les données sont cohérentes
            if(this.verifyConfigJournee(HashConfigJournee)){
                ClassFichierConfiguration.setParamsJournee(HashConfigJournee);

            }else{
                return false;
            }
        }
        return true;
    }

    public boolean setConfigNuitWeekEnd(HashMap HashConfigNuitWeekEnd){

        //Si le tableau n'est pas vide
        if(!HashConfigNuitWeekEnd.isEmpty()){
            //On vérifie que les données sont cohérentes
            if(this.verifyConfigNuitWeekEnd(HashConfigNuitWeekEnd)){
                ClassFichierConfiguration.setParamsNuitWeekEnd(HashConfigNuitWeekEnd);

            }else{
                return false;
            }
        }
        return true;
    }
    /**
     * On vérifie les éléments de la configuration de la journeee
     * On verifie que le total des pourcentages fassent 100
     * 
     * @param HashConfigJournee
     * @return
     */
    public boolean verifyConfigJournee(HashMap HashConfigJournee){

       /* Set cles = HashConfigJournee.keySet();
        Iterator it = cles.iterator();
        while (it.hasNext()){
           Object cle = it.next(); // tu peux typer plus finement ici
           Object valeur = HashConfigJournee.get(cle); // tu peux typer plus finement ici
           if(valeur > 100){
               HashConfigJournee.put(cle, 100)
            }elseif(valeur < 100){
               HashConfigJournee.put(cle, 0)
            }
        }*/

        ArrayList valeurs = (ArrayList)HashConfigJournee.values();
        int sum = 0;

        //On effectue la somme des valeurs du tableaux
        //Elles doivent être égale à 100%
        for(int i=0; i < valeurs.size(); i++){
            sum = sum + (Integer)valeurs.get(i);
        }
        if(sum == 100)
            return true;
        else
            return false;
    }

    public boolean verifyConfigNuitWeekEnd(HashMap HashConfigNuitWeekEnd){

       /* Set cles = HashConfigJournee.keySet();
        Iterator it = cles.iterator();
        while (it.hasNext()){
           Object cle = it.next(); // tu peux typer plus finement ici
           Object valeur = HashConfigJournee.get(cle); // tu peux typer plus finement ici
           if(valeur > 100){
               HashConfigJournee.put(cle, 100)
            }elseif(valeur < 100){
               HashConfigJournee.put(cle, 0)
            }
        }*/

        ArrayList valeurs = (ArrayList)HashConfigNuitWeekEnd.values();
        int sum = 0;

        //On effectue la somme des valeurs du tableaux
        //Elles doivent être égale à 100%
        for(int i=0; i < valeurs.size(); i++){
            sum = sum + (Integer)valeurs.get(i);
        }
        if(sum == 100)
            return true;
        else
            return false;
    }

    public ArrayList getStatistiques(Date dateDebut, Date dateFin){

        ArrayList resultats = new ArrayList();
        
        return resultats;

    }

    public boolean setStatistiques(){

        return true;
        
    }
    /**
     * @Override
     */
    @Override public void finalize()
     {
        System.out.println("Destructeur");
        try{
            XMLEncoder encoderConf = new XMLEncoder(new FileOutputStream(ConfFile));
            XMLEncoder encoderStats = new XMLEncoder(new FileOutputStream(StatsFile));

            //On intilialise l'objet            
            //On ecrit l'objet dans le fichier
            encoderConf.writeObject(ClassFichierConfiguration);
            stats.addStatistiques( new java.util.Date(), new Ascenseur(), 5);
            encoderStats.writeObject(stats);

            //On vide les buffers
            encoderStats.close();
            encoderConf.close();
        }catch(FileNotFoundException e){
            
        }
     }
}

    



