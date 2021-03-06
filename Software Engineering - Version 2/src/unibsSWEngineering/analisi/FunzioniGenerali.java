/**
 * 
 */
package unibsSWEngineering.analisi;

import java.util.Iterator;
import java.util.Vector;

import unibsSWEngineering.analisi.ClasseEquivalenza;


/**
 * @author Massi
 *
 */
public class FunzioniGenerali {
	
	private static final int DELIM = 1; //Delimitatore, per estensioni future
	
	/**
	 * Generazione effettiva MHS, implementando l'algoritmo visto nei lucidi
	 * e ritornando l'insieme con i rispettivi identificatori testuali,
	 * passati come parametro in ingresso
	 * @param corrispondenze
	 * @param idTestuali
	 * @return
	 */
	
	public static Vector<Vector<String>> generaMHS (Vector<Vector<Integer>> corrispondenze,Vector<String> idTestuali){	    
	    
		//estraggo tutti i singoletti dall'insieme dei sottoinsiemi e verifico chi fra loro e' un hitting set
		
	   Vector <Integer> elementiNuovi = new Vector<Integer>();
	   Vector singoletti = generaSingoletti2(idTestuali);
	   Vector hittingSet = new Vector();	
	   Vector singolettiHS = new Vector();
	   Iterator itSing = singoletti.iterator();	 
	   
	   //salvo i singoletti HS in un elenco a parte
	   
	   while(itSing.hasNext()){
		   Vector <Integer> singoletto = (Vector<Integer>)itSing.next();
		   int numsingoletto = singoletto.firstElement();
		   Vector <Integer> colonnaEstr = estraiColonna(corrispondenze, numsingoletto);	
		   boolean isHSSingoletto = isHittingSetSingle(colonnaEstr);
		   if(isHSSingoletto){
			   singolettiHS.add(singoletto);			   
		   }
		   else{
			   elementiNuovi.add(numsingoletto);
		   }		   
	   }
	   
	   //recupero tutti i sottoinsiemi, dopo aver eliminato i singoletti che sono HS e dopo aver
	   //rimpiazzato gli 0 e 1 con i rispettivi elementi(corrispondenza posizionale)
	   
	   Vector sottoinsiemi = recuperaElementi(generaSubsets(elementiNuovi,corrispondenze),elementiNuovi,DELIM);	  
	   
	   Iterator itSottoinsiemi = sottoinsiemi.iterator();
	   
	   while(itSottoinsiemi.hasNext()){
		   Vector <Integer> itemSet = (Vector <Integer>)itSottoinsiemi.next();
		   
			//Controllo se il sottoinsieme considerato e' candidato ad essere un HS
			//Estrazione colonne suggertite dal sottoinsieme
		   
			Vector  colonne = new Vector();
			Iterator itSubset = itemSet.iterator();
			while(itSubset.hasNext()){
				Integer numCol = (Integer)itSubset.next();
				Vector <Integer> colonnaEstr = estraiColonna(corrispondenze, numCol);
				
				//Aggiunta delle colonne ad un insieme temporaneo per effettuarne la somma
				
				colonne.add(colonnaEstr);
			}
			
			//Verifica di candidatura a potenziale HS e verifica delle successive condizioni
			
			boolean isHS = isHittingSet(colonne);		
			if(isHS){
				//se sono qui allora itemSet e' un hitting set(ma devo verificare effettuare altri controlli)
				//navigazione nell'elenco degli hitting set
				//creo una collezione dei possibili candidati per vedere chi potrebbe diventare HS
				
				Vector testPositivo = new Vector();
				Iterator hsIter = hittingSet.iterator();
				while(hsIter.hasNext()){
					Vector <Integer> hsItem = (Vector<Integer>)hsIter.next();
					boolean isSubsetOf = isSubset(hsItem, itemSet);
					if(isSubsetOf){
						testPositivo.add(hsItem);
					}
				}
				if(testPositivo.isEmpty()){
					//se non e' rispettata la condizione precedente (esistenza di un h che e' sottoinsieme di s_i) allora sono
					//autorizzato ad aggiungere il sottoinsieme all'elenco degli hitting set
					
					hittingSet.add(itemSet);
				}
			}
	   }
	   
	   //Infine, aggiungo agli hitting set trovati anche i singoletti(se esistono) calcolati in precedenza
	   
	   for(int i=0;i<singolettiHS.size();i++){
		   hittingSet.add(singolettiHS.get(i));
	   }	   
	   
	   return sostituisciConTesto(hittingSet, idTestuali);
	}	

