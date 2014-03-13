import java.util.Vector;

import analisi.Cammino;


public class CorrettezzaCammino {
	
	/*
	 * parto dall'ipotesi che un cammino e' fatto di sole azioni, 
	 * da qui una serie di conseguenze come i nomi dei metodi
	 * e il fatto che cerchero' solo nel vector di azioni
	 */
	public static int camminoCorretto(Modello modello, Cammino cammino){
		Vector<String> nomiAzioni = cammino.estraiElementi();
		Vector<Azione> azioniCammino = new Vector<Azione>();
		
		//cerco le azioni partendo dal nome scansionando il vector di stringhe
		for(String nome: nomiAzioni){
			Azione a = trovaAzione(modello, nome);
			if (a==null)
				return 1; //primo codice di errore = azione inesistente
			else
			azioniCammino.add(a);
		}
		
		//a questo punto ho un vector di azioni tutte esistenti..
		//se inserisco due volte la stessa azione viene duplicata...
		//quindi cerco un modo di verificare che sia possibile effettuare il percorso
		if(!percorsoPossibile(modello, azioniCammino))
			return 2; //secondo codice di errore = percorso impossibile
		
		
		
		
		
		return 0;
	}

	private static boolean percorsoPossibile(Modello modello, Vector<Azione> azioniCammino){
			Azione a1;
			Azione a2;
			for(int i = 0; i < azioniCammino.size()-1; i++){
				a1=azioniCammino.get(i);
				a2=azioniCammino.get(i+1);
				if(!azioniConsecutive(modello, a1, a2)){
					return false;
				}
			}
			return true;
	}
	
	/**
	 * Dice se a2 &egrave raggiungibile a partire da a1
	 * @param modello
	 * @param a1
	 * @param a2
	 * @return
	 */
	private static boolean azioniConsecutive(Modello modello, Azione a1, Azione a2) {
		
		/*
		 * Analizzo l'uscita di a1.
		 * 
		 * se e' un'azione deve essere uguale ad a2
		 * se e' un branch deve contenere a2 tra le proprie uscite.
		 * se e' un merge deve avere a2 come uscita OPPURE avere come uscita un branch che contiene a2 come uscita.
		 * se e' un fork deve contere a2 come uscita OPPURE un merge o un branch per cui valgono le considerazioni fatte sopra.
		 * se
		 * 
		 */
		
		return false;
	}

	private static Azione trovaAzione(Modello modello, String nome){
		for(Azione azione: modello.getAzioni()){
			if (azione.getNome().equals(nome)){
				return azione;
			}
		}
		return null;
	}
}
