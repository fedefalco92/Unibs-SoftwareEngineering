import java.util.Vector;

/**
 * La classe fork ha come ingresso un qualsiasi elemento e come uscite almeno due flussi di esecuzione paralleli!
 * 
 * @author Maff3x
 *
 */
public class Fork extends Elemento implements ElementoMultiUscita {
	
	private Elemento ingresso;
	private Vector<Elemento> uscite;
	private Join joinAssociato;
	private boolean incompleto; //attenzione! ancora da decidere se per flussiOUT o uscite

	public Join getJoinAssociato() {
		return joinAssociato;
	}

	public void setJoinAssociato(Join joinAssociato) {
		this.joinAssociato = joinAssociato;
	}
	
	public Fork(String nome) {
		super("FORK", nome);
		this.incompleto = true;
		this.uscite = new Vector <Elemento> ();
	}

	public void setIngresso(Elemento ingresso) {
		this.ingresso = ingresso;
	}
	
	//METODI EREDITATI DALLA CLASSE PADRE
	@Override
	public void aggiungiUscita(Elemento uscita){
		uscite.add(uscita);
		if(uscite.size()>=2)
			incompleto=false;
	}
	
	@Override
	public void aggiungiIngresso(Elemento ingresso) {
		this.ingresso = ingresso;
	}
	
	@Override
	public String toString(){
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

	@Override
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
		return uscite;
	}

	@Override
	public void eliminaUscita(Elemento uscita) {
		if(uscite.contains(uscita))
			uscite.remove(uscita);
		
	}

	@Override
	public Vector<Elemento> getUsciteSenzaAzioni() {
		Vector<Elemento> out = new Vector<Elemento>();
		for(Elemento e: uscite){
			if(!e.getID().equals("AZIONE"))
				out.add(e);
		}
		return out;
	}

	
	

}
