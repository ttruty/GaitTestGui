package controllers;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/** Manages control flow for logins */
public class LoginManager{
    
	private Scene scene;
	//private Stage stage;
	// player input
	Input input;
	
    
	
	public LoginManager(Scene scene) {
		
		this.scene = scene;
		input = new Input(scene);

	    // register input listeners
	    input.addListeners(); // TODO: remove listeners on game over
	}

	/**
	 * Callback method invoked to notify that a user has been authenticated. Will
	 * show the main application screen.
	 */
	public void authenticated(String sessionText) {
		showMainView(sessionText, input);
	}

	/**
	 * Callback method invoked to notify that a user has logged out of the main
	 * application. Will show the login application screen.
	 */
	public void logout() {
		showLoginScreen();
	}

	public void showLoginScreen() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
			scene.setRoot((Parent) loader.load());
			LoginController controller = loader.getController();
			controller.initManager(this);	
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private void showMainView(String sessionID, Input input) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Test.fxml"));
			scene.setRoot((Parent) loader.load());
			MainViewController controller = loader.getController();
			controller.initSessionID(this, sessionID, input);
		} catch (IOException ex) {
			ex.printStackTrace();
		}		
		
	}
}