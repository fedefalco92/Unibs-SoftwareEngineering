/**
 * 
 */
package unibsSWEngineering.analisi;

import java.io.Serializable;
import java.util.Vector;

/**
 * @author Massi
 *
 */
public class Prova implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8619453799271123440L;
	private String idProva;
	private Vector<Cammino> insiemeCopertura;
	
	public Prova(String nome){
		idProva = nome;
		insiemeCopertura = new Vector<Cammino>();
	}
	
	public void setNome(String nome){
		idProva = nome;
	}
	
	public String getNome(){
		return idProva;
	}

	public void addPercorso(Cammino cammino){
		insiemeCopertura.add(cammino);
	}
	
	public Vector<Cammino> getInsiemeCopertura(){
		return insiemeCopertura;
	}
	
	public boolean isEmptyInsiemeCopertura(){
		return insiemeCopertura.isEmpty();
	}
	
	public int numeroCammini(){
		return insiemeCopertura.size();
	}	
	
	/**
	 * Metodo che ritorna i cammini con esito OK
	 * @return
	 */
	
	public Vector<Cammino> getEsitoOK(){
		Vector<Cammino> provaOk = new  Vector<Cammino>();
		for(Cammino p : insiemeCopertura){
			if(p.getEsito()){
				provaOk.add(p);
			}
		}
		return provaOk;
	}
	
	/**	
	 * Metodo che ritorna i cammini con esito KO
	 * @return
	 */
	
	public Vector<Cammino> getEsitoKO(){
		Vector<Cammino> provaKo = new  Vector<Cammino>();
		for(Cammino p : insiemeCopertura){
			if(!p.getEsito()){
				provaKo.add(p);
			}
		}
		return provaKo;
	}
	
	/**
	 * Ritorna un Vector di String contenente tutte le azioni coinvolte
	 * in una singola prova
	 * @return
	 */
	
	public Vector<String> getAzioniCoinvolte(){
		Vector<String> azioniProva = new Vector<String>();
		for(Cammino perc : insiemeCopertura){
			Vector<String> azioniCammino = perc.estraiElementi();
			for(String elem : azioniCammino){
				if(azioniProva.indexOf(elem) < 0){
					azioniProva.add(elem);
				}
			}
		}
		return azioniProva;
	}
	
	/**
	 * Stampa dei risultati
	 */
	
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("Prova : " + getNome() + "\n\n");
		for(Cammino p : insiemeCopertura){
			buffer.append(p.toString()+ "\n");
		}
		return buffer.toString();
	}
}
