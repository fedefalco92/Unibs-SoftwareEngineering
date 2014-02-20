import java.util.Vector;

public class Branch extends Elemento{

	private String nome;
	private Elemento ingresso;
	private Vector <Elemento> uscita = new Vector <Elemento>();
	
	//Due costruttori in base a quello che ci serve?
	public Branch (String nome){
		super("BRANCH");
		this.nome  = nome;
	}
	
	public Branch (String nome, Elemento ingresso){
		super("BRANCH");
		this.nome = nome;
		this.ingresso = ingresso;
		//Aggiungere vector?
	}
	
	public void aggiungiUscita(Elemento elem){
		uscita.add(elem);
	}
	
	public void setIngresso(Elemento ingresso) {
		this.ingresso = ingresso;
	}
	
	@Override
	public String toString() {
		StringBuffer output=new StringBuffer();
		output.append("[" + super.getID() + "] " + nome + " :");
		
		return output.toString();
	}
}
