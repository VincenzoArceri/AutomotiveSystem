
public class Packet {
	public final String id;
	public final String text;
	public final String type;
	public final String channel;
	
	public Packet(String id, String text, String type, String channel) {
		this.id = id;
		this.text = text;
		this.type = type;
		this.channel = channel;
	}
}
