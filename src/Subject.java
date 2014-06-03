/**
 * Interfaccia Subject per l'applicazione del pattern Observer
 * 
 * @author Vincenzo Arceri, Matteo Calabria, Pietro Musoni, Carlo Tacchella
 */
public interface Subject {
	public void registerObserver(Packet packet);
	public void unregisterObserver();
	public void notifyObservers(Packet packet);
}
