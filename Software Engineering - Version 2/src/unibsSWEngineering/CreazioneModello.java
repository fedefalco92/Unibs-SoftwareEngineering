package unibsSWEngineering;


import unibsSWEngineering.modello.Azione;
import unibsSWEngineering.modello.Branch;
import unibsSWEngineering.modello.Elemento;
import unibsSWEngineering.modello.ElementoMultiUscita;
import unibsSWEngineering.modello.ElementoTerminale;
import unibsSWEngineering.modello.Fork;
import unibsSWEngineering.modello.Join;
import unibsSWEngineering.modello.Merge;
import unibsSWEngineering.modello.Modello;
import unibsSWEngineering.modello.Start;
import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;



/** 
 * Questa classe contiene i menu per gestire la creazione di un nuovo modello. 
 * L'inserimento del modello &egrave forzato in modo che non si possano commettere errori semantici.
 * 
 * @author Alessandro Maffi
 */
public class CreazioneModello {
	
	private final static String [] VOCI_SCELTA_CON_NODO_FINALE = {
		
		"Una nuova azione", 
		"Un nuovo branch (Crea automaticamente i merge associati)", 
		"Un nuovo fork (crea automaticamente il join associato)", 
		"Nodo finale"
		};

	private final static String [] VOCI_SCELTA_CON_MERGE_FINALE = {
		"Una nuova azione", 
		"Un nuovo branch (Crea automaticamente i merge associati)", 
		"Un nuovo fork (crea automaticamente il join associato)", 
		"Merge"
		};
	private final static String [] VOCI_SCELTA_CON_JOIN_FINALE = {
		"Una nuova azione", 
		"Un nuovo branch (Crea automaticamente i merge associati)", 
		"Un nuovo fork (crea automaticamente il join associato)", 
		"Join"
		};
	private final static String [] VOCI_SCELTA_FORZATA = {
		"Una nuova azione", 
		"Un nuovo branch (Crea automaticamente i merge associati)", 
		"Un nuovo fork (crea automaticamente il join associato)", 
		};


		
	
	/**
	 * Metodo principale della classe.
	 * Crea il modello e lancia la gestione del primo elemento.
	 * Verranno ricorsivamente gestiti tutti gli elementi successivi fino all'ultimo.	 * 
	 * @param nomeModello
	 * @return 
	 */
	public static Modello creaModello(String nomeModello){
		Modello modello = new Modello(nomeModello);
		gestisciStart(modello, modello.getStart());
		modello.termina();
		return modello;
		
	}
	
	
	
	
	/**
	 * Gestisce l'inserimento del primo elemento del modello.
	 * Abbiamo concordato che il primo elemento pu&ograve essere solamente un'azione.
	 * @param modello
	 * @param start
	 */
	private static void gestisciStart(Modello modello, Start start) {
		//acquisizione nome --> non e' necessario controllare che il nome non sia gia' stato inserito
		String nomePrimaAzione = InputDati.leggiStringaNonVuota("Inserisci il nome della prima azione > ");
		Azione primaAzione = new Azione (nomePrimaAzione);
		//la prima azione ha come ingresso lo start
		primaAzione.setIngresso(start);
		//metto la prima azione in coda a start
		start.setUscita(primaAzione);
		modello.aggiungiAzione(primaAzione);
		
		gestisciAzione(modello, primaAzione, null);
		
	}
	
	
	/**
	 * Il metodo riceve in ingresso un'azione e permette di proseguire nell'inserimento.
	 * Offre un menu con le varie scelte possibili.
	 * L'inserimento &egrave forzato, non &egrave possibile interrompere o tornare indietro.
	 * 
	 * @param modello
	 * @param azione: &egrave l'azione da cui si vuole proseguire.
	 * @param terminale: serve per gestire l'innestamento.
	 * 					 Se &egrave null, l'azione pu&ograve terminare sul nodo finale,
	 * 					 altrimenti significa che l'azione appartiene a un'alternativa 
	 * 					 di un branch o a un flusso di un fork. In questi casi pu&ograve 
	 * 					 terminare solo su un merge o un join.
	 */
	private static void gestisciAzione(Modello modello, Azione azione, ElementoTerminale terminale) {
		final String TITOLO = "Cosa vuoi inserire in coda all'azione "+ azione.getNome()+ "?";
		String [] vociMenu = sceltaVociMenu(terminale);
		
		
		MyMenu menuAzione = new MyMenu(TITOLO, vociMenu);
		int scelta = menuAzione.scegliSenzaUscita();
		
		switch (scelta)
		{
			case 1:
				Azione nuovaAzione = nuovaAzione(modello, azione);
				gestisciAzione(modello, nuovaAzione, terminale);
				break;
			case 2:
				Branch nuovoBranch = nuovoBranch(modello, azione);
				gestisciBranch(modello, nuovoBranch, terminale);
				break;
			case 3: //AGGIUNTA FORK
				Fork nuovoFork = nuovoFork(modello, azione);
				gestisciFork(modello, nuovoFork, terminale);
				break;
			case 4:
				if(terminale==null){
					azione.aggiungiUscita(modello.getEnd());
					modello.getEnd().setIngresso(azione);
				} else {
					String ID = terminale.getID();
					switch (ID){
					case "MERGE":
						azione.aggiungiUscita((Merge) terminale);
						break;
					case "JOIN":
						azione.aggiungiUscita((Join) terminale);
					}
					terminale.aggiungiIngresso(azione);
				}
				break;
		}
	}


