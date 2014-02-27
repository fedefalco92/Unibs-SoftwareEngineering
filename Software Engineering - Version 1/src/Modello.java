import java.util.Vector;


public class Modello {
	
	private String nome;
	
	/*AGGIUNGO UN VECTOR DI ELEMENTI! CREDO SIA PIU' COMODO PER IL CARICAMENTO 
	 * Si delega un metodo nella classe modello per riempire tutti gli altri vector partendo da questo
	 * Per ora e' un'idea che quando maffi finisce di fare le sue modifiche implemento :)
	 */
	private Vector <Elemento> elementi; 

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
		elementi = new Vector<Elemento>();
		
		start = new Start("Start");
		azioni = new Vector<Azione>();
		branch = new Vector<Branch>();
		merge = new Vector<Merge>();
		fork = new Vector<Fork>();
		join = new Vector<Join>();
		end = new End("End");
		mergeIncompleti = new Vector<Merge>();
	}
	
	//METODI AGGIUNTA A VECTOR//
	public void aggiungiElemento(Elemento elem){
		elementi.add(elem);
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
	
	public void aggiungiMergeIncompleto(Merge m){
		mergeIncompleti.add(m);
	}
	
	//METODI SETTER//
	public void setUltimaModifica(Elemento e){
		ultimaModifica=e;
	}
	
	public void setPrimaAzione(Azione azione){
		start.setUscita(azione);
	}
	
	public void setUltimoElemento(Elemento e){
		end.setIngresso(e);
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
	
	//METODI GETTER//
	public Elemento getUltimaModifica(){
		return ultimaModifica;
	}
	
	public Vector<Merge> getMergeIncompleti() {
		return mergeIncompleti;
	}
	
	public Vector<Elemento> getElementi() {
		return elementi;
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

	//METODI SUL MODELLO//
	
	//RIEMPI VECTOR SPECIALIZZATI NEL MODELLO
		public void riempiVectorModello(){
			for(Elemento elem: elementi){
				switch (elem.getID()) {
				case "AZIONE":
					aggiungiAzione((Azione) elem);
					break;
				case "BRANCH":
					aggiungiBranch((Branch) elem);
					break;
				case "MERGE":
					aggiungiMerge((Merge) elem);
					break;
				case "END":
					end = (End) elem;
					break;
				case "START":
					start = (Start) elem;
					break;
				case "FORK":
					//
					break;
				case "JOIN":
					//
					break;
				default:
					break;
				}
			}
		}
		
	//METODO CHE CONTROLLA LA CORRETTEZZA DEL MODELLO
	public boolean controllaModello(){
		
		
		return true;
	}
	
	//METODO CHE VERIFICA CHE UN ELEMENTO E' CONTENUTO NEL MODELLO
	public boolean elementoInModello(Elemento elem){
		for(Elemento e: elementi){
			//Se presente restituisco vero
			if(e.equals(elem))
				return true;
		}
		//Altrimenti restituisco falso
		return false;
	}
	
	//METODO DI RICERCA NEL VECTOR DI ELEMENTI//
	public Elemento ricercaElementoInModello(String ID, String nome){
		for(Elemento elem: elementi){
			if(elem.getID().equalsIgnoreCase(ID) && elem.getNome().equalsIgnoreCase(nome)){
				return elem;
			}
		}
		return null;
	}
	
	public int indiceElemento(Elemento elem){
		int index = -1;
		for (int i = 0; i < elementi.size(); i++){
			if (elem.equals(elementi.get(i)))
				return index;
		}
		return index;
	}
	
	@Override
	/**
	 * Restituisce l'elenco di azioni, branch, merge, fork, join.
	 * la stampa completa del modello e' effettuata dal metodo stampaModello()
	 */
	public String toString() {
		StringBuffer output=new StringBuffer();
		
		//la righe commentate verranno eseguite nel metodo "stampaModello()"
		//output.append("NOME MODELLO: " + nome + "\n\n");
		//output.append(start + "\n");
		for(Azione azione: azioni)
			output.append(azione + "\n");
		for(Branch br: branch)
			output.append(br + "\n");
		for(Merge mr: merge)
			output.append(mr + "\n");
		//fork
		
		//join
		
		//output.append(end);
		

		return output.toString();
	}
	
	public String stampaModello() {
		StringBuffer output = new StringBuffer();
		output.append("NOME MODELLO: " + nome + "\n\n");
		output.append(start + "\n");
		output.append(this);
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
			if(nome.equals(azione.getNome())) return false;
		}
		for(Branch b:branch){
			if(nome.equals(b.getNome())) return false;
		}
		for(Merge m:merge){
			if(nome.equals(m.getNome())) return false;
		}
		/* uso futuro (le classi Fork e Join sono vuote al momento)
		for(Fork f:fork){
			if((nome.equals(f.getNome())) return false;
		}
		for(Join j:join){
			if((nome.equals(j.getNome())) return false;
		} */
		
		return true;
	}
	
	//AGGIUNGIAMO QUA UN METODO CHE DICE DA SOLO SE IL MODELLO E' CORRETTO?
	//public boolean corretto(){
	//	//todo
	//}

	
}