	/**
	 * Mette un numero adeguato di "0" in testa alla stringa, per 
	 * permettere una adeguata elaborazione successiva
	 * @param numero
	 * @param bits
	 * @return
	 */
	
	private static String trimZeros(String numero,int bits){
		String modificato = new String(numero);
		for(int j=0;j<(bits-numero.length());j++){
			modificato = "0" + modificato;
		}
		return modificato;
	}
	
	/**
	 * Sostituisce con gli identificatori di tipo String passati in ingresso
	 * i valori numerici(interi) contenuti in ciascun vettore/insieme
	 * facente parte dell'hitting-set(la computazione di tale insieme NON
	 * e' imputata a questo metodo)
	 * @param hittingSet
	 * @param ids
	 * @return
	 */
	 
	private static Vector sostituisciConTesto(Vector hittingSet, Vector<String> ids){
		Vector copiaHittingSet = new Vector();
		Iterator itOnHS = hittingSet.iterator();
		while(itOnHS.hasNext()){
			Vector <Integer> sottoinsieme = (Vector<Integer>)itOnHS.next();
			Vector <String> sottoinsiemeIds = new Vector<String>();
			for(int i=0;i<sottoinsieme.size();i++){
				String singleValue = ids.get(sottoinsieme.get(i));
				sottoinsiemeIds.add(singleValue);
			}
			copiaHittingSet.add(sottoinsiemeIds);
		}
		return copiaHittingSet;
	}
	
	/**
	 * Genera tutti i possibili sottoinsiemi di un insieme a partire da un insieme di corrispondenze
	 * (0 e 1 per stabilire la presenza o meno di un elemento) e dagli identifcatori associati ad
	 * ogni elemento dell'insieme di cui si vogliono generare i sottoinsiemi(ritornando i sottoinsiemi
	 * in ordine crescente di numero di "1")
	 * @param identificatori
	 * @param corrispondenze
	 * @return
	 */
	
	private static Vector generaSubsets(Vector<Integer> identificatori,Vector corrispondenze){
		//creazione di tutti i possibili sottoinsiemi dell'insieme di identificatori
		//e' opportuno generarli in numero di '1' crescente
		//utilizzo di un metodo di ordinamento ad hoc
		int cardinalitaCorrispondenze = corrispondenze.size();
		Vector sottoinsiemiNum = new Vector();
		int numElementi = identificatori.size();
		int maxLength = (int)(Math.pow(2,numElementi));
		for(int i=1;i<maxLength;i++){
		//condizione per escludere i singoletti dalla combinazione 		
		String binario = Integer.toBinaryString(i);
		String binarioMod = trimZeros(binario,numElementi);
		int nbrOnes = countOnes(binarioMod);
				if(nbrOnes>1 && nbrOnes<=cardinalitaCorrispondenze){
					Vector <Integer> combinazione = new Vector<Integer>();
					for(int j=0;j<numElementi;j++){
						int estratto = Character.getNumericValue(binarioMod.charAt(j));
						combinazione.add(estratto);
				}
				sottoinsiemiNum.add(combinazione);
			}
		}
		return ordinaPerDimensione(sottoinsiemiNum);
	}	
	
	/**
	 * Datto un insieme di vettori binari, ritorna l'insieme di vettori
	 * costituito da tutti gli elementi effettivamente presenti in ogni sottoinsieme
	 * "1" significa presenza elemento, "0" assenza elemento.
	 * 
	 * @param sottoinsiemi
	 * @param identificatori
	 * @return
	 */
	