	private static String[] sceltaVociMenu(ElementoTerminale terminale) {
		String [] vociMenu = {};
		if (terminale == null)
			vociMenu = VOCI_SCELTA_CON_NODO_FINALE;
		else {
			String ID = terminale.getID();
			switch (ID){
			case "MERGE":
				vociMenu = VOCI_SCELTA_CON_MERGE_FINALE;
				break;
			case "JOIN":
				vociMenu= VOCI_SCELTA_CON_JOIN_FINALE;
			}
		}
		
		return vociMenu;
	}




	/**
	 * Il metodo riceve in ingresso un branch e permette di proseguire nell'inserimento.
	 * Inizialmente viene chiesto il numero di alternative che si vuole inserire in coda al branch.
	 * Successivamente si chiede quante di queste alternative si riportano al branch stesso:
	 * se il numero &egrave maggiore di 0, viene creato un merge per esplicitare il collegamento:
	 * il merge viene quindi inserito tra il branch e il suo ingresso.
	 * 
	 * Se il numero di alternative rimanenti &egrave maggiore di 1, esse terminano tutte su un merge creato appositamente.
	 * Se rimane una sola alternativa termina sul nodo finale o sul parametro terminale, se diverso da null.
	 * Il parametro terminale &egrave diverso da null solo se il branch si trova all'interno di un alternativa di
	 * un altro branch o nel flusso di un fork.
	 * 
	 * L'inserimento &egrave forzato, non &egrave possibile interrompere o tornare indietro.
	 * 
	 * @param modello
	 * @param branch
	 * @param terminale: serve per gestire l'innestamento.
	 */
	private static void gestisciBranch(Modello modello, Branch branch, ElementoTerminale terminale) {
		
		int numeroAlternative = InputDati.leggiInteroConMinimo("Quante alternative vuoi inserire in coda al branch " + branch.getNome() + "? > ",
				2);
		int numeroWhile = InputDati.leggiIntero("Quante terminano sul branch stesso? "
				+ "(Verra' creato un merge per esplicitare il collegamento) > ", 0, numeroAlternative - 1);
		int rimanenti = numeroAlternative - numeroWhile;
		
		if(numeroWhile > 0){
			
			String nomeMergePrecedente = acquisizioneNome(modello, 
					"Inserisci il nome del merge che verra' posto prima del branch > ");
			Merge mergePrecedente = new Merge(nomeMergePrecedente);
			Elemento ingressoDelBranch = branch.getIngresso();
			//innesto il merge prima del branch
			
			//1) l'ingresso del branch ora deve avere in coda il merge al posto del branch.
			//ATTENZIONE L'INGRESSO DEL BRANCH POTREBBE ESSERE QUALCOSA CON PIU' USCITE.
			//in questo caso non basta fare "aggiungiUscita" ma bisogna eliminare il branch dalle uscite!!
			//questo succede se l'ingresso del branch e' - un branch.
			//											 - un fork.
			
			//scansiono con uno switch.
			String ID = ingressoDelBranch.getID();
			switch(ID){
				case "FORK":
				case "BRANCH":
					((ElementoMultiUscita) ingressoDelBranch).eliminaUscita(branch); //non faccio break;
				default:
					ingressoDelBranch.aggiungiUscita(mergePrecedente);
			}
			//2) il merge deve avere l'ingresso del branch come ingresso 
			mergePrecedente.aggiungiIngresso(branch.getIngresso()); 
			//3) il merge deve avere il branch come uscita
			mergePrecedente.aggiungiUscita(branch); 
			//4) il branch deve avere il merge come ingresso.
			branch.setIngresso(mergePrecedente); 
		
			modello.aggiungiMerge(mergePrecedente);
			
			//avvio le alternative (che potraanno terminare solo sul merge appena creato)
			for(int i = 1; i <= numeroWhile; i++){
				
				String TITOLO = "BRANCH " + 
										branch.getNome() +
										" Alternativa " + i + 
										" - TERMINA SUL MERGE " + mergePrecedente.getNome() +
										"\nCosa vuoi inserire come primo elemento? > ";
				String [] vociMenu = VOCI_SCELTA_FORZATA;
				MyMenu menuBranch = new MyMenu(TITOLO, vociMenu);
				int scelta = menuBranch.scegliSenzaUscita();
				
				switch (scelta)
				{
					case 1:
						Azione nuovaAzione = nuovaAzione(modello, branch);
						gestisciAzione(modello, nuovaAzione, mergePrecedente);
						break;
					case 2:
						Branch nuovoBranch = nuovoBranch(modello, branch);
						gestisciBranch(modello, nuovoBranch, mergePrecedente);
						break;
					case 3: //AGGIUNTA FORK
						Fork nuovoFork = nuovoFork(modello, branch);
						gestisciFork(modello, nuovoFork, mergePrecedente);
						break;
				}
				
			}
		}
		
		if (rimanenti > 1){
			String nomeMergeFinale = acquisizioneNome(modello, 
					"Inserisci il nome del merge su cui si congiungeranno le alternative > ");
			Merge mergeFinaleDelBranchCorrente = new Merge(nomeMergeFinale); //su questo merge terminano tutte le alternative,
															//dopo di che si continua da esso
	
			
			//avvio le alternative (che potraanno terminare solo sul merge appena creato)
			for(int i = numeroWhile + 1; i <= numeroAlternative; i++){
				
				String TITOLO = "BRANCH " + 
										branch.getNome() +
										" Alternativa " + i + " - TERMINA SUL MERGE" + mergeFinaleDelBranchCorrente.getNome() +
										"\nCosa vuoi inserire come primo elemento? > ";
				String [] vociMenu = VOCI_SCELTA_FORZATA;
				MyMenu menuBranch = new MyMenu(TITOLO, vociMenu);
				int scelta = menuBranch.scegliSenzaUscita();
				
				switch (scelta)
				{
					case 1:
						Azione nuovaAzione = nuovaAzione(modello, branch);
						gestisciAzione(modello, nuovaAzione, mergeFinaleDelBranchCorrente);
						break;
					case 2:
						Branch nuovoBranch = nuovoBranch(modello, branch);
						gestisciBranch(modello, nuovoBranch, mergeFinaleDelBranchCorrente);
						break;
					case 3: //AGGIUNTA FORK
						Fork nuovoFork = nuovoFork(modello, branch);
						gestisciFork(modello, nuovoFork, mergeFinaleDelBranchCorrente);
						break;
				}					
			}
			
			//aggiungo il merge al modello
			modello.aggiungiMerge(mergeFinaleDelBranchCorrente);
			//passo alla gestione del merge appena inserito
			gestisciMerge(modello, mergeFinaleDelBranchCorrente, terminale);
		} else { //IN QUESTO CASO C'E' SOLO UN'ALTERNATIVA CHE PROSEGUE...PU0' TERMINARE SUL NODO FINALE O SULL'ELEMENTO TERMINALE
			String TITOLO = "BRANCH " + 
					branch.getNome() +
					" Alternativa " + numeroAlternative + //oppure (numeroWhile + 1)
					"\nCosa vuoi inserire come primo elemento? > ";
			String [] vociMenu = sceltaVociMenu(terminale);
			MyMenu menuBranch = new MyMenu(TITOLO, vociMenu);
			int scelta = menuBranch.scegliSenzaUscita();
			
			switch (scelta)
			{
				case 1:
					Azione nuovaAzione = nuovaAzione(modello, branch);
					gestisciAzione(modello, nuovaAzione, terminale);
					break;
				case 2:
					Branch nuovoBranch = nuovoBranch(modello, branch);
					gestisciBranch(modello, nuovoBranch, terminale);
					break;
				case 3: //AGGIUNTA FORK
					Fork nuovoFork = nuovoFork(modello, branch);
					gestisciFork(modello, nuovoFork, terminale);
					break;
				case 4:
					if (terminale==null){
						branch.aggiungiUscita(modello.getEnd());
						modello.getEnd().setIngresso(branch);
					} else {String ID = terminale.getID();
					switch (ID){
					case "MERGE":
						branch.aggiungiUscita((Merge) terminale);
						break;
					case "JOIN":
						branch.aggiungiUscita((Join) terminale);
					}
						terminale.aggiungiIngresso(branch);
					}
					break;
			}					
			
		}
	}
	
