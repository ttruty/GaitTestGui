/**
 * Controller for the main testing screen
 * 
 * @author Tim Truty
 *
 */

package controllers;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.controlsfx.control.StatusBar;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import models.ConnectionStatus;
import models.ControlButton;
import models.Input;
import models.Marker;
import models.Recording;
import models.RingIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import serialcoms.ComConnect;

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
  @FXML private StatusBar statusBar;
  @FXML private GridPane gridPane;
  @FXML private AnchorPane basePane;
  
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
 
  @FXML private Label perf_8ft1_start;
  @FXML private Label perf_8ft2_start;
  @FXML private Label perf_eo_start;
  @FXML private Label perf_3601_start;
  @FXML private Label perf_ll_start;
  @FXML private Label perf_3602_start;
  @FXML private Label perf_ec_start;
  @FXML private Label perf_tug1_start;
  @FXML private Label perf_rl_start;
  @FXML private Label perf_tug2_start;
  @FXML private Label perf_tan_start;
  @FXML private Label perf_32ft_start;
  @FXML private Label perf_toe_start;
  @FXML private Label perf_cog1_start;
  @FXML private Label perf_cog2_start;
  
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
  
  //Group indicators = new Group();

  @FXML private ImageView statusImage; 
  @FXML private ProgressBar batteryLevel;
  @FXML private Label batteryLabel;
  
  private int clickCount = 0;
  //private int perfCount = 0;
  long start;
  long stop;
  long delayTime;
  Duration timeWatch = Duration.ZERO;
  Timeline timeline = new Timeline();
  long unixTimeStampInSound; // set this in when the sound is played

  Button[] buttonList = new Button[15];
  Button[] controlsList = new Button[4];
  Queue<Button> bQueue;
  
  ArrayList<String> perfList = new ArrayList<String>(); 
  ArrayList<Marker> markerList = new ArrayList<Marker>();
  
  //Drive info
  String driveLetter;
  String driveName;
  
  Input input;
  boolean pageDownPressed;
  boolean pageUpPressed;
  boolean periodPressed;
  boolean slideShowPressed;
  boolean taskRunning = false;
  
  //sound
  String musicFile = "resources\\start.wav";     // For example
  Media sound = new Media(new File(musicFile).toURI().toString());
  MediaPlayer mediaPlayer = new MediaPlayer(sound);
    
  public void initSessionID(final LoginManager loginManager, String sessionID, Input input) {	  
	  buttonList[0] = perf_8ft1;
	  buttonList[1] = perf_8ft2;
	  buttonList[2] = perf_eo;
	  buttonList[3] = perf_3601;
	  buttonList[4] = perf_ll;
	  buttonList[5] = perf_3602;
	  buttonList[6] = perf_ec;
	  buttonList[7] = perf_tug1;
	  buttonList[8] = perf_rl;
	  buttonList[9] = perf_tug2;
	  buttonList[10] = perf_tan;
	  buttonList[11] = perf_32ft;
	  buttonList[12] = perf_toe;
	  buttonList[13] = perf_cog1;
	  buttonList[14] = perf_cog2;
	  
	  controlsList[0] = startTest;
	  controlsList[1] = stopTest;
	  controlsList[2] = sampleSoundButton;
	  controlsList[3] = saveButton;
	  
	  for (Button button : buttonList) {
		  button.setMaxWidth(Double.MAX_VALUE);
		  button.setMaxHeight(Double.MAX_VALUE);
		  button.setStyle("-fx-font-size:32");
		  button.setPadding(Insets.EMPTY);
	  }
	  
	//button queue
	  bQueue = new LinkedList<>(Arrays.asList(buttonList));
	  
	  saveButton.setDisable(true);
	  stopTest.setDisable(true);
	  
	 System.out.println(sessionID);
	 sessionLabel.setText("Session ID: " + sessionID);
	 timeLabel.setText("00:00:00");
	 projIdLabel.setText("ProjID: " + Recording.getRecordingId());
	 fuYearLabel.setText("F/U Year: " + Recording.getFuYear());
	 staffIdLabel.setText("Staff ID: " + Recording.getStaffId());
	 
	 //perf_8ft1_start.setText("00:00:00");
	 //perf_8ft1_stop.setText("00:00:00");
	 batteryLevel.getStylesheets().add(getClass().getResource("/views/progress.css").toExternalForm());
	 
	 gridPane.prefHeightProperty().bind(basePane.heightProperty());
	 gridPane.prefWidthProperty().bind(basePane.widthProperty());	 
	 
	 Image image = new Image("file:resources/connect.png");
	 statusImage.setImage(image);

	//connecting object
 	 ComConnect com = new ComConnect();
 	 
 	 //start recording when screen loads
 	 if (!Recording.isDebugMode()) {
 		StartDeviceRecording(com);
 	 } else {
 		 DebugStartDeviceRecording();
 	 }
 	 
 	 //Connection Status images and status bar update
 	 ConnectionStatus connStatus = new ConnectionStatus();
 	 connStatus.ShowStatus(statusImage, statusBar);
 	 
 	 
 	 //input for remote
 	 this.input = input;
 	 InputHelper(input);
	  
 	//all grid objects
 	 ObservableList<Node> childrens = gridPane.getChildren();
 	 
 	 // set all objects as disabled until start of test
	for (Node node : childrens) {
		if (node instanceof Control) {
			node.setDisable(true);
	    }
	}
	 
	 
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
	  
 	
	 //Sound button
	 sampleSoundButton.setOnAction((e) -> {
		 // Sound varialbees		  
		 new Timer().schedule( 
	  		        new TimerTask() {
	  		            @Override
	  		            public void run() {
	  		            	mediaPlayer.stop();
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
    	 
      Recording.setRecordingState(true);
      Recording.setStartButtonPressed(System.currentTimeMillis());
      
   	  //enable all grid controls
   	  for (Node node : childrens) {
				if (node instanceof Control) {
					node.setDisable(false);
			    }
			}
   	  saveButton.setDisable(false);
	  stopTest.setDisable(false);
	  startTest.setDisable(true);
	  
     	}); //end start button
      
      //stop button
	  stopTest.setOnAction((e) -> {
		  	
		  Recording.setMarkerList(markerList);
		  ControlButton.Stop();
      	    // deactivate all in grid pane
			for (Node node : childrens) {
				if (node instanceof Control) {
					node.setDisable(true);
			    }
			}
			stopTest.setDisable(true);
			}); // end stop button
	    
	  //save button
	  saveButton.setOnAction((e) -> {
		  Recording.setMarkerList(markerList);
		  ControlButton.Save(com, basePane);	  
		    }); // end save button
	  
	  connStatus.unplugStatus(com, basePane, controlsList, gridPane);
	  
	  // perforamnce buttons
	  perf_8ft1.setOnAction((e) -> {
		  	perfButton(perf_8ft1, "8ft1", perf_8ft1_start, perf_8ft1_count, true);
				});
	  perf_8ft2.setOnAction((e) -> {
		  	perfButton(perf_8ft2, "8ft2", perf_8ft2_start, perf_8ft2_count, true);
				});
	  perf_eo.setOnAction((e) -> {
		  	perfButton(perf_eo, "eo", perf_eo_start, perf_eo_count, false);
				});
	  perf_3601.setOnAction((e) -> {
		  	perfButton(perf_3601, "3601", perf_3601_start, perf_3601_count, true);
				});
	  perf_ll.setOnAction((e) -> {
		  	perfButton(perf_ll, "ll", perf_ll_start, perf_ll_count, false);
				});
	  perf_3602.setOnAction((e) -> {
		  	perfButton( perf_3602, "3602", perf_3602_start, perf_3602_count, true);
				});
	  perf_ec.setOnAction((e) -> {
		  	perfButton(perf_ec, "ec", perf_ec_start, perf_ec_count, false);
				});
	  perf_tug1.setOnAction((e) -> {
		  	perfButton(perf_tug1,"tug1", perf_tug1_start, perf_tug1_count, true);
				});
	  perf_rl.setOnAction((e) -> {
		  	perfButton(perf_rl, "rl", perf_rl_start, perf_rl_count, false);
				});
	  perf_tug2.setOnAction((e) -> {
		  	perfButton(perf_tug2, "tug2", perf_tug2_start, perf_tug2_count, true);
				});
	  perf_tan.setOnAction((e) -> {
		  	perfButton(perf_tan, "tan", perf_tan_start, perf_tan_count, true);
				});
	  perf_32ft.setOnAction((e) -> {
		  	perfButton(perf_32ft, "32ft", perf_32ft_start, perf_32ft_count, true);
				});
	  perf_toe.setOnAction((e) -> {
		  	perfButton(perf_toe, "toe", perf_toe_start, perf_toe_count, false);
				});
	  perf_cog1.setOnAction((e) -> {
		  	perfButton(perf_cog1, "cog1", perf_cog1_start, perf_cog1_count, true);
				});
	  perf_cog2.setOnAction((e) -> {
		  	perfButton(perf_cog2, "cog2", perf_cog2_start, perf_cog2_count, true);
				});
  }
  
	private void perfButton(Button button, String label, Label startTime, Label countLabel, boolean isDelay) 
  	{
		
		long time = System.currentTimeMillis();
		
		Marker marker = new Marker();
	   	marker.setLabel(label);
	   	marker.setUnixTimeStampNoDelay(time);
	   	
	  	clickCount++;	
	  	//perfCount++;
	  	timeWatch = Duration.ZERO;
	  	
	  	startTime.setMaxWidth(Double.MAX_VALUE);
		startTime.setMaxHeight(Double.MAX_VALUE);		
		
		// Time label
 		//DateFormat timeFormat = new SimpleDateFormat( "HH:mm:ss" );
 		StringProperty timeLabel = new SimpleStringProperty();
 		timeLabel.set("0.00");
		
	  	//random delay generation
	  	Random delay = new Random();
  		int low = 1;
  		int high = 3000; //1 ms to 3000 ms
	  	
	   	
	   	//Start timer
	  	if (clickCount % 2 != 0)
	  	{
	  		button.setDisable(true);
	  		
	  		taskRunning = true;
	  		gridPane.setStyle("-fx-background-color: #BCED91");
	  		start = time;

	  		if (isDelay) {
	  			int randomDelay = delay.nextInt(high-low) + low;
	  			delayTime = randomDelay;
	  			soundTimer(delayTime, startTime, button);
	  			
	  		}
	  		else {
	  			int randomDelay = 0;
	  			delayTime = randomDelay;
	  			soundTimer(delayTime, startTime, button);
	  		}
	  		//System.out.println("Random Delay: " + randomDelay);
	  		//mediaPlayer.play()
	  		marker.setRandomDelay(delayTime);
		   	
//			   	stopTime.setStyle("-fx-background-color: #00FF00;");
		   	//button.setStyle("-fx-background-color: #00FF00;");
		   	//final long time = System.currentTimeMillis();
		   				   	
	  		marker.setTimeStamp(time + delayTime);
	  		marker.setUnixTimeStamp(time + delayTime);
	  		marker.setMarkerType("Start");
	  		
	  		//StringProperty timeLabel = new SimpleStringProperty();
	  		
		   	
		   	//diable buttons
		   	for (Button b : buttonList)
		   	{			   		
		   		if (b.getId() != (button.getId()))
		   		{
		   			b.setDisable(true);
		   		}
		   	}

		   	
		   	marker.setCount(clickCount);		   	
		   	perfList.add(label);
	  	}
	  	
	  	//stop timer
	  	else {
	  		marker.setUnixTimeStampInSound(unixTimeStampInSound); // set sound timestamp when stopped to make sure the task is finished
		  	
	  		timeline.stop();
	  		
	  		taskRunning = false;
	  		
	  		button.setStyle("-fx-background-color: gray; -fx-font-size: 32");
	  		gridPane.setStyle("-fx-background-color: #DCDCDC");

	  		marker.setTimeStamp(time);
	  		marker.setUnixTimeStamp(time);
	  		marker.setMarkerType("Stop");
	  		stop = time;
	  		
	  		Long timeDelta = (stop - start) - delayTime;
	  		//System.out.println(timeDelta);
	  		marker.setTimeDelta(timeDelta);
	  		marker.setCount(clickCount);
		   	
		   	int repeats = 0;
		   	Map<String, Integer> hm  = countFrequencies(perfList);
		    for (Map.Entry<String, Integer> val : hm.entrySet()) {
		    	if( val.getKey() == label)
		    	{
		    		repeats = val.getValue();
		    		//System.out.println(label + " REPEATES: " + val.getValue());
		    	}
	        }
		    
		    StringProperty repeatCount = new SimpleStringProperty();
		    repeatCount.set(Integer.toString(repeats));
		   	countLabel.textProperty().bind(repeatCount); 	
		   				   	
		   	for (Button b : bQueue)
		   	{
		    	b.setDisable(false);
		   	} 
	  	}
	  	
  		markerList.add(marker);
  	}
  	
  	public static Map<String, Integer> countFrequencies(ArrayList<String> list) 
    { 
        // hash map to store the frequency of element 
        Map<String, Integer> hm = new HashMap<String, Integer>(); 
  
        for (String i : list) { 
            Integer j = hm.get(i); 
            hm.put(i, (j == null) ? 1 : j + 1); 
        } 
		return hm; 
    } 
  	
  	@FXML
  	private void ReRunPerf(){
  		System.out.println("RERUN PRESSES");
  		for (Button b : buttonList)
	   	{
	    	b.setDisable(false);
	   	} 
  		
  				    
  	}
  	
  	//play sound add stopwatch to node
  	private void soundTimer(Long delay, Label label, Button button) {
  		 	
  		long soundTimeStamp;
  		timeline.stop();
  		DoubleProperty timeSeconds = new SimpleDoubleProperty();
  		StringProperty timeLabel = new SimpleStringProperty();
  		mediaPlayer.stop();
  		
  		new Timer().schedule( 	  				
  		        new TimerTask() {
  		            @Override
  		            public void run() {
  		            	
  		            	long time = System.currentTimeMillis();
  		            	
  		            	unixTimeStampInSound = time;
  		            	
  		            	mediaPlayer.play();
  		                
  		            	this.cancel();
  		            	
  		            	timeWatch = Duration.ZERO;
  		            	timeline = new Timeline(
  		     	  		     new KeyFrame(Duration.millis(10),
  		     	  		    		 new EventHandler<ActionEvent>() {
  		     	  		    	 @Override
  		     	  		    	 public void handle (ActionEvent t) {
  		     	  		    		 Duration duration = ((KeyFrame)t.getSource()).getTime();
  		     	  		    		 timeWatch = timeWatch.add(duration);
  		     	  		    		 timeSeconds.set(timeWatch.toSeconds());
  		     	  		    		 timeLabel.set(Double.toString(timeWatch.toSeconds()));
  		     	  		    		 button.setDisable(false);
  		     	  		    	 }
  		     	  		     })
  		     	  		 );
  		     	  		 timeline.setCycleCount( Animation.INDEFINITE );
  		     	  		 timeline.play();
  		     	  		 //this.cancel();
  		     	  		 }
  		        }, 
  		        delay
  		);
  		
  		timeLabel.set(Double.toString(timeWatch.toSeconds()));
	   	label.textProperty().bind(timeLabel);
  	}
  	
  	private void StartDeviceRecording(ComConnect com)
  	{  		
  		Recording.setRecordingStart(System.currentTimeMillis());
   	    com.makeConnection();
	   	
	   	//ring progress bar
   	    RingIndicator.RingProgress(basePane);
   	    
	   	comPortLabel.setText("PORT= " + com.getAccessComPort());
	   	int battery = Recording.getBatteryStatus();
	   	double batteryDbl = ( (double) Recording.getBatteryStatus() / 100.0);
	   	batteryLevel.setProgress(batteryDbl);
	   	BatteryBar(batteryLevel);
	   	batteryLabel.setText(String.valueOf((battery)) + "%");
	   	
  	}
  	
	private void DebugStartDeviceRecording()
  	{
  		
  		Recording.setRecordingStart(System.currentTimeMillis());
  		LocalDateTime timeSet = LocalDateTime.now();
        Recording.setRecordingStartTimeStamp(timeSet);
        //DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("yyyy-MM-dd,HH:mm:ss.SSS");
        //String time = timeSet.format(formatTime);
	   	comPortLabel.setText("PORT= DEBUG MODE");
	   	Double battery = 0.45;	
	   	batteryLevel.setProgress(battery);
	   	BatteryBar(batteryLevel);
	   	batteryLabel.setText(String.valueOf((battery*100)) + "%");
	   	
  	}
	
	private void InputHelper(Input input) {
		AnimationTimer gameLoop = new AnimationTimer() {

			@Override
			public void handle(long now) {
				 // vertical direction
				//System.out.println(input.isPressed());
				if (!bQueue.isEmpty()) {
				    if( input.isPageDownPressed()) {
				       //System.out.println("PAGE DOWN");
				       bQueue.element().fire();
				            pageDownPressed = true;
				        } else if( input.isPageUpPressed()) {
				        	//System.out.println("PAGE UP");
				        	pageUpPressed = true;
				        } else if ( input.isPeriodPressed() && !taskRunning){
				        	//System.out.println("PERIOD!!");
				        	bQueue.element().setDisable(true);
				        	bQueue.remove();
				        	periodPressed=true;
				        } else if (input.isSlideShowPressed() && !taskRunning)
				        {
				        	bQueue.element().setDisable(true);
				        	bQueue.remove();
				        	slideShowPressed=true;
				        }
				}
			    input.setPageDownPressed(false);
			    input.setPageUpPressed(false);
			    input.setPeriodPressed(false);
			    input.setSlideShowPressed(false);

			}
	 		
	 	};
	    gameLoop.start();
	}
	
	private void BatteryBar(ProgressBar bar) {		
		final String RED_BAR    = "red-bar";
		final String YELLOW_BAR = "yellow-bar";
		final String ORANGE_BAR = "orange-bar";
		final String GREEN_BAR  = "green-bar";
		final String[] barColorStyleClasses = { RED_BAR, ORANGE_BAR, YELLOW_BAR, GREEN_BAR };
		
		double progress = bar.progressProperty().doubleValue();
		System.out.println(progress);
		bar.getStyleClass().removeAll(barColorStyleClasses);
		
        if (progress < 0.2) {
        	bar.getStyleClass().add(RED_BAR);
        } else if (progress < 0.4) {
        	bar.getStyleClass().add(ORANGE_BAR);
        } else if (progress < 0.6) {
        	bar.getStyleClass().add(YELLOW_BAR);
        } else {
        	bar.getStyleClass().add(GREEN_BAR);
        }     
	}
}