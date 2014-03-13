/**
 * 
 */
package unibsSWEngineering.analisi;

import java.util.Vector;

/**
 * @author root
 *
 */
public class CalcoloProbabilita2 {

	/**
	 * @param args
	 */
	/*
	public static void main(String[] args) {
		//costruzione degli oggetti tipo prova(esempi del PDF relativo)
		
		Vector<String> azioniTotali = new Vector<String>();


		azioniTotali.add("A4");
		azioniTotali.add("A5");
		azioniTotali.add("A6");
		azioniTotali.add("A1");
		azioniTotali.add("A2");
		azioniTotali.add("A3");
		azioniTotali.add("A7");
		azioniTotali.add("A9");
		azioniTotali.add("A8");

		
		Prova p1 = new Prova("1");
		p1.addPercorso(new Cammino("A1,A3,A4,A5,A7,A8,A9",false));
		p1.addPercorso(new Cammino("A1,A3,A4,A5",true));
		p1.addPercorso(new Cammino("A1,A3,A4,A7",true));
		Prova p2 = new Prova("2");
		p2.addPercorso(new Cammino("A1,A3,A4,A5,A7,A8,A9",false));
		p2.addPercorso(new Cammino("A1,A3,A4,A5",false));
		p2.addPercorso(new Cammino("A1,A3,A4,A7",true));
		Prova p3 = new Prova("3");
		p3.addPercorso(new Cammino("A1,A2,A3,A4,A6,A7,A8,A9",false));
		p3.addPercorso(new Cammino("A1,A2",true));
		p3.addPercorso(new Cammino("A1,A2,A3,A4,A6",true));
		
		//classi di equivalenza
		//QUESTA PARTE DOVRA' ESSERE ORGANIZZATA PER UN RIEMPIMENTO AUTOMATICO
		ClasseEquivalenza cl1 = new ClasseEquivalenza("Classe 1",azioniTotali);
		cl1.setIstanzaProva(p1);
		cl1.setCardinalita(1);
		ClasseEquivalenza cl2 = new ClasseEquivalenza("Classe 2",azioniTotali);
		cl2.setIstanzaProva(p2);	
		cl2.setCardinalita(1);		
		ClasseEquivalenza cl3 = new ClasseEquivalenza("Classe 3",azioniTotali);
		cl3.setIstanzaProva(p3);
		cl3.setCardinalita(1);		
		
		//aggiunta ad un test suite 
		TestSuite ts1 = new TestSuite(azioniTotali);
		ts1.addNuovaClasseEquivalenza(cl1);
		ts1.addNuovaClasseEquivalenza(cl2);
		ts1.addNuovaClasseEquivalenza(cl3);	

		
		ts1.calcolaProbabilitaM1();		
		ts1.calcolaProbabilitaM2();
		
		System.out.println(ts1);
		
		Distanze dist = new Distanze(ts1);
		dist.calcoloDistanze();
		System.out.println(dist);	
	}
*/
}
