/*VERSIONE 1: NO FORK E JOIN
 * 
 * questa classe conterrà un menu per gestire la creazione di un nuovo modello
 * permetterà di
 * -iniziare la creazione
 * -->ci vorrà un sottomenu per scegliere cosa aggiungere..
 * -terminare la creazione e quindi salvare il modello
 * --come testo
 * --come oggetto
 * in teoria bisognerebbe poter terminare solo se il modello è corretto..
 * 
 * 
 */
public class CreazioneModello {
	
	private Modello modello;
	
	public CreazioneModello(String nomemodello){
		modello = new Modello(nomemodello);
	}
	
}
