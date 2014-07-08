package unibsSWEngineering.modello;
import java.util.Vector;

public class Merge extends Elemento implements ElementoTerminale{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1806920999680349562L;
	private Vector <Elemento> ingressi;
	private Elemento uscita;
	
	public Merge (String nome){
		super("MERGE", nome);
		this.ingressi = new Vector <Elemento>();
		this.uscita = null;
	}
	
	//METODI EREDITATI DALLA CLASSE PADRE
	@Override
	public void aggiungiIngresso(Elemento elem){
		ingressi.add(elem);
	}
	
	@Override
	public void aggiungiUscita(Elemento uscita) {
		this.uscita = uscita;
	}
	
	@Override
	public String toString() {
		StringBuffer output=new StringBuffer();
		output.append(super.toString() + " : ");
		
		
		output.append("in(" );
		if(!ingressi.isEmpty()){
			
			for(int i = 0; i < ingressi.size() - 1; i++){
				output.append(ingressi.get(i).getElementoString());
				output.append(", ");
			}
			output.append(ingressi.lastElement().getElementoString());
			
		}
		else output.append("empty");
		output.append(") - out(");
		if(uscita!=null) output.append(uscita.getElementoString());
		else output.append("null");
		output.append(")");
		
		return output.toString();
	}

	@Override
	public Vector<Elemento> getIngressi() {
		return ingressi;
	}
	
	@Override
	public Elemento getUscita() {
		return uscita;
	}
	
	@Override
	public Elemento getIngresso() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Elemento> getUscite() {
		// TODO Auto-generated method stub
		return null;
	}

}
