package unibsSWEngineering;
import it.unibs.fp.mylib.ServizioFile;
import java.io.*;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import unibsSWEngineering.modello.*;


/**
 * Classe con metodi static che permette la costruzione del modello, 
 * partendo dall'apertura di un file e arrivando al caricamento completo.
 *
 */
public class CostruzioneModello {
	
	private static Modello modelloCaricato;
	
	/**
	 * Metodo che carica il modello da un file di testo.
	 * @return il modello caricato
	 */
	public static Modello caricaModello(File file){
		modelloCaricato = new Modello(file.getName());
		try {
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
	}
	
	////////////////////////////////////////////////////////
	//INIZIO ALGORITMO RIEMPIMENTO
	
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
		}
		return false;
	}
	
	//FINE ALGORITMO RIEMPIMENTO
    ////////////////////////////////////////////////////////
	
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
}
