package unibsSWEngineering;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import unibsSWEngineering.analisi.*;
import unibsSWEngineering.modello.Modello;
import it.unibs.fp.mylib.*;

public class MenuClass {

	private static Modello modello;
	private static File file;
	private static TestSuite testSuite;
	
	//////////////////////////////////
	// MENU PRINCIPALE
	//////////////////////////////////
	/**
	 * Metodo per il menu principale dell'applicazione.
	 * @return True se &egrave; stata selezionata l'uscita, altrimenti False.
	 */
	public static boolean menuPrincipale(){
		final String TITOLO = "MENU PRINCIPALE";
		final String [] VOCI = {
				"Creazione Modello", 
				"Caricamento Modello", 
				"Creazione test suite",
				"Caricamento test suite",
				"Stampa diagnosi", 
				"Visualizza modello", 
				"Esporta modello",
				"Esporta test suite"};
		MyMenu menuPrincipale = new MyMenu(TITOLO, VOCI);
		int scelta = menuPrincipale.scegli();
		
		switch (scelta)
		{
			case 0: 
				return InputDati.yesOrNo("Vuoi veramente uscire?");
			case 1:
				if (modello!=null){
					boolean prosegui = InputDati.yesOrNo("Proseguendo si perdera' il modello gia' inserito. Proseguire? > ");
					if(!prosegui) break;
				}
				modello = creaModello();
				testSuite = null;
				break;
			case 2:
				if (modello!=null){
					boolean prosegui = InputDati.yesOrNo("Proseguendo si perdera' il modello gia' inserito. Proseguire? > ");
					if(!prosegui) break;
				}
				caricaModello();
				testSuite = null;
				break;
			case 3:
				gestisciInserimentoClasse();
				break;
			case 4:
				caricaStatistiche();
				break;
			case 5:
				calcolaProbabilita();
				break;
								
			case 6:
				visualizzaModello();
				break;
				
			case 7:
				salvaModello();
				break;
			case 8: 
				GestioneFiles.salvaTestSuite(modello, testSuite);
				break;
		}
		
		return false;
	}
	
	//////////////////////////////////
	// 1 - CREAZIONE MODELLO
	//////////////////////////////////
	/**
	 * Metodo che permette di richiamare la classe Creazione Modello per creare un nuovo modello.
	 * @return Il nuovo modello creato.
	 */
	private static Modello creaModello() {
		String nomeModello = InputDati.leggiStringaNonVuota("Inserisci il nome del nuovo modello > ");
		return CreazioneModello.creaModello(nomeModello);
	
	}

	//////////////////////////////////
	// 2 - CARICAMENTO MODELLO
	//////////////////////////////////
	/**
	 * Metodo che permette di caricare un modello. 
	 * &Egrave; possibile scegliere se caricare il modello da un file di testo o un file salvato in formato .dat.
	 */
	private static void caricaModello() {
		final String TITOLO = "MENU CARICAMENTO MODELLO";
		final String [] VOCI = {"Carica da testo" , "Carica da file.dat"};
		MyMenu menuCreazione = new MyMenu(TITOLO, VOCI); 
		menuCreazione.setVoceUscita("0\tTorna indietro");
		int scelta = menuCreazione.scegli();
		
		switch (scelta)
		{
			case 0: 
				return;
			case 1:				
				caricaModelloTesto();
				break;
			case 2:
				caricaModelloOggetto();
				break;
		}
	}

	/**
	 * Metodo controlla il modello. 
	 * Se il modello &egrave; stato caricato correttamente allora richiama un metodo che controlla la correttezza del modello.
	 * @see Modello
	 */
	private static void checkModello(){
		if(modello!=null){
			if(modello.controllaModello()){
				System.out.println("Modello corretto");
			}
			else{
				System.out.println("Modello errato");
				modello = null;
			}
		}
	}
	
	/**
	 * Metodo che permette il caricamento di un modello da un file Oggetto.
	 * Viene controllato che il modello sia corretto.
	 */
	private static void caricaModelloOggetto() {
		String loc = GestioneFiles.cartellaModelliOggetto + File.separator; //Location del file
		file = aprifile(loc, GestioneFiles.filtroDAT);
		if(file != null){

			modello = GestioneFiles.caricaModelloOggetto(file);
			//checkModello();
		}
		else{
			System.out.println("Non hai selezionato nessun file");
		}
	}

