import java.util.Timer;
import java.util.TimerTask;

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
	 * @param packet
	 */
	public void oldUpdate(Packet packet) {

		if (packet.id.equals("Broadcast")) {
			if (packet.text.equals("Join") && (channel == null)) {
				Packet packetToSend = new Packet(idCar, null, "Automatic", null, 0);
				System.out.println(idCar + ">> È arrivato un messaggio in broadcast");
				baseStation.registerObserver(packetToSend);
			}
			else if (channel != null)
				return;
		} else if (packet.id.equals(idCar)) {
			if (packet.text.equals("All channels are full")) {
				System.out.println(idCar + ">> È pieno! :(");
				return;
			}
			else if (packet.text.equals("You're logged")) {
				this.channel = packet.channel;

				timer.schedule(new TimerTask() {

					@Override
					public void run() {
						sendPacket();
					}
				}, 0, packetRate);
			
			} else if (packet.text.equals("You're OK!")) {
				System.out.println(idCar + ">> Sto andando bene");
			} else if (packet.text.equals("You have to decrease your speed!")) {
				System.out.println(idCar + ">> Non sto andando molto bene andando bene");
			} else if (packet.text.equals("Go away!")) {
				timer.cancel();
				idCar = "-1";
				System.out.println("I'm dead!");
			}
		} else
			return;
	}

	public void sendPacket() {
		double newSpeed = Math.random() * 150;
		Packet packet = new Packet(idCar, null, null, null, (int) newSpeed);
		channel.dispatchPacketToStation(packet);
		System.out.println(idCar + ">> Velocità inviata: " + packet.newSpeed);
	}

	public String getCarId() {
		return idCar;
	}

	public int getSpeed() {
		return (int) speed;
	}

	public String getDisplay() {
		return display;
	}
	
	public void breaking(){
		speed = BaseStation.threshold;
	}
}