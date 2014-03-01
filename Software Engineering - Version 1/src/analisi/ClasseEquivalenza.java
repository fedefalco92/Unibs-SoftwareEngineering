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
	 Definire bene come � espresso il concetto di equivalenza, cio� se basta l'uguaglianza del percorso o �
	 necessario tutto
	 */
	private String nome;
	private Prova istanzaProva;
	private int cardinalita;
	private Vector<String> insiemeAttivita;		//Totale delle attivita' prese in ingresso dal modello...sara' tolto!
	private Vector<Vector<String>> diagnosiMinimali;
	private Hashtable <String,Integer> probabilitaProva;
	private Hashtable <String,Integer> probabilitaClasse;
	
	public ClasseEquivalenza(String nome,Vector<String> insiemeAttivita){
		this.nome = nome;
		istanzaProva = null;
		cardinalita = 0;
		diagnosiMinimali = null;
		this.insiemeAttivita = insiemeAttivita;		
		probabilitaProva = new Hashtable <String,Integer>();
		probabilitaClasse = new Hashtable <String,Integer>();
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
	
	public void incrementaCardinalita(){
		cardinalita++;
	}	
	
	public Prova getProva(){
		return istanzaProva;
	}	
	
	public Hashtable <String,Integer> getProbabilitaClasse(){
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
		
		diagnosiMinimali = UtilitaGenerazioneMHS.generaMHS(corrispondenze,insiemeAttivita);
		
		//System.out.println("Insieme delle diagnosi minimali: " + diagnosiMinimali.toString() + "\n\n\n");
	}
	
	/**
	 * Questo metodo costruisce la tabella binaria delle corrispondenze
	 * valida per qualsiasi vettore in generale
	 * @return 
	 */
	
	//Spostarlo in un'altra classe (e' un metodo che, in teoria e' di validita' generale)
	
	private Vector<Vector<Integer>> generazioneCorrispondenze(){
		//ampiezza della colonna
		int numeroColonne = insiemeAttivita.size();
		
		Vector<Vector<Integer>> tabellaCorrispondenze = new Vector<Vector<Integer>>();
		
		//Parto dal vettore esterno
				
		Vector<Vector<String>> insiemiPartenza = generaInsiemePartenza();
		
		
		if(insiemiPartenza.isEmpty()){
			//N.B: se non ho attivita di partenza come mi comporto??
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
					
					int posizioneInElencoGenerale = insiemeAttivita.indexOf(elemento);
					
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
		
		for(String elem : insiemeAttivita){
			probabilitaProva.put(elem, -1);
		}
		
		//System.out.println(probabilitaProva.toString() + "\n");
		
		//CHIEDERE ALLA ZANELLA PER IL CALCOLO DELLA PROBABILITA'(CONTEGGIO DI APPARTENENZA???)
		//Per ora si e' impostato un valore di default pari a 1
		//successivamente andra' integrato con l'effettiva implementazione
		
		for(Vector<String> sottoinsieme : diagnosiMinimali){
			//passo in rassegna ciascun sottoinsieme
			for(String azione : sottoinsieme){
				if(!probabilitaProva.containsValue(azione)){
					//se l'attivita' non e' presente la aggiungo
					probabilitaProva.put(azione, 1);
				}
				else{
					//altrimenti incremento la relativa probabilita' di 1
					//o eseguo azione specifica di risposta
					
				}
			}
		}
				
		
		//System.out.println(probabilitaProva.toString());
				
		//recupero le azioni coinvolte nella prova in questione
		//e da queste creo un nuovo vettore con solo le azioni non coinvolte(che non fanno parte di alcuna
		//diagnosi minimale)
		//N.B: --> IN FUTURO POSSO SPOSTARE QUESTA OPERAZIONE DIRETTAMENTE IN PROVA!!!
		
		Vector<String> azioniResidue = istanzaProva.getAzioniCoinvolte();
		
		//System.out.println(azioniResidue.toString());
		
		//System.out.println(diagnosiMinimali.toString());
				
		for(Vector<String> sottoinsieme : diagnosiMinimali){
			//passo in rassegna ciascun sottoinsieme
			for(String azione : sottoinsieme){
				int indiceDaRimuovere = azioniResidue.indexOf(azione);
				azioniResidue.removeElementAt(indiceDaRimuovere);				
			}
		}
		
		//System.out.println(azioniResidue.toString());
		
		//ora scansiono le azioni residue e assegno loro probabilita' pari a 0
		
		for(String azione : azioniResidue){
			probabilitaProva.put(azione,0);
		}	
		
		Enumeration<String> iteratore = probabilitaProva.keys();
		while(iteratore.hasMoreElements()){
			String azione = iteratore.nextElement();
			int probProva = probabilitaProva.get(azione);
			probabilitaClasse.put(azione, probProva*cardinalita);
		}
		
	/*	System.out.println("Probabilita' relative alla singola prova : \n(un valore negativo significa IGNOTA)");
		Enumeration<String> iteratore = probabilitaProva.keys();
		while(iteratore.hasMoreElements()){
			String azione = iteratore.nextElement();
			int probProva = probabilitaProva.get(azione);
			if(probProva != -1){
				System.out.println(azione + " = " + probProva);
			}
			else{
				System.out.println(azione + " = " + "IGNOTA");
			}
		}
		
		if(cardinalita>1){
			//solo in questo caso sono distinte
			System.out.println("Probabilita' relative alla classe : \n ");
			Enumeration<String> iteratore_globale = probabilitaProva.keys();
			while(iteratore_globale.hasMoreElements()){
				String azione = iteratore_globale.nextElement();
				int probProva = probabilitaProva.get(azione);
				if(probProva != -1){
					System.out.println(azione + " = " + probProva*cardinalita);
				}
				else{
					System.out.println(azione + " = " + "IGNOTA");
				}
			}
			//System.out.println(probabilitaClasse.toString()+ "\n\n");
			
		}
		*/
	}
	
	
	
}
