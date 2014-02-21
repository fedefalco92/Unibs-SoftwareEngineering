/*VERSIONE 1: NO FORK E JOIN
 * 
 * questa classe conterr� un menu per gestire la creazione di un nuovo modello
 * permetter� di
 * -iniziare la creazione
 * -->ci vorr� un sottomenu per scegliere cosa aggiungere..
 * -terminare la creazione e quindi salvare il modello
 * --come testo
 * --come oggetto
 * in teoria bisognerebbe poter terminare solo se il modello � corretto..
 * 
 * 
 */
public class CreazioneModello {
	
	private Modello modello;
	
	public CreazioneModello(String nomemodello){
		modello = new Modello(nomemodello);
	}
	
}
