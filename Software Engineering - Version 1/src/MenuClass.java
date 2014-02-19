import java.io.File;

import it.unibs.fp.mylib.*;

public class MenuClass {

	
	
	/*Metodo Boolean: un tipo di possibilita' per creare un menu. Il ciclo deve essere fatto da un metodo esterno*/
	public static boolean menuPrincipale(){
		final String TITOLO = "MENU PRINCIPALE";
		final String [] VOCI = {"Creazione Modello", "Caricamento Modello", "Diagnosi e Test", "Probabilita'"};
		MyMenu menuPrincipale = new MyMenu(TITOLO, VOCI);
		int scelta = menuPrincipale.scegli();
		
		switch (scelta)
		{
			case 0: 
				return InputDati.yesOrNo("Vuoi veramente uscire?");
			case 1:
				//Scelta creazione guidata modello
				break;
			case 2:
				CostruzioneModello.caricaFile();
				break;
			case 3:
				//Diagnosi e test
				break;
			case 4:
				//Probabilita'
			default:
				/*Non entra mai qui*/
		}
		
		return false;
	}
	
	
	/*Metodo Void: altro tipo per creare un menu*/
	/*
	public static void menuPrincipale2(){
		boolean fine = false;
		final String TITOLO = "MENU PRINCIPALE 2";
		final String [] VOCI = {"SOTTOMENU 1", "SOTTOMENU 2", "SOTTOMENU 3"};
		MyMenu menuPrincipale = new MyMenu (TITOLO, VOCI);
		
		do{
			int scelta = menuPrincipale.scegli();
			switch (scelta)
			{
				case 0: 
					fine = true;
					break;
				case 1:
					//Scelta sottomenu 1
					break;
				case 2:
					//Scelta sottomenu 2
					break;
				case 3:
					//Scelta sottomenu 3
					break;
				default:
					//Non entra mai qui
			}
			
		}while(!fine);
	
	}
	*/
	
}