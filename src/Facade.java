
public class Facade {

	public static void main(String[] args) {
		BaseStation baseStation;
		FactoryCar factory;
	
		baseStation = new BaseStation();
		factory = new FactoryCar((Subject) baseStation);
		baseStation.allCars = factory.factoryNinetyCars();
		baseStation.joinToMe();
	}
}
