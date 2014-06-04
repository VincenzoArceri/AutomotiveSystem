import java.util.Timer;


public abstract class AbstractCar extends Node {
	/**
	 * Identificatore dell'auto
	 */
	protected String idCar;
	
	/**
	 * Display dell'auto: visualizza i messaggi inviati dalla BaseStation
	 */
	protected String display;
	
	/**
	 * Velocità attuale dell'auto
	 */
	protected float speed;
	
	/**
	 * Canale di trasmissione associato all'auto
	 */
	protected Channel channel;
	
	/**
	 * Timer dell'auto: viene settato un timer per inviare i messaggi alla BaseStation
	 */
	protected Timer timer;
	
	/**
	 * Referenza della BaseStation
	 */
	protected Subject baseStation;
	
	/**
	 * Metodo per recuperare l'identificativo dell'auto
	 * @return idCar: identificativo dell'auto
	 */
	public abstract String getCarId();
	
	/**
	 * Metodo per recuperare la velocità attuale dell'auto
	 * @return speed: velocità attuale dell'auto
	 */
	public abstract int getSpeed(); 
	
	/**
	 * Metodo per recuperare il display dell'auto
	 * @return display: display dell'auto
	 */
	public abstract String getDisplay(); 
	
	/**
	 * Mando un pacchetto alla BaseStation; genero casualmente una nuova velocità
	 */
	public abstract void sendPacket();
}