	/**
	 * Il metodo gestisce il fork che riceve in ingresso. 
	 * All'inizio chiede l'inserimento del numero di flussi che si diramano dal fork.
	 * Il primo elemento di ogni flusso riceve in ingresso il Join associato al Fork in modo che 
	 * possa terminare solo su questo e non sul nodo finale.
	 * 
	 * Il parametro terminale viene passato al Join associato al fork per gestire l'innestamento.
	 * 
	 * @param modello
	 * @param fork
	 * @param terminale: serve per gestire l'innestamento.
	 */

	private static void gestisciFork(Modello modello, Fork fork, ElementoTerminale terminale) {
		int numeroFlussi = InputDati.leggiInteroConMinimo("Quanti flussi paralleli vuoi inserire in coda al fork " + fork.getNome() + "? > ",
				2);
		
		//avvio la creazione dei flussi (che potraanno terminare solo sul join appena creato)
		for(int i = 1; i <= numeroFlussi; i++){
			
			String TITOLO = "FORK " + 
									fork.getNome() +
									" FLUSSO " + i + 
									"\nCosa vuoi inserire come primo elemento? > ";
			String [] vociMenu = VOCI_SCELTA_FORZATA;
			MyMenu menuBranch = new MyMenu(TITOLO, vociMenu);
			int scelta = menuBranch.scegliSenzaUscita();
			
			switch (scelta)
			{
				case 1:
					Azione nuovaAzione = nuovaAzione(modello, fork);
					gestisciAzione(modello, nuovaAzione, fork.getJoinAssociato());
					break;
				case 2:
					Branch nuovoBranch = nuovoBranch(modello, fork);
					gestisciBranch(modello, nuovoBranch, fork.getJoinAssociato());
					break;
				case 3: //AGGIUNTA FORK
					Fork nuovoFork = nuovoFork(modello, fork);
					gestisciFork(modello, nuovoFork, fork.getJoinAssociato());
					break;
			}					
		}
		//una volta terminato l'inserimento dei flussi si continua con il modello a partire dal join!!
		gestisciJoin(modello,  fork.getJoinAssociato(), terminale);
	
	
	}


