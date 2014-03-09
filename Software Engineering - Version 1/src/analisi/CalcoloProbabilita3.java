/**
 * 
 */
package analisi;

import java.util.Vector;

/**
 * @author root
 *
 */
public class CalcoloProbabilita3 {

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
	 
		
		//altri
		Prova p1 = new Prova("1");
		p1.addPercorso(new Cammino("A1,A3,A4,A5",false));
		p1.addPercorso(new Cammino("A1,A3",false));
		Prova p2 = new Prova("2");
		p2.addPercorso(new Cammino("A1,A3,A4,A5",false));
		p2.addPercorso(new Cammino("A1",true));		
		Prova p3 = new Prova("3");
		p3.addPercorso(new Cammino("A1,A3,A4,A5",false));
		p3.addPercorso(new Cammino("A1",true));
		p3.addPercorso(new Cammino("A1,A3,A4",false));
		Prova p4 = new Prova("4");
		p4.addPercorso(new Cammino("A3,A4",false));
		p4.addPercorso(new Cammino("A1,A2,A4",false));	
		p4.addPercorso(new Cammino("A2,A3,A4",false));
		Prova p5 = new Prova("5");
		p5.addPercorso(new Cammino("A1,A2,A3,A4,A6",false));
		p5.addPercorso(new Cammino("A1",true));
		p5.addPercorso(new Cammino("A1,A2,A3",true));
		
		

		//classi di equivalenza
		//QUESTA PARTE DOVRA' ESSERE ORGANIZZATA PER UN RIEMPIMENTO AUTOMATICO
		ClasseEquivalenza cl1 = new ClasseEquivalenza("Classe 1",azioniTotali);
		cl1.setIstanzaProva(p1);
		cl1.setCardinalita(2);
		ClasseEquivalenza cl2 = new ClasseEquivalenza("Classe 2",azioniTotali);
		cl2.setIstanzaProva(p3);	
		cl2.setCardinalita(1);		
		ClasseEquivalenza cl3 = new ClasseEquivalenza("Classe 3",azioniTotali);
		cl3.setIstanzaProva(p4);
		cl3.setCardinalita(1);		
		ClasseEquivalenza cl4 = new ClasseEquivalenza("Classe 4",azioniTotali);
		cl4.setIstanzaProva(p5);
		cl4.setCardinalita(1);		
		
		
		//aggiunta ad un test suite 
		TestSuite ts1 = new TestSuite(azioniTotali);
		ts1.addNuovaClasseEquivalenza(cl1);
		ts1.addNuovaClasseEquivalenza(cl2);
		ts1.addNuovaClasseEquivalenza(cl3);	
		//ts1.addNuovaClasseEquivalenza(cl4);	
		
		ts1.calcolaProbabilitaM1();		
		ts1.calcolaProbabilitaM2();
		
		System.out.println(ts1);
		
		Distanze dist = new Distanze(ts1);
		dist.calcoloDistanze();
		System.out.println(dist);	

	}
*/
}
