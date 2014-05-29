import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

/**
 * Il funzionamento di BaseStation è il seguente:ù
 * 	- BaseStation manda in broadcast un messaggio invitando le macchine ad effettare il join;
 * 	- successivamente, riceverà i pacchetti con le velocità delle macchine;
 * 	- effettua il confronto con la threshold e invia i relativi messaggi in broadcast
*/
public class BaseStation implements FactoryChannel, Subject {
	/**
	 * Variabile che indica il limite di velocità delle macchine
	 */
	public static final int threshold = 50;
	
	/**
	 * Numero massimo di canali creabili
	 */
	private static final int numberChannel = 500;
	
	/**
	 * Tutte le macchine (registrate ad un canale o meno) presenti nel sistema
	 */
	public Vector<ManualCar> allCars;
	
	/**
	 * Array dei canali disponibili
	 */
	public Channel channels[] = new Channel[numberChannel];
	
	/**
	 * Canali creati
	 */
	public int channelsCreated = 0;
	
	/**
	 * Velocità di trasmissione della BaseStation
	 */
	private int packetRate = 1000;
	
	Vector<Packet> packets = new Vector<Packet>();
	
	private int timeToUnregister = 12000;
	
	private Timer timer;
	
	private Timer timerToUnregister;
	
	public BaseStation() {
		/**
		 * Inizialmente ci sarà un solo canale attivo, Channel1
		 */
		channels[0] = createChannel("Channel1");	
		channelsCreated++;
		this.timer = new Timer();
		this.timerToUnregister = new Timer();
	}
	/**
	 * Metodo per invitare tutti le auto a registrarsi al sistema
	 */
	public void joinToMe() {
		Packet packet = new Packet("Broadcast", "Join", null, null, 0);
		
		for (ManualCar car: allCars) 
			car.update(packet);
		
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				checkPackets();
			}
		}, 0, packetRate);
		
		timerToUnregister.schedule(new TimerTask() {

			@Override
			public void run() {
				unregisterObserver();
			}
		}, 0, timeToUnregister);
	}

	/**
	 * Metodo di creazione di un canale
	 * 
	 * @param idChannel: identificatore del canale
	 */
	@Override
	public Channel createChannel(String idChannel) {
		System.out.println("Creato il canale " + idChannel);
		return new Channel(idChannel, this);
	}
	
	/**
	 * Metodo chiamato dalle auto per tentare di registrarsi al sistema
	 * 
	 * @param packet: pacchetto inviato dalle auto
	 */
	@Override
	public void registerObserver(Packet packet) {
		Packet packetToSend = null;
		
		if (packet.type.equals("Manual"))
			packetToSend = registerManualCar(packet.id);
		else if (packet.type.equals("Automatic"))
			packetToSend = registerAutomaticCar(packet.id);
		
		for (ManualCar car: allCars) 
			car.update(packetToSend);
	}
	
	/**
	 * Metodo di registrazione di una macchina automatica
	 * @param id: identificatore dell'auto
	 * @return pacchetto da inviare all'auto
	 */
	private Packet registerAutomaticCar(String id) {
		for (int i = 0; i < channelsCreated; i++) {
			if (channels[i].isFull() && !channels[i].getId().equals("Channel" + numberChannel))
				continue;
			else if (!channels[i].isFull()) {
				if (channels[i].getFreeSpaces() >= 10) {
					channels[i].subSpace(10);
					channels[i].addToQueue(id);
					Packet packet = new Packet(id, "You're logged", null, channels[i], 0);
					return packet;
				}		
			}
		}
		
		if (channelsCreated < numberChannel) {
			channels[channelsCreated] = createChannel("Channel" + ++channelsCreated);
			channels[channelsCreated- 1].subSpace(5);
			channels[channelsCreated- 1].addToQueue(id);
			Packet packet = new Packet(id, "You're logged", null, channels[channelsCreated - 1], 0);
			return packet;
		}

		Packet packet = new Packet(id, "All channels are full", null, null, 0);
		return packet;
	}

	/**
	 * Metodo di registrazione di una macchina manuale
	 * @param id: identificatore dell'auto
	 * @return pacchetto da inviare all'auto
	 */
	private Packet registerManualCar(String id) {
		for (int i = 0; i < channelsCreated; i++)
			if (channels[i].isFull() && !channels[i].getId().equals("Channel5"))
				continue;
			else if (!channels[i].isFull()) {
				if (channels[i].getFreeSpaces() >= 5) {
					channels[i].subSpace(5);
					channels[i].addToQueue(id);
					Packet packet = new Packet(id, "You're logged", null, channels[i], 0);
					return packet;
				}		
			}
		if (channelsCreated < 5) {
			channels[channelsCreated] = createChannel("Channel" + ++channelsCreated);
				channels[channelsCreated- 1].subSpace(5);
				channels[channelsCreated- 1].addToQueue(id);
				Packet packet = new Packet(id, "You're logged", null, channels[channelsCreated - 1], 0);
				return packet;
		}
		
		Packet packet = new Packet(id, "All channels are full", null, null, 0);
		return packet;
	}

	@Override
	public void unregisterObserver() {
		Random random = new Random();

		Packet packet1 = new Packet("manual" + random.nextInt(90), "Go away!", null, null, 0);
		Packet packet2 = new Packet("automatic" + random.nextInt(90), "Go away!", null, null, 0);

		for (ManualCar car: allCars) { 
			car.update(packet1);
			car.update(packet2);	
		}
	}

	@Override
	public void notifyObservers() {
				
	}
	
	public void receivePacket(Packet packet) {	
		packets.add(packet);
	}
	
	public void checkPackets() {
		
		for (int i = 0; i < packets.size(); i++) {
			if (packets.get(i).newSpeed <= threshold) {
				Packet packetToSend = new Packet(packets.get(i).id, "You're OK!", null, null, 0);
				for (ManualCar car: allCars) 
					car.update(packetToSend);
			} else {
				Packet packetToSend = new Packet(packets.get(i).id, "You have to decrease your speed!", null, null, 0);

				for (ManualCar car: allCars) 
					car.update(packetToSend);		
			}
			packets.remove(i);
		}	
	}
}
