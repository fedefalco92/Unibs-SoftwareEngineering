
public class Start extends Elemento{
	
	private String nome;
	private Elemento uscita;
	
	public Start (String nome, Elemento uscita){
		super("AZIONE");
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
