import java.util.Timer;
import java.util.TimerTask;

/**
 * Classe di una macchina manuale
 * 
 * @author Vincenzo Arceri, Matto Calabria, Pietro Musoni, Carlo Tacchela
 *
 */
public class ManualCar extends AbstractCar implements Observer  {

	/**
	 * Costruttore della classe ManualCar
	 * @param id: identificatore della macchina
	 * @param base: referenza alla BaseStation
	 */
	public ManualCar(String id, Subject base) {
		this.packetRate = 4000;
		this.baseStation = base;
		this.idCar = id;
		this.speed = 0;
		this.timer = new Timer();
		this.display = new String("Not registered");
	}

	/**
	 * Metodo di update per ricevere messaggi dalla BaseStation
	 * 
	 * @param packet: pacchetto che deve essere ricevuto da un'auto
	 */
	@Override
	public void update(Packet packet) {

		if (packet.id.equals("Broadcast")) {
			if (packet.text.equals("Join") && (channel == null)) {
				Packet packetToSend = new Packet(idCar, null, "Manual", null, 0);
				System.out.println(idCar + ">> È arrivato un messaggio in broadcast");
				baseStation.registerObserver(packetToSend);
			} else if (channel != null)
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
				display = "Good!";
			} else if (packet.text.equals("Decrease your speed!")) {
				System.out.println(idCar + ">> Non sto andando molto bene andando bene");
				display = "Decrease your speed!";
			} else if (packet.text.equals("Go away!") && channel != null && !idCar.equals("Unregistered")) {
				timer.cancel();
				display = "Unregistered";
				speed = 0;
				channel.addSpace(5);
				channel.removeToQueue(idCar);
				idCar = "Unregistered";
			}
		} else
			return;
	}
	
	/**
	 * Metodo per inviare un pacchetto alla BaseStation; genero casualmente una nuova velocità
	 */
	@Override
	public void sendPacket() {
		double newSpeed = Math.random() * 150;
		speed = (int) newSpeed;
		Packet packet = new Packet(idCar, null, null, null, (int) newSpeed);
		channel.dispatchPacketToStation(packet);
		display = "Waiting for new display";
		System.out.println(idCar + ">> Velocità inviata: " + packet.newSpeed);
	}

	/**
	 * Metodo per recuperare l'identificativo dell'auto
	 * @return idCar: identificativo dell'auto
	 */
	@Override
	public String getCarId() {
		return idCar;
	}
	
	/**
	 * Metodo per recuperare la velocità attuale dell'auto
	 * @return speed: velocità attuale dell'auto
	 */
	@Override
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
}
