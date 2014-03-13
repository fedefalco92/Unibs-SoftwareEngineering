import java.util.Vector;


public class Join extends Elemento implements ElementoTerminale {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5785739028451433157L;
	private Vector<Elemento> ingressi;
	private Fork forkAssociato;
	private Elemento uscita;
	
	public Join(String nome) {
		super("JOIN", nome);
		ingressi = new Vector <Elemento>();
		uscita = null;
	}

	public Fork getForkAssociato() {
		return forkAssociato;
	}

	public void setForkAssociato(Fork forkpadre) {
		this.forkAssociato = forkpadre;
	}

	public Elemento getJoinOUT() {
		return uscita;
	}
	
	//METODI EREDITATI DALLA CLASSE PADRE
	@Override
	public void aggiungiUscita(Elemento uscita) {
		this.uscita = uscita;
	}
	
	@Override
	public void aggiungiIngresso(Elemento elem) {
		ingressi.add(elem);
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
		
		else 
			output.append("empty");
		
		output.append(") - out(");
		if(uscita!=null) 
			output.append(uscita.getElementoString());
		else 
			output.append("null");
		
		output.append(")");
		return output.toString();
	}

	@Override
	public Elemento getIngresso() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Elemento getUscita() {
		return uscita;
	}

	@Override
	public Vector<Elemento> getIngressi() {
		return ingressi;
	}

	@Override
	public Vector<Elemento> getUscite() {
		// TODO Auto-generated method stub
		return null;
	}

}
