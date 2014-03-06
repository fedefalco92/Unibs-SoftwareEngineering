import java.util.Vector;
import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;



/* da mettere in javadoc una volta ultimata la classe
 * 
 * questa classe conterra' un menu per gestire la creazione di un nuovo modello
 * permettera' di
 * -iniziare la creazione
 * -->ci vorra' un sottomenu per scegliere cosa aggiungere..--->inserimento guidato?
 * -visualizzare il modello inqualsiasi momento
 * -terminare la creazione e quindi salvare il modello
 * --come testo
 * --come oggetto
 * in teoria bisognerebbe poter terminare solo se il modello e' corretto..
 * 
 * @author Maffi
 */
public class CreazioneModelloCopia {
	
	private final static String [] VOCI_SCELTA_NODO_FINALE = {
		
		"Una nuova azione", 
		"Un nuovo branch (Crea automaticamente i merge associati)", 
		"Un nuovo fork (crea automaticamente il join associato)", 
		"Nodo finale"
		};

	private final static String [] VOCI_SCELTA_MERGE_FINALE = {
		"Una nuova azione", 
		"Un nuovo branch (Crea automaticamente i merge associati)", 
		"Un nuovo fork (crea automaticamente il join associato)", 
		"Merge"
		};
	private final static String [] VOCI_SCELTA_PRIMO_ELEMENTO = {
		"Una nuova azione", 
		"Un nuovo branch (Crea automaticamente i merge associati)", 
		"Un nuovo fork (crea automaticamente il join associato)", 
		};
	
	//tolgo l'attributo
	//private static Modello modello;
	
	/**
	 * Metodo principale della classe.
	 * Crea il modello e lancia il menu creazione.
	 * 
	 * @param nomeModello
	 * @return 
	 */
	public static Modello creaModello(String nomeModello){
		Modello modello = new Modello(nomeModello);
			
		boolean fineCreazione = false;
		do {
			fineCreazione = menuPrincipaleCreazione(modello);
		} while (!fineCreazione);

		return modello;
		//la prossima istruzione sara' da togliere
		//System.out.println(modello);	
	}
	
	//PER ORA PER COMODITA' METTO QUA I MENU
	//SE VOLETE SI POTRANNO SPOSTARE NELLA CLASSE MenuClass
	/**
	 * Menu principale (boolean)
	 * Permette di
	 * 1) iniziare la creazione
	 * 		(si uscir&agrave da questo metodo solo a creazione ultimata)
	 * 2) visualizzare il modello
	 * 3) salvare il modello
	 * 
	 * oppure uscire senza salvare
	 * l'opzione 1 e 3 si appoggiano su altri menu void
	 * 
	 * @return
	 */
	private static boolean menuPrincipaleCreazione(Modello modello){
		final String TITOLO = "MENU CREAZIONE MODELLO " + modello.getNome();;
		final String [] VOCI = {"Inizia inserimento", "Visualizza il modello", "Salva il modello ed esci"};
		MyMenu menuCreazione = new MyMenu(TITOLO, VOCI);
		//nuova funzione-permette di cambiare VOCE_USCITA 
		menuCreazione.setVoceUscita("0\tTorna al menu principale (Tutte le modifiche non salvate andranno perse)");
		int scelta = menuCreazione.scegli();
		
		switch (scelta)
		{
			case 0: 
				return InputDati.yesOrNo("Vuoi veramente uscire?");
			case 1:
					//CODICE PRECEDENTE CON PRIMA AZIONE OBBLIGATORIA
					/*
					//inserimento prima azione obbligatorio?
					//acquisizione nome
					String nomePrimaAzione = InputDati.leggiStringa("Inserisci il nome della prima azione > ");
					Azione primaAzione = new Azione (nomePrimaAzione);
					//la prima azione ha come ingresso lo start
					primaAzione.setIngresso(modello.getStart());
					//metto la prima azione in coda a start
					modello.setPrimaAzione(primaAzione);
					modello.aggiungiAzione(primaAzione);
					
					modello.setUltimaModifica(modello.getAzioni().firstElement());
					//in alternativa: modello.setUltimaModifica(primaAzione);
					continuaInserimento(modello);
					//a questo punto ci potrebbero essere ancora dei merge non ultimati
					//metto un controllo per verificare che il modello sia corrett
					//TODO
					 * 
					 */
				/*
				while(!modello.completo()){
					continuaInserimento(modello, modello.getStart());
				}
				*/
				
				gestisciStart(modello, modello.getStart());
				modello.termina();
				
				break;
			case 2:
				visualizzaModello(modello);
				break;
			case 3:
				//a questo punto aprirei un altro menu che chiede di salvare come testo o come oggetto...
				//il menu dovra' essere aperto solo se il modello e' corretto!
				//prima vediamo cosa si riesce a fare con la classe CostruzioneModello...
				
				salvaModello(modello);
				
				break;
			default:
				/*Non entra mai qui*/
		}
		
		return false;
	}
	
