import java.util.Vector;

public class Branch extends Elemento{

	private Elemento ingresso;
	private Vector <Elemento> uscite;
	
	public Branch (String nome){
		super("BRANCH", nome);
		this.ingresso = null;
		this.uscite = new Vector <Elemento>();
	}
	
	public Elemento getIngresso() {
		return ingresso;
	}
	
	public Vector<Elemento> getUscite() {
		return uscite;
	}
	
	public void aggiungiUscita(Elemento elem){
		uscite.add(elem);
	}
	
	public void setIngresso(Elemento ingresso) {
		this.ingresso = ingresso;
	}
	
	@Override
	public String toString() {
		StringBuffer output=new StringBuffer();
		output.append(super.toString() + " : ");
		
		output.append("in(" );
		output.append(ingresso.getElementoString());
		output.append(") - out(");
		for(Elemento elemento:uscite){
			output.append(elemento.getElementoString());
			output.append(", ");
		}	
		output.append(")");
		
		/*
		if(ingresso !=null && !uscite.isEmpty()){
			output.append("in(" + "[" + ingresso.getID() + "] " + ingresso.getNome() +  ") - out(");
			for(Elemento elemento:uscite)
				output.append( "[" + elemento.getID() + "] " + elemento.getNome() +", ");
			output.append(")");
		}
		*/
		return output.toString();
	}
}
