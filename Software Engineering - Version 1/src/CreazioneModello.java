/*VERSIONE 1: NO FORK E JOIN
 * 
 * questa classe conterra' un menu per gestire la creazione di un nuovo modello
 * permettera' di
 * -iniziare la creazione
 * -->ci vorra' un sottomenu per scegliere cosa aggiungere..
 * -terminare la creazione e quindi salvare il modello
 * --come testo
 * --come oggetto
 * in teoria bisognerebbe poter terminare solo se il modello e' corretto..
 * 
 * 
 */
public class CreazioneModello {
	
	private static Modello modello;
	
	public static void creaModello(String nomeModello){
		modello = new Modello(nomeModello);
	}
	
	/*
	public CreazioneModello(String nomemodello){
		modello = new Modello(nomemodello);
	}
	*/
}
