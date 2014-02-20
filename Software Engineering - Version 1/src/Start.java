
public class Start extends Elemento{
	
	private String nome;
	private Elemento uscita;
	
	//Due costruttori in base a quello che ci serve?
	public Start (String nome){
		super("START");
		this.nome  = nome;
	}
		
	public Start (String nome, Elemento uscita){
		super("START");
		this.nome = nome;
		this.uscita = uscita;
	}
	
	@Override
	public String toString() {
		StringBuffer output=new StringBuffer();
		output.append("[" + super.getID() + "] " + nome + " :");
		
		return output.toString();
	}
}
