/**
 * 
 */
package analisi;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.Vector;

/**
 * @author root
 *
 */
public class Distanze {
	private TestSuite testSuite;
	private TreeSet<OggettoAnalisi> probabilitaM1Ord;
	private TreeSet<OggettoAnalisi> probabilitaM2Ord;
	
	public Distanze(TestSuite testSuite){
		this.testSuite = testSuite;
		probabilitaM1Ord = new TreeSet<OggettoAnalisi>();
		probabilitaM2Ord = new TreeSet<OggettoAnalisi>();
	}
	
	public void riempiElencoM1(){
		TreeSet<OggettoAnalisi> probabilitaM1Temp = new TreeSet<OggettoAnalisi>();
		Hashtable <String,Double> probabilitaM1 = testSuite.getProbabilitaM1();
		Enumeration<String> iteratore = probabilitaM1.keys();	
		//int flag = 1;
		while(iteratore.hasMoreElements()){
			String azione = iteratore.nextElement();
			double probabilita = probabilitaM1.get(azione);
			probabilitaM1Temp.add(new OggettoAnalisi(azione,probabilita));	
			//flag++;
		}					
		System.out.println(probabilitaM1Temp.toString());
		/*Enumeration<String> iteratore2 = probabilitaM1.keys();		
	
		while(!probabilitaM1Temp.isEmpty()){
			OggettoAnalisi oggetto = probabilitaM1Temp.pollFirst();
			Vector<String> ids = oggetto.getIdentificatori();
			
		}
		*/
	}
	
	public void riempiElencoM2(){
		Hashtable <String,Double> probabilitaM2 = testSuite.getProbabilitaM2();
		Enumeration<String> iteratore = probabilitaM2.keys();
		while(iteratore.hasMoreElements()){
			String azione = iteratore.nextElement();
			double probabilita = probabilitaM2.get(azione);
			probabilitaM2Ord.add(new OggettoAnalisi(azione,probabilita));
		}					
		System.out.println(probabilitaM2Ord.toString());
	}	
	
	
	
}
