import java.util.Vector;


public class Channel {

	private static final int capacity = 100;
	private int freeSpace = capacity;
	private String idChannel;
	private BaseStation baseStation;
	private Vector<String> queue = new Vector<String>();
	
	public Channel(String id, BaseStation base) {
		this.idChannel = id;
		this.baseStation = base;
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
	
	public void dispatchPacketToStation(Packet packet) {
		baseStation.receivePacket(packet);
	}
	
	public Vector<String> getIDs() {
		return queue;
	}
	
}