	private static Vector recuperaElementi (Vector sottoinsiemi,Vector <Integer> identificatori, int delim){
		Vector sottoinsiemiMod = new Vector();
		for(int i=0;i<sottoinsiemi.size();i++){
			Vector <Integer> item = (Vector <Integer>)sottoinsiemi.get(i);
			Vector <Integer> itemInt = new Vector<Integer>();
			for(int j=0;j<item.size();j++){				
				if(item.get(j)==delim){
					itemInt.add(identificatori.get(j));
				}			  
			}	
		    sottoinsiemiMod.add(itemInt);		   
		}
		return sottoinsiemiMod;
	}	
	
	/**
	 * Provvede ad ordinare i sottoinsiemi dell'insieme potenza
	 * in ordine crescente di "1", al fine di migliorare l'algoritmo
	 * brute-force per il calcolo degli hitting set (minimali) 
	 * Evito di fare calcoli inutili se ho una sola riga nel vettore
	 * delle corrispondenze
	 * @param subsets
	 * @return
	 */
	
	private static Vector ordinaPerDimensione(Vector subsets){			
		Vector subsetsTemp = new Vector();		
	 if(subsets.size() > 1){
		Vector <Integer> first = (Vector <Integer>)subsets.firstElement();			 
		int dim = first.size();
		int minimumOnes = 1;
		 for(int k=0;k<dim;k++){		
				for(int i=0;i<subsets.size();i++){
					Vector <Integer> item = (Vector <Integer>)subsets.get(i);
					int numOnes = countOnes(item);
					if(minimumOnes==numOnes){
						subsetsTemp.add(item);
					}			
				}
				minimumOnes++;
		 }		
	 }
	 else{
		 subsetsTemp = (Vector) subsets.clone();
	 }
	 	return subsetsTemp;
	}
	
	/**
	 * Conta il numero di "1" in un vettore di numeri binari(serve
	 * per effettuare successivamente l'ordinamento crescente per
	 * numero di 1, cioe' per numero di cardinalita' di elementi dell'insieme)
	 * @param v
	 * @return
	 */
	
	private static int countOnes(Vector <Integer> v){
		int ones = 0;
		for(int i=0;i<v.size();i++){
			if(v.get(i)==1)
				ones++;
		}
		return ones;
	}
	
	/**
	 * Conta il numero di "1" in una stringa
	 * @param str
	 * @return
	 */
	
	private static int countOnes(String str){
		int ones = 0;
		for(int i=0;i<str.length();i++){
			if(str.charAt(i)=='1')
				ones++;
		}
		return ones;
	}	
	
	/**
	 * Estrae la colonna identificata dal numero ricevuto in ingresso, da ogni
	 * vettore appartenente all'insieme potenza collezioneInsiemi
	 * @param collezioneInsiemi
	 * @param numColonna
	 * @return
	 */
	
	private static Vector<Integer> estraiColonna(Vector collezioneInsiemi,int numColonna){
		Vector <Integer> colonna = new Vector<Integer>();
		for(int i=0;i<collezioneInsiemi.size();i++){
			Vector <Integer> singoloInsieme = (Vector <Integer>)collezioneInsiemi.get(i);
			colonna.add(singoloInsieme.get(numColonna));
		}
		return colonna;
	}
	
	/**
	 * Estrae solamente i singoletti dal superinsieme dei sottoinsiemi(per 
	 * definizione i singoletti hanno dimensione 1) 
	 * @param subsets
	 * @return
	 */
	
	private static Vector estraiSingoletti(Vector subsets){
		Vector singoletti = new Vector();
		int i=0;
		boolean escape = false;
		do{
			Vector <Integer> elemento = (Vector <Integer>)subsets.get(i);
			i++;
			if(elemento.size()==1){
				singoletti.add(elemento);
			}
			else{
				escape = true;
			}
		}
		while(!escape);
		return singoletti;
	}
	
	/**
	 * Genera solamente i singoletti , partendo da un insieme di indicatori
	 * (ne calcola il numero e considera la posizione dell'identificatore come
	 * elemento su cui agire, cioe' le posizioni verranno utilizzate come elementi
	 * numerici di un insieme, di cui si calcoleranno tutti i possibili sottoinsiemi) 
	 * @param idTestuali
	 * @return
	 */
	
