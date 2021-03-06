package unibsSWEngineering.modello;
import java.io.Serializable;
import java.util.Vector;

/*
 * Classe Astratta Elemento.
 * Da questa derivano le classi Azione, Branch, Merge, Fork, Join, Nodi
 */
public abstract class Elemento implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7349356455391999378L;
	private String ID;
	private String nome;
	
	public Elemento(String ID, String nome){
		this.ID = ID;
		this.nome = nome;
	}
	
	public boolean equals(Elemento elem) {
		if(elem == null)
			return false;
		if(ID.equalsIgnoreCase(elem.getID()) && nome.equalsIgnoreCase(elem.getNome()))
			return true;
		return false;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getID() {
		return ID;
	}
	
	/*Questi metodi sono diventati Abstract, cosi sono sicuro che vengono implementati*/
	public abstract Elemento getIngresso();
	public abstract Elemento getUscita();
	public abstract Vector<Elemento> getIngressi();
	public abstract Vector<Elemento> getUscite();
	
	/*Metodi che aggiungono uscite e ingressi*/
	public abstract void aggiungiUscita(Elemento uscita);
	public abstract void aggiungiIngresso(Elemento ingresso);
	
	public String getElementoString(){
		StringBuffer output = new StringBuffer();
		output.append("[" + ID + "] " + nome);
		return output.toString();
	}
	
	@Override
	public String toString() {
		return getElementoString();
	}
}
