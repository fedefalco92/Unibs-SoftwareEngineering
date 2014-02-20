
public class Azione extends Elemento{

	private String nome;
	private Elemento ingresso;
	private Elemento uscita;
	
	//Due costruttori in base a quello che ci serve?
	public Azione (String nome){
		super("AZIONE");
		this.nome  = nome;
	}
	
	public Azione (String nome, Elemento ingresso, Elemento uscita){
		super("AZIONE");
		this.nome = nome;
		this.ingresso = ingresso;
		this.uscita = uscita;
	}
	
	public void setIngresso(Elemento ingresso) {
		this.ingresso = ingresso;
	}
	
	public void setUscita(Elemento uscita) {
		this.uscita = uscita;
	}
	
	@Override
	public String toString() {
		StringBuffer output=new StringBuffer();
		output.append("[" + super.getID() + "] " + nome + " :");
		
		return output.toString();
	}
}
