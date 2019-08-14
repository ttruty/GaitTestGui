package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

import org.controlsfx.control.StatusBar;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressIndicator;
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
	boolean waiting = false;
		 
	public void ShowStatus(ImageView statusImage, StatusBar statusBar, ComConnect com, AnchorPane basePane, Button[] buttons, GridPane gridPane) {
		connectedString.set("Gait Test in Progress...");
		AtomicInteger taskExecution = new AtomicInteger(0);
		
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
								
								System.out.println("Connection NEW  OBSERVATION");						
								
								System.out.println("Plug Alert Changed: waiting");
								Platform.runLater(new Runnable() {
									@Override
									public void run() {								
										Alert alert = new Alert(AlertType.INFORMATION,
												"Please wait while device is set\n"
												+ "PLACE ON THE SPEAKER NOW\n", 
							                      ButtonType.OK);
												  //basePane.getChildren().remove(indicators);
											RingIndicator.removeRing(basePane);
											
											alert.setHeaderText("Please wait... ");
								            ProgressIndicator progressIndicator = new ProgressIndicator();
								            alert.setGraphic(progressIndicator);
								            Task<Void> task = new Task<Void>() {
								                final int N_ITERATIONS = 15;
								 
								                {
								                    setOnFailed(a -> {
								                        alert.close();
								                        updateMessage("Failed");
								                    });
								                    setOnSucceeded(a -> {
								                        alert.close();
								                        updateMessage("Succeeded");
								                    });
								                    setOnCancelled(a -> {
								                        alert.close();
								                        updateMessage("Cancelled");
								                    });
								                }
								 
								                @Override
								                protected Void call() throws Exception {
								                    updateMessage("Processing");
								 
								                    int i;
								                    for (i = 0; i < N_ITERATIONS; i++) {
								                        if (isCancelled()) {
								                            break;
								                        }
								 
								                        updateProgress(i, N_ITERATIONS);
								 
								                        try {
								                            Thread.sleep(1_000);
								                        } catch (InterruptedException e) {
								                            Thread.interrupted();
								                        }
								                    }
								 
								                    if (!isCancelled()) {
								                        updateProgress(i, N_ITERATIONS);
								                    }
								 
								                    return null;
								                }
								            };
								 
								            progressIndicator.progressProperty().bind(task.progressProperty());
								           // processResult.textProperty().unbind();
								           // processResult.textProperty().bind(task.messageProperty());
								 
								            Thread taskThread = new Thread(
								                    task,
								                    "task-thread-" + taskExecution.getAndIncrement()
								            );
								            taskThread.start();
								 
								            //alert.initOwner(stage);
								            alert.showAndWait();
								            waiting = false;
								            //if (result.isPresent() && result.get() == ButtonType.CANCEL && task.isRunning()) {
								            //    task.cancel();
								           // }
								            //
							        	 // alert.showAndWait();
										//connectedString.set("Ok to UNPLUG");
									}
								});							
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
						System.out.println("Plug Alert NEW  OBSERVATION");
						if (!Recording.isSaved() && !waiting)
						{
							System.out.println("Plug Alert Changed: set up");
							Platform.runLater(new Runnable() {
								@Override
								public void run() {								
									Alert alert = new Alert(AlertType.INFORMATION,
											"PLACE DEVICE ON SPEAKER BEFORE UNPLUGGING \n"
						                      + "...   OK TO UNPLUG  ...", 
						                      ButtonType.OK);
											  //basePane.getChildren().remove(indicators);
										RingIndicator.removeRing(basePane);								
							            alert.showAndWait();
							            waiting = true;
							            //if (result.isPresent() && result.get() == ButtonType.CANCEL && task.isRunning()) {
							            //    task.cancel();
							           // }
							            //
						        	 // alert.showAndWait();
									//connectedString.set("Ok to UNPLUG");
								}
							});
						}					
						else { //when the recording is save close EVERYTING!
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
