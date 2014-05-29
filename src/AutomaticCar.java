import java.util.Timer;
import java.util.TimerTask;

/**
 * Classe di una macchina automatica
 * 
 * @author Vincenzo Arceri, Matto Calabria, Pietro Musoni, Carlo Tacchela
 *
 */

public class AutomaticCar {
	/**
	 * Packet rate in trasmissione dell'auto
	 */
	private long packetRate = 2000;
	
	/**
	 * Identificatore dell'auto
	 */
	private String idCar;
	
	/**
	 * Display dell'auto: visualizza i messaggi inviati dalla BaseStation
	 */
	private String display;
	
	/**
	 * Velocità attuale dell'auto
	 */
	private float speed;
	
	/**
	 * Canale di trasmissione associato all'auto
	 */
	private Channel channel;
	
	/**
	 * Timer dell'auto: viene settato un timer per inviare i messaggi alla BaseStation
	 */
	private Timer timer;
	
	/**
	 * Referenza della BaseStation
	 */
	private Subject baseStation;
	
	/**
	 * Costruttore della classe AutomaticCar
	 * @param id: identificatore della macchina
	 * @param base: referenza alla BaseStation
	 */
	public AutomaticCar(String id, Subject base) {
		this.baseStation = base;
		this.idCar = id;
		this.speed = 0;
		this.timer = new Timer();
		this.display = new String("I'm alive! :)");
	}
	/**
	 * Metodo di update: tale metodo non viene mai utilizzato esplicitamente 
	 * poiché viene adattato tramite il patter Adapter ad una ManualCar
	 * @param packet: pacchetto che deve essere ricevuto da un'auto
	 */
	public void oldUpdate(Packet packet) {
		
		// Pacchetto inviato in Broadcast
		if (packet.id.equals("Broadcast")) {
			// Caso in cui il messaggio è un invito di join e non sono registrato
			if (packet.text.equals("Join") && (channel == null)) {
				// Invio il tipo dell'auto e l'identificatore
				Packet packetToSend = new Packet(idCar, null, "Automatic", null, 0);
				System.out.println(idCar + ">> È arrivato un messaggio in broadcast");
				// Tentativo di registrazione alla BaseStation
				baseStation.registerObserver(packetToSend);
			// Se l'auto è già registrata allora non effettuo alcuna operazione
			} else if (channel != null)
				return;
		// Caso in cui il pacchetto è indirizzato all'auto
		} else if (packet.id.equals(idCar)) {
			// Se tutti i canali sono pieni resto in attesa di un nuovo invito di join
			if (packet.text.equals("All channels are full")) {
				System.out.println(idCar + ">> È pieno! :(");
				return;
			// La richiesta di registrazione è stata accettata
			} else if (packet.text.equals("You're logged")) {
				// Canale di comunicazione associato all'auto
				this.channel = packet.channel;
				
				// Inizializzo il timer al packetRate dell'auto:
				// Ogni packetRate ms verrà inviata la nuova velocità
				timer.schedule(new TimerTask() {

					@Override
					public void run() {
						sendPacket();
					}
				}, 0, packetRate);
			
			// La veloctà è sotto i 50 Km/h
			} else if (packet.text.equals("You're OK!")) {
				System.out.println(idCar + ">> Sto andando bene");
				
			// La veloctà è sopra i 50 Km/h
			} else if (packet.text.equals("You have to decrease your speed!")) {
				System.out.println(idCar + ">> Non sto andando molto bene andando bene");
				breaking();
			// Sono stato deregistrato dal sistema:
			// cancello il timer e mi tolgo dal canale
			} else if (packet.text.equals("Go away!")) {
				timer.cancel();
				idCar = "-1";
				System.out.println("I'm dead!");
			}
		} else
			return;
	}
	
	/**
	 * Mando un pacchetto alla BaseStation; genero casualmente una nuova velocità
	 */
	public void sendPacket() {
		double newSpeed = Math.random() * 150;
		Packet packet = new Packet(idCar, null, null, null, (int) newSpeed);
		channel.dispatchPacketToStation(packet);
		System.out.println(idCar + ">> Velocità inviata: " + packet.newSpeed);
	}
	
	/**
	 * Metodo per recuperare l'identificativo dell'auto
	 * @return idCar: identificativo dell'auto
	 */
	public String getCarId() {
		return idCar;
	}
	
	/**
	 * Metodo per recuperare la velocità attuale dell'auto
	 * @return speed: velocità attuale dell'auto
	 */
	public int getSpeed() {
		return (int) speed;
	}
	
	/**
	 * Metodo per recuperare il display dell'auto
	 * @return display: display dell'auto
	 */
	public String getDisplay() {
		return display;
	}
	
	/**
	 * Metodo per decrementare automaticamente la velocità dell'auto
	 */
	public void breaking(){
		speed = BaseStation.threshold;
	}
}