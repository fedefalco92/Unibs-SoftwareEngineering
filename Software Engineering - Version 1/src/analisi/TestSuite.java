/**
 * 
 */
package analisi;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * @author Massi
 *
 */
public class TestSuite {
	
	private Vector<String> insiemeAttivita;
	private Vector<ClasseEquivalenza> classiEquivalenza;
	private Hashtable <String,Double> probabilitaM1;
	
	public TestSuite(Vector<String> insiemeAttivita){
		classiEquivalenza = new Vector<ClasseEquivalenza>();
		this.insiemeAttivita = insiemeAttivita;
		probabilitaM1 = new Hashtable <String,Double>();		
	}

	public void addNuovaClasseEquivalenza(ClasseEquivalenza classe){
		classiEquivalenza.add(classe);
	}	
	
	//PROBLEMI QUI!!!!
	
	public void calcolaProbabilitaM1(){
		
		//ciclo su tutte le classi di equivalenza per determinare la somma delle probabilita'
		double[] probabilitaM1_vec = new double[insiemeAttivita.size()];
		for(int i=0;i<probabilitaM1_vec.length;i++){
			probabilitaM1_vec[i] = 0;
		}				
		for(ClasseEquivalenza cl_eq : classiEquivalenza){
			Hashtable <String,Double> probabilitaSingolaClasse = cl_eq.getProbabilitaClasse();
			//analizzo le probabilita' per eseguire i relativi conteggi
			Enumeration<String> iteratore = probabilitaSingolaClasse.keys();
			while(iteratore.hasMoreElements()){
				String azione = iteratore.nextElement();
				double probabilita = probabilitaSingolaClasse.get(azione);
				int posizioneRelativa = insiemeAttivita.indexOf(azione);
				//System.out.println(posizioneRelativa);
				if (probabilita >= 0){
					//o e' nulla o e' diversa da zero(comunque non IGNOTA)
					if(posizioneRelativa >= 0){
						probabilitaM1_vec[posizioneRelativa] += probabilita;
					}
				}
			}			
		}

		//ora calcolo l'effettivo valore rapportato alla cardinalita' della classe
		int cardinalitaClassi[] = new int[insiemeAttivita.size()];
		
		//inizializzazione al valore minimo
		for(int i=0;i<cardinalitaClassi.length;i++){
			cardinalitaClassi[i] = 0;
		}		
		
		for(ClasseEquivalenza cl_eq : classiEquivalenza){			
			Hashtable <String,Double> probabilitaSingolaClasse = cl_eq.getProbabilitaClasse();
			//analizzo le probabilita' per eseguire i relativi conteggi
			Enumeration<String> iteratore2 = probabilitaSingolaClasse.keys();
			while(iteratore2.hasMoreElements()){
				String azione = iteratore2.nextElement();
				double probabilita = probabilitaSingolaClasse.get(azione);
				int posizioneRelativa = insiemeAttivita.indexOf(azione);	
				if(probabilita >= 0){
					if (posizioneRelativa >= 0){
						cardinalitaClassi[posizioneRelativa] += cl_eq.getCardinalita();					
					}
				}	
			}
		}
		
		//QUESTO PERO' LO MODIFICHEREI ALTRIMENTI IMPONGO SEMPRE UNA CARDINALITA' PARI A 1
		//ANCHE PER UNA AZIONE MAI VERIFICATA, MA SI RISOLVE CON IL CALCOLO DELLA PROBABILITA'...
	
		for(int i=0;i<cardinalitaClassi.length;i++){
			if(cardinalitaClassi[i] == 0){
				cardinalitaClassi[i] = 1;
			}
			probabilitaM1_vec[i] = probabilitaM1_vec[i]/cardinalitaClassi[i];		
		}
		
		for(int i=0;i<insiemeAttivita.size();i++){
			probabilitaM1.put(insiemeAttivita.elementAt(i), probabilitaM1_vec[i]);		
		}		
		
	}
	
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		for(ClasseEquivalenza cl_eq : classiEquivalenza){
			buffer.append(cl_eq.toString() + "\n");
		}
		buffer.append("Probabilita' del test suite calcolate con il METODO 1: \n");
		Enumeration<String> iteratore2 = probabilitaM1.keys();
		while(iteratore2.hasMoreElements()){
			String azione = iteratore2.nextElement();
			double probabilita = probabilitaM1.get(azione);
			buffer.append("Probabilita' " + azione + "\t" + " : " + probabilita+"\n");
		}			
		buffer.append("\n");
		return buffer.toString();
	}
}
