import java.util.Random;
import java.util.Vector;


public class FactoryCar {
	
	Subject baseStation;
	
	public FactoryCar(Subject baseStation) {
		this.baseStation = baseStation;
	}

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

	public ManualCar createManualCar(String id) {
		return new CarAdapter("Automatic" + id, null);
	}
	
	public CarAdapter createAutomaticCar(String id) {
		return new CarAdapter("Manual" + id, null);
	}
}
