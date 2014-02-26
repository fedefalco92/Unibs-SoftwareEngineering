/**
 * 
 */
package analisi;

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
	private int[] probabilita;
	
	public DiagnosiClasse(Vector<String> insiemeAttivita){
		classe = null;
		diagnosiMinimali = new Vector<Vector<String>>();
		this.insiemeAttivita = insiemeAttivita;
		probabilita = new int[insiemeAttivita.size()];
	}
	
	public DiagnosiClasse(ClasseEquivalenza classe,Vector<String> insiemeAttivita){
		this.classe = classe;	
		this.insiemeAttivita = insiemeAttivita;
		diagnosiMinimali = new Vector<Vector<String>>();		
		probabilita = new int[insiemeAttivita.size()];		
	}
	
	public /*private Vector<Vector<String>>*/ void generaDiagnosiMinimali(){
		//algoritmo per generare degli HS minimali
		Vector<Vector<String>> minimalHS = new Vector<Vector<String>>();
		Prova provaEquivalenza = classe.getProva();
		Vector<String> elementiDaRimuovere = new  Vector<String>();		
			//replicazione dell'insieme delle attivita', per poi rimuovere quelle OK
			//e per trovare cosi' l'HS di diagnosi minimale
			 //Vector<String> replicaAttivita = (Vector<String>)insiemeAttivita.clone();
			
		 Vector<Percorso> percorsoOK = provaEquivalenza.getEsitoOK();
			 
		 for(Percorso perc : percorsoOK){
			 //preparo gli elementi delle rilevazioni OK che andranno rimossi da ogni K0
			 Vector<String> elementiPercorso = perc.estraiElementi();
			 for(String elem : elementiPercorso){
					 if(elementiDaRimuovere.indexOf(elem) < 0){
						 elementiDaRimuovere.add(elem);
					 }
			 }				 				 				
		 }						
		
		// System.out.println(elementiDaRimuovere.toString());
		 
		//ora che ho ricercato in tutte le prove posso eliminare gli elementi OK dalle rilevazioni KO
		
		//per ora gestisco la creazione delle diagnosi minimali utilizzando il metodo "a matrice"
		//da ottimizzare
		
		Vector <Vector<Integer>> corrispondenze = new Vector<Vector<Integer>>();
		
		//determino l'insieme di insiemi su cui effettuare l'analisi del calcolo degli MHS
		
		Vector<Vector<String>> insiemiBase = new Vector<Vector<String>>();
		
		Vector<Percorso> percorsoKO = provaEquivalenza.getEsitoKO();
		for(int i=0;i<percorsoKO.size();i++){
			Vector<String> elementiAttivita = percorsoKO.get(i).estraiElementi();
			for(String rim : elementiDaRimuovere){
				elementiAttivita.remove(rim);
			}
			if(!elementiAttivita.isEmpty())
				insiemiBase.add(elementiAttivita);
		}
		
		//--> fino a qui calcola l'insieme di partenza da cui ricavare gli MHS
		
		//TO DO 
		//TO DO
		//creazione e preparazione ID per ricavare i MHS
			
		
		//genero MHS partendo dal nuovo insieme creato utilizzando solo KO e privati degli elementi OK interni a questi
		
		
		/*return minimalHS;*/
	}
	
	
	
	
	
	
}
