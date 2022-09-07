/**
 * Controller for the start screen of the test
 * 
 * @author Tim Truty
 *
 */

package controllers;

import java.awt.Insets;
import java.io.File;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.controlsfx.control.StatusBar;
import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import models.CheckMarkerFiles;
import models.CheckProjId;
import models.ConnectionStatus;
import models.DetectUSB;
import models.MarkerFile;
import models.MarkerFileData;
import models.MarkerFilerChecks;
import models.Recording;
import serialcoms.ComConnect;
import openmovement.Device;
import openmovement.JOM;
import openmovement.JOMAPI;

/** Controls the login screen */ 
public class LoginController {
	
  @FXML private TextField staffIdField;
  @FXML private TextField projIdField;
  @FXML private TextField fuField;
  @FXML private Button startButton;
  @FXML private Button startAX6;
  @FXML private Button startBoth;
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
  
  private JOMAPI omapi;

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
	  
	  populateFileTableView();
	  // start usb connection thread
//	  Thread thread = new Thread(usb);
//	  thread.setDaemon(true);
//	  thread.start(); 
//	  
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
		usb.run();
		Recording.setDeviceType("Dyno");
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
	  
	  // pressing the start button will alert if errors or start main view
	  startAX6.setOnAction((e) -> {	
		  if (validityCheck(projIdField.getText())) {
			  usb.setIsConnected(true);
			  StringBuilder sessionText = null;
			  //System.out.println("DEBUG MODE");
			  Recording.setDeviceType("AX6");
			  Recording.setRecordingStart(System.currentTimeMillis());
			  int result;
		      System.out.println("JOM: OmStartup()");
		      System.out.println(JOMAPI.OM_VERSION);
				
			  result = JOMAPI.OmStartup(JOMAPI.OM_VERSION);
		      if (result < 0)
				{
					System.out.println("JOM: Error during OmStartup() = " + result);
					Alert alert = new Alert(AlertType.ERROR, 
					          "AX6 Error, \n "
					          + "Please try reconnecting device", 
					              ButtonType.OK);
						  alert.showAndWait();
				}
				else
				{
					System.out.println("JOM: ...OK");
					System.out.println("DEVICE CONNECTED ID IS: " + Device.getDeviceId());
					sessionText = authorize();	
					
					LocalDateTime timeSet = LocalDateTime.now();
			        Recording.setRecordingStartTimeStamp(timeSet);
			        
					loginManager.authenticated(Integer.toString(sessionID)); // switches screen to main view
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
   
	// pressing the start button will alert if errors or start main view
		  startBoth.setOnAction((e) -> {	
			  if (validityCheck(projIdField.getText())) {
				  StringBuilder sessionText = null;
				  //System.out.println("DEBUG MODE");
				  Recording.setDeviceType("Both");
				  Recording.setRecordingStart(System.currentTimeMillis());
				  
				  if(usb.getIsConnected()) 
				  //if (1==1) //debug. do not need device in to test
		          	{
			        	  currComPort = usb.comPort;
			          } else {
			        	  Alert alert = new Alert(AlertType.WARNING, 
			                      "Dynaport Device Not connected, \n "
			                      + "Unplug and reconnect", 
			                      ButtonType.OK);
			        	  alert.showAndWait();
			          }
				  
				  int result;
			      System.out.println("JOM: OmStartup()");
			      System.out.println(JOMAPI.OM_VERSION);
			      					
				  result = JOMAPI.OmStartup(JOMAPI.OM_VERSION);
			      if (result < 0)
					{
						System.out.println("JOM: Error during OmStartup() = " + result);
						Alert alert = new Alert(AlertType.ERROR, 
						          "AX6 Error, \n "
						          + "Please try reconnecting device", 
						              ButtonType.OK);
							  alert.showAndWait();
					}
					else
					{
						System.out.println("JOM: ...OK");
						System.out.println("DEVICE CONNECTED ID IS: " + Device.getDeviceId());						
						LocalDateTime timeSet = LocalDateTime.now();
				        Recording.setRecordingStartTimeStamp(timeSet);
				        
						loginManager.authenticated(Integer.toString(sessionID)); // switches screen to main view
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
  
  @FXML private TableView<MarkerFilerChecks> marker_check_table;
  
  @FXML private TableColumn<MarkerFilerChecks, String> proj_id;
  @FXML private TableColumn<MarkerFilerChecks, String> fu_year;
  @FXML private TableColumn<MarkerFilerChecks, String> date;
  
  private ChangeListener<MarkerFilerChecks> markerFileListener;
  private Property<MarkerFilerChecks> fileProperty;
//  marker_check_table = new TableView<String>();
  
	public void populateFileTableView() {
		//clear the table each run
		//stopListeningToSelect();
		marker_check_table.getItems().clear();
		marker_check_table.refresh();
		
		CheckMarkerFiles checkMarkerFiles = new CheckMarkerFiles();
		List<MarkerFile> markerFileList = checkMarkerFiles.readMarkerDirectory();
		
		// reset to null
		ObservableList<MarkerFilerChecks> tableRowData = null;
		
		tableRowData = FXCollections.observableArrayList();
		
		for (MarkerFile mf : markerFileList) {
			tableRowData.add(new MarkerFilerChecks(mf.getProj_id(), mf.getFu_year(), mf.getDate(), mf.getFile()) );
		}

		marker_check_table.setItems(tableRowData);
		
		proj_id.setCellValueFactory(cellData -> cellData.getValue().idLabelProperty());
		fu_year.setCellValueFactory(cellData -> cellData.getValue().fuYearProperty());
		date.setCellValueFactory(cellData -> cellData.getValue().dateLabelProperty());
		
		startListeningToTableSelect(fileProperty, marker_check_table);
		
	}
	
	  private void stopListeningToSelect() {
	    	if (markerFileListener != null) {
	    		marker_check_table.getSelectionModel().selectedItemProperty().removeListener(markerFileListener);
	    	}
	    }
	
	//listern method to make selection
    private void startListeningToTableSelect(Property<?> name, TableView<?> table) {
    	// THIS TOOK SOME SERIOUS DEBUGGING
    	// STILL NOT SURE IT IS THE CORRECT WAY TO GO ABOUT HIGHLIGHTING
    	// BUT IT WORKS AND DOES NOT HAVE THE SERIOUS LAG FROM BEFORE
    	stopListeningToSelect();
    	//stokeProperty = name;
    	
    	markerFileListener = (obs, oldValue, newValue) -> {        	
    		if (newValue != null) {    
    			MarkerFilerChecks selectedMarker = (MarkerFilerChecks) table.getSelectionModel().getSelectedItem();
                System.out.println(selectedMarker.getFile());
                CheckMarkerFiles checkMarkerFiles = new CheckMarkerFiles();
                List <MarkerFileData> mdf = checkMarkerFiles.readDataFromMarkerFile(selectedMarker.getFile());
                for (MarkerFileData mf : mdf) {
                	System.out.println(mf.toString());
                }
                String filename = selectedMarker.getFile().toString();
                selectMarkerFileAlert(filename, mdf);
    		}
    	};
    	
    	marker_check_table.getSelectionModel().selectedItemProperty().addListener(markerFileListener);
    }
    
    //popup file item
	  public void selectMarkerFileAlert(String filename, List<MarkerFileData> data) {
		  // select file from the menu bar
		  //Stage stage = (Stage) fileMenuBar.getScene().getWindow();
		  StringBuilder sb = new StringBuilder();
		  sb.append("File: " + filename + "\n");
		  Alert alert = new Alert(AlertType.INFORMATION);
		  alert.setHeaderText("File: " + filename + "\n");
		  GridPane grid = new GridPane();
		  int row = 0;
		  grid.addRow(row, new Label("proj_id"), new Label("time(secs)"), new Label("repeat"));
		  for (MarkerFileData mf : data) {
			  row ++;
			  String perfName = mf.getName();
			  if (perfName.length() < 15) {
				  int spacesCount = 15 - perfName.length();
				  String spaces = " ";
				  spaces = new String(new char[spacesCount]).replace("\0", spaces);
				  perfName = perfName + spaces;
			  }
			DecimalFormat f = new DecimalFormat("##.00");	 
			grid.addRow(row, new Label(perfName), new Label(f.format(mf.getDuration()/1000.0)), new Label(Integer.toString(mf.getTrial())));
//          	sb.append(perfName +  "\t\t" + f.format(mf.getDuration()/1000.0) + " secs\t\t" + mf.getTrial() +  "\n");
          }

	        grid.setHgap(30);
	        ColumnConstraints right = new ColumnConstraints();
	        right.setHalignment(HPos.RIGHT);
	        grid.getColumnConstraints().setAll(new ColumnConstraints(), right);

	        alert.getDialogPane().setContent(grid);

	        HBox layout = new HBox(10);
	 	 alert.setTitle("Marker File");
	 	 alert.showAndWait();
	  } //end popup file item
    
    

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
	  
	  
	  //Check data menu item on menu bar
	  @FXML
	  public void checks(ActionEvent event) {
		  String[] pathnames;

	        // Creates a new File instance by converting the given pathname string
	        // into an abstract pathname
	        File f = new File("C:\\studies\\dynaport\\");

	        // Populates the array with names of files and directories
	        pathnames = f.list();

	        // For each pathname in the pathnames array
	        for (String pathname : pathnames) {
	        	if (pathname.endsWith(".csv")) {
	        		// Print the names of files and directories
		            System.out.println(pathname);
	        	}
	            
	        }
	  } //end Check
	  
	  @FXML
	  public void closeApp(ActionEvent event) {
		  //select close on the menu bar
		  Platform.exit();
		  System.exit(0);
		  } //end closeApp

}
