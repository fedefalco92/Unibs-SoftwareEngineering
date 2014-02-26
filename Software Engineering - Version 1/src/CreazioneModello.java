import java.util.Vector;

import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;

/*VERSIONE 1: NO FORK E JOIN
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
 * 
 */
public class CreazioneModello {
	
	private static Modello modello;
	
	public static void creaModello(String nomeModello){
		modello = new Modello(nomeModello);
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
			
		boolean fineCreazione = false;
		do {
			fineCreazione = menuCreazione();
		} while (!fineCreazione);
		
		//la prossima istruzione sarà da togliere
		System.out.println(modello);
		
	}
	
	//PER ORA PER COMODITA' METTO QUA I MENU
	//SE VOLETE SI POTRANNO SPOSTARE NELLA CLASSE MenuClass
	public static boolean menuCreazione(){
		final String TITOLO = "MENU CREAZIONE";
		final String [] VOCI = {"Continua Inserimento", "Visualizza il modello allo stato attuale", "Termina e salva"};
		MyMenu menuCreazione = new MyMenu(TITOLO, VOCI);
		//nuova funzione-permette di cambiare VOCE_USCITA 
		menuCreazione.setVoceUscita("0\tTorna al menu principale (Tutte le modifiche non salvate andranno perse)");
		int scelta = menuCreazione.scegli();
		
		switch (scelta)
		{
			case 0: 
				return InputDati.yesOrNo("Vuoi veramente uscire?");
			case 1:
				continuaInserimento();
				//a questo punto ci potrebbero essere ancora dei merge non ultimati
				//metto un controllo
				
				break;
			case 2:
				//si può migliorare
				System.out.println(modello);
				break;
			case 3:
				//a questo punto aprirei un altro menu che chiede di salvare come testo o come oggetto...
				//il menu dovrà essere aperto solo se il modello e' corretto!
				//prima vediamo cosa si riesce a fare con la classe CostruzioneModello...
				
				
				
				break;
			default:
				/*Non entra mai qui*/
		}
		
		return false;
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
				//si può migliorare
				System.out.println(modello);
				break;
			case 3:
				//a questo punto aprirei un altro menu che chiede di salvare come testo o come oggetto...
				//il menu dovrà essere aperto solo se il modello e' corretto!
				//prima vediamo cosa si riesce a fare con la classe CostruzioneModello...
				break;
			default:
				//Non entra mai qui
		}
		
