import java.util.Vector;


public class Modello {
	
	private Start start;
	private Vector<Azione> azioni;
	private Vector<Branch> branch;
	private Vector<Merge> merge;
	private Vector<Fork> fork;
	private Vector<Join> join;
	private End end;
	
	public Modello(){
		start = new Start("Start", null); //Come vogliamo gestire i costruttori?
		azioni = new Vector<Azione>();
		branch = new Vector<Branch>();
		merge = new Vector<Merge>();
		fork = new Vector<Fork>();
		join = new Vector<Join>();
		end = new End("End", null );
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

}
