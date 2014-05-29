
public class Facade {


	public static void main(String[] args) {
		BaseStation baseStation;
		FactoryCar factory;
		
		// Creazione della BaseStation
		baseStation = new BaseStation();
		
		// Creazione delle factory delle macchine
		factory = new FactoryCar((Subject) baseStation);
		
		// Creazione di 90 macchine
		baseStation.allCars = factory.factoryNinetyCars();
		
		// Setup del sistema
		baseStation.joinToMe();
		
		/**
		 * TODO per lo scemo:
		 * Su Channel:
		 * 		- getIDs() per ottere un Vector di ID (macchine in quel canale);
		 * Sulle macchine:
		 * 		- getSpeed();
		 * 		- getDisplay();
		 * 		- getCarId();
		 * 
		 */
	}
}
