import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
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
	public BaseStation baseStation;
	private Timer timer;
    public int manualCreated = 0;
	public int automaticCreated = 0;
	
	/**
	 * Costruttore della Factory
	 * @param baseStation: referenza alla BaseStation
	 */
	public FactoryCar(BaseStation baseStation) {
		this.baseStation = baseStation;
		this.timer = new Timer();
	}
	
	/**
	 * Metodo per la creazione casuale di 90 auto, manuali o automatiche
	 * @return result: Vector di auto create.
	 */
	public void factoryNinetyCars() {
		
		for (int i = 0; i < 5; i++) {
			baseStation.allCars.add(createManualCar("" + i));
			baseStation.allCars.add(createAutomaticCar("" + i));
		}
		
		manualCreated = 5;
		automaticCreated = 5;
		
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				Random random = new Random();
				int tmp = random.nextInt(100);
				
				if ((tmp < 50) && (manualCreated < 50)) {
					baseStation.allCars.add(createManualCar("" + manualCreated));
					manualCreated++;
				} else if ((tmp >= 50) && (automaticCreated < 40)) {
					baseStation.allCars.add(createAutomaticCar("" + automaticCreated));
					automaticCreated++;
				}

			}
		}, 0, 1000);
	}

	/**
	 * Metodo per la creazione di un'auto Manual
	 * @param id: identificatore dell'auto manuale
	 */
	public ManualCar createManualCar(String id) {
		return new ManualCar("Manual" + id, baseStation);
	}
	
	/**
	 * Metodo per la creazione di un'auto Automatic
	 * @param id: identificatore dell'auto automatica
	 */
	public CarAdapter createAutomaticCar(String id) {
		return new CarAdapter("Automatic" + id, baseStation);
	}
}
