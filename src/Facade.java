
public class Facade {

	BaseStation baseStation;
	FactoryCar factory;
	
	public Facade() {
		// Creazione della BaseStation
		baseStation = new BaseStation();
		
		// Creazione delle factory delle macchine
		factory = new FactoryCar((Subject) baseStation);
		
		// Creazione di 90 macchine
		baseStation.allCars = factory.factoryNinetyCars();
		
		// Setup del sistema
		baseStation.joinToMe();

	}
}
