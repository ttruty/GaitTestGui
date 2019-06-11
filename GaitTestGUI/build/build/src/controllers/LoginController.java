/**
 * Controller for the start screen of the test
 * 
 * @author Tim Truty
 *
 */

package controllers;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.controlsfx.control.StatusBar;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import models.CheckProjId;
import models.ConnectionStatus;
import models.DetectUSB;
import models.Recording;
import serialcoms.ComConnect;

/** Controls the login screen */ 
public class LoginController {
	
  @FXML private TextField staffIdField;
  @FXML private TextField projIdField;
  @FXML private TextField fuField;
  @FXML private Button startButton;
  @FXML private Button clearButton;
  @FXML private Label connectionLbl;
  @FXML private StatusBar  statusBar;
  @FXML private ImageView  statusImage;
  @FXML private ImageView  rushLogo;
  @FXML private CheckBox  debugCheck;
  @FXML private AnchorPane  basePane;

  @FXML private GridPane gridPane;
  Button[] controlsList = new Button[4];
  
  //session id counter
  private static int sessionID = 0;
  private String currComPort = null;

  
  public void initManager(final LoginManager loginManager) {	  
	  
	  DetectUSB usb = new DetectUSB();
	 
	  //connecting object
	  ComConnect com = new ComConnect();
	  
	  Thread thread = new Thread(usb);
	  thread.setDaemon(true);
	  thread.start(); 
	  
	  Image logo = new Image("file:resources/rushLogo.jpg");
	  Image image = new Image("file:resources/disconnect.png");
	  rushLogo.setImage(logo);
	  statusImage.setImage(image);
	  
	//status bar	 
	 StringProperty connectedString = new SimpleStringProperty();
	 connectedString.set("Connect Device");
	 //recording.addListener((observable, oldValue, newValue) -> {
	 
	 
	//Connection
 	 ConnectionStatus connStatus = new ConnectionStatus();
 	 connStatus.ShowStatus(statusImage, statusBar, com, basePane, controlsList, gridPane);

	  startButton.setOnAction((e) -> {	
		  if (validityCheck(projIdField.getText())) {
			  if(debugCheck.isSelected())
			  {
				  usb.setIsConnected(true);
				  System.out.println("DEBUG MODE");
				  Recording.setDebugMode(true);
				  StringBuilder sessionText = null;
				  sessionText = authorize();					
			  }
			  
	          if(usb.getIsConnected()) 
			  //if (1==1) //debug. do not need device in to test
	          {
	        	  currComPort = usb.comPort;
	        	  StringBuilder sessionText = null;
	        	  sessionText = authorize();
	        	  System.out.print(sessionText);
	        	  loginManager.authenticated(Integer.toString(sessionID));
	        	  Recording.setRecordingStart(System.currentTimeMillis());
	          } else {
	        	  Alert alert = new Alert(AlertType.WARNING, 
	                      "Device Not connected, \n "
	                      + "Unplug and reconnect", 
	                      ButtonType.OK);
	        	  alert.showAndWait();
	        	  System.out.println("Device not connected!!!");
	          }
		  } else {
        	  Alert alert = new Alert(AlertType.ERROR, 
                      "Project ID is not valid, \n "
                      + "Please re-enter a correct project ID", 
                      ButtonType.OK);
        	  alert.showAndWait();
        	  System.out.println("Project ID error");
          }
	});
   
	clearButton.setOnAction((e) -> {
		System.out.print("cleared fields");    	  
		  //clear text fields
		  staffIdField.clear();
		  projIdField.clear();
		  fuField.clear();
		  sessionID = 0;
	});
	 
  }

/**
   * Check authorization credentials.
   * 
   * If accepted, return a sessionID for the authorized session
   * otherwise, return null.
 * @throws Exception 
 * @throws SQLException 
   */   
  private StringBuilder authorize() {

	  setProjIdField(projIdField);
	  setFuField(fuField);
	  setStaffIdField(staffIdField);
	  	  
	  // set time on device
      LocalDateTime timeSet = LocalDateTime.now();
      DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
      String time = timeSet.format(formatTime);
	  	  
	  StringBuilder sb = new StringBuilder();
	  
	  sb.append("Staff Id: " + staffIdField.getText() + "\n");
	  sb.append("Project Id: " + projIdField.getText() + "\n");
	  sb.append("F/U year: " + fuField.getText() + "\n");
	  sb.append("Access on com port: " + currComPort + "\n");
	  sb.append("Time of start: " + time + "\n");
	  
	  sb.append(generateSessionID() + "\n");
    
	  return sb;
  }
  
  

  private String generateSessionID() {
    sessionID++;
    return  projIdField.getText() + " - session " + sessionID;
  }

	public TextField getStaffIdField() {
		return staffIdField;
	}
	
	public void setStaffIdField(TextField staffIdField) {
		this.staffIdField = staffIdField;
		Recording.setStaffId(staffIdField.getText());
	}
	
	public TextField getProjIdField() {
		return projIdField;
	}
	
	public void setProjIdField(TextField projIdField){
		//initailize proj id checker
		Recording.setRecordingId(projIdField.getText());
		this.projIdField = projIdField;
	}
	
	public TextField getFuField() {
		return fuField;
	}
	
	public void setFuField(TextField fuField) {
		Recording.setFuYear(fuField.getText());
		this.fuField = fuField;
	}
	
	private boolean validityCheck(String Id) {
		CheckProjId checker = new CheckProjId();
		return checker.isLegal(Id);
	}
}
