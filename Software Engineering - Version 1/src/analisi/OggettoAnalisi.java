/**
 * 
 */
package analisi;

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
		//this.identificatori = new Vector<String>();
		//identificatori.addElement(identificatore);
		identificatori = identificatore;
		this.probabilita = probabilita;
		this.extInf = 1;
		extSup = 1;
	}	
	
	public OggettoAnalisi(String identificatore, double probabilita,int extInf){
		//this.identificatori = new Vector<String>();
		//identificatori.addElement(identificatore);
		identificatori = identificatore;
		this.probabilita = probabilita;
		this.extInf = extInf;
		extSup = 1;
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
	
	/*public Vector<String> getIdentificatori(){
		return identificatori;
	}
	
	public void addIdentificatore(String id){
		identificatori.addElement(id);
	}*/
	
	public void setExtInf(int ext){
		extInf = ext;
		//extSup = ext;
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
		//	this.incrementaInf();
			return 1;
			
		}
		else if (probabilita < altroOgg.getProbabilita()){
			//this.incrementaInf();
			return -1;
		}
		else{
			if(altroOgg != this)
				altroOgg.incrementaSup();
			/*for(String elem : identificatori){
				altroOgg.addIdentificatore(elem);
			}
			*/
			//altroOgg.setIdentificatore(identificatore)
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