	private static void salvaModello(Modello _modello) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Il metodo mette in output il modello sfruttando il metodo toString
	 * Si potr&agrave migliorare in futuro.
	 * 
	 * per ora &egrave private, sar&agrave public se i menu verranno esportati nella classe menuClass
	 */
	private static void visualizzaModello(Modello _modello) {
		if(_modello.completo())
			System.out.println(_modello.stampaModello());
		else
			System.out.println("> Modello non ancora creato! <");
		//precedentemente era System.out.println(modello);
		//(Sfruttava il toString())
	}

	
	private static boolean menuInserimento(Modello modello, Elemento elemento){
		final String TITOLO = "Cosa vuoi inserire in coda?";
		final String [] VOCI = {"Una nuova azione", "Un nuovo branch", "Un nuovo merge"};
		MyMenu menuCreazione = new MyMenu(TITOLO, VOCI);
		//nuova funzione-permette di cambiare VOCE_USCITA 
		menuCreazione.setVoceUscita("0\tTorna al menu creazione (Tutte le modifiche non salvate andranno perse)");
		int scelta = menuCreazione.scegli();
		
		switch (scelta)
		{
			case 0: 
				return InputDati.yesOrNo("Vuoi veramente uscire?");
			case 1:
				
				break;
			case 2:
				//si puo' migliorare
				System.out.println(modello);
				break;
			case 3:
				//a questo punto aprirei un altro menu che chiede di salvare come testo o come oggetto...
				//il menu dovra' essere aperto solo se il modello e' corretto!
				//prima vediamo cosa si riesce a fare con la classe CostruzioneModello...
				break;
			default:
				//Non entra mai qui
		}
		
		return false;
	}
	
	/**
	 * Permette di riprendere l'inserimento dopo che lo si e' interrotto per visualizzare il modello.
	 * Non permette di riprendere l'inserimento se l'inserimento &egrave gi&agrave stato ultimato.
	 * @param elemento TODO
	 */
	private static void continuaInserimento(Modello modello, Elemento elemento) {

		String tipo = elemento.getID();
		switch(tipo){
			case "START":
				gestisciStart(modello, (Start) elemento);
				break;
			case "AZIONE":
				gestisciAzione(modello, (Azione) elemento, null);				
				break;
			case "BRANCH":
				gestisciBranch(modello, (Branch) elemento, null);
				break;
			case "MERGE":
				gestisciMerge(modello, (Merge) elemento, null);
				break;
			case "FORK":
				gestisciFork(modello, (Fork) elemento, null);
				break;
			case "JOIN":
				gestisciJoin(modello, (Join) elemento);
				break;
			default:
				//
			
		}
		
		
	}
	
	private static void gestisciStart(Modello modello, Start start) {
		//inserimento prima azione obbligatorio?
		//acquisizione nome
		String nomePrimaAzione = InputDati.leggiStringa("Inserisci il nome della prima azione > ");
		Azione primaAzione = new Azione (nomePrimaAzione);
		//la prima azione ha come ingresso lo start
		primaAzione.setIngresso(start);
		//metto la prima azione in coda a start
		start.setUscita(primaAzione);
		modello.aggiungiAzione(primaAzione);
		
		gestisciAzione(modello, primaAzione, null);
		
	}

