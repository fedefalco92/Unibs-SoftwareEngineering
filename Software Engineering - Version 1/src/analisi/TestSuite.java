/**
 * 
 */
package analisi;

import java.util.Vector;

/**
 * @author Massi
 *
 */
public class TestSuite {
	
	private Vector<Prova> prove;
	private Vector<ClasseEquivalenza> classiEquivalenza;
	
	public TestSuite(){
		prove = new Vector<Prova>();
		classiEquivalenza = new Vector<ClasseEquivalenza>();
	}
	
	public void addProva(Prova _prova){
		prove.add(_prova);
	}
	
	public void addNuovaClasseEquivalenza(ClasseEquivalenza classe){
		classiEquivalenza.add(classe);
	}	
	
	public Vector<Prova> getProve(){
		return prove;
	}
	
	public Prova getProvaById(String nome){
		for(Prova pr : prove){
			if(pr.getNome().equalsIgnoreCase(nome)){
				return pr;
			}
		}
		return null;
	}	
	
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("Prove del Test Suite\n\n");
		for(Prova pr : prove){
			buffer.append(pr.toString());
			buffer.append("\n");
		}
		return buffer.toString();
	}
	
	
}
