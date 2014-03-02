/**
 * Classe da usare come alternativa di un branch...
 * &egrave come se fosse un modello a se'...
 * -inizio: al posto del nodo iniziale ha un branch 
 * -fine: pu&ograve terminare su un merge o sul nodo finale.
 * 
 * @author Maffi
 *
 */
public class Alternativa extends Modello {
	
	
	private Branch padre; //per ora uso il nome padre, si potrebbe usare anche ingresso o inizio 
	private Elemento uscita;
	
	public Alternativa(String nome, Branch _padre) {
		super(nome);
	this.setPadre(_padre);
	}

	public Branch getPadre() {
		return padre;
	}

	public void setPadre(Branch padre) {
		this.padre = padre;
	}

	public Elemento getJoinOUT() {
		return uscita;
	}

	public void setUscita(Elemento uscita) {
		this.uscita = uscita;
	}
	
	
	

}
