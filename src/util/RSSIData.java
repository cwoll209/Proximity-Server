package util;

import java.io.Serializable;

public class RSSIData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	public String uuid;
	public String minor;
	public String major;
	public double rssi;
	
	public RSSIData(){
		
	}
	
}
