package serialcoms;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import models.Recording;

public class ProtocolImplStop implements Protocol {  
   
    byte[] buffer = new byte[1024];  
    int tail = 0;  
    
    //Recording recording = new Recording();
      
    @Override
	public void onReceive(byte b) {  
        // simple protocol: each message ends with new line  
        if (b=='\n') {  
            onMessage();  
        } else {  
            buffer[tail] = b;  
            tail++;  
        }  
    }  
   
    @Override
	public void onStreamClosed() {  
        onMessage();
        
    }  
      
    /* 
     * When message is recognized onMessage is invoked  
     */  
    private void onMessage() {  
        if (tail!=0) {  
            // constructing message  
            String message = getMessage(buffer, tail);  
            
            //uncomment to see received messages
            System.out.println("RECEIVED MESSAGE: " + message);  
              
            // this logic should be placed in some kind of   
            // message interpreter class not here  
            
            //System.out.println(message.contains("OK"));  
            if (message.contains("OK")) {              	
            	System.out.println("CONNECTION MADE");
                //CommPortSender.send(getMessage("TIME"));
            	buffWait(10000);      
            	stopRecording();                  
                buffWait(10000);
                // change running state 
                ComConnect.closeCom();
                
            }
            else if (message.contains("denied")) {  
            	System.out.println("Error: Unplug then Reconnect Device");
            }
            tail = 0;
        }
        else {
        	return;
        }
    }  
      
    // helper methods   
    public byte[] getMessage(String message) {  
        return ("\n\r" + message+"\r\n").getBytes();  
    }  
      
    public String getMessage(byte[] buffer, int len) {  
        return new String(buffer, 0, tail);  
    }  
    
    public void buffWait(long millis) {
    	try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    
    public void stopRecording() {
    	
    	LocalDateTime timeSet = LocalDateTime.now();
        DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("yyyy-MM-dd,HH:mm:ss");
        String time = timeSet.format(formatTime);
        
        System.out.println("STOPPING RECORDING");     
        // recording command
        CommPortSender.send(getMessage("RECORDING=0"));
        buffWait(1000);
        CommPortSender.send(getMessage("Start=-1"));
        buffWait(1000);
        CommPortSender.send(getMessage("STOP " + time));
        buffWait(1000);
        CommPortSender.send(getMessage("LED=7")); 
        buffWait(1000);
        CommPortSender.send(getMessage("Commit")); 
        
        Recording.setRecordingState(false);
    }
}  