	private static Vector generaSingoletti2(Vector<String> idTestuali){
		Vector singoletti = new Vector();
		for(int i=0;i<idTestuali.size();i++){
			Vector <Integer> singoletto = new Vector<Integer>();
			String idSingolo = idTestuali.get(i);
			int indiceNumerico = idTestuali.indexOf(idSingolo);
			singoletto.add(indiceNumerico);			
			singoletti.add(singoletto);
		}
		return singoletti;
	}	
	
	/**
	 * Verifica se il sottoinsieme considerato e' un hitting set secondo le regole
	 * esposte nei lucidi(in realta' questo metodo riceve le colonne corrispondenti
	 * agli elementi dell'hitting set, cioe' se l'hitting set e' {0,2}, allora
	 * ricervera' in ingresso le colonne 0 e 2 del vettore binario delle corrispondenze) 
	 * Effettua inoltre la somma vera e propria fra le colonne per verificare tale principio
	 * @param colonne
	 * @return
	 */
	
	//qui devo agire se voglio cambiare l'elemento jolly che mi identifica se una colonna
	//e' stata selezionata(sarebbe bene modificarlo)
	
	private static boolean isHittingSet(Vector colonne){
		Vector <Integer> sommaColonne = new Vector<Integer>();
		Vector <Integer> column = (Vector <Integer>) colonne.firstElement();
		int dimensioneColonna = column.size();
		for(int i=0;i<dimensioneColonna;i++){
			sommaColonne.add(0);
		}
		//ciclo su tutte le colonne
		for(int i=0;i<colonne.size();i++){
			Vector <Integer> colonnaSingola = (Vector <Integer>) colonne.get(i);
			for(int j=0;j<colonnaSingola.size();j++){
				int elemento = sommaColonne.get(j);
				elemento += colonnaSingola.get(j);
				sommaColonne.set(j, elemento);
			}
		}
		for(int i=0;i<dimensioneColonna;i++){
			int el = sommaColonne.get(i);
			if(el == 0){
				return false;
			}	
		}		
		return true;
	}
	
	/**
	 * Indica se un singoletto e' un hitting set(solo un singoletto,
	 * cioe' un insieme formato da un singolo elemento)
	 * @param colonna
	 * @return
	 */
	
