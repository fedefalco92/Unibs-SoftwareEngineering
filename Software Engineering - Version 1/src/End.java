
public class End extends Elemento {
	private String nome;
	private Elemento ingresso;
	
	//Due costruttori in base a quello che ci serve?
	public End (String nome){
		super("END");
		this.nome  = nome;
	}
	
	public End (String nome, Elemento ingresso){
		super("END");
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
