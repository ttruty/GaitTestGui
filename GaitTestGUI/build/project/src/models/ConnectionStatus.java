package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.controlsfx.control.StatusBar;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import serialcoms.ComConnect;

public class ConnectionStatus {
	
	//status bar	 
	StringProperty connectedString = new SimpleStringProperty();
	
		 
	public void ShowStatus(ImageView statusImage, StatusBar statusBar) {
		connectedString.set("Gait Test in Progress...");
		
		Recording.connectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if (newValue)
				{
					//System.out.println("Plugged IN>>>>");
					Platform.runLater(new Runnable() {
						@Override
						public void run() {						
							connectedString.set("Connected: TRUE");
							Image image = new Image("file:resources/connect.png");
							statusImage.setImage(image);
							
							if (!Recording.isSaved() && !Recording.isRecording() && Recording.getStartButtonPressed() != null)
							{
								LocalDateTime timeSet = LocalDateTime.now();
								DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("yyyy-MM-dd,HH:mm:ss.SSS");
						        String time = timeSet.format(formatTime);
								System.out.println("RECONNECTED at  " + time);
								
								Recording.setReconnectTime(System.currentTimeMillis());
							}
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
							Image image = new Image("file:resources/disconnect.png");
							statusImage.setImage(image);
							if (Recording.isRecording()) {
						        LocalDateTime timeSet = LocalDateTime.now();
						        Recording.setRecordingStartTimeStamp(timeSet);
						        DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("yyyy-MM-dd,HH:mm:ss.SSS");
						        String time = timeSet.format(formatTime);
						        System.out.println("UNPLUGGED at " + time);
						        
						        Image image1 = new Image("file:resources/walk_icon.png");
								statusImage.setImage(image1);
							}

						}				
					});
				}
			}
			});  
		
		statusBar.textProperty().bind(connectedString);
	  
	}
	
	
	public void unplugStatus(ComConnect com, AnchorPane basePane, Button[] buttons, GridPane gridPane) {
		//all grid objects
	 	 ObservableList<Node> childrens = gridPane.getChildren();
		 //OK TO UNPLUG MESSAGE
		 com.connectedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					
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
											  //basePane.getChildren().remove(indicators);
										RingIndicator.removeRing(basePane);
						        	  alert.showAndWait();
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
						        	  
						        	  //disable all grid controls
						        	  for (Node node : childrens) {
						  				node.setDisable(true);					  			    
						  			}
									RingIndicator.removeRing(basePane);

						        	//basePane.getChildren().remove(indicators);
						        	
									for (Button b : buttons) {
										b.setDisable(true);
									}
//						        	startTest.setDisable(true);
//						        	stopTest.setDisable(true);
//						        	saveButton.setDisable(true);
//						        	sampleSoundButton.setDisable(true);
								}
							});
						}
				
					}						
				}
				});
	}
	
}
