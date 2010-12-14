package projetAscenseur;


/**
 * classe main qui lance la simulation
 * @author Checconi maxime, Pilot guillaume et Canessa Marine
 */
public class Main {
    public static void main(String[] args){
         Simulateur simu = new Simulateur();
         Manager mana = new Manager(simu);
         simu.setManager(mana);
         System.out.println("Debut du prog");
        
    }
    
    

}
