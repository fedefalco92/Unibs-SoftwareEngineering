package unibsSWEngineering;
/**
 * 
 */
import java.io.File;
import java.util.Vector;

import unibsSWEngineering.analisi.*;
import unibsSWEngineering.modello.Modello;
import it.unibs.fp.mylib.*;

/**
 * @author root
 *
 */
public class CostruzioneTest {

	
	private static Cammino generaCammino(Modello modello){
		final String TITOLO = "INSERIMENTO PERCORSO";
		final String VOCE_1  = "Inserisci percorso separato da ',' >";
		final String VOCE_2  = "Inserisci esito(0-KO\t1-OK) >";
	
		String percorso = InputDati.leggiStringaNonVuota(VOCE_1);
		
		//verifica del percorso gia' qui???
		
		int esito = InputDati.leggiIntero(VOCE_2, 0, 1);
		boolean esito_bool = false;
		if(esito == 1){
			esito_bool = true;
		}
		
		Cammino camminoTemp = new Cammino(percorso,esito_bool);
		if(CorrettezzaCammino.camminoCorretto(modello, camminoTemp) == 0){
			return camminoTemp;
		}
		
		return null;
		
	}
	
	private static Prova generaProva(Modello modello){
	//	final String VOCE_PROVA = "Inserire il nome della prova >";
		final String TITOLO_MENU = "INSERIMENTO PERCORSI";
		
	//	String nomeProva = InputDati.leggiStringaNonVuota(VOCE_PROVA);
		Prova prova = new Prova();
		
		final String[] VOCI_MENU = {"Aggiungi nuovo percorso "};
		MyMenu menuInserimentoPercorsi = new MyMenu(TITOLO_MENU,VOCI_MENU);
		menuInserimentoPercorsi.setVoceUscita("0\t Torna indietro");
		
		int scelta;
		do{
			scelta = menuInserimentoPercorsi.scegli();
			switch(scelta){
				case 0:{					
					break;
				}		
				case 1:{								
					Cammino camminoInserito = generaCammino(modello);
					if(camminoInserito != null){
						prova.addPercorso(camminoInserito);		
					}
					else{
						System.out.println("ATTENZIONE!!! Il cammino non e' valido");
					}
					System.out.println("Hai inserito " + prova.numeroCammini() + " cammini nella prova");					
					break;
				}
				
			}
		}
		while(scelta != 0);		
		return prova;		
	}
	
	private static ClasseEquivalenza generaClasseEquivalenza(Modello modello){
		final String VOCE_CLASSE = "Inserire il nome della classe di equivalenza >";
		final String VOCE_CARDINALITA = "Inserire la cardinalita' della classe di equivalenza >";
		final String TITOLO_MENU = "INSERIMENTO CLASSE DI EQUIVALENZA";
		
		
		String nomeClasse = InputDati.leggiStringaNonVuota(VOCE_CLASSE);
		int cardinalitaClasse = InputDati.leggiInteroConMinimo(VOCE_CARDINALITA, 1);
		
		ClasseEquivalenza clNuova = new ClasseEquivalenza(nomeClasse,modello.getNomiAzioni(),cardinalitaClasse);

		
		final String[] VOCI_MENU = {"Riempi la classe :  '"+ nomeClasse+"'"};
		MyMenu menuInserimento = new MyMenu(TITOLO_MENU,VOCI_MENU);
		menuInserimento.setVoceUscita("0\t Torna indietro");
		
		int scelta;
		do{
			scelta = menuInserimento.scegli();
			switch(scelta){
				case 0:{					
					break;
				}		
				case 1:{	
					Prova provaInserita = generaProva(modello);
					clNuova.setIstanzaProva(provaInserita);
					break;
				}
				
			}
		}
		while(scelta != 0);	
		return clNuova;		
	}
	
	public static void inserimentoClassiEquivalenza(Modello modello, TestSuite test_suite){
		final String TITOLO_MENU = "CREAZIONE CLASSI DI EQUIVALENZA";			
		final String[] VOCI_MENU = {"Aggiungi nuova classe di equivalenza"};
		MyMenu menuInserimentoClassiEq = new MyMenu(TITOLO_MENU,VOCI_MENU);
		menuInserimentoClassiEq.setVoceUscita("0\t Torna indietro");
		int scelta;
		do{
			scelta = menuInserimentoClassiEq.scegli();
			switch(scelta){
				case 0:{					
					break;
				}		
				case 1:{	
					ClasseEquivalenza classeNuova = generaClasseEquivalenza(modello);
					test_suite.addNuovaClasseEquivalenza(classeNuova);
					break;
				}
				
			}
		}
		while(scelta != 0);			
	}
	
	public static String patternNome(Modello modello){
		String nomeModello = modello.getNome();
		StringBuffer buffer = new StringBuffer(nomeModello);
		return (MenuClass.cartellaStatisticheModello+File.separator+"rilevazioni"+buffer.substring(0, nomeModello.length()-3)+"dat");
	}

	
}
