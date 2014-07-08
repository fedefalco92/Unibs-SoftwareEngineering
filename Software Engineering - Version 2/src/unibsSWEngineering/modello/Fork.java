package unibsSWEngineering.modello;
import java.util.Vector;

/**
 * La classe fork ha come ingresso un qualsiasi elemento e come uscite almeno due flussi di esecuzione paralleli!
 *
 */
public class Fork extends Elemento implements ElementoMultiUscita {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8413293322756072945L;
	private Elemento ingresso;
	private Vector<Elemento> uscite;
	private Join joinAssociato;

	public Join getJoinAssociato() {
		return joinAssociato;
	}

	public void setJoinAssociato(Join joinAssociato) {
		this.joinAssociato = joinAssociato;
	}
	
	public Fork(String nome) {
		super("FORK", nome);
		this.uscite = new Vector <Elemento> ();
	}

	public void setIngresso(Elemento ingresso) {
		this.ingresso = ingresso;
	}
	
	//METODI EREDITATI DALLA CLASSE PADRE
	@Override
	public void aggiungiUscita(Elemento uscita){
		uscite.add(uscita);
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
			
		}
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
