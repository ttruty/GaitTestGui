package serialcoms;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import models.Recording;

public class ProtocolImpl implements Protocol {  
   
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
            
            System.out.println(message);  
            if (message.contains("OK")) {              	
            	System.out.println("CONNECTION MADE");
                //CommPortSender.send(getMessage("TIME"));
            	buffWait(5000);      
            
            	CommPortSender.send(getMessage("recording"));
            	if (message.contains("RECORDING=1")) {
            		System.out.println("Already Recording >>>");
            	} else {
            		System.out.println("NOT recording");
            		startRecording();
            	}
                                           
                // change running state 
               ComConnect.closeCom();
                //CommPortSender.send(getMessage("LED=6"));
                
            }
            else if (message.contains("denied")) {  
            	System.out.println("Error: Unplug then Reconnect Device");
            }
            
            else {
            	System.out.println("Connection not made");
            }
            tail = 0;
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
    public void startRecording() {
    	CommPortSender.send(getMessage("FORMAT Q"));
        
        // wait for format to complete 
        // buffwait is to not confuse bufferreader on device as not sure of limit
        buffWait(10000);			
        // set filename
        CommPortSender.send(getMessage("File=" + Recording.getRecordingId() + ".OMX")); 
        buffWait(500);
        
        
        // set time on device
        LocalDateTime timeSet = LocalDateTime.now();
        DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("yyyy-MM-dd,HH:mm:ss");
        String time = timeSet.format(formatTime);
        System.out.println("SYNCING TIME ON DEVICE: " + time);
        CommPortSender.send(getMessage("TIME " + time));
        buffWait(50);
        
        //setSettings for device
        CommPortSender.send(getMessage("Start=0"));
        buffWait(50);
        CommPortSender.send(getMessage("STOP=-1"));
        buffWait(50);
        CommPortSender.send(getMessage("Session 0")); 
        buffWait(50);
        CommPortSender.send(getMessage("Hibernate 0"));
        buffWait(50);
        CommPortSender.send(getMessage("Stop -1")); 
        buffWait(50);
        CommPortSender.send(getMessage("ANNOTATE00=")); 
        buffWait(50);
        CommPortSender.send(getMessage("ANNOTATE01=")); 
        buffWait(50);
        CommPortSender.send(getMessage("ANNOTATE02=")); 
        buffWait(50);
		CommPortSender.send(getMessage("ANNOTATE03=")); 
        buffWait(50);
        CommPortSender.send(getMessage("ANNOTATE04=")); 
        buffWait(50);
        CommPortSender.send(getMessage("ANNOTATE05=")); 
        buffWait(50);
        // commit save setting on deive
        CommPortSender.send(getMessage("COMMIT")); 
        buffWait(5000);
        
        System.out.println("STARTING RECORDING");     
        // recording command
        CommPortSender.send(getMessage("RECORDING=1"));
        buffWait(5000);
        
        
        Recording.setRecordingState(true);
        
    }
}  