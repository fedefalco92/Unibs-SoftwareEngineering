import java.util.Vector;

public class Branch extends Elemento{

	private Elemento ingresso;
	private Vector <Elemento> uscita;
	
	public Branch (String nome){
		super("BRANCH", nome);
		this.ingresso = null;
		this.uscita = new Vector <Elemento>();
	}
	
	public Elemento getIngresso() {
		return ingresso;
	}
	
	public Vector<Elemento> getUscita() {
		return uscita;
	}
	
	public void aggiungiUscita(Elemento elem){
		uscita.add(elem);
	}
	
	public void setIngresso(Elemento ingresso) {
		this.ingresso = ingresso;
	}
	
	@Override
	public String toString() {
		StringBuffer output=new StringBuffer();
		output.append(super.toString());
		//Aggiungi formattazione altri elementi
		return output.toString();
	}
}
