package projetAscenseur;


/**
 * classe main qui lance la simulation
 * @author fabrice
 */
public class Main {
    public static void main(String[] args){
         Identification auth = new Identification();
         auth.setVisible(true);

         //On attend d'etre loggué pour continuer l'initialisation du programme
         while(!auth.isLogged());
         Simulateur simu = new Simulateur();
         Manager mana = new Manager(simu);
         simu.setManager(mana);
         mana.session();
        
    }
    
    

}
