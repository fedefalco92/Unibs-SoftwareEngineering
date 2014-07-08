package unibsSWEngineering.modello;

import java.util.Vector;
import java.io.Serializable;

public class Modello implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4849673407309324003L;

	private String nome;
	
	//AGGIUNGO UN VECTOR DI ELEMENTI 
	 
	private Vector <Elemento> elementi; 

	private Start start;
	private Vector<Azione> azioni;
	private Vector<Branch> branch;
	private Vector<Merge> merge;
	private Vector<Fork> fork;
	private Vector<Join> join;
	private End end;
	
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
	}
	
	public Vector<String> getNomiAzioni(){
		Vector<String> v = new Vector <String>();
		for (Azione azione: azioni){
			v.add(azione.getNome());
		}
		return v;
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
	
	
	//METODI SETTER//
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
	/**
	 * Metodo che riempie i vector dei modelli specializzati partendo dal vector di elementi contenuti nel modello.
	 * @author federicofalcone
	 */
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
				aggiungiFork((Fork) elem);
				break;
			case "JOIN":
				aggiungiJoin((Join) elem);
				break;
			default:
				break;
			}
		}
	}
	
	//CONTROLLO CORRETTEZZA ELEMENTI
	/**
	 * Metodo padre che fa i controlli necessari sul modello.
	 * Esso richiama altri metodi ausiliari per verificare la correttezza del modello
	 * @return TRUE se il modello e' sintatticamente e semanticamente corretto, FALSE altrimenti.
	 * @author federicofalcone
	 */
	public boolean controllaModello(){
		//Controllo immediatamente che siano impostati il punto iniziale e il punto finale
		if(start == null && end == null){
			System.out.println("Manca il nodo iniziale START e il nodo finale END");
			return false;
		}else if(start == null){
			System.out.println("Manca il nodo iniziale START");
			return false;
		}else if(end == null){
			System.out.println("Manca il nodo finale END");
			return false;
		}else if(fork.size() != join.size()){
			System.out.println("I FORK presenti del modello sono in numero diverso rispetto ai JOIN presenti. C'e' un errore nel modello");
			return false;
		}else if(branch.size() != merge.size()){
			System.out.println("I BRANCH presenti del modello sono in numero diverso rispetto ai MERGE presenti. C'e' un errore nel modello");
			return false;
		}
		else{
			if(controllaInOutModello()){
				if(controlloInOutReciproci()){
					return true;
				} else{
					System.out.println("C'e' un problema nei legami tra in e out di un elemento");
					return false;
				}
			}else{
				System.out.println("Il modello contiene delle uscite o delle entrate non specificate negli elementi.");
				return false;
			}
		}
	}
	
	/**
	 * Metodo che controlla gli In e gli Out reciproci del modello.
	 * @return TRUE se ok, altrimenti FALSE.
	 * @author federicofalcone
	 */
	public boolean controlloInOutReciproci(){
		for(Elemento e: elementi){
			//Per ogni iterazione controllo che l'ingresso e l'uscita reciproci siano corretti
			
			//Inizializzo gli Elementi trovati dagli in e dagli out
			Elemento elemFindOut = null;
			Elemento elemFindIn = null;
			
			//Elementi corrispondenti gli in e gli out
			Elemento auxOut = null;
			Elemento auxIn = null;
			
			//USCITA SINGOLA
			//Controllo che l'uscita non sia diversa da null
			if(e.getUscita() != null){
				//Uscita dell'elemento
				auxOut = e.getUscita();
				elemFindOut = ricercaElemento(auxOut);
				if(elemFindOut.getIngresso() != null){	
					auxIn = elemFindOut.getIngresso();
					elemFindIn = ricercaElemento(auxIn);
					//Se ingresso ed elemento principale non sono uguali
					if(! elemFindIn.equals(e)){
						return false;
					}
				}
			}
			
			//USCITA COME VECTOR
			//Controllo sugli elementi con piu' uscite (Branch e Fork)
			if(e.getUscite() != null){
				for(Elemento elem: e.getUscite()){
					elemFindOut = ricercaElemento(elem);
				
					//Controllo sugli elementi con un solo ingresso
					if(elemFindOut.getIngresso() != null){
						auxIn = elemFindOut.getIngresso();
						elemFindIn = ricercaElemento(auxIn);
						if(! elemFindIn.equals(e)){
							return false;
						}
					}
					
					//Controllo sugli elementi con piu' ingressi (Merge, Join)
					if(elemFindOut.getIngressi() != null){
						/*for(Elemento elemInt: elemFindOut.getIngressi()){
							//auxIn = elemInt;
							elemFindIn = ricercaElemento(elemInt);
							if(! elemFindIn.equals(e)){
								return false;
							}
						}*/
						for(Elemento elemInt: elemFindOut.getIngressi()){
							//auxIn = elemInt;
							elemFindIn = ricercaElemento(elemInt);
							if(elemFindIn.equals(e)){
								break;
							}
						}
						if(! elemFindIn.equals(e)){
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * Metodo che controlla che tutti gli In e gli Out sono presenti nel modello.
	 * @return false se qualche in o out non sono presenti nel modello, true se sono presenti.
	 * @author federicofalcone
	 */
	public boolean controllaInOutModello(){
		//Ciclo sui vari elementi per controllare ingressi e uscite.
		for(Elemento e: elementi){
			//Per ogni iterazione controllo che l'ingresso e l'uscita siano presenti nel Vector di Elementi
			switch (e.getID()) {
			case "AZIONE":
				//Un solo ingresso
				if(!elementoInModello(e.getIngresso()))
					return false;
				//Una sola uscita
				if(!elementoInModello(e.getUscita()))
					return false;
				break;
				
			case "BRANCH":
			case "FORK" :
				//INGRESSO
				//Un solo ingresso
				if(!elementoInModello(e.getIngresso()))
					return false;
				//USCITA
				//Almeno due uscite
				if(e.getUscite().size() < 2)
					return false;
				//+ uscite
				for(Elemento eb: e.getUscite()){
					if(!elementoInModello(eb))
						return false;
				}
				break;
				
			case "MERGE":
			case "JOIN":
				//INGRESSO
				//Almeno due ingressi
				if(e.getIngressi().size() < 2)
					return false;
				//+ ingressi
				for(Elemento eb: e.getIngressi()){
					if(!elementoInModello(eb))
						return false;
				}
				//USCITA
				//Una sola uscita
				if(!elementoInModello(e.getUscita()))
					return false;
				break;
				
			case "START":
				//USCITA
				//Una sola uscita
				if(!elementoInModello(e.getUscita()))
					return false;
				break;
			case "END":
				//INGRESSO
				//Un solo ingresso
				if(!elementoInModello(e.getIngresso()))
					return false;
				break;
				
			default:
				break;
			}
		}
		
		return true;
	}
	
	/**
	 * Verifica se un elemento &egrave contenuto nel modello
	 * @param elem
	 * @return
	 * @author federicofalcone
	 */
	public boolean elementoInModello(Elemento elem){
		for(Elemento e: elementi){
			//Se presente restituisco vero
			if(e.equals(elem))
				return true;
		}
		//Altrimenti restituisco falso
		return false;
	}
	
	/**
	 * Metodo di ricerca nel vector di elementi.
	 * Restituisce l'elemento dato l'ID e il nome
	 * 
	 * @param ID
	 * @param nome
	 * @return L'elemento trovato.
	 * @author federicofalcone
	 */
	public Elemento ricercaElementoInModello(String ID, String nome){
		for(Elemento elem: elementi){
			if(elem.getID().equalsIgnoreCase(ID) && elem.getNome().equalsIgnoreCase(nome)){
				return elem;
			}
		}
		return null;
	}
	
	/**
	 * Metodo di ricerca nel vector di elementi.
	 * Restituisce l'elemento nel Vector di elementi, dato un elemento.
	 * 
	 * @param elem L'elemento da cercare
	 * @return L'elemento trovato.
	 * @author federicofalcone
	 */
	public Elemento ricercaElemento(Elemento elem){
		for(Elemento e: elementi){
			if(e.equals(elem)){
				return e;
			}
		}
		return null;
	}
	
	/**
	 * Restituisce l'indice di elem nel vettore di elementi, se presente, altrimenti -1.
	 * @param elem
	 * @return
	 */
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
		
		
		output.append("NOME MODELLO: " + nome + "\n\n");
		
		for(Elemento elem: elementi){
			output.append(elem.toString() + "\n");
		}

		return output.toString();
	}
	
	/**
	 * Stampa completa del modello
	 * @return
	 */
	public String stampaModello() {
		return this.toString();
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
			if(nome.equalsIgnoreCase(azione.getNome())) return false;
		}
		for(Branch b:branch){
			if(nome.equalsIgnoreCase(b.getNome())) return false;
		}
		for(Merge m:merge){
			if(nome.equalsIgnoreCase(m.getNome())) return false;
		}
		for(Fork f:fork){
			if(nome.equalsIgnoreCase(f.getNome())) return false;
		}
		for(Join j:join){
			if(nome.equalsIgnoreCase(j.getNome())) return false;
		} 
		
		return true;
	}
	
	public void termina(){
		riempiVectorElementi();
	}

	private void riempiVectorElementi() {
		elementi.add(start);
		for(Azione azione:azioni){
			elementi.add(azione);
		}
		for(Branch b:branch){
			elementi.add(b);
		}
		for(Merge m:merge){
			elementi.add(m);
		}
		for(Fork f:fork){
			elementi.add(f);
		}
		for(Join j:join){
			elementi.add(j);
		}
		elementi.add(end);
		
	}
}
