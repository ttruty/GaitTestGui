/**
 * Controller for the main testing screen
 * 
 * @author Tim Truty
 *
 */

package controllers;

import java.awt.event.KeyEvent;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.controlsfx.control.StatusBar;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.ConnectionStatus;
import models.ControlButton;
import models.Input;
import models.Marker;
import models.Recording;
import models.RingIndicator;
import models.ScriptPrompts;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import serialcoms.ComConnect;

/** Controls the main application screen */
public class MainViewController {
  
  @FXML private Button startTest;
  @FXML private Button stopTest;
  @FXML private Button saveButton;
  @FXML private Button sampleSoundButton;
  @FXML private CheckBox  hearingCheck;
  
  @FXML private Label  sessionLabel;
  @FXML private Label  timeLabel;
  @FXML private Label  projIdLabel;  
  @FXML private Label  fuYearLabel;  
  @FXML private Label  staffIdLabel;    
  @FXML private Label  comPortLabel;    
  @FXML private StatusBar statusBar;
  @FXML private GridPane gridPane;
  @FXML private AnchorPane basePane;
  @FXML private MenuBar fileMenuBar;
  @FXML private CheckMenuItem assistedMode;
  
  // performance buttons
  @FXML private Button perf_calib;
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
 
  @FXML private Label perf_calib_start;
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
  
  @FXML private Label perf_calib_count;
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

  // status indicators
  @FXML private ImageView statusImage; 
  @FXML private ProgressBar batteryLevel;
  @FXML private Label batteryLabel;
  @FXML private TextFlow promptTextFlow;
  
  // gui timing info
  private int clickCount = 0;
  long start;
  long stop;
  long delayTime;
  Duration timeWatch = Duration.ZERO;
  Timeline timeline = new Timeline();
  long unixTimeStampInSound; // set this in when the sound is played

  //arrays of buttons on gui
  Button[] buttonList = new Button[16];
  Button[] controlsList = new Button[4];
  Queue<Button> bQueue;
  
  // perfomance and marker lists
  ArrayList<String> perfList = new ArrayList<String>(); 
  ArrayList<Marker> markerList = new ArrayList<Marker>();
  
  //Drive info
  String driveLetter;
  String driveName;
  
  // input info
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
  
  // prompt vars
  Map<String, Text> prompts = new HashMap<String, Text>();
    
