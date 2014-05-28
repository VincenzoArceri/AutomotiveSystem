import java.util.Vector;


public class Channel {

	private static final int capacity = 100;
	private int freeSpace = capacity;
	private String idChannel;
	private Vector<String> queue = new Vector<String>();
	
	public Channel(String id) {
		this.idChannel = id;
	}
	
	public boolean isFull() {
		return freeSpace == 0;
	}
	
	public void addSpace(int packets) {
		freeSpace += packets;
	}
	
	public void subSpace(int packets) {
		freeSpace -= packets;
	}
	
	public String getId() {
		return idChannel;
	}
	
	public int getFreeSpaces() {
		return freeSpace;
	}
	
	public void addToQueue(String id) {
		queue.add(id);
	}
}
