
public interface Subject {
	public void registerObserver(Packet packet);
	public void unregisterObserver();
	public void notifyObservers();
}
