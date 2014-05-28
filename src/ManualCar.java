
public class ManualCar implements Observer {
	
	private String idCar;
	private String display;
	private float speed;
	private String channel;
	private Subject baseStation;
	
	public ManualCar(String id, Subject base) {
		this.baseStation = base;
		this.idCar = id;
		this.speed = 0;
		this.display = new String("I'm alive! :)");
	}
	
	@Override
	public void update(Packet packet) {
		
		if (packet.id.equals("Broadcast")) {
			if (packet.text.equals("Join") && (channel == null)) {
				Packet packetToSend = new Packet(idCar, null, "Manual", null);
				System.out.println(idCar + ">> È arrivato un messaggio in broadcast");
				baseStation.registerObserver(packetToSend);
			}
			else if (channel != null)
				return;
		} else if (packet.id.equals(idCar)) {
			if (packet.text.equals("All channels are full")) {
				System.out.println(idCar + ">> È pieno! :(");
				return;
			}
			else if (packet.text.equals("You're logged")) {
				this.channel = packet.channel;
				System.out.println(idCar + ">> Loggato! :D");
				// LANCIO LOOP
			}
		} else
			return;
	}
	
	public void send(Packet packet) {
		
	}
}
