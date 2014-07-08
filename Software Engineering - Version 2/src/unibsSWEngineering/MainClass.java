package unibsSWEngineering;


public class MainClass {

	public static void main(String[] args) {
		System.out.println("Unibs Software Engineering");
		GestioneFiles.creazioneCartelleDefault();
		boolean fineProgramma = false;
		
		do {
			fineProgramma = MenuClass.menuPrincipale();
		} while (!fineProgramma);
		
		System.out.println("Arrivederci!");
	}

}
