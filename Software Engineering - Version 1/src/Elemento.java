import java.util.Vector;

/*
 * Classe Astratta Elemento.
 * Da questa derivano le classi Azione, Branch, Merge, Fork, Join, Nodi
 */
public abstract class Elemento {
	
	private String ID;
	private String nome;
	
	public Elemento(String ID, String nome){
		this.ID = ID;
		this.nome = nome;
	}
	
	public boolean equals(Elemento elem) {
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
	
	//Metodi Getter per favorire il lavoro del controllo delle classi derivate
	//LI FACCIAMO ABSTRACT? 
	public Elemento getIngresso() {
		return null;
	}
	
	public Elemento getJoinOUT() {
		return null;
	}
	
	public Vector<Elemento> getIngressi() {
		return null;
	}
	
	public Vector<Elemento> getUscite() {
		return null;
	}
	
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
