import java.util.Random;
import java.util.Vector;

/**
 * Classe Factory per la creazione casuale di auto
 * 
 * @author Vincenzo Arceri, Matteo Calabria, Pietro Musoni, Carlo Tacchella
 */
public class FactoryCar {
	/**
	 * Referenza alla BaseStation
	 */
	Subject baseStation;
	
	/**
	 * Costruttore della Factory
	 * @param baseStation: referenza alla BaseStation
	 */
	public FactoryCar(Subject baseStation) {
		this.baseStation = baseStation;
	}
	
	/**
	 * Metodo per la creazione casuale di 90 auto, manuali o automatiche
	 * @return result: Vector di auto create.
	 */
	public Vector<ManualCar> factoryNinetyCars() {
		Vector<ManualCar> result = new Vector<ManualCar>();
		
		Random random = new Random();
		
		int automaticCreated = 0;
		int manualCreated = 0;

		for (int i = 0; i < 90; i++) {
			int rand = random.nextInt(100);
			
			if ((rand < 50) || (manualCreated == 50)) {
				result.add(new CarAdapter("automatic" + i, baseStation));
				automaticCreated++;
			} else if ((rand >= 50) || (automaticCreated == 40)) {
				result.add(new ManualCar("manual" + i, baseStation));
				manualCreated++;
			}	
		}
		return result;
	}
	
	/**
	 * Metodo per la creazione di un'auto Manual
	 * @param id: identificatore dell'auto manuale
	 */
	public ManualCar createManualCar(String id) {
		return new ManualCar("Manual" + id, null);
	}
	
	/**
	 * Metodo per la creazione di un'auto Automatic
	 * @param id: identificatore dell'auto automatica
	 */
	public CarAdapter createAutomaticCar(String id) {
		return new CarAdapter("Automatic" + id, null);
	}
}
