/**
 * 
 */
package unibsSWEngineering.analisi;

import java.util.Vector;

/**
 * @author Massi
 *
 */
public class Prova {
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
	
	//lo chiamiamo addPercorso?
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
	
	public Vector<Cammino> getEsitoOK(){
		Vector<Cammino> provaOk = new  Vector<Cammino>();
		for(Cammino p : insiemeCopertura){
			if(p.getEsito()){
				provaOk.add(p);
			}
		}
		return provaOk;
	}
	
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
	
	/*
	 N.B:
	 Definire bene il concetto di appartenenza ad una classe di equivalenza (per ora fatto tramite 
	 immissione manuale(vedere la classe main di prova)
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
