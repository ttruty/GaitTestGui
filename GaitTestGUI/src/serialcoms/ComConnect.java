package serialcoms;

import java.io.IOException;
import java.util.ArrayList;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;  
   
public class ComConnect { 
	
	static ArrayList<String> comPortList = null;
	static  SerialPort serialPort = null;
	public boolean running = true;
	public volatile static BooleanProperty plugAlert = new SimpleBooleanProperty(false);
	private String accessComPort;

	public void connect(String portName, Protocol protocol) {  
    	setRunning(true);
    	
        CommPortIdentifier portIdentifier = null;
		try {
			portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
		} catch (NoSuchPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
   
        if (portIdentifier.isCurrentlyOwned()) {  
            System.out.println("Port in use!");  
        } else {  
            // points who owns the port and connection timeout  
           
			try {
				serialPort = (SerialPort) portIdentifier.open("ComConnect", 2000);
			} catch (PortInUseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
              
            // setup connection parameters  
            try {
				serialPort.setSerialPortParams(  
						38400, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			} catch (UnsupportedCommOperationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
   
            // setup serial port writer  
            try {
				CommPortSender.setWriterStream(serialPort.getOutputStream());
				new CommPortReceiver(serialPort.getInputStream(), protocol).start();  
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
              
            // setup serial port reader  
            
        }  
    }  
    
    public static void disconnect(SerialPort port) {
    	CommPortIdentifier portIdentifier = null;
    	
		try {
			portIdentifier = CommPortIdentifier.getPortIdentifier(port);
		} catch (NoSuchPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		if (portIdentifier.isCurrentlyOwned()) {  
            System.out.println("Port in use!"); 
            port.close();
            
		}  
    }    
      
	public Runnable makeConnection() 
    {  
    	ComPort com = new ComPort();
   	  	comPortList = com.getCom();
//   	  	for (String comPort : comPortList) {
//   	  		System.out.println("TRYING ACCESSING ON:" + comPort);	
//   	  	}
   	  	
   	  	SystemComs systemComs = new SystemComs();
   	  	systemComs.setComList();
   	  	ArrayList<String> sysComsList = systemComs.getComList();
//   	  	for (String sysPort: sysComsList) {
//   	  		System.out.println("Sysyem Coms: :" + sysPort);	
//   	  	}
   	  	
   	  	ArrayList<String> common = new ArrayList<String>(comPortList);
   	  	common.retainAll(sysComsList);
   	  	//for (String commonPort : common) {
   	  	if (common.size() == 1)
   	  	{ 
   	  		this.setAccessComPort(common.get(0));
   	  		System.out.println("ACCESS COM PORT:" + accessComPort);
   	  		new ComConnect().connect(this.getAccessComPort(), new ProtocolImpl());
   	  		CommPortSender.send(new ProtocolImpl().getMessage("SAMPLE=1"));
   	  		

   	  		CommPortSender.send(new ProtocolImpl().getMessage("AT"));
		}
   	  	else if (common.isEmpty()) {
   	  		System.out.println("NO USABLE COMM PORT CONNECTION");
   	  	}
   	  	else if (common.size() > 1)
   	  	{
   	  		System.out.println("TOO MANY COMM PORT CONNECTIONS");
   	  	}
   	 return null;
   	 }  
    
	public Runnable stopRecording() 
    {      	
        new ComConnect().connect(this.getAccessComPort(), new ProtocolImplStop());
    	CommPortSender.send(new ProtocolImplStop().getMessage("AT"));    
    	 plugAlert.set(false);
		    
		return null;

    }  
    
    
    
    public static void closeCom() {
    	if (serialPort != null) {
    		
		    System.out.println("DISCONNECTING");
		    disconnect(serialPort);
		    
		    plugAlert.set(true);
		   
	    }
    }
    
    public boolean isRunning() {
    	return running;
    }
    
    public void setRunning(boolean value)
    {
    	running = value;
    }
    
	public boolean getPlugAlert() {
		return plugAlert.get();
	}

	public void setPlugAlert(boolean value) {
		plugAlert.set(value);

	}
	
	public BooleanProperty connectedProperty() {
		return plugAlert;
	}
    
	public String getAccessComPort() {
		return accessComPort;
	}

	public void setAccessComPort(String accessComPort) {
		this.accessComPort = accessComPort;
	}
           
}  