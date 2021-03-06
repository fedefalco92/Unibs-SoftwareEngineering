package unibsSWEngineering;
import it.unibs.fp.mylib.ServizioFile;
import java.io.*;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import unibsSWEngineering.modello.*;

/* NOTA: I FILE CON COMMENTI "INUTILE" ASPETTIAMO A CANCELLARLI"!!!!*/

/**
 * Classe con metodi static che permette la costruzione del modello, 
 * partendo dall'apertura di un file e arrivando al caricamento completo.
 *
 */

public class CostruzioneModello {
	
	//private static File file;
	private static Modello modelloCaricato;
	
	/**
	 * 
	 */
	public static Modello caricaModello(File file){
		modelloCaricato = new Modello(file.getName()); //Magari tolgo estensione?
		try {
			//leggoFileOld(new FileReader(file)); //Vecchio algoritmo
			String fileString;
			fileString = leggoFile(new FileReader(file));
			if(!controlloIngressiUscite(fileString)){
				System.out.println("Elementi entrate o uscite non valide");
				return null;
			}
			boolean ok = riempimentoTotale(fileString);
			if(!ok){
				System.out.println("Errore nel modello. Non e' stato possibile caricarlo.");
				return null;
			}
		} catch (FileNotFoundException e) {e.printStackTrace();}
		
		//Riempio i vector specializzati con il modello appena caricato
		modelloCaricato.riempiVectorModello();
		modelloCaricato.toString();
		return modelloCaricato;
				
		
//		String loc = MenuClass.cartella + File.separator; //Location del file
//		try {
//			file = aprifile(loc);
//			if(file != null){
//				modelloCaricato = new Modello(file.getName()); //Magari tolgo estensione?
//				//leggoFileOld(new FileReader(file)); //Vecchio algoritmo
//				/*Nuovo Algoritmo*/
//				String fileString = leggoFile(new FileReader(file));
//				stampaModelloCaricato();
//				riempimentoTotale(fileString);
//				stampaModelloCaricato();
//				/*Fine nuove aggiunte*/
//			}else{
//				System.out.println("Non hai selezionato nessun file");
//				return null;
//			}
//		} catch (FileNotFoundException e) { e.printStackTrace(); }
//		
		
	}
	
//	/**
//	 * Metodo che permette di aprire un file attraverso un JFileChooser.
//	 * Viene aperta una finestra nella cartella predefinita e viene chiesto all'utente di scegliere un file.
//	 * @param Stringa con la location del file.
//	 * @return Il file aperto, altrimenti null se non &egrave; stato aperto alcun file.
//	 * @throws FileNotFoundException
//	 */
//	private static File aprifile(String loc) throws FileNotFoundException{
//		JFileChooser chooser = new JFileChooser(new File(loc));
//	    int returnVal = chooser.showOpenDialog(null);
//	    if(returnVal == JFileChooser.APPROVE_OPTION){
//	    	return chooser.getSelectedFile();
//	    }
//	    else 
//	    	return null;
//	}
	
	////////////////////////////////////////////////////////
	//METODI DI PROVA NUOVO ALGORITMO
	
