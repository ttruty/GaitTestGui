/**
 * Main scene setuo
 * 
 * @author Tim Truty
 *
 */

package controllers;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage stage) {
		
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		Scene scene = new Scene(new StackPane());
	    
	    LoginManager loginManager = new LoginManager(scene);
	    loginManager.showLoginScreen();

	    stage.setScene(scene);
	    stage.setTitle("Gait Test");
	    //stage.setFullScreen(true);
	    stage.setWidth(1045);
	    stage.setHeight(670);
	    //stage.setHeight(bounds.getHeight());
	    stage.show();	    
	}

	public static void main(String[] args) {
		launch(args);
	}
}
