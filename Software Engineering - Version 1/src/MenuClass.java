import it.unibs.fp.mylib.*;
import analisi.*;

public class MenuClass {

	private static Modello modello;
	private static TestSuite ts1;
	private static Distanze dist;
	
	/*Metodo Boolean: un tipo di possibilita' per creare un menu. Il ciclo deve essere fatto da un metodo esterno*/
	public static boolean menuPrincipale(){
		final String TITOLO = "MENU PRINCIPALE";
		final String [] VOCI = {"Creazione Modello", "Caricamento Modello", "Diagnosi e Test", "Probabilita'", "Visualizza modello"};
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
				caricaModello();
				/*
				modello = CostruzioneModello.caricaModello();
				if(modello.controllaModello()){
					System.out.println("Modello corretto");
				}
				else{
					System.out.println("Modello errato");
				}*/
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
			case 5: System.out.println(modello.stampaModello());
			default:
				/*Non entra mai qui*/
		}
		
		return false;
	}

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

	private static void caricaOggetto() {
		modello = CostruzioneModello.caricaModelloOggetto();
		if(modello.controllaModello()){
			System.out.println("Modello corretto");
		}
		else{
			System.out.println("Modello errato");
		}
	}

	private static void caricaTesto() {
		modello = CostruzioneModello.caricaModello();
		if(modello!=null)
			if(modello.controllaModello()){
				System.out.println("Modello corretto");
			}
			else{
				System.out.println("Modello errato");
			}
	}

	//per ora provo a fare tutto static come dice Falcon...
	private static Modello creaModello() {
		String nomeModello = InputDati.leggiStringa("Inserisci il nome del nuovo modello > ");
		return CreazioneModelloCopia.creaModello(nomeModello);
		
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
