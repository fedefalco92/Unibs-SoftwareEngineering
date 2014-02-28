import java.util.Vector;

/*
 * Classe Astratta Elemento.
 * Da questa derivano le classi Azione, Branch, Merge, Fork, Join, Nodi
 */
public abstract class Elemento {
	
	private String ID;
	private String nome;
	
	//E se controlliamo qui anche i vari ingressi? Vi spiego perche' mi e' venuta questa idea...
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

	//Metodi Getter per favorire il lavoro del controllo delle classi derivate
	public Elemento getIngresso() {
		return null;
	}
	
	public Elemento getUscita() {
		return null;
	}
	
	public Vector<Elemento> getIngressi() {
		return null;
	}
	
	public Vector<Elemento> getUscite() {
		return null;
	}
	
	@Override
	public String toString() {
		return getElementoString();
	}
}
