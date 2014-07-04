/**
 * 
 */
package unibsSWEngineering.analisi;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Vector;

/**
 * @author Massi
 *
 */


public class ClasseEquivalenza implements Serializable{
	/**
	 * Classe che gestisce la struttura 'Classe di Equivalenza'
	 * 
	 */
	private static final long serialVersionUID = 2102236797903692153L;

	private String nome;
	private Prova istanzaProva;
	private int cardinalita;
	private Vector<String> insiemeAzioni;		
	private Vector<Vector<String>> diagnosiMinimali;
	private Hashtable <String,Double> probabilitaProva;
	private Hashtable <String,Double> probabilitaClasse;
	
	/**
	 * Costruttore della classe di equivalenza, che accetta il nome identificativo,
	 * l'insieme delle attivita' e la cardinalita' della classe
	 * @param nome
	 * @param insiemeAttivita
	 * @param cardinalita
	 */
	
	public ClasseEquivalenza(String nome,Vector<String> insiemeAttivita){
		this.nome = nome;
		istanzaProva = null;
		this.cardinalita = 0;
		diagnosiMinimali = new Vector<Vector<String>>(); 
		this.insiemeAzioni = insiemeAttivita;		
		probabilitaProva = new Hashtable <String,Double>();
		probabilitaClasse = new Hashtable <String,Double>();
	}		
	
	/**
	 * Costruttore della classe di equivalenza, che accetta il nome identificativo,
	 * l'insieme delle attivita' e la cardinalita' della classe
	 * @param nome
	 * @param insiemeAttivita
	 * @param cardinalita
	 */
	
	public ClasseEquivalenza(String nome,Vector<String> insiemeAttivita, int cardinalita){
		this.nome = nome;
		istanzaProva = null;
		this.cardinalita = cardinalita;
		diagnosiMinimali = new Vector<Vector<String>>(); 
		this.insiemeAzioni = insiemeAttivita;		
		probabilitaProva = new Hashtable <String,Double>();
		probabilitaClasse = new Hashtable <String,Double>();
	}	
	
	/**
	 * Metodo per settare il nome della classe di equivalenza
	 * @param nome
	 */
	
	public void setNome(String nome){
		this.nome = nome;
	}
	
	/**
	 * Ritorna il nome della classe di equivalenza
	 * @return
	 */
	
	public String getNome(){
		return nome;
	}
	
	/**
	 * Setta la prova come appartenente alla classe di equivalenza
	 * @param _prova
	 */
	
	public void setIstanzaProva(Prova _prova){
		istanzaProva = _prova;
	}
	
	/**
	 * Ritorna la cardinalita' della classe
	 * @return
	 */
	
	public int getCardinalita(){
		return cardinalita;
	}
	
	/**
	 * Imposta la cardinalita' della classe
	 * @param card
	 */
	
	public void setCardinalita(int card){
		cardinalita = card;
	}
	
	/**
	 * Incrementa la cardinalita' della classe
	 * @param card
	 */
	
	public void incrementaCardinalita(){
		cardinalita++;
	}
	
	/**
	 * Ritorna l'oggetto Prova della classe di equivalenza
	 * @return
	 */

	public Prova getProva(){
		return istanzaProva;
	}
	
	/**
	 * Metodo che ritorna un vettore di prove replicate in base alla cardinalita
	 * per poterne effettuare l'elaborazione
	 * @return
	 */
	
	public Vector<Prova> getProveReplicate(){
		Vector<Prova> vettoreProve = new Vector<Prova>();
		for(int i=0;i<cardinalita;i++){
			vettoreProve.add(istanzaProva);
		}
		return vettoreProve;
	}

	/**
	 * Metodo che calcola la probabilita' della classe, mediante invocazione
	 * di un metodo interno e ritorna una tabella associativa con le probabilita'
	 * @return
	 */
	
