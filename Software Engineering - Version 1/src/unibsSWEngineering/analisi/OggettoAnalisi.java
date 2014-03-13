/**
 * 
 */
package unibsSWEngineering.analisi;

import java.util.StringTokenizer;
import java.util.Vector;

/**
 * @author root
 *
 */
public class OggettoAnalisi implements Comparable<OggettoAnalisi> {
	
	private String identificatori;
	private double probabilita;
	private int extInf;
	private int extSup;
	
	public OggettoAnalisi(String identificatore, double probabilita){
		identificatori = identificatore;
		this.probabilita = probabilita;
		this.extInf = 1;
		extSup = 1;
	}	
	
	public OggettoAnalisi(String identificatore, double probabilita,int extInf){
		identificatori = identificatore;
		this.probabilita = probabilita;
		this.extInf = extInf;
		extSup = 1;
	}
	
	public void setID(String ID){
		if(!ID.equalsIgnoreCase(this.identificatori))
			identificatori =  ID + "," + identificatori;
	}
	
	public int getExtInf(){
		return extInf;
	}
	
	public int getExtSup(){
		return extSup;
	}
	
	public double getProbabilita(){
		return probabilita;
	}
	
	public String getIDs(){
		return identificatori;
	}
	
	public void setExtInf(int ext){
		extInf = ext;
	}
	
	public void setExtSup(int ext){
		extSup = ext;
	}
	
	public void incrementaSup(){
		extSup++;
	}
	
	public void incrementaInf(){
		extInf++;	
	}	
	
	public void shiftIntevallo(int offset){
		extInf += offset;
		extSup += offset;
	}

	
	@Override
	public int compareTo(OggettoAnalisi altroOgg) {
		
		if(probabilita > altroOgg.getProbabilita()){
			return -1;		
		}
		else if (probabilita < altroOgg.getProbabilita()){
			return 1;
		}
		else{
			altroOgg.setID(this.identificatori);
			return 0; //---> questa va aggiunta quando non si vogliono replicati!!!
		}
	}
	
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("Nome: " + identificatori.toString()+ "\n");
		buffer.append("Probabilita': "+ probabilita + "\n");
		buffer.append("Intervallo : ["+extInf+","+extSup+"]\n");
		return buffer.toString();
	}

}
