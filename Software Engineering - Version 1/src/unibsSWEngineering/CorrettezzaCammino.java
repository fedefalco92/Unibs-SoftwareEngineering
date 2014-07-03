package unibsSWEngineering;
import java.util.Vector;

import unibsSWEngineering.analisi.Cammino;
import unibsSWEngineering.modello.Azione;
import unibsSWEngineering.modello.Elemento;
import unibsSWEngineering.modello.ElementoMultiUscita;
import unibsSWEngineering.modello.Modello;


public class CorrettezzaCammino {
	
	/*Fatti da Fede*/
	//Non va una sega! Studio fisica che e' meglio!
	//LOOOPPPP DI ECCEZIONIIII
	public static boolean camminoOkOld(Elemento eleStart, Elemento eleEnd){
		
		if(eleStart == null || eleEnd == null)
			return false;
		
		//Piede della ricorsione
		if(eleStart.equals(eleEnd))
			return true;
		
		Elemento next;
		next = eleStart.getUscita();
	
		//Significa che ha piu' uscite
		if(next == null){
			for(Elemento e: eleStart.getUscite()){
				if(camminoOk(e, eleEnd))
					return true;
			}
		}
		//Un'unica uscita
		else{
			if(camminoOk(next, eleEnd))
				return true;
		}
		return false;
	}
	
	/*SEMBRA FUNZIONARE!!! SIIII*/
	public static boolean camminoOk(Elemento eleStart, Elemento eleEnd){
		return camminoAux(eleStart, eleEnd, null);
	}
	
	private static boolean camminoAux(Elemento eleStart, Elemento eleEnd, Elemento blocked){
		
		if(eleStart == null || eleEnd == null)
			return false;
		
		//Piede della ricorsione
		if(eleStart.equals(eleEnd))
			return true;
		
		Elemento next;
		next = eleStart.getUscita();
		
		if(next == null){
			if(blocked != null){
				return true;
			}else{
				for(Elemento e: eleStart.getUscite()){
					
					if(camminoAux(e, eleEnd, eleStart)){
						if(eleStart.equals(blocked))
							break;
					}
					return true;
				}
				return false;
			}
			
		} 
		else{
			if(camminoAux(next, eleEnd, blocked))
				return true;
		}
		return false;
	}
	
	
	
	/*Fatto da Maffi*/
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
			Azione a = (Azione) modello.ricercaElementoInModello("AZIONE", nome);
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
		 * OPPURE che contiene unfork come uscita e cosi' via..che bel casino! potrebbero esserci componenti intermedi all'infinito
		 * se e' un fork deve contere a2 come uscita OPPURE un merge o un branch per cui valgono le considerazioni fatte sopra.
		 * 
		 * SERVE UNA COSA RICORSIVA
		 */
		
		Elemento uscitaA1 = a1.getUscita();
		if(uscitaA1.getID().equals("AZIONE")){
			if (uscitaA1.equals(a2))
				return true;
			else return false;
		} else 
			return azioneRaggiungibileDaElementoIntermedio(modello, uscitaA1, a2); //di sicuro uscitaA1 non e' un'azione
			
		/* OPPURE
		boolean out = false;
		Elemento uscitaA1 = a1.getUscita();
		String ID = uscitaA1.getID();
		switch(ID){
			case "AZIONE":
				if (uscitaA1.equals(a2)){
					out = true;
				}
				break;
			default: 
				out = azioneRaggiungibileDaElementoIntermedio(modello, uscitaA1, a2);
		}
		
		return out;
		*/
		
		
	}

	private static boolean azioneRaggiungibileDaElementoIntermedio(Modello modello, Elemento e, Azione a) {
		boolean out = false;
		String ID = e.getID();
		switch(ID){
			case "MERGE":
			case "JOIN":
				out = azioneRaggiungibileDaElementoIntermedio(modello, e.getUscita() , a);
				break;
			case "BRANCH": 
			case "FORK":
				Vector<Elemento> uscite= ((ElementoMultiUscita) e).getUscite();
				if(uscite != null)
					for(Elemento el: uscite){
						out = azioneRaggiungibileDaElementoIntermedio(modello, el, a);
						if (out) break;
					}
				break;
			//ARRIVO A QUESTO CASE SOLO DOPO ESSERE PASSATO ALMENO DA UN ELEMENTO TRA QUELLI SOPRA!
			case "AZIONE":
				if(e.equals(a)) out = true;
				break;
		}
		
		return out;
	}
	/*
	private static Azione trovaAzione(Modello modello, String nome){
		for(Azione azione: modello.getAzioni()){
			if (azione.getNome().equals(nome)){
				return azione;
			}
		}
		return null;
	}
	*/
}
