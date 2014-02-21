
public class End extends Elemento {
	
	private Elemento ingresso;
	
	public End (String nome){
		super("END", nome);
		this.ingresso = null;
	}
	
	public Elemento getIngresso() {
		return ingresso;
	}
	
	public void setIngresso(Elemento ingresso) {
		this.ingresso = ingresso;
	}
	
	@Override
	public String toString() {
		StringBuffer output=new StringBuffer();
		output.append(super.toString() + " : ");
		output.append("in(" + ingresso + ")");
		return output.toString();
	}
}
