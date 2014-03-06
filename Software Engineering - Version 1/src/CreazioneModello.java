import java.util.Vector;

import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;

//VERSIONE 1: NO FORK E JOIN
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
public class CreazioneModello {
	
	private static Modello modello;
	
	/**
	 * Metodo principale della classe.
	 * Crea il modello e lancia il menu creazione.
	 * 
	 * @param nomeModello
	 * @return 
	 */
	public static Modello creaModello(String nomeModello){
		modello = new Modello(nomeModello);
			
		boolean fineCreazione = false;
		do {
			fineCreazione = menuCreazione(modello);
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
	public static boolean menuCreazione(Modello _modello){
		final String TITOLO = "MENU CREAZIONE MODELLO " + _modello.getNome();;
		final String [] VOCI = {"Inizia inserimento", "Visualizza il modello", "Termina e salva"};
		MyMenu menuCreazione = new MyMenu(TITOLO, VOCI);
		//nuova funzione-permette di cambiare VOCE_USCITA 
		menuCreazione.setVoceUscita("0\tTorna al menu principale (Tutte le modifiche non salvate andranno perse)");
		int scelta = menuCreazione.scegli();
		
		switch (scelta)
		{
			case 0: 
				return InputDati.yesOrNo("Vuoi veramente uscire?");
			case 1:
				
					//inserimento prima azione obbligatorio
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
					continuaInserimento();
					//a questo punto ci potrebbero essere ancora dei merge non ultimati
					//metto un controllo per verificare che il modello sia corrett
					//TODO
				
				break;
			case 2:
				visualizzaModello();
				break;
			case 3:
				//a questo punto aprirei un altro menu che chiede di salvare come testo o come oggetto...
				//il menu dovra' essere aperto solo se il modello e' corretto!
				//prima vediamo cosa si riesce a fare con la classe CostruzioneModello...
				
				
				
				break;
			default:
				/*Non entra mai qui*/
		}
		
		return false;
	}
	
	/**
	 * Il metodo mette in output il modello sfruttando il metodo toString
	 * Si potr&agrave migliorare in futuro.
	 * 
	 * per ora &egrave private, sar&agrave public se i menu verranno esportati nella classe menuClass
	 */
	private static void visualizzaModello() {
		
		System.out.println(modello.stampaModello());
		//precedentemente era System.out.println(modello);
		//(Sfruttava il toString())
	}

	/*
	public static boolean menuInserimento(){
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
				continuaInserimento();
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
	*/
	/**
	 * Permette di riprendere l'inserimento dopo che lo si e' interrotto per visualizzare il modello.
	 * Non permette di riprendere l'inserimento se l'inserimento &egrave gi&agrave stato ultimato.
	 */
	private static void continuaInserimento() {
		//
		Elemento elementoCorrente = modello.getUltimaModifica();
		
		if (elementoCorrente==null){
			System.out.println("L'inserimento del modello e' gia' stato ultimato.");
		} else {
		
			String tipo = elementoCorrente.getID();
			switch(tipo){
				case "AZIONE":
					gestisciAzione((Azione) elementoCorrente);				
					break;
				case "BRANCH":
					gestisciBranch((Branch) elementoCorrente);
					break;
				case "MERGE":
					gestisciMerge((Merge) elementoCorrente);
					break;
				case "FORK":
					gestisciFork((Fork) elementoCorrente);
					break;
				case "JOIN":
					gestisciJoin((Join) elementoCorrente);
					break;
				default:
					//
			}
				
		}
		
		
	}
	private static void gestisciJoin(Join join) {
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
				String nomeAzione = acquisizioneNome("Inserisci il nome della nuova azione > ");
				Azione nuovaAzione = new Azione(nomeAzione);
				join.aggiungiUscita(nuovaAzione);
				nuovaAzione.setIngresso(join);
				modello.aggiungiAzione(nuovaAzione);
				modello.setUltimaModifica(nuovaAzione);
				continuaInserimento();
				break;
			case 2:
				String nomeBranch = acquisizioneNome("Inserisci il nome del nuovo branch > ");
				Branch nuovoBranch = new Branch(nomeBranch);
				join.aggiungiUscita(nuovoBranch);
				nuovoBranch.setIngresso(join);
				modello.aggiungiBranch(nuovoBranch);
				modello.setUltimaModifica(nuovoBranch);
				continuaInserimento();
				
				break;
			case 3:
				String nomeMerge = acquisizioneNome("Inserisci il nome del nuovo merge > ");
				Merge nuovoMerge = new Merge(nomeMerge);
				join.aggiungiUscita(nuovoMerge);
				nuovoMerge.aggiungiIngresso(join);
				modello.aggiungiMerge(nuovoMerge);
				//l'utilita' della prossima istruzione si vedra'
				//modello.aggiungiMergeIncompleto(nuovoMerge);
				modello.setUltimaModifica(nuovoMerge);
				continuaInserimento();
				break;
			case 4:
				if(modello.getMerge().isEmpty()) {
					System.out.println("ATTENZIONE! Nessun Merge esistente\n");
					continuaInserimento();
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
				String nomeFork = acquisizioneNome("Inserisci il nome del nuovo fork > ");
				Fork nuovoFork = new Fork(nomeFork);
				String nomeJoin = acquisizioneNome("Inserisci il nome del join associato > ");
				Join nuovoJoin = new Join(nomeJoin);
				
				nuovoFork.setJoinAssociato(nuovoJoin);
				nuovoJoin.setForkAssociato(nuovoFork);
				
				join.aggiungiUscita(nuovoFork);
				nuovoFork.setIngresso(join);
				modello.aggiungiFork(nuovoFork);
				modello.aggiungiJoin(nuovoJoin);
				modello.setUltimaModifica(nuovoFork);
				continuaInserimento();
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

	private static void gestisciFork(Fork fork) {
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
		continuaInserimento();
		
		
	}

	/**
	 * Il metodo offre un menu in cui si richiede l'inserimento dell'uscita di un merge.
	 * la completezza degli ingressi verra' gestita altrove.
	 * 
	 * Il menu permette la visualizzazione del modello allo stato attuale
	 * 
	 * @param merge
	 */
	private static void gestisciMerge(Merge merge) {
		final String TITOLO = "Cosa vuoi inserire in coda al merge "+ merge.getNome()+ "?";
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
				String nomeAzione = acquisizioneNome("Inserisci il nome della nuova azione > ");
				Azione nuovaAzione = new Azione(nomeAzione);
				merge.aggiungiUscita(nuovaAzione);
				nuovaAzione.setIngresso(merge);
				modello.aggiungiAzione(nuovaAzione);
				modello.setUltimaModifica(nuovaAzione);
				continuaInserimento();
				break;
			case 2:
				String nomeBranch = acquisizioneNome("Inserisci il nome del nuovo branch > ");
				Branch nuovoBranch = new Branch(nomeBranch);
				merge.aggiungiUscita(nuovoBranch);
				nuovoBranch.setIngresso(merge);
				modello.aggiungiBranch(nuovoBranch);
				modello.setUltimaModifica(nuovoBranch);
				continuaInserimento();
				
				break;
			case 3:
				String nomeMerge = acquisizioneNome("Inserisci il nome del nuovo merge > ");
				Merge nuovoMerge = new Merge(nomeMerge);
				merge.aggiungiUscita(nuovoMerge);
				nuovoMerge.aggiungiIngresso(merge);
				modello.aggiungiMerge(nuovoMerge);
				//l'utilita' della prossima istruzione si vedra'
				//modello.aggiungiMergeIncompleto(nuovoMerge);
				modello.setUltimaModifica(nuovoMerge);
				continuaInserimento();
				break;
			case 4:
				if(modello.getMerge().isEmpty()) {
					System.out.println("ATTENZIONE! Nessun Merge esistente\n");
					continuaInserimento();
				} else {
					Merge mergeEsistente= sceltaMerge(modello.getMerge());
					mergeEsistente.aggiungiIngresso(merge);
					merge.aggiungiUscita(mergeEsistente);
					
					//a questo punto devo decidere dove mandare il programma!!
					//probabilmente non e' necessario mandarlo da nessuna parte dato che se sono giunto qui
					//ho quasi sicuramente un branch aperto e quindi riprendera' il menu del branch.
					//ci devo pensare ancora un pochino
				}
				
				break;
			case 5: //AGGIUNTA FORK
				String nomeFork = acquisizioneNome("Inserisci il nome del nuovo fork > ");
				Fork nuovoFork = new Fork(nomeFork);
				String nomeJoin = acquisizioneNome("Inserisci il nome del join associato > ");
				Join nuovoJoin = new Join(nomeJoin);
				
				nuovoFork.setJoinAssociato(nuovoJoin);
				nuovoJoin.setForkAssociato(nuovoFork);
				
				merge.aggiungiUscita(nuovoFork);
				nuovoFork.setIngresso(merge);
				modello.aggiungiFork(nuovoFork);
				modello.aggiungiJoin(nuovoJoin);
				modello.setUltimaModifica(nuovoFork);
				continuaInserimento();
				break;
			case 6:
				merge.aggiungiUscita(modello.getEnd());
				modello.setUltimoElemento(merge);
				modello.setUltimaModifica(null);
				break;
		
			default:
				/*Non entra mai qui*/
		}
		
	}
	
	/**
	 * Un nuovo branch permette l'inserimento di due o piu' alternative.
	 * Le prime due sono obbligatorie. Inserite queste si chiede se si vuole aggiungerne un'altra.
	 * All'inizio di ogni alternativa si richiede l'inserimento di un nuovo elemento.
	 * Una volta inserito questo, si continua a inserire le uscite di questo fino a che si termina.
	 * 
	 * DA DISCUTERE
	 * Un branch puo' avere come alternativa il nodo finale? 
	 * In teoria s&igrave, A PATTO CHE esista almeno un merge su cui sia possibile mandare le altre alternative.
	 * Il merge deve essere gi&agrave stato creato perch&eacute deve avere come uscita un elemento gi&agrave creato.
	 * Nel caso non esista nessun merge al quale ci si possa effettivamente collegare
	 * Non deve essere possibile inserire il nodo finale come alternativa all'interno del branch.
	 * 
	 * Il menu permette la visualizzazione del modello allo stato attuale
	 * 
	 * NOVITA' 27/02/2014
	 * UN AZIONE DI UN BRANCH NON PUO' COLLEGARSI A UN MERGE CREATO AL DI FUORI DELL'ALTERNATIVA CORRENTE, 
	 * ALTRIMENTI SAREBBE COME USARE UN GOTO...VE LO SPIEGHERO' A PAROLE...
	 * QUINDI PENSO CHE SAREBBE CORRETTO USARE LA CLASSE STRUTTURA
	 * CHE AVEVAMO PENSATO PER I FORK
	 * ANCHE PER I BRANCH!
	 * CHIAMANDOLA ALTERNATIVA PERO'!
	 * @param branch
	 */
	private static void gestisciBranch(Branch branch) {
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
					String nomeAzione = acquisizioneNome("Inserisci il nome della nuova azione > ");
					Azione nuovaAzione = new Azione(nomeAzione);
					branch.aggiungiUscita(nuovaAzione);
					nuovaAzione.setIngresso(branch);
					modello.aggiungiAzione(nuovaAzione);
					modello.setUltimaModifica(nuovaAzione);
					continuaInserimento();
					break;
		
				case 2:
					String nomeBranch = acquisizioneNome("Inserisci il nome del nuovo branch > ");
					Branch nuovoBranch = new Branch(nomeBranch);
					branch.aggiungiUscita(nuovoBranch);
					nuovoBranch.setIngresso(branch);
					modello.aggiungiBranch(nuovoBranch);
					modello.setUltimaModifica(nuovoBranch);
					continuaInserimento();
					
					break;
			
				case 3:
					String nomeMerge = acquisizioneNome("Inserisci il nome del nuovo merge> ");
					Merge nuovoMerge = new Merge(nomeMerge);
					branch.aggiungiUscita(nuovoMerge);
					nuovoMerge.aggiungiIngresso(branch);
					modello.aggiungiMerge(nuovoMerge);
					//modello.aggiungiMergeIncompleto(nuovoMerge);
					modello.setUltimaModifica(nuovoMerge);
					continuaInserimento();
					break;
				case 4:if(modello.getMerge().isEmpty()) {
					System.out.println("ATTENZIONE! Nessun Merge esistente\n");
					continuaInserimento();
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
					String nomeFork = acquisizioneNome("Inserisci il nome del nuovo fork > ");
					Fork nuovoFork = new Fork(nomeFork);
					String nomeJoin = acquisizioneNome("Inserisci il nome del join associato > ");
					Join nuovoJoin = new Join(nomeJoin);
					
					nuovoFork.setJoinAssociato(nuovoJoin);
					nuovoJoin.setForkAssociato(nuovoFork);
					
					branch.aggiungiUscita(nuovoFork);
					nuovoFork.setIngresso(branch);
					modello.aggiungiFork(nuovoFork);
					modello.aggiungiJoin(nuovoJoin);
					modello.setUltimaModifica(nuovoFork);
					continuaInserimento();
					break;
				case 6:
					branch.aggiungiUscita(modello.getEnd());
					modello.setUltimoElemento(branch);
					modello.setUltimaModifica(null);
					break;
				
				default:
					/*Non entra mai qui*/
			}
			
			i++;
			if (i>2){
				boolean continua= InputDati.yesOrNo("Il branch " + branch.getNome()
						+ " e' completo! Vuoi iniziare l'inserimento di un'altra alternativa?");
				if (!continua) stop=true;
			}
		}
	}
	
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
	 */
	private static void gestisciAzione(Azione azione) {
		final String TITOLO = "Cosa vuoi inserire in coda all'azione "+ azione.getNome()+ "?";
		final String [] VOCI = {
				"Una nuova azione", 
				"Un nuovo branch", 
				"Un nuovo merge", 
				"un merge esistente", 
				"un nuovo fork (crea automaticamente il join associato)", 
				//"un nuovo join",
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
				String nomeAzione = acquisizioneNome("Inserisci il nome della nuova azione > ");
				Azione nuovaAzione = new Azione(nomeAzione);
				azione.aggiungiUscita(nuovaAzione);
				nuovaAzione.setIngresso(azione);
				modello.aggiungiAzione(nuovaAzione);
				modello.setUltimaModifica(nuovaAzione);
				continuaInserimento();
				break;
			case 2:
				String nomeBranch = acquisizioneNome("Inserisci il nome del nuovo branch > ");
				Branch nuovoBranch = new Branch(nomeBranch);
				azione.aggiungiUscita(nuovoBranch);
				nuovoBranch.setIngresso(azione);
				modello.aggiungiBranch(nuovoBranch);
				modello.setUltimaModifica(nuovoBranch);
				continuaInserimento();
				
				break;
			case 3:
				String nomeMerge = acquisizioneNome("Inserisci il nome del nuovo merge > ");
				Merge nuovoMerge = new Merge(nomeMerge);
				azione.aggiungiUscita(nuovoMerge);
				nuovoMerge.aggiungiIngresso(azione);
				modello.aggiungiMerge(nuovoMerge);
				//l'utilita' della prossima istruzione si vedra'
				//modello.aggiungiMergeIncompleto(nuovoMerge);
				modello.setUltimaModifica(nuovoMerge);
				continuaInserimento();
				break;
			case 4:
				if(modello.getMerge().isEmpty()) {
					System.out.println("ATTENZIONE! Nessun Merge esistente\n");
					continuaInserimento();
				} else {
					Merge mergeEsistente= sceltaMerge(modello.getMerge());
					mergeEsistente.aggiungiIngresso(azione);
					azione.aggiungiUscita(mergeEsistente);
					
					//a questo punto devo decidere dove mandare il programma!!
					//probabilmente non e' necessario mandarlo da nessuna parte dato che se sono giunto qui
					//ho quasi sicuramente un branch aperto e quindi riprendera' il menu del branch.
					//ci devo pensare ancora un pochino
				}
				
				break;
			case 5: //AGGIUNTA FORK
				String nomeFork = acquisizioneNome("Inserisci il nome del nuovo fork > ");
				Fork nuovoFork = new Fork(nomeFork);
				String nomeJoin = acquisizioneNome("Inserisci il nome del join associato > ");
				Join nuovoJoin = new Join(nomeJoin);
				
				nuovoFork.setJoinAssociato(nuovoJoin);
				nuovoJoin.setForkAssociato(nuovoFork);
				
				azione.aggiungiUscita(nuovoFork);
				nuovoFork.setIngresso(azione);
				modello.aggiungiFork(nuovoFork);
				modello.aggiungiJoin(nuovoJoin);
				modello.setUltimaModifica(nuovoFork);
				continuaInserimento();
				break;
			
			case 6:
				azione.aggiungiUscita(modello.getEnd());
				modello.setUltimoElemento(azione);
				modello.setUltimaModifica(null);
				break;
			
			default:
				/*Non entra mai qui*/
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
	

	private static String acquisizioneNome(String string) {
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
	
	/* io (maffi) preferirei fare una cosa meno static e piu' istanziata
	 * 
	public CreazioneModello(String nomemodello){
		modello = new Modello(nomemodello);
	}
	*/
}
