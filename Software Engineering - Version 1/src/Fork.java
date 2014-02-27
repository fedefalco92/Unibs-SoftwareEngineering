import java.util.Vector;


public class Fork extends Elemento {
	
	private Elemento ingresso;
	private Vector<Flusso> flussiOUT;
	private Join joinTerminale;

	public Join getJoinTerminale() {
		return joinTerminale;
	}

	public void setJoinTerminale(Join joinTerminale) {
		this.joinTerminale = joinTerminale;
	}

	public Fork(String ID, String nome, Join _jointerminale) {
		super("FORK", nome);
		this.flussiOUT = new Vector<Flusso>();
		this.joinTerminale = _jointerminale;
	}

	

	public Elemento getIngresso() {
		return ingresso;
	}

	public void setIngresso(Elemento ingresso) {
		this.ingresso = ingresso;
	}

}
