import java.util.Vector;

public class Branch extends Elemento{

	private Elemento ingresso;
	private Vector <Elemento> uscite;
	private boolean incompleto;
	
	public Branch (String nome){
		super("BRANCH", nome);
		this.ingresso = null;
		this.uscite = new Vector <Elemento>();
		this.incompleto = true;
	}
	
	/**
	 * Getter per Ingresso
	 * @return ingresso
	 */
	public Elemento getIngresso() {
		return ingresso;
	}
	
	/**
	 * Getter per Uscita
	 * @return uscite
	 */
	public Vector<Elemento> getUscite() {
		return uscite;
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
	
	@Override
	public String toString() {
		
		StringBuffer output=new StringBuffer();
		output.append(super.toString() + " : ");
		
		output.append("in(" );
		if(ingresso!=null) output.append(ingresso.getElementoString());
		else output.append("null");
		output.append(") - out(");
		if(!uscite.isEmpty()){
			for(Elemento elemento:uscite){
				output.append(elemento.getElementoString());
				output.append(", ");
			}
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
}
