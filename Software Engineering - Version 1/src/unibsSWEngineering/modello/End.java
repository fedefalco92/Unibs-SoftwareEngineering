package unibsSWEngineering.modello;
import java.util.Vector;


public class End extends Elemento{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8749422570277378920L;
	private Elemento ingresso;
	
	public End (String nome){
		super("END", nome);
		this.ingresso = null;
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
	public void aggiungiUscita(Elemento uscita) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public String toString() {
		StringBuffer output=new StringBuffer();
		output.append(super.toString() + " : ");
		
		output.append("in(" );
		if(ingresso!=null) output.append(ingresso.getElementoString());
		else output.append("null");
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
		// TODO Auto-generated method stub
		return null;
	}
}
