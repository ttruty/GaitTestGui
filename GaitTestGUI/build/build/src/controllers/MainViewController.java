package controllers;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.controlsfx.control.StatusBar;

import indicator.RingProgressIndicator;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import models.Marker;
import models.Recording;
import models.SaveOMX;
import models.WriteCSV;
import javafx.scene.control.Alert.AlertType;
import serialcoms.ComConnect;
import serialcoms.CommPortSender;

/** Controls the main application screen */
public class MainViewController {
  
  @FXML private Button startTest;
  @FXML private Button stopTest;
  @FXML private Button saveButton;
  @FXML private Button sampleSoundButton;
  
  @FXML private Label  sessionLabel;
  @FXML private Label  timeLabel;
  @FXML private Label  projIdLabel;  
  @FXML private Label  fuYearLabel;  
  @FXML private Label  staffIdLabel;    
  @FXML private Label  comPortLabel;    
  @FXML private StatusBar  statusBar;
  @FXML private GridPane gridPane;
  @FXML private AnchorPane basePane;
  
  Group indicators = new Group();
  //@FXML private GridPane pane_8ft1;
  
  // performance buttons
  @FXML private Button perf_8ft1;
  @FXML private Button perf_8ft2;
  @FXML private Button perf_eo;
  @FXML private Button perf_3601;
  @FXML private Button perf_ll;
  @FXML private Button perf_3602;
  @FXML private Button perf_ec;
  @FXML private Button perf_tug1;
  @FXML private Button perf_rl;
  @FXML private Button perf_tug2;
  @FXML private Button perf_tan;
  @FXML private Button perf_32ft;
  @FXML private Button perf_toe;
  @FXML private Button perf_cog1;
  @FXML private Button perf_cog2;
  
//performance complete buttons
 @FXML private Button perf_8ft1_complete;
 @FXML private Button perf_8ft2_complete;
 @FXML private Button perf_eo_complete;
 @FXML private Button perf_3601_complete;
 @FXML private Button perf_ll_complete;
 @FXML private Button perf_3602_complete;
 @FXML private Button perf_ec_complete;
 @FXML private Button perf_tug1_complete;
 @FXML private Button perf_rl_complete;
 @FXML private Button perf_tug2_complete;
 @FXML private Button perf_tan_complete;
 @FXML private Button perf_32ft_complete;
 @FXML private Button perf_toe_complete;
 @FXML private Button perf_cog1_complete;
 @FXML private Button perf_cog2_complete;
 
  @FXML private Label perf_8ft1_start;
  @FXML private Label perf_8ft1_stop;
  @FXML private Label perf_8ft2_start;
  @FXML private Label perf_8ft2_stop;
  @FXML private Label perf_eo_start;
  @FXML private Label perf_eo_stop;
  @FXML private Label perf_3601_start;
  @FXML private Label perf_3601_stop;
  @FXML private Label perf_ll_start;
  @FXML private Label perf_ll_stop;
  @FXML private Label perf_3602_start;
  @FXML private Label perf_3602_stop;
  @FXML private Label perf_ec_start;
  @FXML private Label perf_ec_stop;
  @FXML private Label perf_tug1_start;
  @FXML private Label perf_tug1_stop;
  @FXML private Label perf_rl_start;
  @FXML private Label perf_rl_stop;
  @FXML private Label perf_tug2_start;
  @FXML private Label perf_tug2_stop;
  @FXML private Label perf_tan_start;
  @FXML private Label perf_tan_stop;
  @FXML private Label perf_32ft_start;
  @FXML private Label perf_32ft_stop;
  @FXML private Label perf_toe_start;
  @FXML private Label perf_toe_stop;
  @FXML private Label perf_cog1_start;
  @FXML private Label perf_cog1_stop;
  @FXML private Label perf_cog2_start;
  @FXML private Label perf_cog2_stop;
  
