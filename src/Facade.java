/**
 * Classe Facade per interagire col sistema
 * 
 * @author Vincenzo Arceri, Matteo Calabria, Pietro Musoni, Carlo Tacchella
 */
public class Facade {

	BaseStation baseStation;
	FactoryCar factory;
	
	public Facade() {
		// Creazione della BaseStation
		baseStation = new BaseStation();
		
		// Creazione delle factory delle macchine
		factory = new FactoryCar((BaseStation) baseStation);
	}
	
	/**
	 * Metodo unico per interagire con il sistema, specifico del pattern Facade
	 */
	public void initSimulator() {
		// Creazione di 90 macchine
	    factory.factoryNinetyCars();
		
		// Setup del sistema
		baseStation.joinToMe();
	}
}
