import java.util.Vector;


public class Merge extends Elemento{

	private Vector <Elemento> ingresso;
	private Elemento uscita;
	
	public Merge (String nome){
		super("MERGE", nome);
		this.ingresso = new Vector <Elemento>();
		this.uscita = null;
	}
	
	public Vector<Elemento> getIngresso() {
		return ingresso;
	}
	
	public Elemento getUscita() {
		return uscita;
	}
	
	public void aggiungiIngresso(Elemento elem){
		ingresso.add(elem);
	}
	
	public void setUscita(Elemento uscita) {
		this.uscita = uscita;
	}
	
	@Override
	public String toString() {
		StringBuffer output=new StringBuffer();
		output.append(super.toString());
		//Aggiungi formattazione altri elementi
		return output.toString();
	}
}
