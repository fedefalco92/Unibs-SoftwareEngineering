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
		
		
		output.append("in(" );
		if(!ingressi.isEmpty())
			for(Elemento elemento:ingressi){
				output.append(elemento.getElementoString());
				output.append(", ");
			}
		else output.append("empty");
		output.append(") - out(");
		if(uscita!=null) output.append(uscita.getElementoString());
		else output.append("null");
		output.append(")");
		
		/*
		if (!ingressi.isEmpty() && uscita!=null){
			output.append("in(");
			for(Elemento elemento:ingressi)
				output.append(elemento.getElementoString()+  ", ");
			output.append(") - out(" + uscita.getElementoString() +")");
		}
		*/
		return output.toString();
	}
}
