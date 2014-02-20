
public class Azione extends Elemento{

		private String nome;
		private Elemento ingresso;
		private Elemento uscita;
		
		public Azione (String nome, Elemento ingresso, Elemento uscita){
			super("AZIONE");
			this.nome = nome;
			this.ingresso = ingresso;
			this.uscita = uscita;
		}
		
		@Override
		public String toString() {
			StringBuffer output=new StringBuffer();
			output.append("[" + super.getID() + "] " + nome + " :");
			
		return super.toString();
		}
}
