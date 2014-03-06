import java.util.Vector;

public class Branch extends Elemento{

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
	
	/**
	 * Aggiunge un elemento al Vector Uscita
	 * @param elemento
	 */
	public void aggiungiUscita(Elemento elem){
		uscite.add(elem);
		if(uscite.size()>=2)
			incompleto=false;
	}
	
	/**
	 * Getter per l'incompletezza del Branch.
	 * @return incompleto
	 */
	public boolean incompleto(){
		return incompleto;
	}
	
	/**
	 * Setter per l'ingresso
	 * @param ingresso
	 */
	public void setIngresso(Elemento ingresso) {
		this.ingresso = ingresso;
	}
	
	/**
	 * Metodo toString() per l'elemento.
	 */
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

	/**
	 * Getter per Ingresso
	 * @return ingresso
	 */
	@Override
	public Elemento getIngresso() {
		return ingresso;
	}
	
	/**
	 * Getter per Uscita
	 * @return uscite
	 */
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
}
