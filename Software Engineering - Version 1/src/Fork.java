import java.util.Vector;

/**
 * La classe fork ha come ingresso un qualsiasi elemento e come uscite almeno due flussi di esecuzione paralleli!
 * @author Maffi
 *
 */
public class Fork extends Elemento {
	
	private Elemento ingresso;
	//private Vector<Flusso> flussiOUT;
	private Vector<Elemento> uscite;
	private Join joinAssociato;
	private boolean incompleto; //attenzione! ancora da decidere se per flussiOUT o uscite

	public Join getJoinAssociato() {
		return joinAssociato;
	}

	public void setJoinAssociato(Join joinAssociato) {
		this.joinAssociato = joinAssociato;
	}
	
	public void aggiungiUscita(Elemento uscita){
		uscite.add(uscita);
		if(uscite.size()>=2)
			incompleto=false;
	}

	public Fork(String nome) {
		super("FORK", nome);
		//this.flussiOUT = new Vector<Flusso>();
		this.incompleto = true;
		this.uscite = new Vector <Elemento> ();
	}

	public void setIngresso(Elemento ingresso) {
		this.ingresso = ingresso;
	}
	
	/*
	public void aggiungiFlusso(Flusso f){
		flussiOUT.add(f);
		if(flussiOUT.size()>=2)
			incompleto=false;
	}
	*/
	public String toString(){
		StringBuffer output=new StringBuffer();
		output.append(super.toString() + " : ");
		
		output.append("in(" );
		if(ingresso!=null) output.append(ingresso.getElementoString());
		else output.append("null");
		output.append(") - out(");
		if(!uscite.isEmpty()){
			for(int i = 0; i < uscite.size() - 1; i++){
				output.append(uscite.get(i)); //IN ALTERNATIVA getFlussoString()
				output.append(", ");
			}
			output.append(uscite.lastElement());
			
			if(incompleto)
				output.append("incompleto");
		}
		/*
		if(!flussiOUT.isEmpty()){
			for(int i = 0; i < flussiOUT.size() - 1; i++){
				output.append(flussiOUT.get(i).getFlussoString()); //IN ALTERNATIVA getFlussoString()
				output.append(", ");
			}
			output.append(flussiOUT.lastElement().getFlussoString());
			
			if(incompleto)
				output.append("incompleto");
		}
		*/
		else output.append("empty");
		
		output.append(")");
		
		
		return output.toString();
	}

	public Elemento getIngresso() {
		return ingresso;
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
	public Vector<Elemento> getUscite() {
		// TODO Auto-generated method stub
		return null;
	}

	
	

}
