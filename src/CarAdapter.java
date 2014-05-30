/**
 * Classe Adapter per adattare un'auto Automatic
 * ad un'auto Manual
 * 
 * @author Vincenzo Arceri, Matteo Calabria, Pietro Musoni, Carlo Tacchella
 *
 */
public class CarAdapter extends ManualCar {
	/**
	 * Referenza ad un'auto Automatic
	 */
	public AutomaticCar automatic;
	
	/**
	 * Costruttore della classe Adapter
	 * @param id: identificatore di un'auto
	 * @param base: referenza alla BaseStation
	 */
	public CarAdapter(String id, Subject base) {
		super(id, base);
		automatic = new AutomaticCar(id, base);
	}
	
	/**
	 * Metodo per la ricezione di un packet (adattato)
	 * da parte della BaseStation
	 * 
	 * @param packet: pacchetto da ricevere
	 */
	@Override
	public void update(Packet packet) {
		this.automatic.oldUpdate(packet);
	}
}
