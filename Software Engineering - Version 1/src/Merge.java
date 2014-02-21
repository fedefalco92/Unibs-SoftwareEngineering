import java.util.Vector;


public class Merge extends Elemento{

	private Vector <Elemento> ingressi;
	private Elemento uscita;
	
	public Merge (String nome){
		super("MERGE", nome);
		this.ingressi = new Vector <Elemento>();
		this.uscita = null;
	}
	
	public Vector<Elemento> getIngressi() {
		return ingressi;
	}
	
	public Elemento getUscita() {
		return uscita;
	}
	
	public void aggiungiIngresso(Elemento elem){
		ingressi.add(elem);
	}
	
	public void setUscita(Elemento uscita) {
		this.uscita = uscita;
	}
	
	@Override
	public String toString() {
		StringBuffer output=new StringBuffer();
		output.append(super.toString() + " : ");
		output.append("in(");
		for(Elemento elemento:ingressi)
			output.append(elemento+ ", ");
		output.append(") - out(" + uscita +")");
		return output.toString();
	}
}