	public Hashtable <String,Double> getProbabilitaClasse(){
		this.setProbabilitaClasse();
		return probabilitaClasse;		
	}	

	/**
	 * Genera l'insieme di partenza per il calcolo delle diagnosi minimali:
	 * analizza tutte le azioni che fanno parte dei vari cammini e "rimuove"
	 * dalle rilevazioni KO le azioni che sono presenti nelle rilevazioni OK
	 * @return
	 */
	
	private Vector<Vector<String>> generaInsiemePartenza(){
		//algoritmo per generare degli HS minimali
		Vector<Vector<String>> minimalHS = new Vector<Vector<String>>();
		Vector<String> elementiDaRimuovere = new  Vector<String>();		
			//replicazione dell'insieme delle attivita', per poi rimuovere quelle OK
			//e per trovare cosi' l'HS di diagnosi minimale
		
		 Vector<Cammino> percorsoOK = istanzaProva.getEsitoOK();
			 
		 for(Cammino perc : percorsoOK){
			 //preparo gli elementi delle rilevazioni OK che andranno rimossi da ogni K0
			 Vector<String> elementiPercorso = perc.estraiElementi();
			 for(String elem : elementiPercorso){
					 if(elementiDaRimuovere.indexOf(elem) < 0){
						 elementiDaRimuovere.add(elem);
					 }
			 }				 				 				
		 }	
	 
		//ora che ho ricercato in tutte le prove posso eliminare gli elementi OK dalle rilevazioni KO
		
		//per ora gestisco la creazione delle diagnosi minimali utilizzando il metodo "a matrice"
		//da ottimizzare
		
		Vector <Vector<Integer>> corrispondenze = new Vector<Vector<Integer>>();
		
		//determino l'insieme di insiemi su cui effettuare l'analisi del calcolo degli MHS
		
		Vector<Vector<String>> insiemiBase = new Vector<Vector<String>>();
		
		Vector<Cammino> percorsoKO = istanzaProva.getEsitoKO();
		for(int i=0;i<percorsoKO.size();i++){
			Vector<String> elementiAttivita = percorsoKO.get(i).estraiElementi();
			for(String rim : elementiDaRimuovere){
				elementiAttivita.remove(rim);
			}
			if(!elementiAttivita.isEmpty())
				insiemiBase.add(elementiAttivita);
		}		
		return insiemiBase;
		
		//--> fino a qui calcola l'insieme di partenza da cui ricavare gli MHS		
	}	
	
	/**
	 * Generazione delle diagnosi minimali relative alla classe di equivalenza considerata
	 */
	
	private void generaDiagnosi(){
		
		//Applicazione dei metodi creati per trovare l'insieme di diagnosi minimali		
		Vector<Vector<Integer>> corrispondenze = generazioneCorrispondenze();
	
		//se ho corrispondenze su cui calcolare le diagnosi minimali...
		
		if(corrispondenze != null){
		
			diagnosiMinimali = UtilityInsiemi.generaMHS(corrispondenze,insiemeAzioni);		
		}

	}
	
	/**
	 * Questo metodo costruisce la tabella binaria delle corrispondenze
	 * valida per qualsiasi vettore in generale
	 * @return 
	 */
	
	
	private Vector<Vector<Integer>> generazioneCorrispondenze(){
		//ampiezza della colonna
		int numeroColonne = insiemeAzioni.size();
		
		Vector<Vector<Integer>> tabellaCorrispondenze = new Vector<Vector<Integer>>();
		
		//Parto dal vettore esterno
				
		Vector<Vector<String>> insiemiPartenza = generaInsiemePartenza();
		
		
		if(insiemiPartenza.isEmpty()){
			//N.B: se non ho attivita di partenza come mi comporto??
			return null;
		}
		
		else{
			for(Vector<String> sottoinsieme : insiemiPartenza){
				//inizializzazione della riga nella matrice delle corrispondenze
				
				Vector<Integer> rigaCorrispondenza = new Vector<Integer>();
				for(int i=0;i<numeroColonne;i++){
					rigaCorrispondenza.add(0);
				}
				
				//analisi di ciascun sottoinsieme per verificare dove mettere a "1" la posizione
				for(String elemento : sottoinsieme){
					//recupero la posizione dell'attivita' nell'elenco(vector) presente nello stato dell'oggetto
					
					int posizioneInElencoGenerale = insiemeAzioni.indexOf(elemento);
					
					//System.out.println("Posizione in elenco: " + posizioneInElencoGenerale);
					
					//se lo trovo allora setto a 1 la sua posizione, altrimenti no
					
					if(posizioneInElencoGenerale != -1){
						rigaCorrispondenza.setElementAt(1, posizioneInElencoGenerale);					
					}
				}
				tabellaCorrispondenze.add(rigaCorrispondenza);
			}
		}
		
		return tabellaCorrispondenze;
	}
		