	/**
	 * Il metodo permette di gestire il merge che riceve in ingresso.
	 * A seconda che il parametro "terminale" sia o meno null, il merge pu&ograve o meno terminare sul nodo finale.
	 * @param modello
	 * @param merge
	 * @param terminale: serve per gestire l'innestamento.
	 */
	private static void gestisciMerge(Modello modello, Merge merge, ElementoTerminale terminale) {
		final String TITOLO = "Cosa vuoi inserire in coda al merge "+ merge.getNome()+ "? > ";
		String [] vociMenu = sceltaVociMenu(terminale);
		MyMenu menuMerge = new MyMenu(TITOLO, vociMenu);
		int scelta = menuMerge.scegliSenzaUscita();
		
	
		switch (scelta)
		{
			case 1:
				Azione nuovaAzione = nuovaAzione(modello, merge);
				gestisciAzione(modello, nuovaAzione, terminale);
				break;
			case 2:
				Branch nuovoBranch = nuovoBranch(modello, merge);
				gestisciBranch(modello, nuovoBranch, terminale);
				break;
			case 3: //AGGIUNTA FORK
				Fork nuovoFork = nuovoFork(modello, merge);
				gestisciFork(modello, nuovoFork, terminale);
				break;
			case 4:
				if (terminale==null){
					merge.aggiungiUscita(modello.getEnd());
					modello.getEnd().setIngresso(merge);
				} else {String ID = terminale.getID();
				switch (ID){
				case "MERGE":
					merge.aggiungiUscita((Merge) terminale);
					break;
				case "JOIN":
					merge.aggiungiUscita((Join) terminale);
				}
					terminale.aggiungiIngresso(merge);
				}
				break;
			
			default:
				/*Non entra mai qui*/
		}
		
	}

