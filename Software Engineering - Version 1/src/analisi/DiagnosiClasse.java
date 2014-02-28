/**
 * 
 */
package analisi;

import java.util.StringTokenizer;
import java.util.Vector;

/**
 * @author Massi
 *
 */
public class DiagnosiClasse {
	
	
	/*
	 A questa classe si dovra' passare l'elenco di tutte le attivita', separate dalla ","
	 Per ora si assume che sia un elemento noto(come string)
	 Le attivita sono note-->uso un vettore classico???--->Attenzione a quando viene istanziato
	 */
	
	private Vector<String> insiemeAttivita;
	private ClasseEquivalenza classe;
	private Vector<Vector<String>> diagnosiMinimali;
	
	public DiagnosiClasse(Vector<String> insiemeAttivita){
		classe = null;
		diagnosiMinimali = new Vector<Vector<String>>();
		this.insiemeAttivita = insiemeAttivita;	
	}
	
	public DiagnosiClasse(ClasseEquivalenza classe,Vector<String> insiemeAttivita){
		this.classe = classe;	
		this.insiemeAttivita = insiemeAttivita;
		diagnosiMinimali = new Vector<Vector<String>>();			
	}
	
	public Vector<Vector<String>> getDiagnosiMinimali(){
		generaDiagnosi();
		return diagnosiMinimali;
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
		Prova provaEquivalenza = classe.getProva();
		Vector<String> elementiDaRimuovere = new  Vector<String>();		
			//replicazione dell'insieme delle attivita', per poi rimuovere quelle OK
			//e per trovare cosi' l'HS di diagnosi minimale
			 //Vector<String> replicaAttivita = (Vector<String>)insiemeAttivita.clone();
			
		 Vector<Cammino> percorsoOK = provaEquivalenza.getEsitoOK();
			 
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
		
		Vector<Cammino> percorsoKO = provaEquivalenza.getEsitoKO();
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
		
		return tabellaCorrispondenze;
		
		//System.out.println("Tabella delle corrispondenze: " + tabellaCorrispondenze.toString() );

	}
	
	/**
	 * Metodo che genera degli identificatori di posizione(da chiarire la sua effettiva utilita')
	 */
	
	private void settaIdentificatorePosizione(){
		/*
		 N.B.: Da fare dopo aver generato le diagnosi minimali
		 1 = probabilita' pari a 1
		 -1 = probabilita' ignota
		 0 = probabilita' nulla
		 */
		
		//ricavo solo gli esiti OK della prova inerente alla classe di equivalenza
		
		Vector<Vector<Integer>> corrispondenze = generazioneCorrispondenze();
		
		Vector<Cammino> percorsiOK = classe.getProva().getEsitoOK();
		for(Cammino percorso : percorsiOK){
			Vector<String> elemPercorso = percorso.estraiElementi();
			for(String elemento : elemPercorso){				
				int posizioneRelativa = insiemeAttivita.indexOf(elemento);
				for(Vector<Integer> rigaCorrispondenza : corrispondenze){
					rigaCorrispondenza.setElementAt(-1, posizioneRelativa);
				}
			}
		}
		
		//System.out.println("Corrispondenze modificate : " + corrispondenze + "\n");
	}
	
	
	
	
	
	
}
