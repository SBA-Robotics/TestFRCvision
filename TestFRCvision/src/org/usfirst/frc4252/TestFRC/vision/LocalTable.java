package org.usfirst.frc4252.TestFRC.vision;

import java.util.Hashtable;

/**
 * Used to share vision and other intermediate processing results and share between components.
 * Change this to NetworkTable based if the vision processing is not local on RoboRio.
 */

public class LocalTable {
		
	private LocalTable(){
		
	}
	
	public static LocalTable getInstance(){
		if (instance == null ){
			instance = new LocalTable();
		}
		return instance;
	}
	
	private static LocalTable instance ;
	
	
    private Hashtable<String, Object> localTable = new Hashtable<String, Object>();
    
    public void putInTable( String key, Object value){
    	localTable.put(key, value);
    }
    
    public Object getFromTable(String key){
    	return localTable.get(key);
    }


}