  @FXML private Label perf_8ft1_count;
  @FXML private Label perf_8ft2_count;
  @FXML private Label perf_eo_count;
  @FXML private Label perf_3601_count;
  @FXML private Label perf_ll_count;
  @FXML private Label perf_3602_count;
  @FXML private Label perf_tug1_count;
  @FXML private Label perf_rl_count;
  @FXML private Label perf_tug2_count;
  @FXML private Label perf_32ft_count;
  @FXML private Label perf_tan_count;
  @FXML private Label perf_toe_count;
  @FXML private Label perf_cog1_count;
  @FXML private Label perf_cog2_count;
  @FXML private Label perf_ec_count;
  
  @FXML private Label perf_8ft1_timeD;
  @FXML private Label perf_8ft2_timeD;
  @FXML private Label perf_eo_timeD;
  @FXML private Label perf_3601_timeD;
  @FXML private Label perf_ll_timeD;
  @FXML private Label perf_3602_timeD;
  @FXML private Label perf_tug1_timeD;
  @FXML private Label perf_rl_timeD;
  @FXML private Label perf_tug2_timeD;
  @FXML private Label perf_32ft_timeD;
  @FXML private Label perf_tan_timeD;
  @FXML private Label perf_toe_timeD;
  @FXML private Label perf_cog1_timeD;
  @FXML private Label perf_cog2_timeD;
  @FXML private Label perf_ec_timeD;
  
  
  private int clickCount = 0;
  private int remoteClick = 0;
  //private int perfCount = 0;
  long start;
  long stop;
  long delayTime;

  Button[] buttonList = new Button[15];
  Queue<Button> bQueue;
  
  ArrayList<Marker> markerList = new ArrayList<Marker>();
  
  //Drive info
  String driveLetter;
  String driveName;
  
  Input input;
  boolean pageDownPressed;
  boolean pageUpPressed;
  boolean periodPressed;
    
