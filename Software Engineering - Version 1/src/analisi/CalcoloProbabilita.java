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
	/*public static void main(String[] args) {
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
		p1.addCammino(new Percorso("A1,A3,A4,A5",false));
		p1.addCammino(new Percorso("A1",true));
		Prova p2 = new Prova("2");
		p2.addCammino(new Percorso("A1,A3,A4,A5",false));
		p2.addCammino(new Percorso("A1",true));		
		Prova p3 = new Prova("3");
		p3.addCammino(new Percorso("A1,A3,A4,A5",false));
		p3.addCammino(new Percorso("A1",true));
		p3.addCammino(new Percorso("A1,A3,A4",false));
		Prova p4 = new Prova("4");
		p4.addCammino(new Percorso("A1,A2,A3,A4,A6",false));
		p4.addCammino(new Percorso("A1",true));
		p4.addCammino(new Percorso("A1,A2,A3",true));
		
		//aggiunta ad un test suite 
		TestSuite ts1 = new TestSuite();
		ts1.addProva(p1);
		ts1.addProva(p2);
		ts1.addProva(p3);
		ts1.addProva(p4);
		
		//classi di equivalenza
		//QUESTA PARTE DOVRA' ESSERE ORGANIZZATA PER UN RIEMPIMENTO AUTOMATICO
		ClasseEquivalenza cl1 = new ClasseEquivalenza("Classe 1");
		cl1.setIstanzaProva(p1);
		ClasseEquivalenza cl2 = new ClasseEquivalenza("Classe 2");
		cl2.setIstanzaProva(p3);
		ClasseEquivalenza cl3 = new ClasseEquivalenza("Classe 3");
		cl3.setIstanzaProva(p4);	
		ts1.addNuovaClasseEquivalenza(cl1);
		ts1.addNuovaClasseEquivalenza(cl2);
		ts1.addNuovaClasseEquivalenza(cl3);
		
		 DiagnosiClasse diagC1 = new DiagnosiClasse(cl1,attivitaTotali);	 
		 diagC1.generaDiagnosi();
		 DiagnosiClasse diagC2 = new DiagnosiClasse(cl2,attivitaTotali);	 
		 diagC2.generaDiagnosi();
		 DiagnosiClasse diagC3 = new DiagnosiClasse(cl3,attivitaTotali);	 
		 diagC3.generaDiagnosi();
		 
		 diagC1.settaIdentificatorePosizione();		
		 diagC2.settaIdentificatorePosizione();		 
		 diagC3.settaIdentificatorePosizione();			 
	
		 
	}
	*/
}
