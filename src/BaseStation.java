import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

/**
 * Il funzionamento di BaseStation è il seguente:
 * 	- BaseStation manda in broadcast un messaggio invitando le macchine ad effettare il join;
 * 	- successivamente, riceverà i pacchetti con le velocità delle macchine;
 * 	- effettua il confronto con la threshold e invia i relativi messaggi in broadcast.
 * 
 * @author Vincenzo Arceri, Matteo Calabria, Pietro Musoni, Carlo Tacchella
 *
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
	
	/**
	 * Vector di Packet inviati dai canali
	 */
	Vector<Packet> packets = new Vector<Packet>();
	
	/**
	 * Tempo di deregistrazione di un'auto: ogni 12 secondi vengono deregistrate delle auto
	 */
	private int timeToUnregister = 12000;
	
	/**
	 * Timer per l'invio dei Packet: ogni packetRate ms vengono prelevati i messaggi dal
	 * Vector packets e vengono esaminati.
	 */
	private Timer timer;
	
	/**
	 * Timer per deregistrare delle auto dal sistema: ogni 12 secondi vengono deregistrate
	 * delle auto dal sistema.
	 */
	private Timer timerToUnregister;
	
	/**
	 * Costruttore della classe BaseStation
	 */
	public BaseStation() {
		// Inizialmente ci sarà solamente un canale attivo
		channels[0] = createChannel("Channel1");	
		channelsCreated++;
		// Inizializzo i timer
		this.timer = new Timer();
		this.timerToUnregister = new Timer();
	}
	
	/**
	 * Metodo per invitare tutti le auto a registrarsi al sistema
	 */
	public void joinToMe() {
		Packet packet = new Packet("Broadcast", "Join", null, null, 0);

		notifyObservers(packet);

		// Inizializzo il timer per il checking dei messaggi
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
		
		// Per ogni canale attivo controllo che sia libero
		for (int i = 0; i < channelsCreated; i++) {
			// Se il canale è pieno e non è l'ultimo canale controllo il canale successivo
			if (channels[i].isFull() && !channels[i].getId().equals("Channel" + numberChannel))
				continue;
			// Se il canale non è pieno controllo che ci siamo spazione per l'auto automatica
			else if (!channels[i].isFull()) {
				if (channels[i].getFreeSpaces() >= 10) {
					channels[i].subSpace(10);
					channels[i].addToQueue(id);
					// L'auto è loggata, lo notifico all'auto
					Packet packet = new Packet(id, "You're logged", null, channels[i], 0);
					return packet;
				}		
			}
		}
		
		// Se arrivo a questo punto significa che tutti
		// i canali disponibili sono occupati;
		// controllo che possa ancora creare canali
		if (channelsCreated < numberChannel) {
			channels[channelsCreated] = createChannel("Channel" + ++channelsCreated);
			channels[channelsCreated- 1].subSpace(10);
			channels[channelsCreated- 1].addToQueue(id);
			
			// Creato il nuovo canale e registrata l'auto; lo notifico
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
		// Per ogni canale attivo controllo che sia libero
		for (int i = 0; i < channelsCreated; i++)
			// Se il canale è pieno e non è l'ultimo canale controllo il canale successivo
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
		
		// Se arrivo a questo punto significa che tutti
		// i canali disponibili sono occupati;
		// controllo che possa ancora creare canali
		if (channelsCreated < 5) {
			channels[channelsCreated] = createChannel("Channel" + ++channelsCreated);
				channels[channelsCreated- 1].subSpace(5);
				channels[channelsCreated- 1].addToQueue(id);
				// L'auto è loggata, lo notifico all'auto
				Packet packet = new Packet(id, "You're logged", null, channels[channelsCreated - 1], 0);
				return packet;
		}
		
		// Creato il nuovo canale e registrata l'auto; lo notifico
		Packet packet = new Packet(id, "All channels are full", null, null, 0);
		return packet;
	}
	
	/**
	 * Metodo per deregistrare delle auto random.
	 */
	@Override
	public void unregisterObserver() {
		Random random = new Random();

		Packet packet1 = new Packet("manual" + random.nextInt(90), "Go away!", null, null, 0);
		Packet packet2 = new Packet("automatic" + random.nextInt(90), "Go away!", null, null, 0);
		
		notifyObservers(packet1);
		notifyObservers(packet2);
	}
	
	/**
	 * Metodo per inviare un pacchetto in broadcast
	 * 
	 * @param packet: pacchetto da inviare in broadcast
	 */
	@Override
	public void notifyObservers(Packet packet) {
		for (ManualCar car: allCars) 
			car.update(packet);		
	}
	
	/**
	 * Metodo invocato dai Channel che permette
	 * alla BaseStation di ricevere un pacchetto
	 * 
	 * @param packet: pacchetto da ricevere
	 */
	public void receivePacket(Packet packet) {	
		packets.add(packet);
	}
	
	/**
	 * Metodo per controllare i pacchetti ed inviare le relative
	 * informazioni alle auto. Ogni secondo controllerà
	 * la coda dei messaggi.
	 * 
	 */
	public void checkPackets() {
		
		for (int i = 0; i < packets.size(); i++) {
			if (packets.get(i).newSpeed <= threshold) {
				Packet packetToSend = new Packet(packets.get(i).id, "You're OK!", null, null, 0);
				notifyObservers(packetToSend);
			} else {
				Packet packetToSend = new Packet(packets.get(i).id, "You have to decrease your speed!", null, null, 0);
				notifyObservers(packetToSend);	
			}
			
			packets.remove(i);
		}	
	}
}