  public void initSessionID(final LoginManager loginManager, String sessionID, Input input) {	  
	  buttonList[0] = perf_8ft1;
	  buttonList[1] = perf_8ft2;
	  buttonList[2] = perf_eo;
	  buttonList[3]= perf_3601;
	  buttonList[4]= perf_ll;
	  buttonList[5]=perf_3602;
	  buttonList[6]=perf_ec;
	  buttonList[7]=perf_tug1;
	  buttonList[8]=perf_rl;
	  buttonList[9]=perf_tug2;
	  buttonList[10]=perf_tan;
	  buttonList[11]=perf_32ft;
	  buttonList[12]=perf_toe;
	  buttonList[13]=perf_cog1;
	  buttonList[14]=perf_cog2;
	  
	 System.out.println(sessionID);
	 sessionLabel.setText("Session ID: " + sessionID);
	 timeLabel.setText("00:00:00");
	 projIdLabel.setText("ProjID: " + Recording.getRecordingId());
	 fuYearLabel.setText("F/U Year: " + Recording.getFuYear());
	 staffIdLabel.setText("Staff ID: " + Recording.getStaffId());
	 
	 //perf_8ft1_start.setText("00:00:00");
	 //perf_8ft1_stop.setText("00:00:00");
	 
	 gridPane.prefHeightProperty().bind(basePane.heightProperty());
	 gridPane.prefWidthProperty().bind(basePane.widthProperty());
	 
	 //status bar
	 //statusBar.setText("Starting, please wait");	 

	 //connectiong object
 	 ComConnect com = new ComConnect();

 	 //input for remote
 	 this.input = input;
 	 bQueue = new LinkedList<>(Arrays.asList(buttonList));
 	 //Button[] editButtonList = buttonList;
 	 
 	AnimationTimer gameLoop = new AnimationTimer() {
		@Override
		public void handle(long now) {
			// TODO Auto-generated method stub
			 // vertical direction
			//System.out.println(input.isPressed());
			if (!bQueue.isEmpty()) {
			    if( input.isPageDownPressed()) {
			       System.out.println("PAGE DOWN");
			       bQueue.element().fire();
			            pageDownPressed = true;
			            remoteClick++;
			        } else if( input.isPageUpPressed()) {
			        	System.out.println("PAGE UP");
			        	pageUpPressed = true;
			        } else if ( input.isPeriodPressed()){
			        	System.out.println("PERIOD!!");
			        	bQueue.element().setDisable(true);
			        	bQueue.remove();
			        	periodPressed=true;
			        }
			}
		    input.setPageDownPressed(false);
		    input.setPageUpPressed(false);
		    input.setPeriodPressed(false);

		}
 		
 	};
    gameLoop.start();
	  
 	 //all grid objects
 	 ObservableList<Node> childrens = gridPane.getChildren();
 	 
 	 // set all objects as disabled until start of test
 	for (Node node : childrens) {
		if (node instanceof Control) {
			node.setDisable(true);
	    }
	}
	 
	 //status bar	 
	 StringProperty connectedString = new SimpleStringProperty();
	 connectedString.set("Gait Test in Progress...");
	 
	 Recording.connectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (newValue)
				{
					//System.out.println("Plugged IN>>>>");
					Platform.runLater(new Runnable() {
						@Override
						public void run() {						
							connectedString.set("Connected: TRUE");
						}
					});
					
					//connected.set("Connected: " + Recording.isConnected());				
				}
				else {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {						
							connectedString.set("Connected: FALSE");
							// set the start time stamp of recroding when device is uplugged after the start button is pressed
							if (Recording.isRecording()) {
						        LocalDateTime timeSet = LocalDateTime.now();
						        Recording.setRecordingStartTimeStamp(timeSet);
						        DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("yyyy-MM-dd,HH:mm:ss.SSS");
						        String time = timeSet.format(formatTime);
						        System.out.println("UNPLUGGED at " + time);
							}

						}				
					});
				}
			}
			});  
		
	statusBar.textProperty().bind(connectedString);
	  
	 // Time label
	 DateFormat timeFormat = new SimpleDateFormat( "HH:mm:ss" );
	 
	 final Timeline timeline = new Timeline(
	     new KeyFrame(
	         Duration.millis( 500 ),
	         event -> {
	             final long time = System.currentTimeMillis();
	             timeLabel.setText( timeFormat.format( time ) );
	         	
	             }
	         )
	     );
	 timeline.setCycleCount( Animation.INDEFINITE );
	 timeline.play();
	 
	 //OK TO UNPLUG MESSAGE
	 com.connectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (newValue)
				{
					if (!Recording.isSaved())
					{
						System.out.println("Plug Alert Changed");
						Platform.runLater(new Runnable() {
							@Override
							public void run() {								
								Alert alert = new Alert(AlertType.INFORMATION, 
					                      "OK TO UNPLUG", 
					                      ButtonType.OK);
											basePane.getChildren().remove(indicators);
					        	  alert.showAndWait();
					        	  Recording.setRecordingState(true);
					        	  //enable all grid controls
					        	  for (Node node : childrens) {
					  				if (node instanceof Control) {
					  					node.setDisable(false);
					  			    }
					  			}
								//connectedString.set("Ok to UNPLUG");
							}
						});
					}
					else { //when the recoring is save close EVERYTING!
						Platform.runLater(new Runnable() {
							@Override
							public void run() {								
								Alert alert = new Alert(AlertType.INFORMATION, 
					                      "Gait Test Saved!", 
					                      ButtonType.OK);
											//basePane.getChildren().remove(indicators);
					        	  alert.showAndWait();
					        	  Recording.setRecordingState(true);
					        	  //enable all grid controls
					        	  for (Node node : childrens) {
					  				node.setDisable(true);					  			    
					  			}
					        	basePane.getChildren().remove(indicators);
					        	startTest.setDisable(true);
					        	stopTest.setDisable(true);
					        	saveButton.setDisable(true);
					        	sampleSoundButton.setDisable(true);
					        	
					        	
								//connectedString.set("Ok to UNPLUG");
							}
						});
					}
					//connected.set("Connected: " + Recording.isConnected());				
				}						
			}
			});  
 	
	 //Sound button
	 sampleSoundButton.setOnAction((e) -> {
		 // Sound varialbees
		  String musicFile = "resources\\start.wav";     // For example

		  Media sound = new Media(new File(musicFile).toURI().toString());
		  MediaPlayer mediaPlayer = new MediaPlayer(sound);
		  
		 new Timer().schedule( 
	  		        new TimerTask() {
	  		            @Override
	  		            public void run() {
	  		            	mediaPlayer.play();
	  		                
	  		            	this.cancel();
	  		            }
	  		        }, 
	  		        1
	  		);
	 });
	 //end sound button
	 
	 //start button
     startTest.setOnAction((e) -> {    	
    	Alert alert = new Alert(AlertType.WARNING, 
                 "This will format the device", 
                 ButtonType.OK);
   	    alert.showAndWait();
   	    Recording.setRecordingStart(System.currentTimeMillis());
	   	com.makeConnection();
	   	
	   	//ring progress bar
	   	RingProgressIndicator ring = new RingProgressIndicator();
	   	ring.setRingWidth(200);
	   	ring.makeIndeterminate();
	   	StackPane stackRing = new StackPane();
	   	stackRing.prefHeightProperty().bind(basePane.heightProperty());
	   	stackRing.prefWidthProperty().bind(basePane.widthProperty());
	   	
	   	stackRing.getChildren().add(ring);
	   	StackPane.setAlignment(ring, Pos.CENTER);
	   	indicators.getChildren().add(stackRing);
	   	
	   	basePane.getChildren().add(indicators);
	   	
	   	connectedString.set("Gait Test in Progress ...");
	      //new Thread(longRunningTask).start();
	      //loginManager.logout();
	   	comPortLabel.setText("PORT= " + com.getAccessComPort());
		startTest.setDisable(true);
//		statusBar.textProperty().bind(recording);   	  
		}); //end start button
      
      //stop button
	  stopTest.setOnAction((e) -> {
		   	//ComConnect.makeConnection();
		  	
		   	Recording.setRecordingState(false);
		   	Recording.setMarkerList(markerList);
		   	Alert alert = new Alert(AlertType.WARNING, 
                    "Recording stopped, \n "
                    + "Please plug in device to save", 
                    ButtonType.OK);
      	  alert.showAndWait();
      	  
      	  
      	    // deactivate all in gridpane
			
			for (Node node : childrens) {
				if (node instanceof Control) {
					node.setDisable(true);
			    }
			}
			stopTest.setDisable(true);
		   
			}); // end stop button
	    
	  //save button
	  saveButton.setOnAction((e) -> {
		   	//ComConnect.makeConnection();
		   	Recording.setMarkerList(markerList);
		    
		    if(Recording.isConnected()) 
		    {
		    	
		    	//ring progress bar
			   	RingProgressIndicator ring = new RingProgressIndicator();
			   	ring.setRingWidth(200);
			   	ring.makeIndeterminate();
			   	StackPane stackRing = new StackPane();
			   	stackRing.prefHeightProperty().bind(basePane.heightProperty());
			   	stackRing.prefWidthProperty().bind(basePane.widthProperty());
			   	
			   	stackRing.getChildren().add(ring);
			   	StackPane.setAlignment(ring, Pos.CENTER);
			   	indicators.getChildren().add(stackRing);
			   	
			   	basePane.getChildren().add(indicators);
		    	
		    	System.out.println("Device is Connected. Can Dowmoad");
			   	
		    	//write out to csv
			    WriteCSV writer = new WriteCSV();
			    try {
					writer.write();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}			      	
		    	
		    	
		    	
		    	
			   	Recording.setRecordingState(true);
			   	
			   	//TODO: SAVE OMX FILE
			   	SaveOMX saveObj = Recording.getSaveObj();
			   	//System.out.println("SAVE OBJECT: letter " + saveObj.getSaveDriveLetter());
			   	//System.out.println("SAVE OBJECT: name " + saveObj.getSaveDriveName());
			   	
			   	File rawSaveFile = saveObj.fileSearch(saveObj.getSaveDriveLetter());
			   	saveObj.setSaveFileName(writer.getBaseFilename()+ ".OMX");
			   	
			   	saveObj.saveFile(rawSaveFile);			   	        	 
		        
			   	Recording.setSaved(true);
			   	
			   	com.stopRecording();
			   	
		          } else {
		        	  Alert alert = new Alert(AlertType.WARNING, 
		                      "Device Not connected, \n "
		                      + "Unplug and reconnect", 
		                      ButtonType.OK);
		        	  alert.showAndWait();
		        	  System.out.println("Device not connected!!!");
		          }
		    
			}); // end save button
	  
	  // perforamnce buttons
	  perf_8ft1.setOnAction((e) -> {
		  	perfButton(perf_8ft1, "8ft1", perf_8ft1_start, perf_8ft1_stop, perf_8ft1_timeD, perf_8ft1_count);
				});
	  perf_8ft2.setOnAction((e) -> {
		  	perfButton(perf_8ft2, "8ft2", perf_8ft2_start, perf_8ft2_stop,perf_8ft2_timeD, perf_8ft2_count);
				});
	  perf_eo.setOnAction((e) -> {
		  	perfButton(perf_eo, "eo", perf_eo_start, perf_eo_stop, perf_eo_timeD, perf_eo_count);
				});
	  perf_3601.setOnAction((e) -> {
		  	perfButton(perf_3601, "3601", perf_3601_start, perf_3601_stop,perf_3601_timeD, perf_3601_count);
				});
	  perf_ll.setOnAction((e) -> {
		  	perfButton(perf_ll, "ll", perf_ll_start, perf_ll_stop,perf_ll_timeD, perf_ll_count);
				});
	  perf_3602.setOnAction((e) -> {
		  	perfButton( perf_3602, "3602", perf_3602_start, perf_3602_stop,perf_3602_timeD, perf_3602_count);
				});
	  perf_ec.setOnAction((e) -> {
		  	perfButton(perf_ec, "ec", perf_ec_start, perf_ec_stop, perf_ec_timeD, perf_ec_count);
				});
	  perf_tug1.setOnAction((e) -> {
		  	perfButton(perf_tug1,"tug1", perf_tug1_start, perf_tug1_stop,perf_tug1_timeD, perf_tug1_count);
				});
	  perf_rl.setOnAction((e) -> {
		  	perfButton(perf_rl, "rl", perf_rl_start, perf_rl_stop, perf_rl_timeD, perf_rl_count);
				});
	  perf_tug2.setOnAction((e) -> {
		  	perfButton(perf_tug2, "tug2", perf_tug2_start, perf_tug2_stop, perf_tug2_timeD, perf_tug2_count);
				});
	  perf_tan.setOnAction((e) -> {
		  	perfButton(perf_tan, "tan", perf_tan_start, perf_tan_stop,perf_tan_timeD, perf_tan_count);
				});
	  perf_32ft.setOnAction((e) -> {
		  	perfButton(perf_32ft, "32ft", perf_32ft_start, perf_32ft_stop, perf_32ft_timeD, perf_32ft_count);
				});
	  perf_toe.setOnAction((e) -> {
		  	perfButton(perf_toe, "toe", perf_toe_start, perf_toe_stop, perf_toe_timeD, perf_toe_count);
				});
	  perf_cog1.setOnAction((e) -> {
		  	perfButton(perf_cog1, "cog1", perf_cog1_start, perf_cog1_stop, perf_cog1_timeD, perf_cog1_count);
				});
	  perf_cog2.setOnAction((e) -> {
		  	perfButton(perf_cog2, "cog2", perf_cog2_start, perf_cog2_stop, perf_cog2_timeD, perf_cog2_count);
				});
  
  }
  
  	private void perfButton(Button button, String label, Label startTime, Label stopTime, Label timeDLabel, Label countLabel) {
		  	clickCount++;	
		  	//perfCount++;
		  	//random delay generation
		  	Random delay = new Random();
	  		int low = 1;
	  		int high = 3000; //1 ms to 3000 ms
		  	Marker marker = new Marker();
		   	marker.setLabel(label);
		   	long time = System.currentTimeMillis();
		   	//change color
//		   	startTime.setStyle("-fx-background-color: #eeeeee;");
//		   	stopTime.setStyle("-fx-background-color: #eeeeee;");
		   	
		   	//Start timer
		  	if (clickCount % 2 != 0)
		  	{
		  		gridPane.setStyle("-fx-background-color: #00FF00;");
		  		start = time;
		  		
		  		
		  		int randomDelay = delay.nextInt(high-low) + low;
		  		delayTime = randomDelay;

		  		
//		  		try {
//					Thread.sleep(randomDelay);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
		  		 // Sound varialbees
		  	  String musicFile = "resources\\start.wav";     // For example

		  	  Media sound = new Media(new File(musicFile).toURI().toString());
		  	  MediaPlayer mediaPlayer = new MediaPlayer(sound);
		  		
		  		new Timer().schedule( 
		  				
		  		        new TimerTask() {
		  		            @Override
		  		            public void run() {
		  		            	mediaPlayer.play();
		  		                
		  		            	this.cancel();
		  		            }
		  		        }, 
		  		        randomDelay 
		  		);
		  		System.out.println("Random Delay: " + randomDelay);
		  		//mediaPlayer.play();
		  		
		  		marker.setRandomDelay(randomDelay);
			   	
//			   	stopTime.setStyle("-fx-background-color: #00FF00;");
			   	//button.setStyle("-fx-background-color: #00FF00;");
			   	//final long time = System.currentTimeMillis();
			   				   	
		  		marker.setTimeStamp(time + randomDelay);
		  		marker.setUnixTimeStamp(time + randomDelay);
		  		marker.setMarkerType("Start");
		  		StringProperty timeLabel = new SimpleStringProperty();
		  		timeLabel.set(marker.getTimeStamp());
			   	startTime.textProperty().bind(timeLabel);
			   	
			   	//diable buttons
			   	for (Button b : buttonList)
			   	{			   		
			   		if (b.getId() != (button.getId()))
			   		{
			   			b.setDisable(true);
			   		}
			   	}
			   	//stopTime.disableProperty();
			   	stopTime.setVisible(false);
			   	marker.setCount(clickCount);
			   	
			   	StringProperty count = new SimpleStringProperty();
			   	count.set(Integer.toString((clickCount+1)/2));
			   	countLabel.textProperty().bind(count); 	
			   	
		  	}
		  	
		  	//stop timer
		  	else {		  		
		  		
		  		gridPane.setStyle("-fx-background-color: #FFFFFF;");
		  		marker.setTimeStamp(time);
		  		marker.setUnixTimeStamp(time);
		  		marker.setMarkerType("Stop");
		  		stop = time;
		  		
		  		Long timeDelta = (stop - start) - delayTime;
		  		System.out.println(timeDelta);
		  		marker.setTimeDelta(timeDelta);
		  		marker.setCount(clickCount);
		  		
		  		StringProperty timeLabel = new SimpleStringProperty();
		  		timeLabel.set(marker.getTimeStamp());
		  		stopTime.setVisible(true);
			   	stopTime.textProperty().bind(timeLabel);
			   	
			   	StringProperty timeDString = new SimpleStringProperty();
			   	timeDString.set(Double.toString(timeDelta.doubleValue()/1000));
			   	timeDLabel.textProperty().bind(timeDString);
			   				   	
			   	for (Button b : bQueue)
			   	{
  			    	b.setDisable(false);
			   	} 
		  	}	
		  	markerList.add(marker);
		  	}
}