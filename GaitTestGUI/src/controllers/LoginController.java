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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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
  @FXML private MenuBar fileMenuBar;

  @FXML private GridPane gridPane;
  Button[] controlsList = new Button[4];
  
  //session id counter
  private static int sessionID = 0;
  private String currComPort = null;

  /**
   * Initializes usb threads and connection in project
   *
   * 
   */
  public void initManager(final LoginManager loginManager) {	  
	  	  
	  // threaded usb object to persistantly poll if usb device conneted
	  DetectUSB usb = new DetectUSB();
	 
	  //connecting object open the connection to talk to the object
	  ComConnect com = new ComConnect();
	  
	  // start usb connection thread
	  Thread thread = new Thread(usb);
	  thread.setDaemon(true);
	  thread.start(); 
	  
	  //images on GUI
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
 	 connStatus.ShowStatus(statusImage, com, basePane, controlsList, gridPane);
 	 
 	 // pressing the start button will alert if errors or start main view
	  startButton.setOnAction((e) -> {	
		  if (validityCheck(projIdField.getText())) {
			  if(debugCheck.isSelected())
			  {
				  usb.setIsConnected(true);
				  //System.out.println("DEBUG MODE");
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
	        	  //System.out.print(sessionText);
	        	  loginManager.authenticated(Integer.toString(sessionID)); // switches screen to main view
	        	  Recording.setRecordingStart(System.currentTimeMillis());
	          } else {
	        	  Alert alert = new Alert(AlertType.WARNING, 
	                      "Device Not connected, \n "
	                      + "Unplug and reconnect", 
	                      ButtonType.OK);
	        	  alert.showAndWait();
	        	  //System.out.println("Device not connected!!!");
	          }
		  } else {
        	  Alert alert = new Alert(AlertType.ERROR, 
                      "Project ID is not valid, \n "
                      + "Please re-enter a correct project ID", 
                      ButtonType.OK);
        	  alert.showAndWait();
        	  //System.out.println("Project ID error");
          }
	});
   
	clearButton.setOnAction((e) -> {
		//System.out.print("cleared fields");    	  
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
	
	  //About menu item on menu bar
	  @FXML
	  public void aboutHelp(ActionEvent event) {
		  // select file from the menu bar
		  //Stage stage = (Stage) fileMenuBar.getScene().getWindow();
		  Alert alert = new Alert(AlertType.INFORMATION,
	 	           "Gait Initiation GUI\n\n\n"
	 	         + "- Used to signal the start of gait activities with sound\n "
	 	         + "- Make the device to be connected without the use of bluetooth\n"
	 	         + "- Provide a java program framework for connecting devices and collecting data\n"
	 	         + "- Output is currently in a directory in the Documents folder of the user under GaitFiles\n\n\n"
	 	         + " CREATED BY TIM TRUTY\n"
	 	         + " ttruty@gmail.com\n"
	 	         + " RADC Winter 2018\n"
	 	         + " RUSH ALZHEIMER'S DISEASE CENTER\n"
	 	         + " CHICAGO IL\n");


	 	 alert.setTitle("About info");
	 	 alert.showAndWait();
	  } //end aboutmenu
	  
	  @FXML
	  public void closeApp(ActionEvent event) {
		  //select close on the menu bar
		  Platform.exit();
		  System.exit(0);
		  } //end closeApp

}
