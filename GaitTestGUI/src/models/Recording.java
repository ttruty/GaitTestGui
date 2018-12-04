package models;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Recording {
	 
	private static String recordingId;
	private static String fuYear;
	private static String staffId;
	public volatile static BooleanProperty connectedBoolean = new SimpleBooleanProperty();
	
	private static Long recordingStart;
	private static LocalDateTime recordingStartTimeStamp;
	private static Long unixRecordingTimeStamp;
	private static boolean recordingState = false;
	private static boolean isConnected = false;
	private Long startTimeStamp;
	private ArrayList<Long>  markers;
	private static ArrayList<Marker> markerList;
	
	// saving the omx objects
	private static SaveOMX saveObj;
	
	
	
	public static String getRecordingId() {
		return recordingId;
	}
	public static void setRecordingId(String recordingId) {
		Recording.recordingId = recordingId;
	}
	public static boolean isRecording() {
		return recordingState;
	}
	public static void setRecordingState(boolean recordingState) {
		Recording.recordingState = recordingState;
	}
	
	public Long getStartTimeStamp() {
		return startTimeStamp;
	}
	public void setStartTimeStamp(Long startTimeStamp) {
		this.startTimeStamp = startTimeStamp;
	}
	public ArrayList<Long> getMarkers() {
		return markers;
	}
	public void setMarkers(ArrayList<Long> markers) {
		this.markers = markers;
	}
	public final static boolean isConnected() {
		return isConnected;
	}
	public static void setIsConnected(boolean connectionState) {
		
		System.out.println("Recording isConnected changed to: " + connectionState);
		connectedBoolean.set(connectionState);
		Recording.isConnected = connectionState;
	}
	public static ArrayList<Marker> getMarkerList() {
		return markerList;
	}
	public static void setMarkerList(ArrayList<Marker> markerList) {
		Recording.markerList = markerList;
	}
	public static Long getRecordingStart() {
		return recordingStart;
	}
	public static void setRecordingStart(Long recordingStart) {
		Recording.recordingStart = recordingStart;
	}
	public static String getStaffId() {
		return staffId;
	}
	public static void setStaffId(String string) {
		Recording.staffId = string;
	}
	public static String getFuYear() {
		return fuYear;
	}
	public static void setFuYear(String fuYear) {
		Recording.fuYear = fuYear;
	}
	
	public static BooleanProperty connectedProperty() {
		return connectedBoolean;
	}
	public static SaveOMX getSaveObj() {
		return saveObj;
	}
	public static void setSaveObj(SaveOMX saveObj) {
		Recording.saveObj = saveObj;
	}
	public static LocalDateTime getRecordingStartTimeStamp() {
		return recordingStartTimeStamp;
	}
	public static void setRecordingStartTimeStamp(LocalDateTime recordingStartTimeStamp) {
		Recording.recordingStartTimeStamp = recordingStartTimeStamp;		
		Recording.setUnixRecordingTimeStamp(recordingStartTimeStamp.atZone(ZoneId.systemDefault())
				.toInstant().toEpochMilli());
	}
	public static Long getUnixRecordingTimeStamp() {
		return unixRecordingTimeStamp;
	}
	public static void setUnixRecordingTimeStamp(Long unixRecordingTimeStamp) {
		Recording.unixRecordingTimeStamp = unixRecordingTimeStamp;
	}
	
	
}
