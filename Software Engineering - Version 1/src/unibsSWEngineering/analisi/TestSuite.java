/**
 * 
 */
package unibsSWEngineering.analisi;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * @author Massi
 *
 */
public class TestSuite {
	
	private Vector<String> insiemeAzioni;
	private Vector<ClasseEquivalenza> classiEquivalenza;
	private Hashtable <String,Double> probabilitaM1;
	private Hashtable <String,Double> probabilitaM2;
	
	public TestSuite(Vector<String> insiemeAzioni){
		classiEquivalenza = new Vector<ClasseEquivalenza>();
		this.insiemeAzioni = insiemeAzioni;
		probabilitaM1 = new Hashtable <String,Double>();
		probabilitaM2 = new Hashtable <String,Double>();
	}

	public void addNuovaClasseEquivalenza(ClasseEquivalenza classe){
		classiEquivalenza.add(classe);
	}	
	
	public Hashtable <String,Double> getProbabilitaM1(){
		return probabilitaM1;
	}
	
	public Hashtable <String,Double> getProbabilitaM2(){
		return probabilitaM2;
	}	
	
	public void calcolaProbabilitaM1(){
		
		//ciclo su tutte le classi di equivalenza per determinare la somma delle probabilita'
		double[] probabilitaM1_vec = new double[insiemeAzioni.size()];
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
				int posizioneRelativa = insiemeAzioni.indexOf(azione);
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
		int cardinalitaClassi[] = new int[insiemeAzioni.size()];
		
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
				int posizioneRelativa = insiemeAzioni.indexOf(azione);	
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
		
		for(int i=0;i<insiemeAzioni.size();i++){
			probabilitaM1.put(insiemeAzioni.elementAt(i), probabilitaM1_vec[i]);		
		}		
		
	}
	
	/**
	 * Ritorna il vettore binario M da utilizzare come secondo vettore per il calcolo
	 * del coefficiente di Ochiai(1 o 0 in base all'esito della rilevazione del percorso)
	 * @return
	 */
	
	private Vector<Integer> calcolaVettoreM(){
		Vector<Integer> vettoreM = new Vector<Integer>();
		for(ClasseEquivalenza cl_eq : classiEquivalenza){
			Vector<Prova> prove = cl_eq.getProveReplicate();
			for(Prova prv : prove){
				for(Cammino camm : prv.getInsiemeCopertura()){
					vettoreM.addElement(camm.getEsito() ? 0 : 1);
				}
			}
		}
		return vettoreM;
	}
	
	/**
	 * Genera il vettore binario per azione passata in ingresso come id di
	 * tipo String, per calcolare il coefficiente di Ochiai associato
	 * (il secondo parametro e' il vettore M, generabile con il metodo apposito)
	 * @param idAzione
	 * @return
	 */
	
	private Vector<Integer> generaColonnaAzioneK(String idAzione){
		int posElencoAzioni = insiemeAzioni.indexOf(idAzione);
		if(posElencoAzioni >= 0){
			//se esiste l'azione prescelta
			Vector<Integer> azioneK = new Vector<Integer>();
			for(ClasseEquivalenza cl_eq : classiEquivalenza){
				Vector<Prova> proveReplicate = cl_eq.getProveReplicate();
				for(Prova prv : proveReplicate){
					for(Cammino camm : prv.getInsiemeCopertura()){
						Vector<String> azioniCammino = camm.estraiElementi();
						int posizioneAzione = azioniCammino.indexOf(idAzione);
						if(posizioneAzione >=0){
							azioneK.addElement(1);
						}
						else{
							azioneK.addElement(0);
						}
					}
				}
			}
			return azioneK;
		}
		return null;
	}
	
	/**
	 * Metodo che esegue la computazione del calcolo delle probabilita secondo
	 * il metodo 2
	 */
	
	public void calcolaProbabilitaM2(){
		Vector<Integer> vettoreM = calcolaVettoreM();
		for(String azione : insiemeAzioni){
			double coefficienteOchiaiK = UtilityInsiemi.coeffOchiai(generaColonnaAzioneK(azione), vettoreM);
			probabilitaM2.put(azione,coefficienteOchiaiK);	
		}
	}
		
	
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		for(ClasseEquivalenza cl_eq : classiEquivalenza){
			buffer.append(cl_eq.toString() + "\n");
		}
		buffer.append("Probabilita' del test suite calcolate con il METODO 1: \n");
		Enumeration<String> iteratore = probabilitaM1.keys();
		while(iteratore.hasMoreElements()){
			String azione = iteratore.nextElement();
			double probabilita = probabilitaM1.get(azione);
			buffer.append("Probabilita' " + azione + "\t" + " : " + probabilita+"\n");
		}			
		buffer.append("\n");		
		buffer.append("Probabilita' del test suite calcolate con il METODO 2: \n");
		Enumeration<String> iteratore2 = probabilitaM1.keys();
		while(iteratore2.hasMoreElements()){
			String azione = iteratore2.nextElement();
			double probabilita = probabilitaM2.get(azione);
			buffer.append("Probabilita' " + azione + "\t" + " : " + probabilita+"\n");
		}			
		return buffer.toString();
	}
}
