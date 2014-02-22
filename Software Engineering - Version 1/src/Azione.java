
public class Azione extends Elemento{
	
	private Elemento ingresso;
	private Elemento uscita;
	
	public Azione (String nome){
		super("AZIONE", nome);
		this.ingresso = null;
		this.uscita = null;
	}
	
	public Elemento getIngresso() {
		return ingresso;
	}
	
	public Elemento getUscita() {
		return uscita;
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
		output.append(super.toString() + " : ");
		output.append("in(" + ingresso.getID() + "] " + ingresso.getNome() +  ") - out(" + "[" + uscita.getID() + "] " + uscita.getNome() + ")");
		return output.toString();
	}
}
