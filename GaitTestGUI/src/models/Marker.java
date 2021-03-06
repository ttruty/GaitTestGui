/**
 * Data model for markers object
 * 
 * @author Tim Truty
 *
 */
package models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Marker {
	
	private String label;
	private String timeStamp;
	private int count;
	private boolean isComplete;
	private String markerType;
	private Long timeDelta;
	private Long UnixTimeStamp;
	private Long UnixTimeStampNoDelay;
	private Long UnixTimeStampInSound;
	private int randomDelay;
	
	 public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setTimeStamp(long time) {
		  DateFormat timeFormat = new SimpleDateFormat( "HH:mm:ss:SSS" );
		  timeStamp = timeFormat.format( time );
	  }
	  
	  public String getTimeStamp() {
		  return timeStamp;
		  
	  }

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public boolean isComplete() {
		return isComplete;
	}

	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}

	
	public String getMarkerType() {
		return markerType;
	}

	public void setMarkerType(String markerType) {
		this.markerType = markerType;
	}
	

	public Long getTimeDelta() {
		return timeDelta;
	}

	public void setTimeDelta(Long timeDelta) {
		this.timeDelta = timeDelta;
	}

	public Long getUnixTimeStamp() {
		return UnixTimeStamp;
	}

	public void setUnixTimeStamp(Long unixTimeStamp) {
		UnixTimeStamp = unixTimeStamp;
	}
	
	public Long getUnixTimeStampNoDelay() {
		return UnixTimeStampNoDelay;
	}

	public void setUnixTimeStampNoDelay(Long unixTimeStampNoDelay) {
		UnixTimeStampNoDelay = unixTimeStampNoDelay;
	}
	
	public Long getUnixTimeStampInSound() {
		return UnixTimeStampInSound;
	}

	public void setUnixTimeStampInSound(Long unixTimeStampInSound) {
		UnixTimeStampInSound = unixTimeStampInSound;
	}

	public int getRandomDelay() {
		return randomDelay;
	}

	public void setRandomDelay(long delayTime) {
		this.randomDelay = (int) delayTime;
	}

	@Override
	public String toString() {
		return " [label=" + label + ", timeStamp=" + timeStamp + ", count=" + count + ", isComplete=" + isComplete
				+ ", markerType=" + markerType + ", randomDelay=" + randomDelay + "]";
	}

	
	  
	  
}
