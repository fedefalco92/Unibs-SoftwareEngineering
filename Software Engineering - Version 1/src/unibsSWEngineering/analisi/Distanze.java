/**
 * 
 */
package unibsSWEngineering.analisi;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

/**
 * @author root
 * 
 */
public class Distanze implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3080770671383154482L;
	private TestSuite testSuite;
	private TreeSet<OggettoAnalisi> probabilitaM1Ord;
	private TreeSet<OggettoAnalisi> probabilitaM2Ord;
	private TreeMap<String, Vector<Integer>> elencoM1;
	private TreeMap<String, Vector<Integer>> elencoM2;
	private TreeMap<String, Integer> distanzaPerAzione;
	private int distanzaTotale;
	private double distanzaMedia;	
	
	/**
	 * Costruttore per il calcolo delle distanze secondo i due metodi di calcolo
	 * della probabilita', avendo ricevuto un test suite
	 * @param testSuite
	 */

	public Distanze(TestSuite testSuite) {
		this.testSuite = testSuite;
		probabilitaM1Ord = new TreeSet<OggettoAnalisi>();
		probabilitaM2Ord = new TreeSet<OggettoAnalisi>();
		elencoM1 = new TreeMap<String, Vector<Integer>>();
		elencoM2 = new TreeMap<String, Vector<Integer>>();
		distanzaPerAzione = new TreeMap<String, Integer>();
		distanzaTotale = 0;
		distanzaMedia = 0.0;		
	}
	
	/**
	 * Riempimento dell'elenco con le probabilita' derivanti dal primo metodo
	 */

	private void riempiElencoM1() {
		TreeSet<OggettoAnalisi> probabilitaM1Temp = new TreeSet<OggettoAnalisi>();
		Hashtable<String, Double> probabilitaM1 = testSuite.getProbabilitaM1();
		Enumeration<String> iteratore = probabilitaM1.keys();
		while (iteratore.hasMoreElements()) {
			String azione = iteratore.nextElement();
			double probabilita = probabilitaM1.get(azione);
			probabilitaM1Temp.add(new OggettoAnalisi(azione, probabilita));
		}
		int endAdd = 0;
		while (!probabilitaM1Temp.isEmpty()) {
			OggettoAnalisi testa = probabilitaM1Temp.pollFirst();
			int starter = testa.getExtInf() + endAdd;
			StringTokenizer analyzer = new StringTokenizer(testa.getIDs(), ",");
			int contaToken = analyzer.countTokens();
			int end = starter + contaToken - 1;
			endAdd = end;
			testa.setExtInf(starter);
			testa.setExtSup(end);
			probabilitaM1Ord.add(testa);

		}
	}
	
	/**
	 * Riempimento dell'elenco con le probabilita' derivanti dal secondo metodo
	 */	

	private void riempiElencoM2() {
		TreeSet<OggettoAnalisi> probabilitaM2Temp = new TreeSet<OggettoAnalisi>();
		Hashtable<String, Double> probabilitaM2 = testSuite.getProbabilitaM2();
		Enumeration<String> iteratore = probabilitaM2.keys();
		while (iteratore.hasMoreElements()) {
			String azione = iteratore.nextElement();
			double probabilita = probabilitaM2.get(azione);
			probabilitaM2Temp.add(new OggettoAnalisi(azione, probabilita));
		}
		int endAdd = 0;
		while (!probabilitaM2Temp.isEmpty()) {
			OggettoAnalisi testa = probabilitaM2Temp.pollFirst();
			int starter = testa.getExtInf() + endAdd;
			StringTokenizer analyzer = new StringTokenizer(testa.getIDs(), ",");
			int contaToken = analyzer.countTokens();
			int end = starter + contaToken - 1;
			endAdd = end;
			testa.setExtInf(starter);
			testa.setExtSup(end);
			probabilitaM2Ord.add(testa);

		}
	}

	/**
	 * Calcolo delle distanze fra i due elenchi, calcolati secondo i due metodi
	 * (metodo privato)
	 */
	
	private void generaDistanze() {
		riempiElencoM1();
		riempiElencoM2();
		Iterator<OggettoAnalisi> itEl1 = probabilitaM1Ord.iterator();
		Iterator<OggettoAnalisi> itEl2 = probabilitaM2Ord.iterator();
		while (itEl1.hasNext()) {
			OggettoAnalisi elemento = itEl1.next();
			StringTokenizer analyzer = new StringTokenizer(elemento.getIDs(),
					",");
			while (analyzer.hasMoreTokens()) {
				Vector<Integer> nbr = new Vector<Integer>();
				nbr.add(elemento.getExtInf());
				nbr.add(elemento.getExtSup());
				elencoM1.put(analyzer.nextToken(), nbr);
			}
		}	
		while (itEl2.hasNext()) {
			OggettoAnalisi elemento = itEl2.next();
			StringTokenizer analyzer = new StringTokenizer(elemento.getIDs(),
					",");
			while (analyzer.hasMoreTokens()) {
				Vector<Integer> nbr = new Vector<Integer>();
				nbr.add(elemento.getExtInf());
				nbr.add(elemento.getExtSup());
				elencoM2.put(analyzer.nextToken(), nbr);
			}
		}
		
		TreeMap<String, Vector<Integer>> copiaM1 = (TreeMap<String, Vector<Integer>>)elencoM1.clone();
		TreeMap<String, Vector<Integer>> copiaM2 = (TreeMap<String, Vector<Integer>>)elencoM2.clone();

		// generazione delle intersezioni fra gli intervalli
		while (!copiaM1.isEmpty() && !copiaM2.isEmpty()) {
			// sono ordinati per azione (crescente, come confronto su stringhe)
			String chiaveAttuale = copiaM1.firstKey();
			Vector<Integer> intervalloAttualeM1 = copiaM1.get(chiaveAttuale);
			Vector<Integer> intervalloAttualeM2 = copiaM2.get(chiaveAttuale);
			int intersezione = UtilityInsiemi.intersecaIntervallo(
					intervalloAttualeM1.firstElement(),
					intervalloAttualeM1.lastElement(),
					intervalloAttualeM2.firstElement(),
					intervalloAttualeM2.lastElement());
			copiaM1.pollFirstEntry();
			copiaM2.pollFirstEntry();
			distanzaPerAzione.put(chiaveAttuale, intersezione);
		}
	}
	
	/**
	 * Calcolo effettivo delle distanze
	 * 
	 */

	public void calcoloDistanze() {
		generaDistanze();
		int valori = 0;
		int quantita = elencoM1.keySet().size();
		TreeMap<String, Integer> mappa = (TreeMap<String, Integer>)distanzaPerAzione.clone();
		while (!mappa.isEmpty() ) {
			// sono ordinati per azione (crescente, come confronto su stringhe)
			String chiaveAttuale = mappa.firstKey();
			int valore = mappa.get(chiaveAttuale);
			valori += valore;
			mappa.pollFirstEntry();
		}
		distanzaTotale = valori;
		distanzaMedia = (double)valori/quantita;		
	}
	
	/**
	 * Stampa dei risultati
	 */
	
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("Distanza totale fra elenchi: " + distanzaTotale + "\n");
		buffer.append("Distanza media fra azioni: " + distanzaMedia + "\n");
		return buffer.toString();
	}

}
