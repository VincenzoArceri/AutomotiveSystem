import java.util.Vector;


public class BaseStation implements FactoryChannel, Subject {
	
	private static final int threshold = 50;
	private static final int numberChannel = 5;
	public Vector<ManualCar> allCars;
	public Channel channels[] = new Channel[numberChannel];
	public int channelsCreated = 0;
	public BaseStation() {
		channels[0] = createChannel("Channel1");	
		channelsCreated++;
	}
	
	public void joinToMe() {
		Packet packet = new Packet("Broadcast", "Join", null, null);
		
		for (ManualCar car: allCars) 
			car.update(packet);
	}

	
	@Override
	public Channel createChannel(String idChannel) {
		System.out.println("Creato il canale " + idChannel);
		return new Channel(idChannel);
	}
	
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

	private Packet registerAutomaticCar(String id) {
		for (int i = 0; i < channelsCreated; i++)
			if (channels[i].isFull())
				continue;
			else {
				if (channels[i].getFreeSpaces() >= 10) {
					channels[i].subSpace(5);
					channels[i].addToQueue(id);
					Packet packet = new Packet(id, "You're logged", null, channels[i].getId());
					return packet;
				}		
			}
		Packet packet = new Packet(id, "All channels are full", null, null);
		return packet;
	}

	private Packet registerManualCar(String id) {
		for (int i = 0; i < channelsCreated; i++)
			if (channels[i].isFull())
				continue;
			else {
				if (channels[i].getFreeSpaces() >= 5) {
					channels[i].subSpace(5);
					channels[i].addToQueue(id);
					Packet packet = new Packet(id, "You're logged", null, channels[i].getId());
					return packet;
				}		
			}
		Packet packet = new Packet(id, "All channels are full", null, null);
		return packet;
	}

	@Override
	public void unregisterObserver() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyObservers() {
		// TODO Auto-generated method stub
		
	}
}
