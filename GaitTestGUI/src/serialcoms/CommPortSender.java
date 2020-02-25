package serialcoms;
import java.io.IOException;  
import java.io.OutputStream;  
   
public class CommPortSender {  
   
    static OutputStream out;  
      
    public static void setWriterStream(OutputStream out) {  
        CommPortSender.out = out;  
    }  
      
    public static void send(byte[] bytes) {  
        try { 
        	// uncomment to see serial sends
        	
            //System.out.println("SENDING: " + new String(bytes, 0, bytes.length));  
              
            // sending through serial port is simply writing into OutputStream  
            out.write(bytes);  
            out.flush();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }
        
    } 
    
}  