		return false;
	}
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
					//uso futuro
					break;
				case "JOIN":
					//uso futuro
					break;
				default:
					//
			}
				
		}
		
		
	}
	/**
	 * si richiede l'inserimento dell'uscita del merge.
	 * la completezza degli ingressi verrà gestita altrove.
	 * 
	 * un merge può terminare sul nodo finale.
	 * @param merge
	 */
	private static void gestisciMerge(Merge merge) {
		final String TITOLO = "Cosa vuoi inserire in coda al merge "+ merge.getNome()+ "?";
		final String [] VOCI = {"Una nuova azione", 
				"Un nuovo branch", 
				"Un nuovo merge", 
				"un merge esistente", 
				"un nuovo fork", 
				"un nuovo join", 
				"termina modello (nodo finale)"};
		MyMenu menuAzione = new MyMenu(TITOLO, VOCI);
		menuAzione.setVoceUscita("0\tTorna al menu creazione (potrai riprendere l'inserimento in seguito)");
		int scelta = menuAzione.scegli();
		
		switch (scelta)
		{
			case 0: 
				return;
			case 1:
				//l'acquisizione nome va fatta in modo che non ci siano doppioni
				String nomeAzione = acquisizioneNome("Inserisci il nome della nuova azione > ");
				Azione nuovaAzione = new Azione(nomeAzione);
				merge.setUscita(nuovaAzione);
				nuovaAzione.setIngresso(merge);
				modello.aggiungiAzione(nuovaAzione);
				modello.setUltimaModifica(nuovaAzione);
				continuaInserimento();
				break;
			case 2:
				String nomeBranch = acquisizioneNome("Inserisci il nome del nuovo branch > ");
				Branch nuovoBranch = new Branch(nomeBranch);
				merge.setUscita(nuovoBranch);
				nuovoBranch.setIngresso(merge);
				modello.aggiungiBranch(nuovoBranch);
				modello.setUltimaModifica(nuovoBranch);
				continuaInserimento();
				
				break;
			case 3:
				String nomeMerge = acquisizioneNome("Inserisci il nome del nuovo merge > ");
				Merge nuovoMerge = new Merge(nomeMerge);
				merge.setUscita(nuovoMerge);
				nuovoMerge.aggiungiIngresso(merge);
				modello.aggiungiMerge(nuovoMerge);
				//l'utilità della prossima istruzione si vedrà
				//modello.aggiungiMergeIncompleto(nuovoMerge);
				modello.setUltimaModifica(nuovoMerge);
				continuaInserimento();
				break;
			case 4:
				if(modello.getMerge().isEmpty()) {
					System.out.println("ATTENZIONE! Nessun Merge esistente\n");
					break;
				} else {
					Merge mergeEsistente= sceltaMerge(modello.getMerge());
					mergeEsistente.aggiungiIngresso(merge);
					merge.setUscita(mergeEsistente);
					
					//a questo punto devo decidere dove mandare il programma!!
					//probabilmente non è necessario mandarlo da nessuna parte dato che se sono giunto qui
					//ho quasi sicuramente un branch aperto e quindi riprenderà il menu del branch.
					//ci devo pensare ancora un pochino
				}
				
				break;
			case 5:
				//da fare
				break;
			case 6:
				//da fare
				break;
			case 7:
				merge.setUscita(modello.getEnd());
				modello.setUltimoElemento(merge);
				modello.setUltimaModifica(null);
				break;
			default:
				/*Non entra mai qui*/
		}
		
	}
	
	/**
	 * un nuovo branch permette l'inserimento di due o più alternative
	 * all'inizio ogni alternativa richiede l'inserimento di un nuovo elemento.
	 * inserito questo si continua a inserire le uscite di questo fino a che si termina.
	 * una volta terminato il primo "filone" si ritorna al menu del branch
	 * da dove è possibile inserire un'altra alternativa
	 * 
	 * un branch non può avere come alternativa il nodo finale
	 * 
	 * @param branch
	 */
	private static void gestisciBranch(Branch branch) {
		//ci sono almeno due alternative da inserire obbligatoriamente.
		int i=1;
		boolean stop=false;
		while(!stop){
			final String TITOLO = "Menu creazione del branch "+ branch.getNome() +" - ALTERNATIVA " + i +
					"\nCosa vuoi inserire?";
			final String [] VOCI = {"Una nuova azione", 
					"Un nuovo branch", 
					"Un nuovo merge", 
					"Un merge esistente", 
					"un nuovo fork", 
					"un nuovo join"};
			MyMenu menuAzione = new MyMenu(TITOLO, VOCI);
			menuAzione.setVoceUscita("0\tTorna al menu creazione (potrai riprendere l'inserimento in seguito)");
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
					break;
				} else {
					System.out.println("A quale merge vuoi collegarti?");
					Merge mergeEsistente= sceltaMerge(modello.getMerge());
					mergeEsistente.aggiungiIngresso(branch);
					branch.aggiungiUscita(mergeEsistente);
					
					//a questo punto devo decidere dove mandare il programma!!
					//probabilmente non è necessario mandarlo da nessuna parte dato che se sono giunto qui
					//ho quasi sicuramente un branch aperto e quindi riprenderà il menu del branch.
					//ci devo pensare ancora un pochino
				}
					break;
				case 5:
					//da fare
					break;
				case 6:
					//da fare
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
		final String [] VOCI = {"Una nuova azione", 
				"Un nuovo branch", 
				"Un nuovo merge", 
				"un merge esistente", 
				"un nuovo fork", 
				"un nuovo join",
				"termina modello (nodo finale)"};
		MyMenu menuAzione = new MyMenu(TITOLO, VOCI);
		menuAzione.setVoceUscita("0\tTorna al menu creazione (potrai riprendere l'inserimento in seguito)");
		int scelta = menuAzione.scegli();
		
		switch (scelta)
		{
			case 0: 
				return;
			case 1:
				//l'acquisizione nome va fatta in modo che non ci siano doppioni
				String nomeAzione = acquisizioneNome("Inserisci il nome della nuova azione > ");
				Azione nuovaAzione = new Azione(nomeAzione);
				azione.setUscita(nuovaAzione);
				nuovaAzione.setIngresso(azione);
				modello.aggiungiAzione(nuovaAzione);
				modello.setUltimaModifica(nuovaAzione);
				continuaInserimento();
				break;
			case 2:
				String nomeBranch = acquisizioneNome("Inserisci il nome del nuovo branch > ");
				Branch nuovoBranch = new Branch(nomeBranch);
				azione.setUscita(nuovoBranch);
				nuovoBranch.setIngresso(azione);
				modello.aggiungiBranch(nuovoBranch);
				modello.setUltimaModifica(nuovoBranch);
				continuaInserimento();
				
				break;
			case 3:
				String nomeMerge = acquisizioneNome("Inserisci il nome del nuovo merge > ");
				Merge nuovoMerge = new Merge(nomeMerge);
				azione.setUscita(nuovoMerge);
				nuovoMerge.aggiungiIngresso(azione);
				modello.aggiungiMerge(nuovoMerge);
				//l'utilità della prossima istruzione si vedrà
				//modello.aggiungiMergeIncompleto(nuovoMerge);
				modello.setUltimaModifica(nuovoMerge);
				continuaInserimento();
				break;
			case 4:
				if(modello.getMerge().isEmpty()) {
					System.out.println("ATTENZIONE! Nessun Merge esistente\n");
					break;
				} else {
					Merge mergeEsistente= sceltaMerge(modello.getMerge());
					mergeEsistente.aggiungiIngresso(azione);
					azione.setUscita(mergeEsistente);
					
					//a questo punto devo decidere dove mandare il programma!!
					//probabilmente non è necessario mandarlo da nessuna parte dato che se sono giunto qui
					//ho quasi sicuramente un branch aperto e quindi riprenderà il menu del branch.
					//ci devo pensare ancora un pochino
				}
				
				break;
			case 5:
				//da fare
				break;
			case 6:
				//da fare
				break;
			case 7:
				azione.setUscita(modello.getEnd());
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
				System.out.println("ATTENZIONE! NOME GIA' UTILIZZATO! RIPROVA >\n");
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
