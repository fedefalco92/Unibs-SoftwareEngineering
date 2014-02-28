/**
 * 
 */
package analisi;


import java.util.Map;
import java.util.Vector;


/**
 * @author Massi
 *
 */

public class CalcoloProbabilita {

	/**
	 * @param args
	 */
	
/*	
	public static void main(String[] args) {
		//PRIMO METODO DI CALCOLO DELLE PROBABILITA'
		//costruzione degli oggetti tipo prova(esempi del PDF relativo)
		
		Vector<String> attivitaTotali = new Vector<String>();
		attivitaTotali.add("A1");
		attivitaTotali.add("A2");
		attivitaTotali.add("A3");
		attivitaTotali.add("A4");
		attivitaTotali.add("A5");
		attivitaTotali.add("A6");
		
		Prova p1 = new Prova("1");
		p1.addPercorso(new Cammino("A1,A3,A4,A5",false));
		p1.addPercorso(new Cammino("A1",true));
		Prova p2 = new Prova("2");
		p2.addPercorso(new Cammino("A1,A3,A4,A5",false));
		p2.addPercorso(new Cammino("A1",true));		
		Prova p3 = new Prova("3");
		p3.addPercorso(new Cammino("A1,A3,A4,A5",false));
		p3.addPercorso(new Cammino("A1",true));
		p3.addPercorso(new Cammino("A1,A3,A4",false));
		Prova p4 = new Prova("4");
		p4.addPercorso(new Cammino("A1,A2,A3,A4,A6",false));
		p4.addPercorso(new Cammino("A1",true));
		p4.addPercorso(new Cammino("A1,A2,A3",true));
		
		//aggiunta ad un test suite 
		TestSuite ts1 = new TestSuite();
		ts1.addProva(p1);
		ts1.addProva(p2);
		ts1.addProva(p3);
		ts1.addProva(p4);
		
		//classi di equivalenza
		//QUESTA PARTE DOVRA' ESSERE ORGANIZZATA PER UN RIEMPIMENTO AUTOMATICO
		ClasseEquivalenza cl1 = new ClasseEquivalenza("Classe 1",attivitaTotali);
		cl1.setIstanzaProva(p1);
		cl1.setCardinalita(2);
		ClasseEquivalenza cl2 = new ClasseEquivalenza("Classe 2",attivitaTotali);
		cl2.setIstanzaProva(p3);	
		cl2.setCardinalita(1);		
		ClasseEquivalenza cl3 = new ClasseEquivalenza("Classe 3",attivitaTotali);
		cl3.setIstanzaProva(p4);
		cl3.setCardinalita(1);		
		
		cl1.probabilitaClasse();
		cl2.probabilitaClasse();
		cl3.probabilitaClasse();
		
		//ts1.addNuovaClasseEquivalenza(cl1);
		//ts1.addNuovaClasseEquivalenza(cl2);
		//ts1.addNuovaClasseEquivalenza(cl3);
		
		 
	}
	*/
}
