/**
 * Classe Packet per modellare un pacchetto per inviare dati
 * 
 * @author Vincenzo Arceri, Matteo Calabria, Pietro Musoni, Carlo Tacchella
 */
public class Packet {
	/**
	 * Indica l'identificativo di un auto
	 */
	public final String id;
	
	/**
	 * Testo del messaggio
	 */
	public final String text;
	
	/**
	 * Tipo dell'auto: usato in fase di invio delle caratteristiche dell'auto
	 */
	public final String type;
	
	/**
	 * Canale da associare ad un'auto
	 */
	public final Channel channel;
	
	/**
	 * Nuova velocità da inviare alla BaseStation 
	 */
	public final int newSpeed;
	
	/**
	 * Costruttore di un Packet
	 */
	public Packet(String id, String text, String type, Channel channel, int speed) {
		this.id = id;
		this.text = text;
		this.type = type;
		this.channel = channel;
		this.newSpeed = speed;
	}
}
