package serialcoms;


public class TestConnection implements Protocol {  
   
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
}  