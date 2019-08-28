package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.CreateButtons;
import model.CreateLabels;

public class GuideLines { 
	
	private static final double SCENE_WIDTH = 800;
	private static final double SCENE_HEIGHT = 600;
	private AnchorPane guidelinesPane;
	private Stage guidelinesStage;
	private Scene guidelinesScene;
	private Button okInGuidelines;
	public GuideLines(String playerName) {
		guidelinesPane = new AnchorPane();
		guidelinesPane.setStyle("-fx-background-color: #800000");
		guidelinesScene = new Scene(guidelinesPane, SCENE_WIDTH, SCENE_HEIGHT);
		guidelinesStage = new Stage();
		guidelinesStage.getIcons().add(new Image("/model/resources/gameIcon.png"));
		guidelinesStage.setResizable(false);
		guidelinesStage.setScene(guidelinesScene);
		CreateLabels.GetLabel(guidelinesPane, "Press 'A' for Attacking with Normal Blasts", 25, 25, 16);
		CreateLabels.GetLabel(guidelinesPane, "Press 'CTRL' for Attacking with Big Blasts", 25, 55, 16);
		CreateLabels.GetLabel(guidelinesPane, "Press 'UP' key for to move Iron man up", 25, 85, 16);
		CreateLabels.GetLabel(guidelinesPane, "Press 'DOWN' key for to move Iron man down", 25, 115, 16);
		CreateLabels.GetLabel(guidelinesPane, "Press 'LEFT' key for to move Iron man left", 25, 145, 16);
		CreateLabels.GetLabel(guidelinesPane, "Press 'RIGHT' key for to move Iron man right", 25, 175, 16);
		CreateLabels.GetLabel(guidelinesPane, "Hit 60 times with normal blasts to kill thanos", 25, 205, 16);
		CreateLabels.GetLabel(guidelinesPane, "Hit 3 times with big blasts to reduce 30% of thanos energy", 25, 235, 16);
		CreateLabels.GetLabel(guidelinesPane, "You can use big blasts only three times", 25, 265, 16);
		CreateLabels.GetLabel(guidelinesPane, "If you are hit by meteors, you will loose 10% of your energy", 25, 295, 16);
		CreateLabels.GetLabel(guidelinesPane, "If you are hit by thanos blasts 3 times, you will loose 10% of your energy", 25, 325, 16);
		CreateLabels.GetLabel(guidelinesPane, "DON'T GO TOO CLOSE TO HIM", 25, 355, 24);
		
		okInGuidelines = CreateButtons.GetButtons(guidelinesPane, "OK", 700, 500, 49, 49, "green");
		okInGuidelines.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				UserProfileViewManager userProfileView = new UserProfileViewManager(playerName);
				Stage userProfileStage = userProfileView.GetStage();
				userProfileStage .show();
				guidelinesStage.hide();	
			}
		});
	
	}
	
	public Stage GetStage() {
		return guidelinesStage;
	}
	
}
