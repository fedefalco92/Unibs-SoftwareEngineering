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

	private Fork forkIN;
	private Join joinOUT;
	
	public Flusso(String nome, Fork _padre) {
		super("FLUSSO", nome);
		this.forkIN = _padre;
		this.setJoinOUT(forkIN.getJoinTerminale());
	}

	public Fork getForkIN() {
		return forkIN;
	}

	public void setForkIN(Fork padre) {
		this.forkIN = padre;
	}

	public Join getJoinOUT() {
		return joinOUT;
	}

	public void setJoinOUT(Join uscita) {
		this.joinOUT = uscita;
	}
	
	/*
	/**
	 * Equivale al metodo Elemento.getElementoString() 
	 * Fornisce una stampa "semplice" del flusso
	 * 
	 * NOTA:
	 * Se la classe Modello derivasse da Elemento questo metodo sarebbe superfluo
	 * @return
	 */
	/*
	public String getFlussoString(){
		StringBuffer output = new StringBuffer();
		output.append("[" + super.getID() + "] " + super.getNome());
		return output.toString();
	}
	
	*/
	
	/**
	 * Questo metodo stampa completamente il flusso
	 * 
	 * Giungeremo al formato corretto ragionando insieme.
	 */
	public String stampaFlusso(){
		StringBuffer output = new StringBuffer();
		output.append("[FLUSSO]: " + super.getNome() + "\n");
		output.append("\t"+ forkIN + "\n");
		output.append(super.toStringINDENTATO());
		output.append("\t" + joinOUT);
		return output.toString();
		
		
	}
	

}