	private static boolean isHittingSetSingle(Vector <Integer> colonna){
		for(int i=0;i<colonna.size();i++){
			if(colonna.get(i)==0){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Verifica che un elemento di tipo intero appartenga ad un
	 * Vector di integer
	 * @param elem
	 * @param v1
	 * @return
	 */
	
	private static boolean member(int elem,Vector<Integer> v1){
		for(int i=0;i<v1.size();i++){
			if(v1.get(i)==elem)
				return true;
		}
		return false;
	}
	
	/**
	 * Verifica che un elemento di tipo String appartenga ad un
	 * Vector di String
	 * @param elem
	 * @param v1
	 * @return
	 */
	
	public static boolean member(String elem,Vector<String> v1){
		for(int i=0;i<v1.size();i++){
			if(v1.get(i).equalsIgnoreCase(elem))
				return true;
		}
		return false;
	}	
	
	/**
	 * Verifica il fatto che elemento sia un sottoinsieme di insieme,
	 * richiamando il metodo member, per la verifica di membership
	 * di un elemento ad un insieme
	 * @param elemento
	 * @param insieme
	 * @return
	 */
	
	private static boolean isSubset(Vector<Integer> elemento,Vector<Integer> insieme){
		if(elemento.isEmpty())
			return true;
		else if(elemento.size()>insieme.size())
			return false;
		else{
			for(int i=0;i<elemento.size();i++){
				if(!member(elemento.get(i),insieme))
					return false;
			}
			return true;
		}
	}	
	
	/**
	 * Ritorna l'insieme di azioni distinte contenute nel superinsieme considerato
	 * @param superinsieme
	 * @return
	 */
	
	public static Vector<String> getAzioniInsieme(Vector<Vector<String>> superinsieme){
		Vector<String> azioni = new Vector<String>();
		for(Vector<String> insieme : superinsieme){
			for(String elemento : insieme){
				if(azioni.indexOf(elemento) < 0){
					azioni.add(elemento);
				}
			}
		}
		return azioni;			
	}
	
	/**
	 * Conta quante volte un elemento e' contenuto in un superinsieme
	 * @param item
	 * @param superinsieme
	 * @return
	 */
	
	public static int contaPresenze(String item, Vector<Vector<String>> superinsieme){
		int presenze = 0;
		for(Vector<String> insieme : superinsieme){
			for(String elemento : insieme){
				if(item.equalsIgnoreCase(elemento)){
					presenze++;
				}
			}
		}
		return presenze;
	}
	
	/**
	 * Calcolo del coefficiente di Ochiai fra due vettori binari
	 * @param v1
	 * @param v2
	 * @return
	 */
	
	public static double coeffOchiai(Vector<Integer> v1, Vector<Integer> v2){
		int countn11 = 0;
		int countn10 = 0;
		int countn01 = 0;
		for(int i=0;i<v1.size();i++){
			if(v1.elementAt(i) == 1 && v2.elementAt(i)==1)
				countn11++;
			else if(v1.elementAt(i) == 1 && v2.elementAt(i)==0)
				countn10++;
			else if(v1.elementAt(i) == 0 && v2.elementAt(i)==1)
				countn01++;								
		}
		double denom = (countn11+countn10)*(countn11+countn01);
		if(denom == 0)
			return 0;
		return ((double)countn11/Math.sqrt(denom));	
	}
	
	/**
	 * Calcolo dell'intersezione fra due intervalli
	 * (da controllare)
	 * @param e1
	 * @param e2
	 * @return
	 */
	
	public static int intersecaIntervallo(int infE1,int supE1,int infE2, int supE2){
		if(infE1<infE2 && infE1<supE2 && supE1<infE2 && supE1<supE2){
			return Math.abs(infE2-supE1);
		}
		else if(infE1>supE2 && infE1>infE2 && supE1>supE2 && supE1>infE2){
			return Math.abs(infE1-supE2);
		}		
		return 0;
	}	
	
	/**
	 * Genera l'insieme di partenza per il calcolo delle diagnosi minimali:
	 * analizza tutte le azioni che fanno parte dei vari cammini e "rimuove"
	 * dalle rilevazioni KO le azioni che sono presenti nelle rilevazioni OK
	 * @return
	 * @param classe_in
	 */
	
	public static Vector<Vector<String>> generaInsiemePartenza(ClasseEquivalenza classe_in){
		//algoritmo per generare degli HS minimali
		Vector<Vector<String>> minimalHS = new Vector<Vector<String>>();
		Vector<String> elementiDaRimuovere = new  Vector<String>();		
			//replicazione dell'insieme delle attivita', per poi rimuovere quelle OK
			//e per trovare cosi' l'HS di diagnosi minimale
		
		 Vector<Cammino> percorsoOK = classe_in.getProva().getEsitoOK();
			 
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
		
		Vector<Cammino> percorsoKO = classe_in.getProva().getEsitoKO();
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
	 * Questo metodo costruisce la tabella binaria delle corrispondenze
	 * valida per qualsiasi vettore in generale
	 * @return 
	 * @param insiemeAzioni
	 * @param cl_eq
	 */
	
	
	public static Vector<Vector<Integer>> generazioneCorrispondenze(Vector<String> insiemeAzioni,ClasseEquivalenza cl_eq){
		//ampiezza della colonna
		int numeroColonne = insiemeAzioni.size();
		
		Vector<Vector<Integer>> tabellaCorrispondenze = new Vector<Vector<Integer>>();
		
		//Parto dal vettore esterno
				
		Vector<Vector<String>> insiemiPartenza = FunzioniGenerali.generaInsiemePartenza(cl_eq);
		
		
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
	

}
