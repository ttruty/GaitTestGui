/**
 * Data model for Recording object
 * 
 * @author Tim Truty
 *
 */
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
	private static Long startButtonPressed;
	private static Long reconnectTime;
	private static boolean isDebugMode;
	private static LocalDateTime recordingStartTimeStamp;
	private static Long unixRecordingTimeStamp;
	private static boolean recordingState = false;
	private static boolean isConnected = false;
	private Long startTimeStamp;
	private ArrayList<Long>  markers;
	private static ArrayList<Marker> markerList;
	private static int batteryStatus;
	private static String batteryString;
	
	// saving the omx objects
	private static SaveOMX saveObj;
	protected static boolean isSaved;
	
	public static String getRecordingId() {
		return recordingId;
	}
	public static void setRecordingId(String recordingId) {
		Recording.recordingId = recordingId;
	}
	public static boolean isRecording() {
		return recordingState;
	}
	public static boolean isSaved() {
		return isSaved;
	}
	public static void setSaved(boolean isSaved) {
		Recording.isSaved = isSaved;
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
	public static Long getStartButtonPressed() {
		return startButtonPressed;
	}
	public static void setStartButtonPressed(Long startButtonPressed) {
		Recording.startButtonPressed = startButtonPressed;
	}
	
	public static Long getReconnectTime() {
		return reconnectTime;
	}
	public static void setReconnectTime(Long reconnectTime) {
		Recording.reconnectTime = reconnectTime;
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
	public static boolean isDebugMode() {
		return isDebugMode;
	}
	public static void setDebugMode(boolean isDebugMode) {
		Recording.isDebugMode = isDebugMode;
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
	
	public static int getBatteryStatus() {
		return batteryStatus;
	}
	public static void setBatteryStatus(int batteryStatus) {
		Recording.batteryStatus = batteryStatus;
	}
	public static String getBatteryString() {
		return batteryString;
	}
	public static void setBatteryString(String batteryString) {
		Recording.batteryString = batteryString;
		String[] splitString =  getBatteryString().split(",");
		System.out.println("BATTERY= " + splitString[3]);
		setBatteryStatus(Integer.parseInt(splitString[3]));
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
