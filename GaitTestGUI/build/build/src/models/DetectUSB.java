package models;
import java.io.*;
import javax.swing.filechooser.FileSystemView;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import serialcoms.ComPort;
import serialcoms.SystemComs;

public class DetectUSB implements Runnable
{
	public volatile BooleanProperty isConnected = new SimpleBooleanProperty();
	public volatile String comPort;
	
	//private ArrayList<String> systemComs;
	private String driveLetter;
	private String driveName;
	
	private SaveOMX saveObj = new SaveOMX();
	
	SystemComs systemComs = new SystemComs();
	
	//public static int connectionCount = 0
	
	@Override
	public void run() {		
		
		// TODO Auto-generated method stub
		String[] letters = new String[]{ "A", "B", "C", "D", "E", "F", "G", "H", "I", "Z"};
        File[] drives = new File[letters.length];
        boolean[] isDrive = new boolean[letters.length];
        
        FileSystemView fsv = FileSystemView.getFileSystemView();
        
        for ( int i = 0; i < letters.length; ++i )
            {
            drives[i] = new File(letters[i]+":/");

            isDrive[i] = drives[i].canRead();
            
            }
         while(true)
            {
            for ( int i = 0; i < letters.length; ++i )
                {
                boolean pluggedIn = drives[i].canRead();                
                setDriveName(fsv.getSystemDisplayName(drives[i]));
                            	

                if ( pluggedIn != isDrive[i] )
                    {
                    if ( pluggedIn )
                    {
                        System.out.println("Drive "+ letters[i] +" has been plugged in");
                        System.out.println("Drive name: " + getDriveName());
                  	  	
                        setIsConnected(true);
                        Recording.setIsConnected(true);
                        setDriveLetter(letters[i]);
                        saveObj.setSaveDriveLetter(letters[i]);
                        saveObj.setSaveDriveName(getDriveName());
                        
                        //String keyPath = "SYSTEM\\CurrentControlSet\\Enum\\USB\\VID_04D8&PID_0057&MI_01\\";
                        //String device1 = "6&227ba45a&0&0001";

                        //Device USB\VID_04D8&PID_0057&MI_01\6&227ba45a&0&0001 
                        //Last Device Instance Id: USB\VID_04D8&PID_0057\5&f4d1e07&0&2    

                        //connectionCount++;
                    }

                    else {
                    	System.out.println("Drive "+letters[i]+" has been unplugged");
                    	setIsConnected(false);
                    	Recording.setIsConnected(false);
                    	
                    }
                    isDrive[i] = pluggedIn;
                    }
                }
            Recording.setSaveObj(saveObj);
            // to not overload buffer
            try { Thread.sleep(50); }
            catch (InterruptedException e) { }
            }
         
        
	}

	public boolean getIsConnected() {
		return isConnected.get();
	}

	public void setIsConnected(boolean value) {
		isConnected.set(value);

	}
	
	public BooleanProperty connectedProperty() {
		return isConnected;
	}

	public String getDriveLetter() {
		return driveLetter;
	}

	public void setDriveLetter(String driveLetter) {
		this.driveLetter = driveLetter;
	}
	
	public String getDriveName() {
		return driveName;
	}

	public void setDriveName(String driveName) {
		this.driveName = driveName;
	}
}