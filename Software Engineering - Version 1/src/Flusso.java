/**
 * Classe da usare come flusso di un fork...
 * &egrave come se fosse un modello a se'...
 * -inizio: al posto del nodo iniziale ha un fork
 * -fine: termina necessariamente su un join "legato" al fork padre
 * 
 * @author Maffi
 *
 */
public class Flusso extends Modello {

	private Fork padre;
	private Join uscita;
	
	public Flusso(String nome, Fork _padre) {
		super(nome);
		this.padre = _padre;
		this.setUscita(padre.getJoinTerminale());
	}

	public Fork getPadre() {
		return padre;
	}

	public void setPadre(Fork padre) {
		this.padre = padre;
	}

	public Join getUscita() {
		return uscita;
	}

	public void setUscita(Join uscita) {
		this.uscita = uscita;
	}
	

}
