import java.util.Timer;
import java.util.TimerTask;


public class ManualCar implements Observer {
	private int packetRate = 4000;
	private String idCar;
	private String display;
	private float speed;
	private Channel channel;
	private Timer timer;
	private Subject baseStation;
	
	public ManualCar(String id, Subject base) {
		this.baseStation = base;
		this.idCar = id;
		this.speed = 0;
		this.timer = new Timer();
	}

	@Override
	public void update(Packet packet) {

		if (packet.id.equals("Broadcast")) {
			if (packet.text.equals("Join") && (channel == null)) {
				Packet packetToSend = new Packet(idCar, null, "Manual", null, 0);
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

				timer.schedule(new TimerTask() {

					@Override
					public void run() {
						sendPacket();
					}
				}, 0, packetRate);

			} else if (packet.text.equals("You're OK!")) {
				System.out.println(idCar + ">> Sto andando bene");
				//sendPacket();
			} else if (packet.text.equals("You have to decrease your speed!")) {
				System.out.println(idCar + ">> Non sto andando molto bene andando bene");
				//sendPacket();
			} else if (packet.text.equals("Go away!")) {
				timer.cancel();
				idCar = "-1";
			}
		} else
			return;
	}

	public void sendPacket() {
		double newSpeed = Math.random() * 150;
		Packet packet = new Packet(idCar, null, null, null, (int) newSpeed);
		channel.dispatchPacketToStation(packet);
		System.out.println(idCar + ">> Velocità inviata: " + packet.newSpeed);
	}

	public String getCarId() {
		return idCar;
	}

	public int getSpeed() {
		return (int) speed;
	}

	public String getDisplay() {
		return display;
	}
}
