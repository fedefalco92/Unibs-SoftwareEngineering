/*
 * Classe Astratta Elemento.
 * Da questa derivano le classi Azione, Branch, Merge, Fork, Join, Nodi
 */
public abstract class Elemento {
	
	private String ID;
	//private String nome;
	
	public Elemento(String ID){
		this.ID = ID;
		//this.nome = nome;
	}
	
	public String getID() {
		return ID;
	}

}
