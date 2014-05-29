
public class Packet {
	public final String id;
	public final String text;
	public final String type;
	public final Channel channel;
	public final int newSpeed;
	
	public Packet(String id, String text, String type, Channel channel, int speed) {
		this.id = id;
		this.text = text;
		this.type = type;
		this.channel = channel;
		this.newSpeed = speed;
	}
}
