package unibsSWEngineering.modello;
import java.util.Vector;


public interface ElementoMultiUscita {
	
	public void eliminaUscita(Elemento uscita);
	public Vector<Elemento> getUscite();
	public void aggiungiUscita(Elemento e);
	
	//serve per il controllo correttezza del cammino
	public Vector<Elemento> getUsciteSenzaAzioni();

}
