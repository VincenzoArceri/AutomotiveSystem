
public class CarAdapter extends ManualCar {
		
	public AutomaticCar automatic;
	
	public CarAdapter(String id, Subject base) {
		super(id, base);
		automatic = new AutomaticCar(id, base);
	}

	@Override
	public void update(Packet packet) {
		this.automatic.oldUpdate(packet);
	}
}
