import java.io.*;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;

/**
 * Classe con metodi static che permette la costruzione del modello, 
 * partendo dall'apertura di un file e arrivando al caricamento completo.
 *
 */

public class CostruzioneModello {

	//E fare due classi: Analisi File e Costruzione Modello?
	
	private static File file;
	private static Modello modelloCaricato;
	private final static String cartella = "Modelli"; //La cartella dove risiederanno i file modelli salvati. Magari cambiata
	
	/**
	 * Metodo che carica un file. 
	 * Esso invoca un altro metodo per aprire il file attraverso una interfaccia grafica.
	 * Se il file &egrave; stato aperto correttamente invoca un altro metodo per leggere il file.
	 */
	public static Modello caricaModello(){
		String loc = cartella + File.separator; //Location del file
		try {
			file = aprifile(loc);
			if(file != null){
				modelloCaricato = new Modello(file.getName()); //Magari tolgo estensione?
				leggoFile(new FileReader(file));
			}else{
				System.out.println("Non hai selezionato nessun file");
			}
		} catch (FileNotFoundException e) { e.printStackTrace(); }
		return modelloCaricato;
	}
	
	/**
	 * Metodo che permette di aprire un file attraverso un JFileChooser.
	 * Viene aperta una finestra nella cartella predefinita e viene chiesto all'utente di scegliere un file.
	 * @param Stringa con la location del file.
	 * @return Il file aperto, altrimenti null se non &egrave; stato aperto alcun file.
	 * @throws FileNotFoundException
	 */
	private static File aprifile(String loc) throws FileNotFoundException{
		JFileChooser chooser = new JFileChooser(new File(loc));
	    int returnVal = chooser.showOpenDialog(null);
	    if(returnVal == JFileChooser.APPROVE_OPTION){
	    	return chooser.getSelectedFile();
	    }
	    else 
	    	return null;
	}
	
