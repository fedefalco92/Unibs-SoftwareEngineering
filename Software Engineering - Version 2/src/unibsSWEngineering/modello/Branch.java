package unibsSWEngineering.modello;
import java.util.Vector;

public class Branch extends Elemento implements ElementoMultiUscita{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8113738236219764986L;
	private Elemento ingresso;
	private Vector <Elemento> uscite;
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
		
			if(incompleto)
				output.append("incompleto");
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
