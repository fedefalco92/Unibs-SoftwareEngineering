import java.util.Vector;


public class Start extends Elemento{
	
	private Azione uscita;
	
	public Start (String nome){
		super("START", nome);
		this.uscita = null;
	}
	
	public void setUscita(Azione uscita) {
		this.uscita = uscita;
	}
	
	@Override
	public String toString() {
		StringBuffer output=new StringBuffer();
		output.append(super.toString() + " : ");
		
		output.append("out(");
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Elemento> getUscite() {
		// TODO Auto-generated method stub
		return null;
	}

}
