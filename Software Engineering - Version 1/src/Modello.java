import java.util.Vector;


public class Modello {
	
	private String nome;
	
	private Start start;
	private Vector<Azione> azioni;
	private Vector<Branch> branch;
	private Vector<Merge> merge;
	private Vector<Fork> fork;
	private Vector<Join> join;
	private End end;
	
	private Elemento ultimaModifica;
	private Vector<Merge> mergeIncompleti;
	
		public Modello(String nome){
		this.nome = nome;
		start = new Start("Start"); //Come vogliamo gestire i costruttori?
		azioni = new Vector<Azione>();
		branch = new Vector<Branch>();
		merge = new Vector<Merge>();
		fork = new Vector<Fork>();
		join = new Vector<Join>();
		end = new End("End");
		mergeIncompleti = new Vector<Merge>();
	}
	
	public void setUltimaModifica(Elemento e){
		ultimaModifica=e;
	}
	
	public Elemento getUltimaModifica(){
		return ultimaModifica;
	}
	
	public Vector<Merge> getMergeIncompleti() {
		return mergeIncompleti;
	}
	
	public void aggiungiMergeIncompleto(Merge m){
		mergeIncompleti.add(m);
	}
	
	public void setPrimaAzione(Azione azione){
		start.setUscita(azione);
	}
	
	public Vector<Azione> getAzioni() {
		return azioni;
	}

	public Vector<Branch> getBranch() {
		return branch;
	}

	public Vector<Merge> getMerge() {
		return merge;
	}

	public Vector<Fork> getFork() {
		return fork;
	}

	public Vector<Join> getJoin() {
		return join;
	}

	public String getNome() {
		return nome;
	}

	public Start getStart() {
		return start;
	}

	public End getEnd() {
		return end;
	}

	public void setNome(String _nome){
		nome=_nome;
	}
	
	public void setStart(Start start){
		this.start = start;
	}
	
	public void setEnd(End end){
		this.end = end;
	}
	
	public void aggiungiAzione(Azione azione){
		azioni.add(azione);
	}
	
	public void aggiungiBranch(Branch _branch){
		branch.add(_branch);
	}
	
	public void aggiungiMerge(Merge _merge){
		merge.add(_merge);
	}
	
	public void aggiungiFork(Fork _fork){
		fork.add(_fork);
	}
	
	public void aggiungiJoin(Join _join){
		join.add(_join);
	}

	@Override
	
	//PRIMA VERSIONE...quando saremo riusciti a creare un modello vedremo come migliorarla!
	public String toString() {
		StringBuffer output=new StringBuffer();
		
		output.append("NOME MODELLO: " + nome + "\n\n");
		output.append(start + "\n");
		for(Azione azione: azioni)
			output.append(azione + "\n");
		for(Branch br: branch)
			output.append(br + "\n");
		for(Merge mr: merge)
			output.append(mr + "\n");
		//fork
		
		//join
		
		output.append(end);
		

		return output.toString();
	}
	
	/**
	 * Questo metodo restituisce falso se un nome &egrave gi&agrave stato utilizzato per un qualsiasi elemento del modello, 
	 * vero altrimenti
	 * 
	 * il metodo non fa confronto con il nome del modello stesso, il nome del nodo iniziale e quello del nodo finale
	 * @param nome
	 * @return
	 */
	public boolean nomeOK(String nome) {
		for(Azione azione:azioni){
			if(nome==azione.getNome()) return false;
		}
		for(Branch b:branch){
			if(nome==b.getNome()) return false;
		}
		for(Merge m:merge){
			if(nome==m.getNome()) return false;
		}
		/* uso futuro (le classi Fork e Join sono vuote al momento)
		for(Fork f:fork){
			if(nome==f.getNome()) return false;
		}
		for(Join j:join){
			if(nome==j.getNome()) return false;
		} */
		
		return true;
	}
	
	//AGGIUNGIAMO QUA UN METODO CHE DICE DA SOLO SE IL MODELLO E' CORRETTO?
	//public boolean corretto(){
	//	//todo
	//}

	
}
