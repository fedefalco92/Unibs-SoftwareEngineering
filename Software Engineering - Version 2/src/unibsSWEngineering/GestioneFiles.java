package unibsSWEngineering;

import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.ServizioFile;

import java.io.File;

import javax.swing.filechooser.FileNameExtensionFilter;

import unibsSWEngineering.analisi.TestSuite;
import unibsSWEngineering.modello.Modello;

public class GestioneFiles {

	public static final String cartellaStatisticheModello = "Rilevazioni";
	public static final String cartellaModelliOggetto = "ModelliDAT";
	public static final String cartellaModelliTesto = "ModelliTesto"; //La cartella dove risiederanno i file modelli salvati. Magari cambiata
	public static final FileNameExtensionFilter filtroDAT = new FileNameExtensionFilter("File .dat","dat");
	public static final FileNameExtensionFilter filtroTXT = new FileNameExtensionFilter("File .txt","txt");
	
	public static void salvaTestSuite(Modello modello, TestSuite testSuite){
		if(modello != null){
			if(testSuite != null){
				String strDest = CostruzioneTestSuite.patternNome(modello);
				File fileDest = new File(strDest);
				if(fileDest.exists()){
					boolean risposta = InputDati.yesOrNo("File gia' esistente. Sovrascriverlo?");
					if(risposta){
						ServizioFile.salvaSingoloOggetto(fileDest, testSuite);
					}					
				}
				else{
					ServizioFile.salvaSingoloOggetto(fileDest, testSuite);
				}
				
			}
			else{
				System.out.println("Test Suite non ancora generato");
			}					
		}
		else{
			System.out.println("Modello non ancora caricato");
		}			
	}

	public static void salvaModelloOggetto(Modello modello) {	
		
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

	public static void salvaModelloTesto(Modello modello) {
		
		String nomeFile = InputDati.leggiStringa("Quale nome vuoi dare al file da salvare? (ESTENSIONE APPLICATA AUTOMATICAMENTE .TXT) > ");
		String loc = cartellaModelliTesto + File.separator + nomeFile + ".txt";
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

	/**
	 * Carica un modello salvato come oggetto da File.
	 * @param file File contentente l'oggetto.
	 * @return Il modello caricato.
	 */
	public static Modello caricaModelloOggetto(File file) {
		
		if(file.exists()){
			try{
				CostruzioneModello.modelloCaricato = (Modello) ServizioFile.caricaSingoloOggetto(file);
			}
			catch(ClassCastException exc){
				System.out.println("Errore Cast");
			}
		}
		return CostruzioneModello.modelloCaricato;
	}

	public static void creazioneCartelleDefault() {
		File folderDest = new File(cartellaStatisticheModello);
		if(!folderDest.exists()){
			folderDest.mkdirs();
		}
		
		File folderDestModelliOggetto = new File(cartellaModelliOggetto);
		if(!folderDestModelliOggetto.exists()){
			folderDestModelliOggetto.mkdirs();
		}
		
		File folderDestModelliTesto = new File(cartellaModelliTesto);
		if(!folderDestModelliTesto.exists()){
			folderDestModelliTesto.mkdirs();
		}
	}

}
