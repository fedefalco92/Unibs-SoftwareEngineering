import java.util.Vector;


public class Merge extends Elemento{

	private String nome;
	private Vector <Elemento> ingresso = new Vector <Elemento>();
	private Elemento uscita;
	
	//Due costruttori in base a quello che ci serve?
	public Merge (String nome){
		super("MERGE");
		this.nome  = nome;
	}
	public Merge (String nome, Elemento uscita){
		super("MERGE");
		this.nome = nome;
		this.uscita = uscita;
		//Aggiungere vector?
	}
	
	public void aggiungiIngress(Elemento elem){
		ingresso.add(elem);
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
