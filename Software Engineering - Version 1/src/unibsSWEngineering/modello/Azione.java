package unibsSWEngineering.modello;
import java.util.Vector;


public class Azione extends Elemento{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3006147024509738108L;
	private Elemento ingresso;
	private Elemento uscita;
	
	public Azione (String nome){
		super("AZIONE", nome);
		this.ingresso = null;
		this.uscita = null;
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
		this.uscita = uscita;
	}
	
	@Override
	public String toString() {
		StringBuffer output=new StringBuffer();
		
		output.append(super.toString() + " : ");
		output.append("in(" );
		if(ingresso!=null) output.append(ingresso.getElementoString());
		else output.append("null");
		output.append(") - out(");
		if(uscita!=null) output.append(uscita.getElementoString());
		else output.append("null");
		output.append(")");
		
		/*
		if (ingresso == null ) output.append("null)");
		else output.append("[" +ingresso.getID() + "] " + ingresso.getNome() +  ") - out(" );
		if (uscita==null) output.append("null)");
		else output.append("[" + uscita.getID() + "] " + uscita.getNome() + ")");
		*/
		return output.toString();
	}

	@Override
	public Elemento getIngresso() {
		return ingresso;
	}
	
	@Override
	public Elemento getUscita() {
		return uscita;
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