	/**
	 * Generazione delle probabilita' relative alla prova di cui fa parte la classe di equivalenza
	 * (in caso di molteplicita' >1) si considera una sola prova(non la si replica in questa classe)
	 */
	
	/*
	 * ATTENZIONE: CONTROLLARE QUESTO METODO CHE C'E' QUALCOSA CHE NON VA!!!!
	 * SBALLATI GLI INDICI DI SETTAGGIO!!!
	 * N.B!!!
	 */
	
	private void setProbabilitaClasse(){
		//solo qui vado ad istanziare effettivamente le diagnosi 
		
		generaDiagnosi();
			
		//Qui devo contare le frequenze di ciascuna attivita'--->come devo fare???
		
		//HO PROVATO AD UTILIZZARE LE HASHTABLE, MA DEVO VERIFICARE SE EFFETTIVAMENTE FUNZIONA...
		//IN APPARENZA SEMBRA ANDARE , MA DEVO VEDERE COME SISTEMARE IL METODO hashCode() CHE 
		//OGNI METODO EREDITA DA OBJECT
		
		//assegnazione della probabilita' alle attivita' appartenenti alle diagnosi minimali
		//negli esempi si hanno sempre e solo singoletti....ma e' cosi' sempre???
		//conto quante volte un'attivita' appare in ciascun sottoinsieme--->vedro' se servira'
		//scansione dell'insieme delle diagnosi minimali
		
		//ciclo di riempimento della tabella delle attivita'
		
		for(String elem : insiemeAzioni){
			probabilitaProva.put(elem, -1.0);
		}
		
		//recupero le azioni coinvolte nella prova in questione
		//e da queste creo un nuovo vettore con solo le azioni non coinvolte(che non fanno parte di alcuna
		//diagnosi minimale)
		//N.B: --> IN FUTURO POSSO SPOSTARE QUESTA OPERAZIONE DIRETTAMENTE IN PROVA!!!
		
		Vector<String> azioniResidue = istanzaProva.getAzioniCoinvolte();

		if(!azioniResidue.isEmpty()){
			// prevengo dal caso in cui non vi siano diagnosi minimali(esito OK)
			if(!diagnosiMinimali.isEmpty()){
				for(Vector<String> sottoinsieme : diagnosiMinimali){
					//passo in rassegna ciascun sottoinsieme
					for(String azione : sottoinsieme){
						int indiceDaRimuovere = azioniResidue.indexOf(azione);
						if(indiceDaRimuovere >= 0){
							azioniResidue.removeElementAt(indiceDaRimuovere);
						}
					}
				}
			}
		}
		
		//ora scansiono le azioni residue e assegno loro probabilita' pari a 0
	
		for(String azione : azioniResidue){
			probabilitaProva.put(azione,0.0);
		}			
	
		//Recupero tutte le azioni coinvolte nelle diagnosi minimali(in generale)
		Vector <String> azioniInDiagnosi = UtilityInsiemi.getAzioniInsieme(diagnosiMinimali);

		//se non e' vuoto posso procedere all'elaborazione
			
			//applicazione della formula dei lucidi per il calcolo della probabilita'
			//con il metodo 1
			double probabilitaCumulate[] = new double[azioniInDiagnosi.size()];
			//inizializzazione del vettore temporaneo delle probabilita' cumulate			
			for(int i=0;i<probabilitaCumulate.length;i++){
				probabilitaCumulate[i] = 0.0;
			}
			//ciclo sulle attivita' contenute nella diagnosi minimale
			//ed internamente ciclo sulle diangosi minimali
			
		for(String singleAction : azioniInDiagnosi){
			//posizione in elenco(qui so che c'e' di sicuro)
			
			int posizioneInElenco = azioniInDiagnosi.indexOf(singleAction);
			for(Vector<String> diagnosi_sing : diagnosiMinimali){
				int dimensioneSingola = diagnosi_sing.size();
				//se l'azione e' presente nel sottoinsieme faccio 1/cardinalita' del sottoinsieme
				//e la sommo nell'array delle probabilita' cumulate, creato sulla dimensione delle
				//attivita' considerate
				if(UtilityInsiemi.member(singleAction, diagnosi_sing)){
					probabilitaCumulate[posizioneInElenco] += (1/(double)dimensioneSingola);
				}
			}
			//se arrivo qui ho finito di passare in rassegna i sottoinsiemi e posso calcolare la 
			//probabilita' definitiva
			int contaOccorrenzeAttivita = UtilityInsiemi.contaPresenze(singleAction, diagnosiMinimali);
			probabilitaProva.put(singleAction, probabilitaCumulate[posizioneInElenco]/(double)contaOccorrenzeAttivita);
		}

		Enumeration<String> iteratore = probabilitaProva.keys();
		while(iteratore.hasMoreElements()){
			String azione = iteratore.nextElement();
			double probProva = probabilitaProva.get(azione);
			probabilitaClasse.put(azione, probProva*cardinalita);
		}
	}
	