	/**
	 * Metodo che legge le righe di un file.
	 * Attraverso un BufferedReader viene letto il file riga per riga.
	 * Ogni riga viene analizzata attraverso un altro metodo che si occupa di fare analisi
	 * sulle stringhe. 
	 * La lettura finisce alla fine del file, ovvero quando viene letta la stringa null.
	 * @param FileReader. Il file da leggere.
	 */
	private static void leggoFile(FileReader fileReader){
		
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String file="";
		
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
	    	if(riga == null){
	    		break;
	    	}
	    	
	    	//
	    	String elemento = analisiElemento(riga);
	    	Elemento nuovoElemento = restituisciElemento(elemento);
	    	if(nuovoElemento != null){
	    		aggiungoElemento(nuovoElemento);
	    		file = file + riga + "\n";
	    	};
	    }
	    System.out.println(file);
	    for(Elemento elem: modelloCaricato.getElementi()){
	    	System.out.println(elem.toString());
	    }
	}
	
	private static Elemento restituisciElemento(String elemento){
		Elemento elem = null;
		if(elemento != null){
			String IDElem = restituisciID(elemento);
			String NomeElem = restituisciNome(elemento);
			if(IDElem != null && NomeElem != null){
				switch(IDElem){
				case "AZIONE":
					elem = new Azione(NomeElem);
					break;
				case "BRANCH":
					elem = new Branch(NomeElem);
					break;
				case "MERGE":
					elem = new Merge(NomeElem);
					break;
				case "START":
					elem = new Start(NomeElem);
					break;
				case "END":
					elem = new End(NomeElem);
					break;
//				case "FORK":
//					//elem = new Fork(NomeElem);
//					break;
//				case "JOIN":
//					//elem = new Join(NomeElem);
//					break;
				default:
					System.out.println("Elemento non riconosciuto");
				}
			}
		}
		return elem;
	}
	
	private static void aggiungoElemento(Elemento elem){
		modelloCaricato.aggiungiElemento(elem);
	}
	
	private static void aggiungoEntrate(String stringa){
		String in = analisiIn(stringa);
		if(in != null){
			Vector <String> stringheIn = analisiSeparatori(",", in);
			if(stringheIn != null){
				for(String elem: stringheIn){
					System.out.println(elem);
					//Aggiungi all'elemento
				}
			}
		}
	}
	
	private static void analisiRiga(String stringa){
		//Elemento nuovoElemento = null;
		
		String elemento = analisiElemento(stringa);
		if(elemento != null){
			String IDElem = restituisciID(elemento);
			String NomeElem = restituisciNome(elemento);
			if(IDElem != null && NomeElem != null){
				System.out.println("ID: " + IDElem + " Nome: " + NomeElem);
				
			}
		}
		
		String in = analisiIn(stringa);
		//String in = restituisciStringa("in(", ")", stringa);
		if(in != null){
			System.out.println("Analisi in#" + in);
			Vector <String> stringheIn = analisiSeparatori(",", in);
			if(stringheIn != null){
				for(String elem: stringheIn){
					System.out.println(elem);
				}
			}
		}
		
		String out = analisiOut(stringa);
		//String out = restituisciStringa("out(", ")", stringa);
		if( out != null){
			System.out.println("Analisi out#" + out);
			Vector <String> stringheOut = analisiSeparatori(",", out);
			if(stringheOut != null){
				for(String elem: stringheOut){
					System.out.println(elem);
				}
			}
		}
		System.out.println();
		
	}
	
	/**
	 * Analizza una stringa e restituisce l'elemento inziale del pattern.
	 * @param stringa
	 * @return elemento
	 */
	private static String analisiElemento(String stringa){
		String elemento = null;
		String patternRegex = "^.*[\\w]*.*:";
		Pattern pattern = Pattern.compile(patternRegex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(stringa);
		
		while (matcher.find()){
			elemento = matcher.group().replaceAll(":", ""); //Elimino due punti
			elemento = elemento.trim(); //Tolgo eventuali spazi
		}
		return elemento;
	}
	
	/**
	 * Analizza una stringa e restituisce la stringa all'interno di in(..)
	 * @param riga
	 * @return inElem
	 */
	private static String analisiIn(String stringa){
		return restituisciStringa("in(", ")", stringa);
		/*
		String inElem = null;
		int index = stringa.indexOf("in(");
		int end = stringa.indexOf(")", index);
		if(index != -1 && end != -1){
			inElem = stringa.substring(index, end);
			inElem = inElem.replaceAll("in\\(", "");
			inElem = inElem.trim();
		}
		return inElem;
		*/
	}
	
	/**
	 * Analizza una stringa e restituisce la stringa all'interno di out(..)
	 * @param stringa
	 * @return outElem
	 */
	private static String analisiOut(String stringa){
		return restituisciStringa("out(", ")", stringa);
		/*
		String outElem = null;
		int index = stringa.indexOf("out(");
		int end = stringa.indexOf(")", index);
		if(index != -1 && end != -1){
			outElem = stringa.substring(index, end);
			outElem = outElem.replaceAll("out\\(", "");
			outElem = outElem.trim();
		}
		return outElem;
		*/
	}
	
	/**
	 * Passata una stringa e due parametri per l'inizio e la fine restituisce una stringa tra i due parametri.
	 * @param initRegEx Stringa (espressione regolare) per trovare l'inizio della stringa.
	 * @param endRegEx Stringa (espressione regolare) per trovare la fine della stringa.
	 * @param stringa Stringa sulla quale effettuare la ricerca.
	 * @return Stringa filtrata tra l'initRegEx e l'endRegEx.
	 */
	private static String restituisciStringa(String initRegEx, String endRegEx, String stringa){
		String stringaFiltrata = null;
		int index = stringa.indexOf(initRegEx);
		int end = stringa.indexOf(endRegEx, index);
		if(index != -1 && end != -1){
			stringaFiltrata = stringa.substring(index, end);
			stringaFiltrata = stringaFiltrata.replace(initRegEx, "");
			stringaFiltrata = stringaFiltrata.trim();
		}
		return stringaFiltrata;
	}
	
	/**
	 * Riceve in ingresso un separatore e una stringa e restituisce il vector contenente tutte le sottostringhe divise da quel separatore
	 * @param regSeparator. Un separatore
	 * @param stringa
	 * @return Vector di stringhe separate
	 */
	private static Vector <String> analisiSeparatori(String regSeparator, String stringa){
		Vector <String> stringheSeparate = new Vector <String> ();
		String [] result = stringa.split(regSeparator);
	     for (int i=0; i < result.length; i++){
	    	// System.out.println(result[i]);
	    	 stringheSeparate.add(result[i].trim());
	     }
		return stringheSeparate;
	}
	
	/**
	 * Controlla che l'ID sia valido.
	 * @param id
	 * @return
	 */
	private static boolean checkID(String id){
		if(id != null){
			boolean condizioniVerificate = id.equalsIgnoreCase("Azione") || id.equalsIgnoreCase("Branch") || 
					id.equalsIgnoreCase("Merge") || id.equalsIgnoreCase("Start") || id.equalsIgnoreCase("End"); //Da aggiungere condizioni
			return condizioniVerificate;
		}
		else
			return false;
	}
	
	/**
	 * Metodo che data una stringa dal pattern "[ID] nome" restituisce l'ID.
	 * @param stringa
	 * @return ID
	 */
	private static String restituisciID(String stringa){
		String ID = null;
		
		String patternRegex = "\\[.*\\]";
		Pattern pattern = Pattern.compile(patternRegex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(stringa);
		
		if(matcher.find()){
			ID = matcher.group();
			ID = ID.replace("[", "").replace("]", "");
		}
		
		//Aggiunto controllo validita' ID
		if(checkID(ID))
			return ID;
		return null;
		
	}
	
	/**
	 * Metodo che data una stringa dal pattern "[ID] nome" restituisce il nome.
	 * @param stringa
	 * @return
	 */
	private static String restituisciNome(String stringa){
		String nome = null;
		
		String patternRegex = "\\].*";
		Pattern pattern = Pattern.compile(patternRegex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(stringa);
		
		if(matcher.find()){
			nome = matcher.group();
			nome = nome.replace("]", "").trim();
		}
		
		return nome;
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
