package unibsSWEngineering.modello;
import java.util.Vector;

/**
 * Interfaccia usata per accomunare Join e Merge.
 *
 */
public interface ElementoTerminale {
	
	public String getID();
	
	public void aggiungiIngresso(Elemento elem);
	
	public void aggiungiUscita(Elemento uscita);
	
	public Vector<Elemento> getIngressi();
	
	public Elemento getUscita();
	
}
