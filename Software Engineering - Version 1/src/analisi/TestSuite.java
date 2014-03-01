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
	private double[] probabilitaM1;
	
	public TestSuite(Vector<String> insiemeAttivita){
		classiEquivalenza = new Vector<ClasseEquivalenza>();
		this.insiemeAttivita = insiemeAttivita;
		probabilitaM1 = new double[insiemeAttivita.size()];
		for(int i=0;i<probabilitaM1.length;i++){
			probabilitaM1[i] = 0;
		}
	}

	public void addNuovaClasseEquivalenza(ClasseEquivalenza classe){
		classiEquivalenza.add(classe);
	}	
	
	public void calcolaProbabilitaM1(){
		
		//ciclo su tutte le classi di equivalenza per determinare la somma delle probabilita'
				
		for(ClasseEquivalenza cl_eq : classiEquivalenza){
			Hashtable <String,Integer> probabilitaSingolaClasse = cl_eq.getProbabilitaClasse();
			//analizzo le probabilita' per eseguire i relativi conteggi
			Enumeration<String> iteratore = probabilitaSingolaClasse.keys();
			while(iteratore.hasMoreElements()){
				String azione = iteratore.nextElement();
				int probabilita = probabilitaSingolaClasse.get(azione);
				int posizioneRelativa = insiemeAttivita.indexOf(azione);
				if (probabilita >= 0){
					//o e' nulla o e' diversa da zero(comunque non IGNOTA)
					if(posizioneRelativa >= 0){
						probabilitaM1[posizioneRelativa] += probabilita;
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
			Hashtable <String,Integer> probabilitaSingolaClasse = cl_eq.getProbabilitaClasse();
			//analizzo le probabilita' per eseguire i relativi conteggi
			Enumeration<String> iteratore2 = probabilitaSingolaClasse.keys();
			while(iteratore2.hasMoreElements()){
				String azione = iteratore2.nextElement();
				int probabilita = probabilitaSingolaClasse.get(azione);
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
			probabilitaM1[i] = probabilitaM1[i]/cardinalitaClassi[i];		
		}	
		
	}
	
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("Probabilita' del test suite calcolate con il METODO 1: \n");
		for(int i=0;i<probabilitaM1.length;i++){
			buffer.append("Probabilita A"+(i+1)+" = " + probabilitaM1[i] + "\n");
		}
		buffer.append("\n");
		return buffer.toString();
	}
}
