package serialcoms;

import gnu.io.CommPortIdentifier;

import java.util.ArrayList;
import java.util.Enumeration;

public class ComPort {

	public ArrayList<String> getCom() {		
		ArrayList<String> comPortList = new ArrayList<String>();
		@SuppressWarnings("unchecked")
		Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
		
		while (portEnum.hasMoreElements()) {
		    CommPortIdentifier currPortId = portEnum.nextElement();
//		    System.out.println("AVAILABLE COMS:" + currPortId.getName());
//		    System.out.println("tYPE:" + currPortId.getPortType());
//		    
//		    System.out.println("OWENED:" + currPortId.isCurrentlyOwned());
//		    System.out.println("STRING:" + currPortId.toString());
		    comPortList.add( currPortId.getName());
		}
		
//		while (portEnum.hasMoreElements()) {
//		    CommPortIdentifier currPortId = portEnum.nextElement();
//		     currPortId.getName();
		    
		return comPortList;
	}
}
