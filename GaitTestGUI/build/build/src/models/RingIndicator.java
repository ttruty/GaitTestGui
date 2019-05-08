package models;

import indicator.RingProgressIndicator;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class RingIndicator {
	
	//static AnchorPane basePane;
	static Group indicators = new Group();
	
	public static void RingProgress(AnchorPane basePane) {
		
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
	}
	
	public static void removeRing(AnchorPane basePane) {
		basePane.getChildren().remove(indicators);
	}
}
