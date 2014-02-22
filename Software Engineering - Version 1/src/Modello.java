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
	
	public Modello(String nome){
		this.nome = nome;
		start = new Start("Start"); //Come vogliamo gestire i costruttori?
		azioni = new Vector<Azione>();
		branch = new Vector<Branch>();
		merge = new Vector<Merge>();
		fork = new Vector<Fork>();
		join = new Vector<Join>();
		end = new End("End");
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
	
	//AGGIUNGIAMO QUA UN METODO CHE DICE DA SOLO SE IL MODELLO E' CORRETTO?
	//public boolean corretto(){
	//	//todo
	//}

	
}
