/**
 * 
 */
package analisi;

import java.util.Vector;

/**
 * @author Massi
 *
 */
public class ClasseEquivalenza {
	/*
	 Definire bene come è espresso il concetto di equivalenza, cioè se basta l'uguaglianza del percorso o è
	 necessario tutto
	 */
	private String nome;
	private Prova istanzaProva;
	private int cardinalita;
	
	public ClasseEquivalenza(String nome){
		this.nome = nome;
		istanzaProva = null;
		cardinalita = 0;
	}
	
	public void setNome(String nome){
		this.nome = nome;
	}
	
	public String getNome(){
		return nome;
	}
	
	public void setIstanzaProva(Prova _prova){
		istanzaProva = _prova;
	}
	
	public int getCardinalita(){
		return cardinalita;
	}
	
	public void setCardinalita(int card){
		cardinalita = card;
	}
	
	public void setCardinalita(){
		cardinalita++;
	}	
	
	public Prova getProva(){
		return istanzaProva;
	}
}
