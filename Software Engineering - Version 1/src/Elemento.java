/*
 * Classe Astratta Elemento.
 * Da questa derivano le classi Azione, Branch, Merge, Fork, Join, Nodi
 */
public abstract class Elemento {
	
	private String ID;
	
	public Elemento(String ID){
		this.ID = ID;
	}
	
	public String getID() {
		return ID;
	}

}
