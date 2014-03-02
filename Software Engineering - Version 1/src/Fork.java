import java.util.Vector;

/**
 * La classe fork ha come ingresso un qualsiasi elemento e come uscite almeno due flussi di esecuzione paralleli!
 * @author Maffi
 *
 */
public class Fork extends Elemento {
	
	private Elemento ingresso;
	private Vector<Flusso> flussiOUT;
	private Join joinAssociato;
	private boolean incompleto;

	public Join getJoinTerminale() {
		return joinAssociato;
	}

	public void setJoinTerminale(Join joinTerminale) {
		this.joinAssociato = joinTerminale;
	}

	public Fork(String nome, Join _jointerminale) {
		super("FORK", nome);
		this.flussiOUT = new Vector<Flusso>();
		this.joinAssociato = _jointerminale;
		this.incompleto = true;
	}

	

	public Elemento getIngresso() {
		return ingresso;
	}

	public void setIngresso(Elemento ingresso) {
		this.ingresso = ingresso;
	}
	
	public void aggiungiFlusso(Flusso f){
		flussiOUT.add(f);
		if(flussiOUT.size()>=2)
			incompleto=false;
	}
	
	public String toString(){
		StringBuffer output=new StringBuffer();
		output.append(super.toString() + " : ");
		
		output.append("in(" );
		if(ingresso!=null) output.append(ingresso.getElementoString());
		else output.append("null");
		output.append(") - out(");
		if(!flussiOUT.isEmpty()){
			for(int i = 0; i < flussiOUT.size() - 1; i++){
				output.append(flussiOUT.get(i).getElementoString()); //IN ALTERNATIVA getFlussoString()
				output.append(", ");
			}
			output.append(flussiOUT.lastElement().getElementoString());
			
			if(incompleto)
				output.append("incompleto");
		}
		else output.append("empty");
		
		output.append(")");
		
		
		return output.toString();
	}
	
	

}
