package controllers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage stage) {
//		try {
//			//load the rxtx libraries from the system path
//			// need to place ddl in Docs/GaitFiles/libs
//			String docDir = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() ;
//			String libSerialDir = docDir.concat("/GaitFiles/libs/rxtxSerial.dll");
//			String libParallelDir = docDir.concat("/GaitFiles/libs/rxtxParallel.dll");
//			System.load( libSerialDir);
//			//System.load( libParallelDir);			
//    	} catch (UnsatisfiedLinkError e) {
//    		System.err.println("Native code library failed to load.\n" + e);
//    	}
		
		Scene scene = new Scene(new StackPane());
	    
	    LoginManager loginManager = new LoginManager(scene);
	    loginManager.showLoginScreen();

	    stage.setScene(scene);
	    stage.setTitle("Gait Test");
	    stage.setFullScreen(true);
	    stage.setWidth(700);
	    stage.show();	    
	}

	public static void main(String[] args) {
		launch(args);
	}
}