	/**
	 * Metodo per la stampa dei risultati complessivi che richiama altri metodi di stampa
	 * relativi a prove e test suite
	 */
	
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("Nome classe di equivalenza : " + nome + "\n");
		buffer.append(istanzaProva.toString() + "\n");
		buffer.append("Cardinalita' della classe : " + cardinalita + "\n");
		buffer.append("Diagnosi minimali : " + diagnosiMinimali.toString() + "\n");
		buffer.append("\nPROBABILITA' PROVA : \n");
		//Iteratore sulla tabella delle probabilita' della prova
		Enumeration<String> iteratore = probabilitaProva.keys();
		while(iteratore.hasMoreElements()){
			String azione = iteratore.nextElement();
			double probabilita = probabilitaProva.get(azione);
			if (probabilita >= 0){
				//per evitare la stampa di valori negativi(IGNOTA)
				buffer.append("Azione " + azione + "\t" + "Probabilita': " + probabilita+"\n");
			}
			else{
				buffer.append("Azione " + azione + "\t" + "Probabilita': " +"IGNOTA" +"\n");
			}
		}	
	 if(cardinalita > 1){
		buffer.append("\nPROBABILITA' CLASSE DI EQUIVALENZA : \n");
		//Iteratore sulla tabella delle probabilita' della classe di equivalenza
		Enumeration<String> iteratore2 = probabilitaClasse.keys();
		while(iteratore2.hasMoreElements()){
			String azione = iteratore2.nextElement();
			double probabilita = probabilitaClasse.get(azione);
			if (probabilita >= 0){
				//per evitare la stampa di valori negativi(IGNOTA)
				buffer.append("Azione " + azione + "\t" + "Probabilita': " + probabilita+"\n");
			}
			else{
				buffer.append("Azione " + azione + "\t" + "Probabilita': " +"IGNOTA" +"\n");
			}
		}		
	 }
		return buffer.toString();
	}
	
}