  /**
   * Initialize the main running project
   * Main initialization of objects and button controls happen here
   *
   * @param loginManager	the LoginManager object
   * @param sessionID		the number of starts of the current main view
   * @param input			the input object
   */
  public void initSessionID(final LoginManager loginManager, String sessionID, Input input) {	  
	  // play sound first on load to avoid lag later
	  mediaPlayer.play();
	  
	  // setting button array
	  buttonList[0] = perf_calib;
	  buttonList[1] = perf_8ft1;
	  buttonList[2] = perf_8ft2;
	  buttonList[3] = perf_eo;
	  buttonList[4] = perf_3601;
	  buttonList[5] = perf_ll;
	  buttonList[6] = perf_3602;
	  buttonList[7] = perf_ec;
	  buttonList[8] = perf_tug1;
	  buttonList[9] = perf_rl;
	  buttonList[10] = perf_tug2;
	  buttonList[11] = perf_tan;
	  buttonList[12] = perf_32ft;
	  buttonList[13] = perf_toe;
	  buttonList[14] = perf_cog1;
	  buttonList[15] = perf_cog2;
	  
	  // setting control button array
	  controlsList[0] = startTest;
	  controlsList[1] = stopTest;
	  controlsList[2] = sampleSoundButton;
	  controlsList[3] = saveButton;
	  	  
	  // button style and spacing
	  //for (Button button : buttonList) {
		  //button.setMaxWidth(Double.MAX_VALUE);
		  //button.setMaxHeight(Double.MAX_VALUE);
		  //button.setStyle("-fx-font-size:32");
		  //button.setPadding(Insets.EMPTY);
	  //}
	  
	  //Assisted Mode debugging
	  assistedMode.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		        System.out.println("assistedMode Enabled!");
		        System.out.println(assistedMode.isSelected());
		    }
		});
	  ScriptPrompts scriptPrompts = new ScriptPrompts();
	  prompts = scriptPrompts.generate();
	  
	  // button queue
	  bQueue = new LinkedList<>(Arrays.asList(buttonList));
	  
	  //disable buttons
	  saveButton.setDisable(true);
	  stopTest.setDisable(true);
	  
	  //Set text and indicators on gui
	  System.out.println(sessionID);
	  sessionLabel.setText("Session ID: " + sessionID);
	  timeLabel.setText("00:00:00");
	  projIdLabel.setText("ProjID: " + Recording.getRecordingId());
	  fuYearLabel.setText("F/U Year: " + Recording.getFuYear());
	  staffIdLabel.setText("Staff ID: " + Recording.getStaffId());
	  batteryLevel.getStylesheets().add(getClass().getResource("/views/progress.css").toExternalForm());
	 
	  // grid pane spacing
	  gridPane.prefHeightProperty().bind(basePane.heightProperty());
	  gridPane.prefWidthProperty().bind(basePane.widthProperty());	 
	 
	  // Set connection image
	  Image image = new Image("file:resources/connect.png");
	  statusImage.setImage(image);

	  // Instantiate connecting object
	  ComConnect com = new ComConnect();
 	 
 	 //start recording when screen loads
 	 if (!Recording.isDebugMode()) {
 		StartDeviceRecording(com);
 	 } else {
 		 DebugStartDeviceRecording();
 	 }
 	  	 
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
	 // end Time label
	  
 	
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
    	 
    	 Text text = new Text("Before unplugging the DynaPort, make sure the device "
    	 		+ "is placed directly on the laptop’s speaker; make sure the belt "
    	 		+ "is facing up and is NOT between the speaker and the DynaPort.  "
    	 		+ "After unplugging the device, make sure it is completely still "
    	 		+ "and run the calibration test.  Once the calibration test is "
    	 		+ "complete, the device can be removed from the laptop.");
    	 text.setWrappingWidth(600);
    	 text.setFont(Font.font ("Verdana", 24));
    	 text.setFill(Color.RED);
    	 promptTextFlow.getChildren().add(text); 
    	 
    	 if (assistedMode.isSelected())
    	 {
    		 Alert alert = new Alert(Alert.AlertType.INFORMATION);
//    		 alert.getButtonTypes().set(0, ButtonType.NO);
//    		 alert.getButtonTypes().set(1, ButtonType.YES);
    		 alert.getDialogPane().getStylesheets().add("/styles/style.css");
    		 //alert.setGraphic(new ImageView(getIcon(icon)));
    		 Label lb = (Label) alert.getDialogPane().getChildren().get(1);
    		 lb.setWrapText(true); //Attempt to set wrapText option
    		 alert.setTitle("GAIT SCRIPT");
    		 alert.setHeaderText("Prompt");
    		 alert.getDialogPane().setContent(text);
    		 //alert.setContentText(content);
    		 
    		 alert.showAndWait();
    	 }
    	 
    	 
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
		  
		  }); 
      //end start button
      
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
			}); 
	  // end stop button
	    
	  //save button
	  saveButton.setOnAction((e) -> {
		  Recording.setMarkerList(markerList);
		  ControlButton.Save(com, basePane);	
		  Recording.setHearingImpaired(hearingCheck.isSelected());
		    }); 
	  // end save button
	  
	  
	  // Connection Status images and status bar update
	  ConnectionStatus connStatus = new ConnectionStatus();
	  connStatus.ShowStatus(statusImage, statusBar, com, basePane, controlsList, gridPane);
	  connStatus.unplugStatus(com, basePane, controlsList, gridPane);
	  
	  // Performance buttons
	  perf_calib.setOnAction((e) -> {
		  	perfButton(perf_calib, "calibrate", perf_calib_start, perf_calib_count, false);
				});
	  perf_8ft1.setOnAction((e) -> {
		  	perfButton(perf_8ft1, "walk_8ft_1", perf_8ft1_start, perf_8ft1_count, true);
				});
	  perf_8ft2.setOnAction((e) -> {
		  	perfButton(perf_8ft2, "walk_8ft_2", perf_8ft2_start, perf_8ft2_count, true);
				});
	  perf_eo.setOnAction((e) -> {
		  	perfButton(perf_eo, "eo", perf_eo_start, perf_eo_count, false);
				});
	  perf_3601.setOnAction((e) -> {
		  	perfButton(perf_3601, "turn_360_1", perf_3601_start, perf_3601_count, true);
				});
	  perf_ll.setOnAction((e) -> {
		  	perfButton(perf_ll, "ll", perf_ll_start, perf_ll_count, false);
				});
	  perf_3602.setOnAction((e) -> {
		  	perfButton( perf_3602, "turn_360_2", perf_3602_start, perf_3602_count, true);
				});
	  perf_ec.setOnAction((e) -> {
		  	perfButton(perf_ec, "ec", perf_ec_start, perf_ec_count, false);
				});
	  perf_tug1.setOnAction((e) -> {
		  	perfButton(perf_tug1,"tug_1", perf_tug1_start, perf_tug1_count, true);
				});
	  perf_rl.setOnAction((e) -> {
		  	perfButton(perf_rl, "rl", perf_rl_start, perf_rl_count, false);
				});
	  perf_tug2.setOnAction((e) -> {
		  	perfButton(perf_tug2, "tug_2", perf_tug2_start, perf_tug2_count, true);
				});
	  perf_tan.setOnAction((e) -> {
		  	perfButton(perf_tan, "tan", perf_tan_start, perf_tan_count, true);
				});
	  perf_32ft.setOnAction((e) -> {
		  	perfButton(perf_32ft, "walk_32ft", perf_32ft_start, perf_32ft_count, true);
				});
	  perf_toe.setOnAction((e) -> {
		  	perfButton(perf_toe, "toe", perf_toe_start, perf_toe_count, false);
				});
	  perf_cog1.setOnAction((e) -> {
		  	perfButton(perf_cog1, "cog_1", perf_cog1_start, perf_cog1_count, true);
				});
	  perf_cog2.setOnAction((e) -> {
		  	perfButton(perf_cog2, "cog_2", perf_cog2_start, perf_cog2_count, true);
				});
  	}
  
  /**
   * Button controls of the performance buttons
   *
   * @param button		the button object on gui
   * @param label		the string object of performance label
   * @param startTime	the label object of starting time of performance
   * @param countLabel 	The label object for times the performance ran
   * @param isDelay		boolean object for if the performance requires a random delay (moving tasks)
   * 
  **/
	private void perfButton(Button button, String label, Label startTime, Label countLabel, boolean isDelay) 
  	{
		ArrayList<String> labelList = new ArrayList<String>();
			labelList.add("calibrate");
			labelList.add("walk_8ft_1");
			labelList.add("walk_8ft_2");
			labelList.add("eo");
			labelList.add("turn_360_1");
			labelList.add("ll");
			labelList.add("turn_360_2");
			labelList.add("ec"); 
			labelList.add("tug_1");
			labelList.add("rl");
			labelList.add("tug_2");
			labelList.add("tan");
			labelList.add("walk_32ft");
			labelList.add("toe");
			labelList.add("cog_1");
			labelList.add("cog_2");
		
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
  		int low = 1000;
  		int high = 3000; //1 ms to 3000 ms
	  	
  		
	   	
	   	//Start timer
	  	if (clickCount % 2 != 0)
	  	{
	  		
	  		
	  		//Assisted mode
	  		if (assistedMode.isSelected())	  		{  				  			
	  			Text text = prompts.get(label);
	  			ButtonType start = new ButtonType("Start", ButtonData.OK_DONE);
	  			ButtonType skip = new ButtonType("Skip", ButtonData.CANCEL_CLOSE);
	  			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	  			alert.getButtonTypes().set(0, start);
	  			alert.getButtonTypes().set(1, skip);
	  			//alert.getDialogPane().getStylesheets().add("/styles/style.css");
	  			//alert.setGraphic(new ImageView(getIcon(icon)));
	  			Label lb = (Label) alert.getDialogPane().getChildren().get(1);
	  			lb.setWrapText(true); //Attempt to set wrapText option
	  			alert.setTitle("GAIT SCRIPT");
	  			alert.setHeaderText(label);
	  			alert.getDialogPane().setContent(text);
	  			//alert.setContentText(content);
	  			//Platform.runLater(alert::showAndWait);
	  			Optional<ButtonType> result = alert.showAndWait();
	  			if (result.orElse(skip) == start) {
	  			    System.out.println("Continue");
	  			} 
	  			if (result.orElse(start) == skip) {
	  				button.setStyle("-fx-background-color: gray");
	  		  		gridPane.setStyle("-fx-background-color: #DCDCDC");
	  				return;
	  			}
	  			
	  		}
	  		
	  		button.setDisable(true);
	  		
	  		taskRunning = true;
	  		gridPane.setStyle("-fx-background-color: #BCED91");
	  		start = time;

	  		if (isDelay) {
	  			int randomDelay = delay.nextInt(high-low) + low;
	  			delayTime = randomDelay;
	  			soundTimer(delayTime, startTime, button, true);
	  			
	  		}
	  		
	  		else if (!isDelay & label.equals("calibrate"))
	  		{
	  			int randomDelay = 0;
	  			delayTime = randomDelay;
	  			soundTimer(delayTime, startTime, button, true);
	  		}
	  		else {
	  			int randomDelay = 0;
	  			delayTime = randomDelay;
	  			soundTimer(delayTime, startTime, button, false);
	  		}
	  		//System.out.println("Random Delay: " + randomDelay);
	  		//mediaPlayer.play()
	  		marker.setRandomDelay(delayTime);
		   	
	  		//stopTime.setStyle("-fx-background-color: #00FF00;");
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
	  		
	  		promptTextFlow.getChildren().clear(); //clear out textflow
	  		
	  		// prompt side bar
	  		if (labelList.indexOf(label) < 15)
	  		{		
		  		
		  		
		  		String promtLabel = labelList.get(labelList.indexOf(label) + 1);
		  		Text text = prompts.get(promtLabel);
		  		text.setWrappingWidth(600);
		    	text.setFont(Font.font ("Verdana", 24));
		    	text.setFill(Color.RED);
		    	promptTextFlow.getChildren().add(text); 
	  		}
	  		else {
	  			Text text = prompts.get("finish");
		  		text.setWrappingWidth(600);
		    	text.setFont(Font.font ("Verdana", 24));
		    	text.setFill(Color.RED);
		    	promptTextFlow.getChildren().add(text); 
	  		}
	  		
	  		marker.setUnixTimeStampInSound(unixTimeStampInSound); // set sound timestamp when stopped to make sure the task is finished
		  	
	  		timeline.stop();
	  		
	  		taskRunning = false;
	  		
	  		button.setStyle("-fx-background-color: gray");
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
  	
  	/**
     * Controls the sound and timer object of playing sound 
     *
     * @param delay		long num that is the amount of ms to delay sound
     * @param label		label object of timer
     * @param button	button object of perf
     * @param button	boolean for if sound is played
     */
  	private void soundTimer(Long delay, Label label, Button button, boolean isSound) {
  		 	
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
  		            	
  		            	//play the sound
  		            	if(isSound) {
	  		            	mediaPlayer.play();
	  		                
	  		            	this.cancel();
  		            	}
  		            	
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
  	
  	/**
     * Make connection to serial port for project
     *
     * @param com	the Comconnect object that controls the serial connection of project
     */
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
  	
  	/**
     * 	Sets the mode to debug so a device does not need to be connected
     * 	in order to test the gui
     * 
     */
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
	
	/**
	   * helper for using the Logitec r200 presenter with gui project
	   * 
	   * @param input	Input object to use with project
	   */
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
				        } 
				    else if( input.isPageUpPressed()) {
				        	//System.out.println("PAGE UP");
				    	sampleSoundButton.fire();
				        pageUpPressed = true;
				        } 
				    else if (input.isPeriodPressed() && !taskRunning){
				        	//System.out.println("PERIOD!!");
				        bQueue.element().setDisable(true);
				        bQueue.remove();
				        periodPressed=true;				        	
				        } 
				    else if (input.isSlideShowPressed() && !taskRunning)
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
	
	/**
	   * Sets the battery bar on gui
	   *
	   * @param bar		the progress bar object to update
	   */
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
	
	//About menu item on menu bar
	  @FXML
	  public void aboutHelp(ActionEvent event) {
		  // select file from the menu bar
		  Stage stage = (Stage) fileMenuBar.getScene().getWindow();
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