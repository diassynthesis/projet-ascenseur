package projetAscenseur.factories;

import projetAscenseur.factories.concreteFactories.GroupeAleatoireImpl;
import projetAscenseur.factories.concreteFactories.GroupeMonteImpl;
import projetAscenseur.factories.concreteFactories.PersGroupeFactory;
import projetAscenseur.factories.concreteFactories.PersonneSeuleAleatoireImpl;
import projetAscenseur.factories.concreteFactories.PersonneSeuleMonteImpl;
import projetAscenseur.*;

/**
 * classe de factories
 * @author Checconi maxime, Pilot guillaume et Canessa Marine
 */

public class PersonneMaker {
	
	public final static String SeuleAleatoire = "SeuleAleatoire";
	public final static String SeuleQuiMonte = "SeuleQuiMonte";
        public final static String GroupeQuiMonte = "GroupeQuiMonte";
        public final static String GroupeAleatoire = "GroupeAleatoire";
        public final static String GroupeEtPersonne = "GroupeEtPersonne";
	
	protected static PersonneMaker instance = null;
	
	protected PersonneMaker(){}
	
        /**
         * retourne l'instance de personneMaker
         * @return un PersonneMaker
         */
	public static PersonneMaker getInstance()
	{
            if (instance == null) instance = new PersonneMaker();
            return instance;
	}
	
        /**
         * retourne une factory personne
         * @param description type de factory choisi dans l'interface
         * @return la personne factory
         */
	public PersonneFactory getPersonneFactory(String description)
	{
            PersonneFactory result = null;

            if (description.equals(SeuleAleatoire)) result = new PersonneSeuleAleatoireImpl();
            else if (description.equals(SeuleQuiMonte)) result = new PersonneSeuleMonteImpl();
            else if (description.equals(GroupeQuiMonte)) result = new GroupeMonteImpl();
            else if (description.equals(GroupeAleatoire)) result = new GroupeAleatoireImpl();
            else if (description.equals(GroupeEtPersonne)) result = new PersGroupeFactory();
            return result;
	}

}
