import java.io.*;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;

/**
 * Classe con metodi static che permette la costruzione del modello, 
 * partendo dall'apertura di un file e arrivando al caricamento completo.
 *
 */

public class CostruzioneModello {

	private static FileReader fileReader;
	private static BufferedReader bufferedReader;
	private final static String cartella = "Modelli"; //La cartella dove risiederanno i file modelli salvati.
	
	
	/**
	 * Metodo che carica un file. 
	 * Esso invoca un altro metodo per aprire il file attraverso una interfaccia grafica.
	 * Se il file &egrave; stato aperto correttamente invoca un altro metodo per leggere il file.
	 */
	public static void caricaFile(){
		try {
			fileReader = aprifile();
			if(fileReader != null){
				leggoFile();
			}else{
				System.out.println("Non hai selezionato nessun file");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}
	
	/**
	 * Metodo che legge le righe di un file.
	 * Attraverso un BufferedReader viene letto il file riga per riga.
	 * Ogni riga viene analizzata attraverso un altro metodo che si occupa di fare analisi
	 * sulle stringhe. 
	 * La lettura finisce alla fine del file, ovvero quando viene letta la stringa null.
	 */
	private static void leggoFile(){
		
		//Preparo il BufferReader del file aperto
		bufferedReader = new BufferedReader(fileReader);
		
		//Inizializzo la riga letta come null
		String riga = null;
		
		//Eseguo un ciclo infinito che termina solamente alla fine del file,
		//ovvero quando la riga e' null.
	    while(true) {
	    	
	    	//Leggo riga per riga gestendo le IOException
	    	try {
				riga = bufferedReader.readLine();
			} catch (IOException e) { e.printStackTrace(); }
	    	
	    	//Controllo se la riga e' null e in caso esco. 
	    	//Se null significa che sono arrivato in fondo al file.
	    	if(riga == null)
	    		break;
	    	
	    	//Mostro la riga che ho letto
	    	//System.out.println(riga);
	    	
	    	//Passo la riga che ho letto ad un metodo che analizza la riga.
	    	analisiRiga(riga);
	    	
	    }
	}
	
	/**
	 * Questo metodo permette di analizzare una stringa attraverso l'utilizzo delle espressioni
	 * regolari. 
	 * Creo un Pattern e un Matcher per fare in modo che di estrarre i nomi e gli ID che mi servono.
	 * @param riga
	 */
	private static void analisiRiga(String riga){
		
		String patternRegex = "^.*[\\w]*.*:";
		Pattern pattern = Pattern.compile(patternRegex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(riga);
		
		while (matcher.find()){
			String elem = matcher.group().replaceAll(":", ""); //Elimino due punti
			elem = elem.trim(); //Tolgo eventuali spazi
			System.out.println("#" + elem);
		}
			
		/*
		if(matcher.matches()){
			System.out.println("OK");
		}
		else
			System.out.println("NO");
		*/
	}
	
	private static void metodo(){
		/*
		StringTokenizer oggetto = new StringTokenizer(riga, ":");
		oggetto.hasMoreTokens();
		oggetto.countTokens(); 
		*/ 
	}
	
	/**
	 * Metodo che permette di aprire un file attraverso un JFileChooser.
	 * Viene aperta una finestra nella cartella predefinita e viene chiesto all'utente di scegliere un file.
	 * @return Il file aperto, altrimenti null se non &egrave; stato aperto nessun file.
	 * @throws FileNotFoundException
	 */
	private static FileReader aprifile() throws FileNotFoundException{
		JFileChooser chooser = new JFileChooser(new File( cartella + File.separator));
	    int returnVal = chooser.showOpenDialog(null);
	    if(returnVal == JFileChooser.APPROVE_OPTION){
	    	return (new FileReader (chooser.getSelectedFile()));
	    }
	    else 
	    	return null;
	}
	
	//Metodi che servono per la carica del file tramite console.
	/*
	//INUTILE
	private static boolean mostraFileInDirectory(String dir){
		File folder = new File(dir);
		
		//Controllo se la cartella esiste.
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
			return true;
		}
		
		return false;
	}
	//Metodo che serve per caricare un file dato il nome
	//INUTILE
	public static void caricaFileDaStringa(){
		System.out.println();
		boolean fileMostrati = false;
		fileMostrati = mostraFileInDirectory(cartella);
		System.out.println();
		
		if(fileMostrati){
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
				//System.out.println("Il file " + nomeFile + " non esiste");
			}
		} else{
			System.out.println("La directory " + cartella + " nel percorso selezionato non esiste" );
			//Implementare richiesta creazione?!?
		}
	}
	*/
}