	/**
	 * Metodo che permette il caricamento di un modello da un file di Testo. 
	 * Se il modello caricato &egrave; null viene stampato a video un errore.
	 */
	private static void caricaModelloTesto() {
		String loc = GestioneFiles.cartellaModelliTesto + File.separator; //Location del file
		file = aprifile(loc, GestioneFiles.filtroTXT);
		if(file != null){
			modello = CostruzioneModello.caricaModelloTesto(file);
			if(modello == null){
				System.out.println("Il file del modello contiene un errore. Modello non corretto");
			}else{
				checkModello();
			}
		}
		else{
			System.out.println("Non hai selezionato nessun file");
		}
	}

	/**
	 * Metodo che permette di aprire un file attraverso un JFileChooser.
	 * Viene aperta una finestra nella cartella predefinita e viene chiesto all'utente di scegliere un file.
	 * @param Stringa con la location del file.
	 * @return Il file aperto, altrimenti null se non &egrave; stato aperto alcun file.
	 * @throws FileNotFoundException
	 */
	private static File aprifile(String loc, FileNameExtensionFilter filtro) {
		JFileChooser chooser = new JFileChooser(new File(loc));
		
		//Opzioni finestra
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.addChoosableFileFilter(filtro);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setMultiSelectionEnabled(false);
		
	    //int returnVal = chooser.showDialog(null, "Open");
		int returnVal = chooser.showOpenDialog(null);
		
	    if(returnVal == JFileChooser.APPROVE_OPTION){
	    	return chooser.getSelectedFile();
	    }
	    else 
	    	return null;
	}
	
	//////////////////////////////////
	// 3 - CREAZIONE TEST SUITE
	//////////////////////////////////
	
	private static void gestisciInserimentoClasse(){
		if(modello != null){			
			if(testSuite == null){
				testSuite = new TestSuite(modello.getNomiAzioni());
			}
			CostruzioneTestSuite.inserimentoClassiEquivalenza(modello, testSuite);				
		}
		else{
			System.out.println("Modello non ancora caricato");
		}			
	}
	
	//////////////////////////////////
	// 4 - CARICAMENTO TEST SUITE
	//////////////////////////////////	
	
	private static void caricaStatistiche(){
		if(modello != null){
			File fileStat = new File(CostruzioneTestSuite.patternNome(modello));
			if(fileStat.exists()){
				testSuite = (TestSuite)ServizioFile.caricaSingoloOggetto(new File(CostruzioneTestSuite.patternNome(modello)));
				System.out.println("File con Test Suite caricato");
			}
			else{
				System.out.println("File con Test Suite non presente");
			}
		}
		else{
			System.out.println("Modello non ancora caricato");
		}
	}

	//////////////////////////////////
	// 5 - STAMPA DIAGNOSI
	//////////////////////////////////
	
	private static void calcolaProbabilita(){
		if(modello != null){
			if(testSuite != null){
				testSuite.eseguiComputazioni();
				System.out.println(testSuite);
			} else {
				System.out.println(" > Nessun test suite inserito");
			}
		}
		else{
			System.out.println(" > Modello non ancora caricato");
		}			
	}
	
	//////////////////////////////////
	// 6 - VISUALIZZA MODELLO
	//////////////////////////////////
	/**
	* Il metodo mette in output il modello sfruttando il metodo Modello.stampaModello()
	* 
	*/
	private static void visualizzaModello() {
		if(modello!=null){
			System.out.println(modello.stampaModello());
		}	
		else{
			System.out.println ("> Nessun modello inserito <");
		}
	}

	//////////////////////////////////
	// 7 - ESPORTA MODELLO
	//////////////////////////////////
	private static void salvaModello() {
		
		final String TITOLO = "MENU SALVATAGGIO MODELLO " + modello.getNome();
		final String [] VOCI = {"Salva come testo" , "Salva come oggetto"};
		MyMenu menuCreazione = new MyMenu(TITOLO, VOCI); 
		menuCreazione.setVoceUscita("0\tTorna indietro");
		int scelta = menuCreazione.scegli();
		
		switch (scelta)
		{
			case 0: 
				return;
			case 1:				
				GestioneFiles.salvaModelloTesto(modello);
				break;
			case 2:
				GestioneFiles.salvaModelloOggetto(modello);
				break;
		}
	} 
	
}
