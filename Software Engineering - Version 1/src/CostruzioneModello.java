import java.io.*;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;

/**
 * Classe con metodi static che permette la costruzione del modello, 
 * partendo dall'apertura di un file e arrivando al caricamento completo.
 * 
 * NOTA: I FILE CON COMMENTI "INUTILE" ASPETTIAMO A CANCELLARLI"!!!!
 *
 */

public class CostruzioneModello {
	
	private static File file;
	private static Modello modelloCaricato;
	public final static String cartella = "Modelli"; //La cartella dove risiederanno i file modelli salvati. Magari cambiata
	
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
				//leggoFileOld(new FileReader(file)); //Vecchio algoritmo
				/*Nuovo Algoritmo*/
				String fileString = leggoFile(new FileReader(file));
				stampaModelloCaricato();
				riempimentoTotale(fileString);
				stampaModelloCaricato();
				/*Fine nuove aggiunte*/
			}else{
				System.out.println("Non hai selezionato nessun file");
			}
		} catch (FileNotFoundException e) { e.printStackTrace(); }
		
		//Riempio i vector specializzati con il modello appena caricato
		modelloCaricato.riempiVectorModello();
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
	
	////////////////////////////////////////////////////////
	//METODI DI PROVA NUOVO ALGORITMO
	public static void stampaModelloCaricato(){
	    for(Elemento elem: modelloCaricato.getElementi()){
	    	System.out.println(elem.toString());
	    }
	}
	
	public static String leggoFile(FileReader fileReader){
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		StringBuffer fileString = new StringBuffer();
		
		//Inizializzo la riga letta come null
		String riga = null;
				
		//Eseguo un ciclo infinito che termina solamente alla fine del file,
		//ovvero quando la riga e' null.
		while(true){
			//Leggo riga per riga gestendo le IOException
	    	try {
				riga = bufferedReader.readLine();
			} catch (IOException e) { e.printStackTrace(); }
	    	
	    	//Controllo se la riga e' null e in caso esco. 
	    	//Se null significa che sono arrivato in fondo al file.
	    	if(riga == null){
	    		break;
	    	}
			
			String elemento = analisiElemento(riga);
	    	Elemento nuovoElemento = restituisciElemento(elemento);
	    	if(nuovoElemento != null){
	    		aggiungoElemento(nuovoElemento);
	    		fileString.append(riga + "\n");
	    	};
		}
		
		//Chiudo il bufferedReader
		try {
			bufferedReader.close();
		} catch (IOException e) {e.printStackTrace();}
		
		//Restituisco la stringa del file
		return fileString.toString();
	}
	
	public static boolean riempimentoTotale(String stringa){
		String [] result = stringa.split("\n");
		for (int i=0; i < result.length; i++){
	    	 String elem = analisiElemento(result[i]);
	    	 String idElem = restituisciID(elem);
	    	 String nomeElem = restituisciNome(elem);
	    	 Elemento corrente = modelloCaricato.ricercaElementoInModello(idElem, nomeElem);
	    	 
	    	 for (Elemento next: restisciUscite(result[i])){
	    		 next = modelloCaricato.ricercaElemento(next);
	    		 riempimentoInOut(corrente, next);
	    	 }
	    }
		System.out.println("***");
		
		//Controllo ingressi
		/*
		for(int i = result.length; i > 0; i++){
			String elem = analisiElemento(result[i]);
			String idElem = restituisciID(elem);
	    	String nomeElem = restituisciNome(elem);
	    	Elemento corrente = modelloCaricato.ricercaElementoInModello(idElem, nomeElem);
	    	
	    	for(Elemento before: restituisciEntrate(result[i])){
	    		before = modelloCaricato.ricercaElemento(before);
	    		if(before.getIngresso() != null){
	    			if(!before.getIngresso().equals(corrente))
	    				return false;
	    		}
	    		
	    		if(before.getIngressi() != null){
	    			for(int j = 0; j < before.getIngressi().size(); j++){
	    				
	    				//Se uguali
	    				if(before.getIngressi().get(i).equals(corrente))
	    					break;
	    				
	    				//Controlla ultimo elemento
	    				if( j == before.getIngressi().size()){
	    					if(before.getIngressi().get(j).equals(corrente))
	    						return false;
	    				}	
	    			}
	    			
	    		}
	    	}
		}
		*/
		
		return true;
	}
	
	public static void riempimentoInOut(Elemento corrente, Elemento next){
		if(corrente != null && next != null){
			corrente.aggiungiUscita(next);
			next.aggiungiIngresso(corrente);
		}
	}
	
	//FINE METODI NUOVO ALGORITMO
    ////////////////////////////////////////////////////////
	
	
	/**
	 * Metodo che legge le righe di un file.
	 * Attraverso un BufferedReader viene letto il file riga per riga.
	 * Ogni riga viene analizzata attraverso un altro metodo che si occupa di fare analisi
	 * sulle stringhe. 
	 * La lettura finisce alla fine del file, ovvero quando viene letta la stringa null.
	 * @param FileReader. Il file da leggere.
	 */
	//Inutile
	/*
	private static void leggoFileOld(FileReader fileReader){
		
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		Vector <Elemento> entrate = new Vector <Elemento>();
		Vector <Elemento> uscite = new Vector <Elemento>();
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
	    		entrate = restituisciEntrate(riga);
	    		aggiungoEntrate(nuovoElemento, entrate);
	    		uscite = restisciUscite(riga);
	    		aggiungoUscite(nuovoElemento, uscite);
	    		file = file + riga + "\n";
	    	};
	    }
	    System.out.println(file);
	    for(Elemento elem: modelloCaricato.getElementi()){
	    	System.out.println(elem.toString());
	    }
	}
	*/
	
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
				case "FORK":
					elem = new Fork(NomeElem);
					break;
				case "JOIN":
					elem = new Join(NomeElem);
					break;
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
	
	private static Vector <Elemento> restituisciEntrate(String stringa){
		Vector <Elemento> entrate = new Vector <Elemento>();
		String in = analisiIn(stringa);
		if(in != null){
			Vector <String> stringheIn = analisiSeparatori(",", in);
			if(stringheIn != null){
				for(String elem: stringheIn){
					entrate.add(restituisciElemento(elem));
				}
			}
		}
		return entrate;
	}
	
	private static Vector <Elemento> restisciUscite(String stringa){
		Vector <Elemento> uscite = new Vector <Elemento>();
		String out = analisiOut(stringa);
		if( out != null){
			Vector <String> stringheOut = analisiSeparatori(",", out);
			if(stringheOut != null){
				for(String elem: stringheOut){
					uscite.add(restituisciElemento(elem));
				}
			}
		}
		return uscite;
	}
	
	/**
	 * Metodo che dato un elemento lo aggiunge al vector delle entrate.
	 * @param elemIndex
	 * @param entrate
	 */
	//Inutile
	/*
	private static void aggiungoEntrate(Elemento elemIndex, Vector <Elemento> entrate){
		if(! entrate.isEmpty()){
			Elemento elemFirst = entrate.firstElement();
			int i;
			
			switch (elemIndex.getID()) {
			case "AZIONE":
				Azione nuovaAzione = (Azione) elemIndex;
				if(elemFirst != null)
					nuovaAzione.setIngresso(elemFirst);
				i = modelloCaricato.indiceElemento(elemIndex);
				if(i != -1)
					modelloCaricato.getElementi().set(i, nuovaAzione);
				break;
			case "BRANCH":
				Branch nuovoBranch = (Branch) elemIndex;
				if(elemFirst != null)
					nuovoBranch.setIngresso(elemFirst);
				i = modelloCaricato.indiceElemento(elemIndex);
				if(i != -1)
					modelloCaricato.getElementi().set(i, nuovoBranch);
				break;
			case "MERGE":
				Merge nuovoMerge = (Merge) elemIndex;
				for(Elemento elemIter: entrate){
					nuovoMerge.aggiungiIngresso(elemIter);
				}
				i = modelloCaricato.indiceElemento(elemIndex);
				if(i != -1)
					modelloCaricato.getElementi().set(i, nuovoMerge);
				break;
			case "FORK":
				Fork nuovoFork = (Fork) elemIndex;
				if(elemFirst != null)
					nuovoFork.setIngresso(elemFirst);
				i = modelloCaricato.indiceElemento(elemIndex);
				if(i != -1)
					modelloCaricato.getElementi().set(i, nuovoFork);
				break;
			case "JOIN":
				Join nuovoJoin = (Join) elemIndex;
				for(Elemento elemIter: entrate){
					nuovoJoin.aggiungiIngresso(elemIter);
				}
				i = modelloCaricato.indiceElemento(elemIndex);
				if(i != -1)
					modelloCaricato.getElementi().set(i, nuovoJoin);
				break;
			case "END":
				End nuovoEnd = (End) elemIndex;
				if(elemFirst != null)
					nuovoEnd.setIngresso(elemFirst);
				i = modelloCaricato.indiceElemento(elemIndex);
				if(i != -1)
					modelloCaricato.getElementi().set(i, nuovoEnd);
				break;
			default:
				break;
			}
		}
	}
	*/
	
	//Inutile
	/*
	private static void aggiungoUscite(Elemento elemIndex, Vector <Elemento> uscite){
		if(! uscite.isEmpty()){
			Elemento elemFirst = uscite.firstElement();
			int i;
			
			switch (elemIndex.getID()) {
			case "AZIONE":
				Azione nuovaAzione = (Azione) elemIndex;
				if(elemFirst != null)
					nuovaAzione.aggiungiUscita(elemFirst);
				i = modelloCaricato.indiceElemento(elemIndex);
				if(i != -1)
					modelloCaricato.getElementi().set(i, nuovaAzione);
				break;
			case "BRANCH":
				Branch nuovoBranch = (Branch) elemIndex;
				for(Elemento elemIter: uscite){
					nuovoBranch.aggiungiUscita(elemIter);
				}
				i = modelloCaricato.indiceElemento(elemIndex);
				if(i != -1)
					modelloCaricato.getElementi().set(i, nuovoBranch);
				break;
			case "MERGE":
				Merge nuovoMerge = (Merge) elemIndex;
				if(elemFirst != null)
					nuovoMerge.aggiungiUscita(elemFirst); //FORSE E' MEGLIO METTERE SETUSCITA
				i = modelloCaricato.indiceElemento(elemIndex);
				if(i != -1)
					modelloCaricato.getElementi().set(i, nuovoMerge);
				break;
			case "FORK":
				Fork nuovoFork = (Fork) elemIndex;
				for(Elemento elemIter: uscite){
					nuovoFork.aggiungiUscita(elemIter);
				}
				i = modelloCaricato.indiceElemento(elemIndex);
				if(i != -1)
					modelloCaricato.getElementi().set(i, nuovoFork);
				break;
			case "JOIN":
				Join nuovoJoin = (Join) elemIndex;
				if(elemFirst != null)
					nuovoJoin.aggiungiUscita(elemFirst); //FORSE E' MEGLIO METTERE SETUSCITA
				i = modelloCaricato.indiceElemento(elemIndex);
				if(i != -1)
					modelloCaricato.getElementi().set(i, nuovoJoin);
				break;
			case "START":
				Start nuovoStart = (Start) elemIndex;
				Azione azioneFirst = (Azione) elemFirst;
				if(elemFirst != null)
					nuovoStart.setUscita(azioneFirst);
				i = modelloCaricato.indiceElemento(elemIndex);
				if(i != -1)
					modelloCaricato.getElementi().set(i, nuovoStart);
				break;
			default:
				break;
			}
		}
	}
	*/
	
	//ORA METODO CHE NON SERVE PIU'
	//Inutile
	/*
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
	*/
	
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
	}
	
	
	/**
	 * Analizza una stringa e restituisce la stringa all'interno di out(..)
	 * @param stringa
	 * @return outElem
	 */
	private static String analisiOut(String stringa){
		return restituisciStringa("out(", ")", stringa);
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
					id.equalsIgnoreCase("Merge") || id.equalsIgnoreCase("Start") || id.equalsIgnoreCase("End") ||
					id.equalsIgnoreCase("Fork") || id.equalsIgnoreCase("Join");
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

	public static Modello caricaOggetto() {
		String loc = cartella + File.separator; //Location del file
		try {
			file = aprifile(loc);
			if(file != null){
				modelloCaricato = new Modello(file.getName()); //Magari tolgo estensione?
			}else{
				System.out.println("Non hai selezionato nessun file");
			}
		} catch (FileNotFoundException e) { e.printStackTrace(); }
		return modelloCaricato;
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
