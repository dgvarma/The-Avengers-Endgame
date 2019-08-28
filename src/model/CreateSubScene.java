package model;

import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class CreateSubScene extends SubScene{
	
	private int initialX;
	private int initialY;
	
	private static AnchorPane subScenePane;

	public CreateSubScene(int width, int height, int initialSceneXCoordinate, int initialSceneYCoordinate) {
		super(new AnchorPane(), width, height);
		initialX = initialSceneXCoordinate;
		initialY = initialSceneYCoordinate;
		setLayoutX(initialX);
		setLayoutY(initialY);
		prefHeight(height);
		prefWidth(width);
		subScenePane = (AnchorPane) this.getRoot();
		subScenePane.setStyle("-fx-border-color:#D4AF37;"
				+ "-fx-background-color: #800000; "
				+ "-fx-background-radius: 5px;"
				+ " -fx-border-width: 1px;"
				+ " -fx-border-radius:5px");
	}
	
	public void moveSubScene(int xCoordinate, int yCoordinate) {
		TranslateTransition transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(0.3));
		transition.setNode(this);
		transition.setToX(xCoordinate-initialX);
		transition.setToY(yCoordinate-initialY);
		transition.play();
	}
	
	public AnchorPane GetSubScenePane() {
		return (AnchorPane) this.getRoot();
	}
}