	/**
	 * Il metodo permette di proseguire l'inserimento a partire dal Join che riceve come parametro.
	 * 
	 * 
	 * @param modello
	 * @param join
	 * @param terminale: serve per gestire l'innestamento.
	 */
	private static void gestisciJoin(Modello modello, Join join, ElementoTerminale terminale) {
		final String TITOLO = "Cosa vuoi inserire in coda al join "+ join.getNome()+ "? > ";
		String [] vociMenu = sceltaVociMenu(terminale);
		MyMenu menuJoin = new MyMenu(TITOLO, vociMenu);
		int scelta = menuJoin.scegliSenzaUscita();
		

		switch (scelta)
		{
			case 0: 
				return;
			case 1:
				Azione nuovaAzione = nuovaAzione(modello, join);
				gestisciAzione(modello, nuovaAzione, terminale);
				break;
			case 2:
				Branch nuovoBranch = nuovoBranch(modello, join);
				gestisciBranch(modello, nuovoBranch, terminale);
				break;
			case 3: //AGGIUNTA FORK
				Fork nuovoFork = nuovoFork(modello, join);
				gestisciFork(modello, nuovoFork, terminale);
				break;
			case 4:
				if (terminale==null){
					join.aggiungiUscita(modello.getEnd());
					modello.getEnd().setIngresso(join);
				} else {String ID = terminale.getID();
				switch (ID){
				case "MERGE":
					join.aggiungiUscita((Merge) terminale);
					break;
				case "JOIN":
					join.aggiungiUscita((Join) terminale);
				}
					terminale.aggiungiIngresso(join);
				}
				break;
			
			default:
				/*Non entra mai qui*/
		}
	}
	
	
	/**
	 * Verifica che il nome inserito non sia gi&agrave stato utilizzato.
	 * @param modello
	 * @param string
	 * @return
	 */
	private static String acquisizioneNome(Modello modello, String string) {
		boolean finito=false;
		String nome;
		do {
			nome=InputDati.leggiStringaNonVuota(string);
			finito=modello.nomeOK(nome);
			if(!finito)
				System.out.println("ATTENZIONE! NOME GIA' UTILIZZATO! RIPROVA > ");
		} while (!finito);
		return nome;
	}
	
	/**
	 * Inserisce una nuova azione nel modello in coda a ingresso.
	 * @param modello
	 * @param ingresso: elemento precedente alla nuova azione.
	 * @return: l'azione creata.
	 */
	private static Azione nuovaAzione(Modello modello, Elemento ingresso){
		//l'acquisizione nome va fatta in modo che non ci siano doppioni
		String nomeAzione = acquisizioneNome(modello, "Inserisci il nome della nuova azione > ");
		Azione nuovaAzione = new Azione(nomeAzione);
		ingresso.aggiungiUscita(nuovaAzione);
		nuovaAzione.setIngresso(ingresso);
		modello.aggiungiAzione(nuovaAzione);
		return nuovaAzione;
	}
	/**
	 * Inserisce un nuovo branch nel modello in coda a ingresso.
	 * @param modello
	 * @param ingresso: elemento precedente al nuovo branch.
	 * @return il branch creato.
	 */
	private static Branch nuovoBranch(Modello modello, Elemento ingresso) {
		
		String nomeBranch = acquisizioneNome(modello, "Inserisci il nome del nuovo branch > ");
		Branch nuovoBranch = new Branch(nomeBranch);
		ingresso.aggiungiUscita(nuovoBranch);
		nuovoBranch.setIngresso(ingresso);
		modello.aggiungiBranch(nuovoBranch);
	
		return nuovoBranch;
		
	}
	/**
	 * Inserisce un nuovo fork nel modello e il join associato.
	 * @param modello
	 * @param ingresso: elemento precedente al nuovo fork.
	 * @return il fork creato.
	 */
	private static Fork nuovoFork(Modello modello, Elemento ingresso) {
		String nomeFork = acquisizioneNome(modello, 
				"Inserisci il nome del nuovo fork > ");
		Fork nuovoFork = new Fork(nomeFork);
		modello.aggiungiFork(nuovoFork);
		
		String nomeJoin = acquisizioneNome(modello, 
				"Inserisci il nome del join sul quale termineranno i flussi > ");
		Join nuovoJoin = new Join(nomeJoin);
		modello.aggiungiJoin(nuovoJoin);
		
		nuovoFork.setJoinAssociato(nuovoJoin);
		nuovoJoin.setForkAssociato(nuovoFork);
		
		ingresso.aggiungiUscita(nuovoFork);
		nuovoFork.setIngresso(ingresso);
		
		
		
		return nuovoFork;
		
	}
}
