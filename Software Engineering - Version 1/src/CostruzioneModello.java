import it.unibs.fp.mylib.InputDati;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class CostruzioneModello {

	private static FileReader fileReader;
	private static BufferedReader bufferedReader;
	private final static String cartella = "Modelli/"; //La cartella dove risiederanno i file modelli salvati
	
	public static void caricaFile(){
		String nomeFile = InputDati.leggiStringaNonVuota("Nome del file che vuoi leggere > ");
		String locFile = cartella + nomeFile + ".txt";
		
		//Vedo la location del file
		//File nuovo = new File (locFile);
		//System.out.println(nuovo.getAbsolutePath());
		
		try {
			fileReader = new FileReader(locFile);
			//Leggo il file riga per riga
			leggoFile();
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			System.out.println("Il file " + nomeFile + " non esiste");
		}
		
	}
	
	public static void leggoFile(){
		
		bufferedReader = new BufferedReader(fileReader);
		
		String riga = null; //Inizializzo come null
		
	    while(true) {
	    	
	    	//Leggo la riga del file
	    	try {
				riga = bufferedReader.readLine();
			} catch (IOException e) { e.printStackTrace(); }
	    	
	    	//Quando arrivo in fondo (riga == null) esco dal ciclo
	    	if(riga == null)
	    		break;
	    	
	    	//Mostro la riga che ho letto
	    	System.out.println(riga);
	    }
	}
}
