
public class Start extends Elemento{
	
	private Elemento uscita;
	
	public Start (String nome){
		super("START", nome);
		this.uscita = null;
	}
	
	public Elemento getUscita() {
		return uscita;
	}
	
	public void setUscita(Elemento uscita) {
		this.uscita = uscita;
	}
	
	@Override
	public String toString() {
		StringBuffer output=new StringBuffer();
		output.append(super.toString());
		//Aggiungi formattazione altri elementi
		return output.toString();
	}
}
