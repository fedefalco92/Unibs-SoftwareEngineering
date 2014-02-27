import java.util.Vector;

/*
 * Classe Astratta Elemento.
 * Da questa derivano le classi Azione, Branch, Merge, Fork, Join, Nodi
 */
public abstract class Elemento {
	
	private String ID;
	private String nome;
//	private Elemento ingresso;
//	private Elemento uscita;
//	private Vector <Elemento> ingressi;
//	private Vector <Elemento> uscite;
	
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
