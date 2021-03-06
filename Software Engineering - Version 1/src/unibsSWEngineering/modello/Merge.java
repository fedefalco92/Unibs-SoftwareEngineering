package unibsSWEngineering.modello;
import java.util.Vector;

public class Merge extends Elemento implements ElementoTerminale{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1806920999680349562L;
	private Vector <Elemento> ingressi;
	private Elemento uscita;
	private boolean incompleto;
	
	public Merge (String nome){
		super("MERGE", nome);
		this.ingressi = new Vector <Elemento>();
		this.uscita = null;
		this.incompleto=true;
	}
	
	public boolean incompleto(){
		return incompleto;
	}
	
	//METODI EREDITATI DALLA CLASSE PADRE
	@Override
	public void aggiungiIngresso(Elemento elem){
		ingressi.add(elem);
		if(ingressi.size()>=2)
			incompleto=false;
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
			
			/* BUG VIRGOLA
			for(Elemento elemento:ingressi){
				output.append(elemento.getElementoString());
				output.append(", ");
			}
			*/
			if(incompleto)
				output.append("incompleto");
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
