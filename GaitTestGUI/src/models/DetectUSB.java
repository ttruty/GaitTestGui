/**
 * Data model for every time a usb is connected
 * 
 * @author Tim Truty
 *
 */

package models;
import java.io.*;
import javax.swing.filechooser.FileSystemView;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import serialcoms.SystemComs;

public class DetectUSB
{
	public volatile BooleanProperty isConnected = new SimpleBooleanProperty();
	public volatile String comPort;
	
	//private ArrayList<String> systemComs;
	private String driveLetter;
	private String driveName;
	private String dynaportDriveLetter;
	private String targetDriveName = "MMT";
	
	
	private SaveOMX saveObj = new SaveOMX();
	
	SystemComs systemComs = new SystemComs();
	
	//public static int connectionCount = 0
	
	public void run() {		
		
		// TODO Auto-generated method stub
		String[] letters = new String[]{ "A", "B", "C", "D", "E", "F", "G", "H", "I", "Z"};
        FileSystemView fsv = FileSystemView.getFileSystemView();
        
        for ( int i = 0; i < letters.length; ++i )
        {
        	File drive = this.checkFileCanRead(letters[i]);
        	String driveName = fsv.getSystemDisplayName(drive);
        	if (drive != null && driveName.contains(targetDriveName)) {
        		System.out.println("Drive "+ letters[i] +" has been plugged in");        		System.out.println("Drive name: " + getDriveName());
        		setDriveName(driveName);
        		setDriveLetter(letters[i]);
        		
        		setIsConnected(true);        		Recording.setIsConnected(true);        		saveObj.setSaveDriveLetter(letters[i]);        		saveObj.setSaveDriveName(getDriveName());        		Recording.setSaveObj(saveObj);        		break;        	}
        }  
	}
	
	private File checkFileCanRead(String letter) {
		File drive = new File(letter +":/");
		boolean isDrive = drive.canRead();
		if (isDrive) {
			return drive;
		}
		else {
			return null;
		}
	}
	
	public boolean checkTargetDriveConnected() {
		if (this.checkFileCanRead(this.driveLetter) != null) {
			return true;
		} else {
			return false;
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