	/**
	 * Legge un file attraverso un BufferedReader e riempie il modello con elementi corretti trovati.
	 * @param fileReader Il file da leggere
	 * @return Una stringa contentente gli elementi corretti del file, separati da a capo.
	 */
	private static String leggoFile(FileReader fileReader){
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
	    		modelloCaricato.aggiungiElemento(nuovoElemento);
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
	
	/**
	 * Metodo che assicura che gli elementi descritti nel file abbiano un numero di ingressi e di uscite valido.
	 * @param stringaFile Stringa che contiene gli elementi corretti del file, separati da un a capo.
	 * @return True se gli elementi del file contengono entrate e uscite corrette, False altrimenti.
	 */
	private static boolean controlloIngressiUscite(String stringaFile){
		Vector <Elemento> entrate = new Vector <Elemento>();
		Vector <Elemento> uscite = new Vector <Elemento>();
		
		String [] result = stringaFile.split("\n");
		for (int i=0; i < result.length; i++){
			String id = restituisciID(analisiElemento(result[i]));
			entrate = restituisciEntrate(result[i]);
	    	uscite = restisciUscite(result[i]);
	    	
	    	switch (id) {
			case "AZIONE":
				if(entrate.size() > 1)
					return false;
				if(uscite.size() > 1)
					return false;
				break;
				
			case "BRANCH":
			case "FORK":
				if(entrate.size() > 1)
					return false;
				if(uscite.size() < 2)
					return false;
				break;
				
			case "MERGE":
			case "JOIN":
				if(entrate.size() < 2)
					return false;
				if(uscite.size() > 1)
					return false;
				break;
			
			case "START":
				if(!entrate.isEmpty())
					return false;
				if(uscite.size() > 1)
					return false;
				break;
				
			case "END":
				if(entrate.size() > 1)
					return false;
				if(!uscite.isEmpty())
					return false;
				break;
			default:
				System.out.println("ID non valido");
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Metodo che filtra gli elementi della stringaFile passata e li confronta con gli elementi caricati.
	 * Va a cercare le uscite di un elemento e trova la riga dell'elemento corrispondente.
	 * @param stringaFile Stringa che contiene gli elementi corretti del file, separati da un a capo.
	 * @return True se &egrave; corretto, false altrimenti.
	 */
	private static boolean riempimentoTotale(String stringaFile){
		String [] result = stringaFile.split("\n");
		for (int i=0; i < result.length; i++){
	    	 String elem = analisiElemento(result[i]);
	    	 String idElem = restituisciID(elem);
	    	 String nomeElem = restituisciNome(elem);
	    	 Elemento corrente = modelloCaricato.ricercaElementoInModello(idElem, nomeElem);
	    	 
	    	 for (Elemento next: restisciUscite(result[i])){
	    		 next = modelloCaricato.ricercaElemento(next);
	    		 if(!riempimentoInOut(corrente, next))
	    			 return false;
	    	 }
	    }
		return true;
	}
	
	/**
	 * Trova la riga corrispondente ad un elemento.
	 * @param stringaFile Stringa che contiene gli elementi corretti del file, separati da un a capo.
	 * @param elemento Elemento da cercare nella stringaFile
	 * @return la Stringa contenente quell'elemento.
	 */
	//INUTILIZZATO
	private static String findRigaElemento(String stringaFile, Elemento elemento){
		String [] result = stringaFile.split("\n");
		for (int i=0; i < result.length; i++){
	    	 String elem = analisiElemento(result[i]);
	    	 String idElem = restituisciID(elem);
	    	 String nomeElem = restituisciNome(elem);
	    	 Elemento trovato = modelloCaricato.ricercaElementoInModello(idElem, nomeElem);
	    	 
	    	 //Trova elemento corrispondente
	    	 if(trovato.equals(elemento))
	    		 return result[i];
	    }
		return null;
	}
	
	/**
	 * Dato un elemento corrente e un elemento next, se questi sono diversi da null e non sono uguali,
	 * vengono aggiunti alle proprie uscite e ingressi.
	 * @param stringaFile Stringa che contiene gli elementi corretti del file, separati da un a capo.
	 * @param corrente Elemento principale, alle sue uscite viene aggiunto next.
	 * @param next Elemento secondario, ai suoi ingressi viene aggiunto corrente.
	 * @return
	 */
	private static boolean riempimentoInOut(Elemento corrente, Elemento next){
		if(corrente != null && next != null && !corrente.equals(next)){
			corrente.aggiungiUscita(next);
			next.aggiungiIngresso(corrente);
			return true;
			/*
			//ANALISI INGRESSO CORRENTE
			String rigaNext = findRigaElemento(stringaFile, next);
			if(rigaNext != null){
				for ( Elemento e: restituisciEntrate(rigaNext)){
					e = modelloCaricato.ricercaElemento(e);
					if(e.equals(corrente)){
						
						//AGGIUNTA EFFETTIVA
						if(corrente.getUscita() == null){ //Vado a vedere se e' gia' completo
							corrente.aggiungiUscita(next);
							if(next.getIngresso() == null){
								next.aggiungiIngresso(corrente);
								return true;
							}
							else{ //Vado a vedere se e' gia' completo
								System.out.println("Ricontrolla gli ingressi del seguente elemento:");
								System.out.println(next.toString());
								System.out.println();
								return false;
							}
						}
						else{
							System.out.println("Ricontrolla le uscite del seguente elemento:");
							System.out.println(corrente.toString());
							System.out.println();
							return false;
						}
					}
				}
			}
			*/
		}
		return false;
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
	
	/**
	 * Restituisce un Elemento nuovo data una stringa che descrive questo elemento.
	 * @param elemento stringa del formato "[ELEMENTO] Nome"
	 * @return Elemento con quel determinato ID e Nome
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
	
	/**
	 * Restituisce un Vector di Elementi di Entrata data una stringa che descrive interamente questo elemento.
	 * @param stringa Stringa intera che descrive l'elemento.
	 * @return Vector di Elementi che corrispondono alle Entrate dell'elemento.
	 */
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
	
	/**
	 * Restituisce un Vector di Elementi di Uscita data una stringa che descrive interamente questo elemento.
	 * @param stringa Stringa intera che descrive l'elemento.
	 * @return Vector di Elementi che corrispondono alle Uscite dell'elemento.
	 */
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
	 * @return elemento Stringa completa che descrive l'elemento del tipo "[ID] nome"
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

	/**
	 * Carica un modello salvato come oggetto da File.
	 * @param file File contentente l'oggetto.
	 * @return Il modello caricato.
	 */
	public static Modello caricaModelloOggetto(File file) {
		
		if(file.exists()){
			try{
				modelloCaricato = (Modello) ServizioFile.caricaSingoloOggetto(file);
			}
			catch(ClassCastException exc){
				System.out.println("Errore Cast");
			}
		}
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
