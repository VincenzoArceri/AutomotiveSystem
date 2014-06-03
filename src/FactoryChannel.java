/**
 * Interfaccia FactoryChannel implementata dalla BaseStation per creare
 * canali quando necessario.
 * 
 * @author Vincenzo Arceri, Matteo Calabria, Pietro Musoni, Carlo Tacchella
 *
 */
public interface FactoryChannel {
	public Channel createChannel(String idChannel);
}
