import java.util.Vector;


public class Join extends Elemento implements ElementoTerminale {

	//private Vector<Flusso> flussiIN;
	private Vector<Elemento> ingressi;
	private Fork forkAssociato;
	private Elemento uscita;
	//private boolean incompleto;
	
	/*
	 * nel costruttore non metto il fork padre dato che 
	 * il join viene creato prima del fork padre e viene passato
	 * in ingresso al fork padre come join terminale
	 */
	public Join(String nome) {
		super("JOIN", nome);
		//flussiIN = new Vector <Flusso>();
		ingressi = new Vector <Elemento>();
		//incompleto = true;
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

	@Override
	public void aggiungiUscita(Elemento uscita) {
		this.uscita = uscita;
	}
	
	/*
	public void aggiungiFlussoIN(Flusso f){
		flussiIN.add(f);
		if(flussiIN.size()>=2)
			incompleto=false;
	}
	*/
	
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
			
//			if(incompleto)
//				output.append("incompleto");
		}
		/*
		 * if(!flussiIN.isEmpty()){
			
			for(int i = 0; i < flussiIN.size() - 1; i++){
				output.append(flussiIN.get(i).getFlussoString());
				output.append(", ");
			}
			output.append(flussiIN.lastElement().getFlussoString());
			
			if(incompleto)
				output.append("incompleto");
		}
		 */
		else output.append("empty");
		output.append(") - out(");
		if(uscita!=null) output.append(uscita.getElementoString());
		else output.append("null");
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

	@Override
	public void aggiungiIngresso(Elemento elem) {
		ingressi.add(elem);
	}

}
