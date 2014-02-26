/**
 * 
 */
package analisi;

import java.util.Vector;

/**
 * @author Massi
 *
 */
public class Prova {
	private String idProva;
	private Vector<Percorso> insiemeCopertura;
	
	public Prova(String nome){
		idProva = nome;
		insiemeCopertura = new Vector<Percorso>();
	}
	
	public void setNome(String nome){
		idProva = nome;
	}
	
	public String getNome(){
		return idProva;
	}
	
	public void addCammino(Percorso cammino){
		insiemeCopertura.add(cammino);
	}
	
	public Vector<Percorso> getInsiemeCopertura(){
		return insiemeCopertura;
	}
	
	public Vector<Percorso> getEsitoOK(){
		Vector<Percorso> provaOk = new  Vector<Percorso>();
		for(Percorso p : insiemeCopertura){
			if(p.getEsito()){
				provaOk.add(p);
			}
		}
		return provaOk;
	}
	
	public Vector<Percorso> getEsitoKO(){
		Vector<Percorso> provaKo = new  Vector<Percorso>();
		for(Percorso p : insiemeCopertura){
			if(!p.getEsito()){
				provaKo.add(p);
			}
		}
		return provaKo;
	}
	
	/*
	 N.B:
	 Definire bene il concetto di appartenenza ad una classe di equivalenza (per ora fatto tramite 
	 immissione manuale(vedere la classe main di prova)
	 */
	
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("Prova : " + getNome() + "\n");
		for(Percorso p : insiemeCopertura){
			buffer.append(p.toString());
		}
		return buffer.toString();
	}
}
