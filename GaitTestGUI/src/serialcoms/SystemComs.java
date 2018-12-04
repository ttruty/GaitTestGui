package serialcoms;

import java.util.ArrayList;
import java.util.List;

import models.Device;

public class SystemComs {
	private ArrayList<String> comList = new ArrayList<String>();
	
	
	public ArrayList<String> getComList() {
		return comList;
	}
	
	public void setComList() {
		String usbPath = "SYSTEM\\CurrentControlSet\\Enum\\USB\\";
	    Device device = new Device();
	    List<String> ls = device.listDevice(usbPath);
	    for (String l : ls) {
	    	String p = usbPath + l.toString();
//	    	System.out.println(usbPath + l.toString());
	    	if (l.toString().contains("VID_04D8")) { //only find the vendor Id for target device
//	    		System.out.println("<<<<<Found DEVICE>>>>");
	    		List<String> ds = device.listDevice(p);
		    	for (String k : ds) {
		    		String device1=p + "\\" + k.toString();
	//	    		System.out.println(device1);
	//	    		 System.out.println("First Friendly device: " + device.getFriendlyName(device1));
	//	    		 System.out.println("First COM device: " + device.getComNumber(device1));
		    		 if ( device.getComNumber(device1) != -1) {
		    			 comList.add("COM"+ device.getComNumber(device1));
		    		 }
		    	}
	    	}
		this.comList = comList;
	    }
	}

}
