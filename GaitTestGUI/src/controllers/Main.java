/**
 * Main scene setuo
 * 
 * @author Tim Truty
 *
 */

package controllers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage stage) {
		
		Scene scene = new Scene(new StackPane());
	    
	    LoginManager loginManager = new LoginManager(scene);
	    loginManager.showLoginScreen();

	    stage.setScene(scene);
	    stage.setTitle("RADC Gait Test");
	    //stage.setFullScreen(true);
	    stage.setWidth(1045);
	    stage.setHeight(785);
	    Image icon = new Image("file:resources/icon.png");
	    stage.getIcons().add(icon);
	    //stage.setHeight(bounds.getHeight());
	    stage.show();	    
	}

	public static void main(String[] args) {
		launch(args);
	}
}
