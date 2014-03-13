import java.util.Vector;

public class Branch extends Elemento implements ElementoMultiUscita{

	private Elemento ingresso;
	private Vector <Elemento> uscite;
	private Merge mergePrecedente;
	private Merge mergeSeguente;
	private boolean incompleto;
	
	public Branch (String nome){
		super("BRANCH", nome);
		this.ingresso = null;
		this.uscite = new Vector <Elemento>();
		this.incompleto = true;
	}
	
	public boolean incompleto(){
		return incompleto;
	}
	
	public Merge getMergePrecedente() {
		return mergePrecedente;
	}

	public void setMergePrecedente(Merge mergePrecedente) {
		this.mergePrecedente = mergePrecedente;
	}

	public Merge getMergeSeguente() {
		return mergeSeguente;
	}

	public void setMergeSeguente(Merge mergeSeguente) {
		this.mergeSeguente = mergeSeguente;
	}
	
	public void setIngresso(Elemento ingresso) {
		this.ingresso = ingresso;
	}
	
	//METODI EREDITATI DALLA CLASSE PADRE
	@Override
	public void aggiungiIngresso(Elemento ingresso) {
		this.ingresso = ingresso;
	}
	
	@Override
	public void aggiungiUscita(Elemento elem){
		uscite.add(elem);
		if(uscite.size()>=2)
			incompleto=false;
	}
	
	@Override
	public String toString() {
		
		StringBuffer output=new StringBuffer();
		output.append(super.toString() + " : ");
		
		output.append("in(" );
		if(ingresso!=null) output.append(ingresso.getElementoString());
		else output.append("null");
		output.append(") - out(");
		if(!uscite.isEmpty()){
			for(int i = 0; i < uscite.size() - 1; i++){
				output.append(uscite.get(i).getElementoString());
				output.append(", ");
			}
			output.append(uscite.lastElement().getElementoString());
			/* BUG VIRGOLA
			for(Elemento elemento:uscite){
				output.append(elemento.getElementoString());
				output.append(", ");
			}
			*/
			if(incompleto)
				output.append("incompleto");
		}
		else output.append("empty");
		
		output.append(")");
		
		/*
		
		if(ingresso !=null && !uscite.isEmpty()){
			output.append("in(" +ingresso.getElementoString() +  ") - out(");
			for(Elemento elemento:uscite)
				output.append( "[" + elemento.getElementoString() +", ");
			output.append(")");
		}
		*/
		return output.toString();
	}

	@Override
	public Elemento getIngresso() {
		return ingresso;
	}
	
	@Override
	public Vector<Elemento> getUscite() {
		return uscite;
	}
	
	@Override
	public Elemento getUscita() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Elemento> getIngressi() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminaUscita(Elemento uscita) {
		if(uscite.contains(uscita))
			uscite.remove(uscita);
		
	}

}
