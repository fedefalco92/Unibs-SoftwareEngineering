import java.util.Vector;


public class Join extends Elemento {

	private Vector<Flusso> flussiIN;
	private Fork forkpadre;
	private Elemento uscita;
	
	/*
	 * nel costruttore non metto il fork padre dato che 
	 * il join viene creato prima del fork padre e viene passato
	 * in ingresso al fork padre come join terminale
	 */
	public Join(String nome) {
		super("JOIN", nome);
		flussiIN = new Vector <Flusso>();
	}

	public Fork getForkpadre() {
		return forkpadre;
	}

	public void setForkpadre(Fork forkpadre) {
		this.forkpadre = forkpadre;
	}

	public Elemento getJoinOUT() {
		return uscita;
	}

	public void setUscita(Elemento uscita) {
		this.uscita = uscita;
	}
	
	public void aggiungiFlusso(Flusso f){
		flussiIN.add(f);
	}

}
