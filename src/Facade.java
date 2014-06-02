
public class Facade {

	BaseStation baseStation;
	FactoryCar factory;
	
	public Facade() {
		// Creazione della BaseStation
		baseStation = new BaseStation();
		
		// Creazione delle factory delle macchine
		factory = new FactoryCar((BaseStation) baseStation);
		
		// Creazione di 90 macchine
	    factory.factoryNinetyCars();
		
		// Setup del sistema
		baseStation.joinToMe();

	}
}
