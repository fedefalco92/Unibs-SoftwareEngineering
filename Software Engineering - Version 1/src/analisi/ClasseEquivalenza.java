/**
 * 
 */
package analisi;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Vector;

/**
 * @author Massi
 *
 */


public class ClasseEquivalenza {
	/*
	 Definire bene come e' espresso il concetto di equivalenza, cioe' se basta l'uguaglianza del percorso o e'
	 necessario tutto
	 */
	private String nome;
	private Prova istanzaProva;
	private int cardinalita;
	private Vector<String> insiemeAzioni;		//Totale delle attivita' prese in ingresso dal modello...sara' tolto!
	private Vector<String> attivitaCoinvolte;
	private Vector<Vector<String>> diagnosiMinimali;
	private Hashtable <String,Double> probabilitaProva;
	private Hashtable <String,Double> probabilitaClasse;
	
	public ClasseEquivalenza(String nome,Vector<String> insiemeAttivita){
		this.nome = nome;
		istanzaProva = null;
		cardinalita = 0;
		diagnosiMinimali = null;
		attivitaCoinvolte = new Vector<String>();
		this.insiemeAzioni = insiemeAttivita;		
		probabilitaProva = new Hashtable <String,Double>();
		probabilitaClasse = new Hashtable <String,Double>();
	}
	
	public void setNome(String nome){
		this.nome = nome;
	}
	
	public String getNome(){
		return nome;
	}
	
	public void setIstanzaProva(Prova _prova){
		istanzaProva = _prova;
	}
	
	public int getCardinalita(){
		return cardinalita;
	}
	
	public void setCardinalita(int card){
		cardinalita = card;
	}

	public Prova getProva(){
		return istanzaProva;
	}
	
	/**
	 * Metodo che ritorna un vettore di prove replicate in base alla cardinalita
	 * (soluzione temporanea...si cerchera' di trovare un modo migliore...)
	 * @return
	 */
	
	public Vector<Prova> getProveReplicate(){
		Vector<Prova> vettoreProve = new Vector<Prova>();
		for(int i=0;i<cardinalita;i++){
			vettoreProve.add(istanzaProva);
		}
		return vettoreProve;
	}
	
	public Hashtable <String,Double> getProbabilitaClasse(){
		this.setProbabilitaClasse();
		return probabilitaClasse;		
	}	
	
	/**
	 * Parte aggiunta in data 28/02/2014
	 */

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
			 //Vector<String> replicaAttivita = (Vector<String>)insiemeAttivita.clone();
			
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
		 		 
		
		 //System.out.println(elementiDaRimuovere.toString()+"\n\n");
		 
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
		
		//System.out.println("Insieme di partenza :" + insiemiBase.toString() + "\n\n");
		
		return insiemiBase;
		
		//--> fino a qui calcola l'insieme di partenza da cui ricavare gli MHS		
	}	
	
	/**
	 * Generazione delle diagnosi minimali relative alla classe di equivalenza considerata
	 */
	
	private void generaDiagnosi(){
		
		//Applicazione dei metodi creati per trovare l'insieme di diagnosi minimali		
		
		//System.out.println("Insieme Attivita' : " + insiemeAttivita);
		
		//System.out.println("Insieme base di partenza: " + generaInsiemePartenza().toString());
	
		Vector<Vector<Integer>> corrispondenze = generazioneCorrispondenze();
		
		//System.out.println("Corrispondenze per calcolo MHS: " + corrispondenze); 
		
		//se ho corrispondenze su cui calcolare le diagnosi minimali...
		
		if(corrispondenze != null){
		
			diagnosiMinimali = UtilityInsiemi.generaMHS(corrispondenze,insiemeAzioni);
		
			//System.out.println("Insieme delle diagnosi minimali: " + diagnosiMinimali.toString() + "\n\n\n");
		}

	}
	
	/**
	 * Questo metodo costruisce la tabella binaria delle corrispondenze
	 * valida per qualsiasi vettore in generale
	 * @return 
	 */
	
	//Spostarlo in un'altra classe (e' un metodo che, in teoria e' di validita' generale)
	
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
				/*	else{
						rigaCorrispondenza.setElementAt(0, posizioneInElencoGenerale);		
					}*/
				}
				tabellaCorrispondenze.add(rigaCorrispondenza);
			}
							
			//System.out.println("Tabella delle corrispondenze: " + tabellaCorrispondenze.toString() );
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
		
		//System.out.println("Diagnosi minimali : " + diagnosiMinimali.toString() + "\n\n");
		
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
		
		//System.out.println(azioniResidue.toString());
		
		//System.out.println(diagnosiMinimali.toString());
	
		if(!azioniResidue.isEmpty()){
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
		
		//System.out.println(azioniResidue.toString());
		
		//ora scansiono le azioni residue e assegno loro probabilita' pari a 0
	
		for(String azione : azioniResidue){
			probabilitaProva.put(azione,0.0);
		}			
	
		//Recupero tutte le azioni coinvolte nelle diagnosi minimali(in generale)
		Vector <String> azioniInDiagnosi = UtilityInsiemi.getAzioniInsieme(diagnosiMinimali);
		
		//System.out.println(azioniInDiagnosi.toString());
		
		//se non e' vuoto posso procedere all'elaborazione
		
	//	if(!azioniInDiagnosi.isEmpty()){
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
			
			//System.out.println("Posizione in elenco: " + posizioneInElenco);
			
			for(Vector<String> diagnosi_sing : diagnosiMinimali){
				int dimensioneSingola = diagnosi_sing.size();
				
				//System.out.println("Dimensione Singola " + dimensioneSingola);
				
				//se l'azione e' presente nel sottoinsieme faccio 1/cardinalita' del sottoinsieme
				//e la sommo nell'array delle probabilita' cumulate, creato sulla dimensione delle
				//attivita' considerate
				if(UtilityInsiemi.member(singleAction, diagnosi_sing)){
					probabilitaCumulate[posizioneInElenco] += (1/(double)dimensioneSingola);
					//System.out.println("probabilitaCumulate[" + posizioneInElenco+"]" + "=" + probabilitaCumulate[posizioneInElenco]);
				}
			}
			//se arrivo qui ho finito di passare in rassegna i sottoinsiemi e posso calcolare la 
			//probabilita' definitiva
			int contaOccorrenzeAttivita = UtilityInsiemi.contaPresenze(singleAction, diagnosiMinimali);
			//probabilitaCumulate[posizioneInElenco] /= contaOccorrenzeAttivita;
			probabilitaProva.put(singleAction, probabilitaCumulate[posizioneInElenco]/(double)contaOccorrenzeAttivita);
		}
		
		//System.out.println(probabilitaProva.toString());
		
		//System.out.println(probabilitaProvaM1.toString());			

		Enumeration<String> iteratore = probabilitaProva.keys();
		while(iteratore.hasMoreElements()){
			String azione = iteratore.nextElement();
			double probProva = probabilitaProva.get(azione);
			probabilitaClasse.put(azione, probProva*cardinalita);
		}
		
		//System.out.println(probabilitaClasseM1.toString());	
		
	}
	
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
