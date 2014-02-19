import it.unibs.fp.mylib.InputDati;

import java.io.*;


public class CostruzioneModello {

	private static FileReader fileReader;
	private static BufferedReader bufferedReader;
	private final static String cartella = "Modelli"; //La cartella dove risiederanno i file modelli salvati.
	
	//Metodo che serve per caricare un file dato il nome
	public static void caricaFile(){
		System.out.println();
		mostraFileInDirectory(cartella);
		System.out.println();
		
		String nomeFile = InputDati.leggiStringaNonVuota("Nome del file che vuoi leggere > ");
		String locFile = cartella + File.separator + nomeFile; //+ ".txt"; //Viene usato il File.separator per la differenza degli SO
		
		//Vedo la location del file
		//File nuovo = new File (locFile);
		//System.out.println(nuovo.getAbsolutePath());
		
		try {
			//Inizializzo l'oggetto
			fileReader = new FileReader(locFile);
			
			//Leggo il file riga per riga
			leggoFile();
			
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			System.out.println("Il file " + nomeFile + " non esiste");
		}
		
	}
	
	//Metodo che legge riga per riga il file
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
	
	private static void mostraFileInDirectory(String dir){
		File folder = new File(dir);
		
		//Controllo se la cartella esiste. Da migliorare (metodo boolean)
		if(folder.exists()){ 
			System.out.println("Elenco file presenti nella cartella");
			File[] listOfFiles = folder.listFiles();
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					System.out.println(listOfFiles[i].getName());
				} 
	//			else if (listOfFiles[i].isDirectory()) {
	//				System.out.println("Directory " + listOfFiles[i].getName());
	//			}
			}
		} else{
			System.out.println("La directory " + dir + " nel percorso selezionato non esiste" );
		}
	}
}