	private static void gestisciJoin(Modello modello, Join join) {
		final String TITOLO = "Cosa vuoi inserire in coda al join "+ join.getNome()+ "?";
		final String [] VOCI = {
				"Una nuova azione", 
				"Un nuovo branch", 
				"Un nuovo merge", 
				"un merge esistente", 
				"un nuovo fork (crea automaticamente il join associato)", 
				"nodo finale"
				};
		MyMenu menuAzione = new MyMenu(TITOLO, VOCI);
		//RENDO "IMPOSSIBILE" O MEGLIO "INVISIBILE" L'USCITA PER EVITARE DI LASCIARE 
		//IL MODELLO INCOMPLETO E NON POTER PIU' RIPRENDERE L'INSERIMENTO
		menuAzione.setVoceUscita("");
		int scelta = menuAzione.scegli();
		
		switch (scelta)
		{
			case 0: 
				return;
			case 1:
				//l'acquisizione nome va fatta in modo che non ci siano doppioni
				String nomeAzione = acquisizioneNome(modello, "Inserisci il nome della nuova azione > ");
				Azione nuovaAzione = new Azione(nomeAzione);
				join.aggiungiUscita(nuovaAzione);
				nuovaAzione.setIngresso(join);
				modello.aggiungiAzione(nuovaAzione);
				modello.setUltimaModifica(nuovaAzione);
				continuaInserimento(modello, null);
				break;
			case 2:
				String nomeBranch = acquisizioneNome(modello, "Inserisci il nome del nuovo branch > ");
				Branch nuovoBranch = new Branch(nomeBranch);
				join.aggiungiUscita(nuovoBranch);
				nuovoBranch.setIngresso(join);
				modello.aggiungiBranch(nuovoBranch);
				modello.setUltimaModifica(nuovoBranch);
				continuaInserimento(modello, null);
				
				break;
			case 3:
				String nomeMerge = acquisizioneNome(modello, "Inserisci il nome del nuovo merge > ");
				Merge nuovoMerge = new Merge(nomeMerge);
				join.aggiungiUscita(nuovoMerge);
				nuovoMerge.aggiungiIngresso(join);
				modello.aggiungiMerge(nuovoMerge);
				//l'utilita' della prossima istruzione si vedra'
				//modello.aggiungiMergeIncompleto(nuovoMerge);
				modello.setUltimaModifica(nuovoMerge);
				continuaInserimento(modello, null);
				break;
			case 4:
				if(modello.getMerge().isEmpty()) {
					System.out.println("ATTENZIONE! Nessun Merge esistente\n");
					continuaInserimento(modello, null);
					break;
				} else {
					Merge mergeEsistente= sceltaMerge(modello.getMerge());
					mergeEsistente.aggiungiIngresso(join);
					join.aggiungiUscita(mergeEsistente);
					
					//a questo punto devo decidere dove mandare il programma!!
					//probabilmente non e' necessario mandarlo da nessuna parte dato che se sono giunto qui
					//ho quasi sicuramente un branch aperto e quindi riprendera' il menu del branch.
					//ci devo pensare ancora un pochino
				}
				
				break;
			case 5: //AGGIUNTA FORK
				String nomeFork = acquisizioneNome(modello, "Inserisci il nome del nuovo fork > ");
				Fork nuovoFork = new Fork(nomeFork);
				String nomeJoin = acquisizioneNome(modello, "Inserisci il nome del join associato > ");
				Join nuovoJoin = new Join(nomeJoin);
				
				nuovoFork.setJoinAssociato(nuovoJoin);
				nuovoJoin.setForkAssociato(nuovoFork);
				
				join.aggiungiUscita(nuovoFork);
				nuovoFork.setIngresso(join);
				modello.aggiungiFork(nuovoFork);
				modello.aggiungiJoin(nuovoJoin);
				modello.setUltimaModifica(nuovoFork);
				continuaInserimento(modello, null);
				break;
			case 6:
				join.aggiungiUscita(modello.getEnd());
				modello.setUltimoElemento(join);
				modello.setUltimaModifica(null);
				break;
		
			default:
				/*Non entra mai qui*/
		}
	}

