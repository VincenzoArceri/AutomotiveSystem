import java.util.Vector;


public class FactoryCar {
	
	Subject baseStation;
	
	public FactoryCar(Subject baseStation) {
		this.baseStation = baseStation;
	}
	
	public Vector<ManualCar> factoryNinetyCars() {
		Vector<ManualCar> result = new Vector<ManualCar>();
		
		for (int i = 0; i < 50; i++)
			result.add(new CarAdapter("automatic" + i, baseStation));
				
		for (int i= 40; i < 50; i++)
			result.add(new ManualCar("manual" + i, baseStation));
			
		return result;
	}
	
	public ManualCar createManualCar(String id) {
		return new CarAdapter("Automatic" + id, null);
	}
	
	public CarAdapter createAutomaticCar(String id) {
		return new CarAdapter("Manual" + id, null);
	}
}
