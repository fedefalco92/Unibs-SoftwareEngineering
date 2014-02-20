
public class End extends Elemento {
	private String nome;
	private Elemento ingresso;
	
	public End (String nome, Elemento ingresso){
		super("AZIONE");
		this.nome = nome;
		this.ingresso = ingresso;
	}
	
	
	@Override
	public String toString() {
		StringBuffer output=new StringBuffer();
		output.append("[" + super.getID() + "] " + nome + " :");
		
		return output.toString();
	}
}