	private static void gestisciFork(Modello modello, Fork fork, Merge finale) {
		int numeroFlussi = InputDati.leggiInteroConMinimo("Quanti flussi paralleli vuoi inserire in coda al fork " + fork.getNome() + "?",
				2);
		for (int i = 1; i <= numeroFlussi; i++){
			String nomeFlusso = InputDati.leggiStringa("Inserisci il nome del Fusso " + i + " > ");
			
			Flusso flusso = new Flusso(nomeFlusso, fork);
			
			
			fork.aggiungiFlusso(flusso);
			fork.getJoinAssociato().aggiungiFlussoIN(flusso);
		}
		
		//una volta terminato l'inserimento dei flussi si continua con il modello a partire dal join!!
		modello.setUltimaModifica(fork.getJoinAssociato());
		continuaInserimento(modello, null);
		
		
	}

	/**
	 * Il metodo offre un menu in cui si richiede l'inserimento dell'uscita di un merge.
	 * la completezza degli ingressi verra' gestita altrove.
	 * 
	 * Il menu permette la visualizzazione del modello allo stato attuale
	 * @param merge
	 * @param finale TODO
	 */
	private static void gestisciMerge(Modello modello, Merge merge, Merge finale) {
		final String TITOLO = "Cosa vuoi inserire in coda al merge "+ merge.getNome()+ "?";
		String [] vociMenu;
		if (finale == null)
			vociMenu = VOCI_SCELTA_NODO_FINALE;
		else 
			vociMenu = VOCI_SCELTA_MERGE_FINALE;
		MyMenu menuMerge = new MyMenu(TITOLO, vociMenu);
		int scelta = menuMerge.scegliSenzaUscita();
		

		switch (scelta)
		{
			case 0: 
				return;
			case 1:
				Azione nuovaAzione = nuovaAzione(modello, merge);
				gestisciAzione(modello, nuovaAzione, null);
				break;
			case 2:
				Branch nuovoBranch = nuovoBranch(modello, merge);
				gestisciBranch(modello, nuovoBranch, null);
				break;
			case 3: //AGGIUNTA FORK
				Fork nuovoFork = nuovoFork(modello, merge);
				gestisciFork(modello, nuovoFork, null);
				break;
			case 4:
				if (finale==null){
					merge.aggiungiUscita(modello.getEnd());
					modello.getEnd().setIngresso(merge);
				} else {
					merge.aggiungiUscita(finale);
					finale.aggiungiIngresso(merge);
				}
				break;
			
			default:
				/*Non entra mai qui*/
		}
		
	}
	
	
	//Merge finale serve se ricevuto dall'ingresso e viene rimandato sul merge finale o sull'alternativa libera.
	private static void gestisciBranch(Modello modello, Branch branch, Merge finale) {
		
		int numeroAlternative = InputDati.leggiInteroConMinimo("Quante alternative vuoi inserire in coda al branch " + branch.getNome() + "? > ",
				2);
		int numeroWhile = InputDati.leggiIntero("Quante terminano sul branch stesso? "
				+ "(Verra' creato un merge per esplicitare il collegamento) > ", 0, numeroAlternative - 1);
		int rimanenti = numeroAlternative - numeroWhile;
		
		if(numeroWhile > 0){
			
			String nomeMergePrecedente = acquisizioneNome(modello, 
					"Inserisci il nome del merge che verra' posto prima del branch > ");
			Merge mergePrecedente = new Merge(nomeMergePrecedente);
	
			//inserisco il merge prima del branch
			mergePrecedente.aggiungiIngresso(branch.getIngresso());
			mergePrecedente.aggiungiUscita(branch);
			branch.setIngresso(mergePrecedente);
			modello.aggiungiMerge(mergePrecedente);
			
			//avvio le alternative (che potraanno terminare solo sul merge appena creato)
			for(int i = 1; i <= numeroWhile; i++){
				
				String TITOLO = "BRANCH " + 
										branch.getNome() +
										" Alternativa " + i + 
										" - TERMINA SUL MERGE " + mergePrecedente.getNome() +
										"\nCosa vuoi inserire come primo elemento? > ";
				String [] vociMenu = VOCI_SCELTA_PRIMO_ELEMENTO;
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
			Merge mergeFinale = new Merge(nomeMergeFinale); //su questo merge terminano tutte le alternative,
															//dopo di che si continua da esso

			
			//avvio le alternative (che potraanno terminare solo sul merge appena creato)
			for(int i = numeroWhile + 1; i <= numeroAlternative; i++){
				
				String TITOLO = "BRANCH " + 
										branch.getNome() +
										" Alternativa " + i + 
										"\nCosa vuoi inserire come primo elemento? > ";
				String [] vociMenu = VOCI_SCELTA_PRIMO_ELEMENTO;
				MyMenu menuBranch = new MyMenu(TITOLO, vociMenu);
				int scelta = menuBranch.scegliSenzaUscita();
				
				switch (scelta)
				{
					case 1:
						Azione nuovaAzione = nuovaAzione(modello, branch);
						gestisciAzione(modello, nuovaAzione, mergeFinale);
						break;
					case 2:
						Branch nuovoBranch = nuovoBranch(modello, branch);
						gestisciBranch(modello, nuovoBranch, mergeFinale);
						break;
					case 3: //AGGIUNTA FORK
						Fork nuovoFork = nuovoFork(modello, branch);
						gestisciFork(modello, nuovoFork, mergeFinale);
						break;
				}					
			}
			gestisciMerge(modello, mergeFinale, finale);
		} else {
			String TITOLO = "BRANCH " + 
					branch.getNome() +
					" Alternativa " + numeroAlternative + //oppure (numeroWhile + 1)
					"\nCosa vuoi inserire come primo elemento? > ";
			String [] vociMenu = VOCI_SCELTA_PRIMO_ELEMENTO;
			MyMenu menuBranch = new MyMenu(TITOLO, vociMenu);
			int scelta = menuBranch.scegliSenzaUscita();
			
			switch (scelta)
			{
				case 1:
					Azione nuovaAzione = nuovaAzione(modello, branch);
					gestisciAzione(modello, nuovaAzione, finale);
					break;
				case 2:
					Branch nuovoBranch = nuovoBranch(modello, branch);
					gestisciBranch(modello, nuovoBranch, finale);
					break;
				case 3: //AGGIUNTA FORK
					Fork nuovoFork = nuovoFork(modello, branch);
					gestisciFork(modello, nuovoFork, finale);
					break;
			}					
			
		}
	}
		
	
		/*
		//ci sono almeno due alternative da inserire obbligatoriamente.
		int i=1;
		
		boolean stop=false;
		while(!stop){
			final String TITOLO = "Menu creazione del branch "+ branch.getNome() +" - ALTERNATIVA " + i +
					"\nCosa vuoi inserire?";
			final String [] VOCI = {
					"Una nuova azione", 
					"Un nuovo branch", 
					"Un nuovo merge", 
					"Un merge esistente", 
					"un nuovo fork (crea automaticamente il join associato)", 
					"nodo finale"
					};
			MyMenu menuAzione = new MyMenu(TITOLO, VOCI);
			//RENDO "IMPOSSIBILE" O MEGLIO "INVISIBILE" L'USCITA PER EVITARE DI LASCIARE 
			//IL MODELLO INCOMPLETO E NON POTER PIU' RIPRENDERE L'INSERIMENTO
			menuAzione.setVoceUscita("");
			//menuAzione.setVoceUscita("0\tTorna al menu creazione (potrai riprendere l'inserimento in seguito)");
			int scelta = menuAzione.scegli();
			
			switch (scelta)
			{
				case 0: 
					return;
				case 1:
					//l'acquisizione nome va fatta in modo che non ci siano doppioni
					String nomeAzione = acquisizioneNome(modello, "Inserisci il nome della nuova azione > ");
					Azione nuovaAzione = new Azione(nomeAzione);
					branch.aggiungiUscita(nuovaAzione);
					nuovaAzione.setIngresso(branch);
					modello.aggiungiAzione(nuovaAzione);
					modello.setUltimaModifica(nuovaAzione);
					continuaInserimento(modello, null);
					break;
		
				case 2:
					String nomeBranch = acquisizioneNome(modello, "Inserisci il nome del nuovo branch > ");
					Branch nuovoBranch = new Branch(nomeBranch);
					branch.aggiungiUscita(nuovoBranch);
					nuovoBranch.setIngresso(branch);
					modello.aggiungiBranch(nuovoBranch);
					modello.setUltimaModifica(nuovoBranch);
					continuaInserimento(modello, null);
					
					break;
			
				case 3:
					String nomeMerge = acquisizioneNome(modello, "Inserisci il nome del nuovo merge> ");
					Merge nuovoMerge = new Merge(nomeMerge);
					branch.aggiungiUscita(nuovoMerge);
					nuovoMerge.aggiungiIngresso(branch);
					modello.aggiungiMerge(nuovoMerge);
					//modello.aggiungiMergeIncompleto(nuovoMerge);
					modello.setUltimaModifica(nuovoMerge);
					continuaInserimento(modello, null);
					break;
				case 4:if(modello.getMerge().isEmpty()) {
					System.out.println("ATTENZIONE! Nessun Merge esistente\n");
					continuaInserimento(modello, null);
				} else {
					System.out.println("A quale merge vuoi collegarti?");
					Merge mergeEsistente= sceltaMerge(modello.getMerge());
					mergeEsistente.aggiungiIngresso(branch);
					branch.aggiungiUscita(mergeEsistente);
					
					//a questo punto devo decidere dove mandare il programma!!
					//probabilmente non e' necessario mandarlo da nessuna parte dato che se sono giunto qui
					//ho quasi sicuramente un branch aperto e quindi riprendera' il menu del branch.
					//ci devo pensare ancora un pochino
				}
					break;
				case 5: //AGGIUNTA FORK
					String nomeFork = acquisizioneNome(modello, "Inserisci il nome del nuovo fork > ");
					Fork nuovoFork = new Fork(nomeFork);
					String nomeJoin = acquisizioneNome(modello, "Inserisci il nome del join associato > ");
					Join nuovoJoin = new Join(nomeJoin);
					
					nuovoFork.setJoinAssociato(nuovoJoin);
					nuovoJoin.setForkAssociato(nuovoFork);
					
					branch.aggiungiUscita(nuovoFork);
					nuovoFork.setIngresso(branch);
					modello.aggiungiFork(nuovoFork);
					modello.aggiungiJoin(nuovoJoin);
					modello.setUltimaModifica(nuovoFork);
					continuaInserimento(modello, null);
					break;
				case 6:
					branch.aggiungiUscita(modello.getEnd());
					modello.setUltimoElemento(branch);
					modello.setUltimaModifica(null);
					break;
				
				default:
					//Non entra mai qui
			}
			
			i++;
			if (i>2){
				boolean continua= InputDati.yesOrNo("Il branch " + branch.getNome()
						+ " e' completo! Vuoi iniziare l'inserimento di un'altra alternativa?");
				if (!continua) stop=true;
			}
		}
		}*/
	
	
	/**
	 * in coda a un'azione si possono inserire
	 * -una nuova azione
	 * -un nuovo branch
	 * -un nuovo merge
	 * -un merge esistente
	 * -un nuovo fork
	 * -un nuovo join
	 * -il nodo finale
	 * @param azione
	 * @param finale TODO
	 */
	private static void gestisciAzione(Modello modello, Azione azione, Merge finale) {
		final String TITOLO = "Cosa vuoi inserire in coda all'azione "+ azione.getNome()+ "?";
		String [] vociMenu;
		if (finale == null)
			vociMenu = VOCI_SCELTA_NODO_FINALE;
		else 
			vociMenu = VOCI_SCELTA_MERGE_FINALE;
		MyMenu menuAzione = new MyMenu(TITOLO, vociMenu);
		int scelta = menuAzione.scegliSenzaUscita();
		
		switch (scelta)
		{
			case 1:
				Azione nuovaAzione = nuovaAzione(modello, azione);
				gestisciAzione(modello, nuovaAzione, finale);
				break;
			case 2:
				Branch nuovoBranch = nuovoBranch(modello, azione);
				gestisciBranch(modello, nuovoBranch, null);
				break;
			case 3: //AGGIUNTA FORK
				Fork nuovoFork = nuovoFork(modello, azione);
				gestisciFork(modello, nuovoFork, null);
				break;
			case 4:
				if(finale==null){
					azione.aggiungiUscita(modello.getEnd());
					modello.getEnd().setIngresso(azione);
				} else {
					azione.aggiungiUscita(finale);
					finale.aggiungiIngresso(azione);
				}
				break;
		}
	}

	private static Merge sceltaMerge(Vector<Merge> merge) {
		System.out.println("A quale merge esistente vuoi collegarti? > \n");
		
		 for (int n=0; n < merge.size() ; n++)
		 {
		  System.out.println( n + "\t" + merge.elementAt(n));
		 }
		 System.out.println();
		int scelta = InputDati.leggiIntero("Digita il numero dell'opzione desiderata > ",0, merge.size());
		
		return merge.elementAt(scelta);
		
	}
	

	private static String acquisizioneNome(Modello modello, String string) {
		boolean finito=false;
		String nome;
		do {
			nome=InputDati.leggiStringa(string);
			finito=modello.nomeOK(nome);
			if(!finito)
				System.out.println("ATTENZIONE! NOME GIA' UTILIZZATO! RIPROVA > ");
		} while (!finito);
		return nome;
	}
	
	private static Azione nuovaAzione(Modello modello, Elemento ingresso){
		//l'acquisizione nome va fatta in modo che non ci siano doppioni
		String nomeAzione = acquisizioneNome(modello, "Inserisci il nome della nuova azione > ");
		Azione nuovaAzione = new Azione(nomeAzione);
		ingresso.aggiungiUscita(nuovaAzione);
		nuovaAzione.setIngresso(ingresso);
		modello.aggiungiAzione(nuovaAzione);
		//modello.setUltimaModifica(nuovaAzione);
		
		return nuovaAzione;
	}

	private static Branch nuovoBranch(Modello modello, Elemento ingresso) {
		
		String nomeBranch = acquisizioneNome(modello, "Inserisci il nome del nuovo branch > ");
		Branch nuovoBranch = new Branch(nomeBranch);
		ingresso.aggiungiUscita(nuovoBranch);
		nuovoBranch.setIngresso(ingresso);
		modello.aggiungiBranch(nuovoBranch);
	
		return nuovoBranch;
		
	}

	private static Fork nuovoFork(Modello modello, Elemento elemento) {
		String nomeFork = acquisizioneNome(modello, "Inserisci il nome del nuovo fork > ");
		Fork nuovoFork = new Fork(nomeFork);
		String nomeJoin = acquisizioneNome(modello, "Inserisci il nome del join associato > ");
		Join nuovoJoin = new Join(nomeJoin);
		
		nuovoFork.setJoinAssociato(nuovoJoin);
		nuovoJoin.setForkAssociato(nuovoFork);
		
		elemento.aggiungiUscita(nuovoFork);
		nuovoFork.setIngresso(elemento);
		modello.aggiungiFork(nuovoFork);
		modello.aggiungiJoin(nuovoJoin);
		
		return nuovoFork;
		
	}
	
	/* io (maffi) preferirei fare una cosa meno static e piu' istanziata
	 * 
	public CreazioneModello(String nomemodello){
		modello = new Modello(nomemodello);
	}
	*/
}
