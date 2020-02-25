package models;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import serialcoms.ComConnect;
import javafx.scene.control.Alert.AlertType;

public class ControlButton {
	
	public static void Stop() {

	   	Recording.setRecordingState(false);
	   	Alert alert = new Alert(AlertType.WARNING, 
                "Recording stopped, \n "
                + "Please plug in device to save", 
                ButtonType.OK);
  	  alert.showAndWait();
  	  
  	  
	}
	
	public static void Save(ComConnect com, AnchorPane basePane) throws NoSuchAlgorithmException, IOException {
		if(Recording.isConnected() || Recording.isDebugMode()) 
	    {
	    	
	    	//ring progress bar
	   	    RingIndicator.RingProgress(basePane);
	    			   	
	    	//write out to csv
		    WriteCSV writer = new WriteCSV();
		    try {
				writer.write();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    
		    if (Recording.isDebugMode())
		    {
		    	Recording.setRecordingState(true);
		    }    	    	
		   	
		   				   	
		   	if (!Recording.isDebugMode())
		   	{
	   			SaveOMX saveObj = Recording.getSaveObj();
			   	
			   	File rawSaveFile = saveObj.fileSearch(saveObj.getSaveDriveLetter());
			   	saveObj.setSaveFileName(writer.getBaseFilename()+ ".OMX");
			   	
			   	saveObj.saveFile(rawSaveFile);	
			   	
		   		com.stopRecording();
		   		
		   	}
		   	else {
		   		Alert alert = new Alert(AlertType.WARNING, 
	                      "Program is in DEBUG MODE, \n "
	                      + "only saved CSV FILE", 
	                      ButtonType.OK);
	        	  alert.showAndWait();
	        	  RingIndicator.removeRing(basePane);
		   	}
		   	
	    } else {
        	  Alert alert = new Alert(AlertType.WARNING, 
                      "Device Not connected, \n "
                      + "Unplug and reconnect", 
                      ButtonType.OK);
        	  alert.showAndWait();
        	  //System.out.println("Device not connected!!!");
        	  }
	}
}
