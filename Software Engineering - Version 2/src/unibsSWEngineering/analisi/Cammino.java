/**
 * 
 */
package unibsSWEngineering.analisi;

import java.io.Serializable;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * @author Massi
 *
 */

//N.B. Il percorso viene gia' fornito in maniera ordinata e validato dall'apposito metodo che 
//verra' predisposto nella classe Modello

public class Cammino implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5363675680889278477L;
	private String path;
	private boolean esito;
	
	public Cammino(String path,boolean esito){
		this.path = path;
		this.esito = esito;
	}
	
	public void setPath(String path){
		this.path = path;
	}
	
	public String getPath(){
		return path;
	}
	
	public void setEsito(boolean esito){
		this.esito = esito;
	}
	
	public boolean getEsito(){
		return esito;
	}
	
	/**
	 * Estrazione degli elementi dall'insieme del cammino
	 * @return
	 */
	
	public Vector<String> estraiElementi(){
		Vector<String> elementi = new Vector<String>();
		StringTokenizer analizzatore = new StringTokenizer(getPath(),",");
		while(analizzatore.hasMoreTokens()){
			elementi.add(analizzatore.nextToken());
		}
		return elementi;
	}
	
	/**
	 * Stampa degli esiti del cammino
	 */
	
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("Percorso : " + getPath() + "\n");
		buffer.append("Esito : " + getEsito() + "\n");
		return buffer.toString();
	}
}
