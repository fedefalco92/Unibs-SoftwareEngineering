/**
 * 
 */
package analisi;

import java.util.Iterator;
import java.util.Vector;


/**
 * @author Massi
 *
 */
public class UtilitaGenerazioneMHS {
	
	public static Vector<Vector<String>> generaMHS (Vector<Vector<Integer>> corrispondenze,Vector<String> idTestuali){	    
	    //estraggo tutti i singoletti dall'insieme dei sottoinsiemi e verifico chi fra loro � un hitting set
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
	   for(int i=0;i<singolettiHS.size();i++){
		   Vector <Integer> singoletto = (Vector<Integer>)singolettiHS.get(i);
		   int numsingoletto = singoletto.firstElement();
		   //UtilitaMHS.rimuoviColonna(corrispondenze, numsingoletto-1); (superfluo perch� tanto la colonna non viene utilizzata!)
	   }
	   //ciclo sui singoletti trovati per rimuovere dalla mappatura delle corrispondenze
	   Vector sottoinsiemi = traformaId(generaSubsets(elementiNuovi,corrispondenze),elementiNuovi);	  
	   Iterator itSottoinsiemi = sottoinsiemi.iterator();
	   while(itSottoinsiemi.hasNext()){
		   Vector <Integer> itemSet = (Vector <Integer>)itSottoinsiemi.next();
			//Controllo se il sottoinsieme considerato � candidato ad essere un HS
			//Estrazione colonne suggertite dal sottoinsieme
			Vector  colonne = new Vector();
			Iterator itSubset = itemSet.iterator();
			while(itSubset.hasNext()){
				Integer numCol = (Integer)itSubset.next();
				//System.out.println(numCol);
				Vector <Integer> colonnaEstr = estraiColonna(corrispondenze, numCol);				
				//Aggiunta delle colonne ad un insieme temporaneo per effettuarne la somma
				colonne.add(colonnaEstr);
			}
			//Verifica di candidatura a potenziale HS e verifica delle successive condizioni
			boolean isHS = isHittingSet(colonne);		
			if(isHS){
				//se sono qui allora itemSet � un hitting set(ma deve verificare effettuare altri controlli)
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
					//se non � rispettata la condizione precedente (esistenza di un h che � sottoinsieme di s_i) allora sono
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

	
	private static String trimZeros(String numero,int bits){
		String modificato = new String(numero);
		for(int j=0;j<(bits-numero.length());j++){
			modificato = "0" + modificato;
		}
		return modificato;
	}
	
	
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
	
	
	
	private static Vector generaSubsets(Vector<Integer> identificatori,Vector corrispondenze){
		//creazione di tutti i possibili sottoinsiemi dell'insieme di identificatori
		//� opportuno generarli in numero di '1' crescente
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
	
	private static Vector traformaId (Vector sottoinsiemi,Vector <Integer> identificatori){
		Vector sottoinsiemiMod = new Vector();
		for(int i=0;i<sottoinsiemi.size();i++){
			Vector <Integer> item = (Vector <Integer>)sottoinsiemi.get(i);
			Vector <Integer> itemInt = new Vector<Integer>();
			for(int j=0;j<item.size();j++){				
				if(item.get(j)==1){
					itemInt.add(identificatori.get(j));
				}			  
			}	
		    sottoinsiemiMod.add(itemInt);		   
		}
		return sottoinsiemiMod;
	}	
	
	private static Vector ordinaPerDimensione(Vector subsets){			
		Vector subsetsTemp = new Vector();
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
		return subsetsTemp;
	}
	
	private static int countOnes(Vector <Integer> v){
		int ones = 0;
		for(int i=0;i<v.size();i++){
			if(v.get(i)==1)
				ones++;
		}
		return ones;
	}
	
	private static int countOnes(String str){
		int ones = 0;
		for(int i=0;i<str.length();i++){
			if(str.charAt(i)=='1')
				ones++;
		}
		return ones;
	}	
	
	private static Vector<Integer> estraiColonna(Vector collezioneInsiemi,int numColonna){
		Vector <Integer> colonna = new Vector<Integer>();
		for(int i=0;i<collezioneInsiemi.size();i++){
			Vector <Integer> singoloInsieme = (Vector <Integer>)collezioneInsiemi.get(i);
			colonna.add(singoloInsieme.get(numColonna));
		}
		return colonna;
	}
	
	
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
	
	private static boolean isHittingSetSingle(Vector <Integer> colonna){
		for(int i=0;i<colonna.size();i++){
			if(colonna.get(i)==0){
				return false;
			}
		}
		return true;
	}
	
	private static boolean member(int elem,Vector<Integer> v1){
		for(int i=0;i<v1.size();i++){
			if(v1.get(i)==elem)
				return true;
		}
		return false;
	}
	
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
	

}
