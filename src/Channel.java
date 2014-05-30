import java.util.Vector;

/**
 * Classe di un canale di comunicazione
 * 
 * @author Vincenzo Arceri, Matteo Calabria, Pietro Musoni
 */
public class Channel {
	/**
	 * Capacità del canale
	 */
	private static final int capacity = 100;
	
	/**
	 * Posto libero nel canale
	 */
	private int freeSpace = capacity;
	
	/**
	 * Identificatore del canale
	 */
	private String idChannel;
	
	/**
	 * Referenza alla BaseStation
	 */
	private BaseStation baseStation;
	
	/**
	 * Vettore di auto associate al canale
	 */
	private Vector<String> queue = new Vector<String>();
	
	/**
	 * Costruttore di Channel
	 * @param id: identificatore del Channel
	 * @param base: referenza alla BaseStation
	 */
	public Channel(String id, BaseStation base) {
		this.idChannel = id;
		this.baseStation = base;
	}
	
	/**
	 * Metodo che controlla se un canale è pieno
	 * @return true se è pieno, false altrimenti.
	 */
	public boolean isFull() {
		return freeSpace == 0;
	}
	
	public void addSpace(int packets) {
		freeSpace += packets;
	}
	
	public void subSpace(int packets) {
		freeSpace -= packets;
	}
	
	/**
	 * Metodo per recuperare l'identificatore del Channel
	 * 
	 * @return idChannel: identificatore del Channel
	 */
	public String getId() {
		return idChannel;
	}
	
	/**
	 * Metodo per recuperare i posti liberi nel Channel
	 * 
	 * @return freeSpace: posti liberi nel canale
	 */
	public int getFreeSpaces() {
		return freeSpace;
	}
	
	/**
	 * Metodo per aggiungere un'auto al Channel
	 * 
	 * @param id: identificatore dell'auto nel Channel
	 */
	public void addToQueue(String id) {
		queue.add(id);
	}
	
	/**
	 * Metodo per inviare un pacchetto alla BaseStation
	 * 
	 * @param packet: packet da inviare alla BaseStation
	 */
	public void dispatchPacketToStation(Packet packet) {
		baseStation.receivePacket(packet);
	}
	
	/**
	 * Metodo per recuperare gli identificatori delle auto nel Channel
	 * @return queue: Vector di identificatori
	 */
	public Vector<String> getIDs() {
		return queue;
	}
	
}
