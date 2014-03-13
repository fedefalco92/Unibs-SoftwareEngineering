package unibsSWEngineering;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import unibsSWEngineering.analisi.*;
import unibsSWEngineering.modello.Modello;
import it.unibs.fp.mylib.*;

public class MenuClass {

	private static final FileNameExtensionFilter filtroTXT = new FileNameExtensionFilter("File .txt","txt");
	private static final FileNameExtensionFilter filtroDAT = new FileNameExtensionFilter("File .dat","dat");
	public final static String cartella = "Modelli"; //La cartella dove risiederanno i file modelli salvati. Magari cambiata
	public static final String cartellaModelliOggetto = "ModelliDAT";
	
	private static Modello modello;
	private static File file;
	private static TestSuite ts1;
	private static Distanze dist;
	
	//////////////////////////////////
	// MENU PRINCIPALE
	//////////////////////////////////
	public static boolean menuPrincipale(){
		final String TITOLO = "MENU PRINCIPALE";
		final String [] VOCI = {"Creazione Modello", "Caricamento Modello", "Diagnosi e Test", "Probabilita'", "Visualizza modello", "Salva modello"};
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
				break;
			case 2:
				if (modello!=null){
					boolean prosegui = InputDati.yesOrNo("Proseguendo si perdera' il modello gia' inserito. Proseguire? > ");
					if(!prosegui) break;
				}
				caricaModello();
				break;
			case 3:
				//Diagnosi e test
				if(modello != null){
					Prova p1 = new Prova("Prova 1");
					p1.addPercorso(new Cammino("A1,A3,A4,A5",false));
					p1.addPercorso(new Cammino("A1",true));
					Prova p2 = new Prova("Prova 2");
					p2.addPercorso(new Cammino("A1,A3,A4,A5",false));
					p2.addPercorso(new Cammino("A1",true));		
					Prova p3 = new Prova("Prova 3");
					p3.addPercorso(new Cammino("A1,A3,A4,A5",false));
					p3.addPercorso(new Cammino("A1",true));
					p3.addPercorso(new Cammino("A1,A3,A4",false));
					Prova p4 = new Prova("Prova 4");
					p4.addPercorso(new Cammino("A1,A2,A3,A4,A6",false));
					p4.addPercorso(new Cammino("A1",true));
					p4.addPercorso(new Cammino("A1,A2,A3",true));
				 
						//classi di equivalenza
					//QUESTA PARTE DOVRA' ESSERE ORGANIZZATA PER UN RIEMPIMENTO AUTOMATICO
					ClasseEquivalenza cl1 = new ClasseEquivalenza("Classe 1",modello.getNomiAzioni());
					cl1.setIstanzaProva(p1);
					cl1.setCardinalita(2);
					ClasseEquivalenza cl2 = new ClasseEquivalenza("Classe 2",modello.getNomiAzioni());
					cl2.setIstanzaProva(p3);	
					cl2.setCardinalita(1);		
					ClasseEquivalenza cl3 = new ClasseEquivalenza("Classe 3",modello.getNomiAzioni());
					cl3.setIstanzaProva(p4);
					cl3.setCardinalita(1);		
					
					//aggiunta ad un test suite 
					ts1 = new TestSuite(modello.getNomiAzioni());
					ts1.addNuovaClasseEquivalenza(cl1);
					ts1.addNuovaClasseEquivalenza(cl2);
					ts1.addNuovaClasseEquivalenza(cl3);	
					
					ts1.calcolaProbabilitaM1();		
					ts1.calcolaProbabilitaM2();			
				}
				break;
			case 4:
				//Probabilita'
				if(modello != null && ts1 != null){
					ts1.calcolaProbabilitaM1();		
					ts1.calcolaProbabilitaM2();
					System.out.println(ts1);
					dist = new Distanze(ts1);
					dist.calcoloDistanze();
					System.out.println(dist);		
				}
				break;
			case 5: 
				visualizzaModello();
				break;
			case 6:
				salvaModello();
				break;
		}
		
		return false;
	}
	
	//////////////////////////////////
	// 1 - METODI CREAZIONE MODELLO
	//////////////////////////////////
	private static Modello creaModello() {
		String nomeModello = InputDati.leggiStringa("Inserisci il nome del nuovo modello > ");
		return CreazioneModello.creaModello(nomeModello);
	
	}

	//////////////////////////////////
	// 2 - METODI CARICAMENTO MODELLO
	//////////////////////////////////

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
				caricaTesto();
				break;
			case 2:
				caricaOggetto();
				break;
		}
	}

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
	
	private static void caricaOggetto() {
		String loc = MenuClass.cartellaModelliOggetto + File.separator; //Location del file
		file = aprifile(loc, filtroDAT);
		if(file != null){
			modello = CostruzioneModello.caricaModelloOggetto(file);
			checkModello();
		}
		else{
			System.out.println("Non hai selezionato nessun file");
		}
	}

	private static void caricaTesto() {
		String loc = cartella + File.separator; //Location del file
		file = aprifile(loc, filtroTXT);
		if(file != null){
			modello = CostruzioneModello.caricaModello(file);
			checkModello();
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
	// 5 - METODI VISUALIZZAZIONE MODELLO
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
	// 6 - METODI SALVATAGGIO MODELLO
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
				salvaFormatoTestuale();
				break;
			case 2:
				salvaFormatoOggetto();
				break;
		}
	}

	private static void salvaFormatoOggetto() {	
		
		String nomeFile = InputDati.leggiStringa("Quale nome vuoi dare al file da salvare? (ESTENSIONE APPLICATA AUTOMATICAMENTE .DAT) > ");
		String loc = cartellaModelliOggetto + File.separator + nomeFile + ".dat";
		File modelloFile = new File(loc);
		if (modelloFile.exists()){
			boolean sovrascrivi = InputDati.yesOrNo("> ATTENZIONE! Esiste gia' un file con il nome inserito!! <\n"
					+ "> Vuoi sovrascriverlo? > ");
			if(sovrascrivi){
				ServizioFile.confermaSovrascrittura(modelloFile, modello);
			}
		} else {
			ServizioFile.salvaSingoloOggetto(modelloFile, modello);
		}

	}

	private static void salvaFormatoTestuale() {
		
		String nomeFile = InputDati.leggiStringa("Quale nome vuoi dare al file da salvare? (ESTENSIONE APPLICATA AUTOMATICAMENTE .TXT) > ");
		String loc = cartella + File.separator + nomeFile + ".txt";
		File modelloFile = new File(loc);
		if (modelloFile.exists()){
			boolean sovrascrivi = InputDati.yesOrNo("> ATTENZIONE! Esiste gia' un file con il nome inserito!! <\n"
					+ "> Vuoi sovrascriverlo? > ");
			if(sovrascrivi){
				ServizioFile.confermaSovrascritturaTesto(modelloFile, modello.stampaModello());
			}
		} else {
			ServizioFile.salvaFileTesto(modelloFile, modello.stampaModello());
		}

	}
		
}
