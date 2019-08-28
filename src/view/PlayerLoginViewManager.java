package view;

import dao.PlayerLoginDAO;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import model.CreateButtons;
import model.CreateCheckBox;
import model.CreateLabels;
import model.CreateSubScene;
import model.CreateTextFields;

public class PlayerLoginViewManager extends PlayerLoginDAO{
	private static final int SCENE_WIDTH = 800;
	private static final int SCENE_HEIGHT = 600;
	
	private AnchorPane playerLoginPane;
	private AnchorPane loginSubScenePane;
	private Scene playerLoginScene;
	private Stage playerLoginStage;
	private Label userCheckLabel;
	private Button enter;
	private Button loginOrRegister;
	private Button back;
	private Button exit;
	private TextField playerName;
	private CheckBox loginCheckBox;
	private String player;
	private boolean isPlayerInDataBase;
	private AnchorPane NotInDBWarningPane;
	private AnchorPane FillDetailsWarningPane;
	private AnchorPane RegistrationConfirmationPane;
	private boolean isRegistered;
	private AnchorPane RegistrationWarningPane;
	
	public PlayerLoginViewManager() {
		AudioClip buttonMusic = new AudioClip(getClass().getResource("/view/resources/ButtonClick.wav").toString());
		
		playerLoginPane = new AnchorPane();
		playerLoginPane.setStyle("-fx-background-image: url('/view/resources/playerLoginPageBackground.png')");
		Image ironManImage = new Image(getClass().getResourceAsStream("/view/resources/ironMan.png"));
		ImageView ironManImageView = new ImageView(ironManImage);
		ironManImageView.setLayoutX(55);
		ironManImageView.setLayoutY(100);
		playerLoginPane.getChildren().add(ironManImageView);
	
		ImageView backgroundTitle = new ImageView("/view/resources/FrameBackground.png");
		backgroundTitle.setLayoutX(300);
		backgroundTitle.setLayoutY(150);
		backgroundTitle.setOpacity(0.8);
		playerLoginPane.getChildren().add(backgroundTitle);
		
		CreateSubScene loginSubScene = new CreateSubScene(400,450,900,100);
		loginSubScene.setOpacity(0.9);
		playerLoginPane.getChildren().add(loginSubScene);
		
		CreateSubScene NotInDBWarningSubScene = new CreateSubScene(400,300,900,150);
		NotInDBWarningSubScene.setOpacity(0.9);
		playerLoginPane.getChildren().add(NotInDBWarningSubScene);
		NotInDBWarningPane = NotInDBWarningSubScene.GetSubScenePane();
		
		CreateSubScene FillDetailsWarningSubScene = new CreateSubScene(400,300,900,150);
		FillDetailsWarningSubScene.setOpacity(0.9);
		playerLoginPane.getChildren().add(FillDetailsWarningSubScene);
		FillDetailsWarningPane = FillDetailsWarningSubScene.GetSubScenePane();
		
		CreateSubScene RegistrationConfirmationSubScene = new CreateSubScene(400,300,900,150);
		RegistrationConfirmationSubScene.setOpacity(0.9);
		playerLoginPane.getChildren().add(RegistrationConfirmationSubScene);
		RegistrationConfirmationPane = RegistrationConfirmationSubScene.GetSubScenePane();
		
		CreateSubScene RegistrationWarningSubScene = new CreateSubScene(400,300,900,150);
		RegistrationWarningSubScene.setOpacity(0.9);
		playerLoginPane.getChildren().add(RegistrationWarningSubScene);
		RegistrationWarningPane = RegistrationWarningSubScene.GetSubScenePane();
		
		CreateLabels.GetLabel(RegistrationWarningPane, "You are already registered", 50, 75, 22);
		Button okInRegistrationWarning = CreateButtons.GetButtons(RegistrationWarningPane, "OK", 175, 200, 49, 49, "green");
		
		okInRegistrationWarning.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				buttonMusic.play();
				RegistrationWarningSubScene.moveSubScene(900, 150);
				loginSubScene.moveSubScene(350, 100);
				loginCheckBox.setSelected(false);
			}
		});
		
		CreateLabels.GetLabel(RegistrationConfirmationPane, "You are registered", 75, 75, 22);
		Button okInConfirmation = CreateButtons.GetButtons(RegistrationConfirmationPane, "OK", 175, 200, 49, 49, "green");
		
		okInConfirmation.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				buttonMusic.play();
				RegistrationConfirmationSubScene.moveSubScene(900, 150);
				loginSubScene.moveSubScene(350, 100);
				loginCheckBox.setSelected(false);
			}
		});
		
		CreateLabels.GetLabel(NotInDBWarningPane, "You are not registered", 75, 75, 22);
		Button okInNotInDBWarning = CreateButtons.GetButtons(NotInDBWarningPane, "OK", 175, 200, 49, 49, "green");
		
		CreateLabels.GetLabel(FillDetailsWarningPane, "Fill Your Name", 100, 75, 24);
		Button okInFillDetails = CreateButtons.GetButtons(FillDetailsWarningPane, "OK", 175, 200, 49, 49, "green");
		
		okInFillDetails.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				buttonMusic.play();
				FillDetailsWarningSubScene.moveSubScene(900, 150);
				loginSubScene.moveSubScene(350, 100);	
			}
		});
		
		okInNotInDBWarning.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				buttonMusic.play();
				NotInDBWarningSubScene.moveSubScene(900, 150);
				loginSubScene.moveSubScene(350, 100);
				loginCheckBox.setSelected(true);
			}
		});
		
		loginSubScenePane = loginSubScene.GetSubScenePane();
		playerLoginScene = new Scene(playerLoginPane,SCENE_WIDTH,SCENE_HEIGHT);
		playerLoginStage = new Stage();
		playerLoginStage.getIcons().add(new Image("/model/resources/gameIcon.png"));
		playerLoginStage.setResizable(false);
		playerLoginStage.setScene(playerLoginScene);
		
		playerLoginScene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
		
		enter = CreateButtons.GetButtons(loginSubScenePane, "ENTER",100,350,190,49,"red");
		loginOrRegister = CreateButtons.GetButtons(playerLoginPane, "GET IN", 450,25,190,49,"red");
		loginOrRegister.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent event) {
				buttonMusic.play();
				loginSubScene.moveSubScene(350,100);			
			}
		});
		
		exit = CreateButtons.GetButtons(playerLoginPane, "EXIT", 50, 25,190,49,"red");
		
		exit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				buttonMusic.play();
				Platform.exit();
			}
			
		});
		playerName = CreateTextFields.GetTextField(loginSubScenePane,"Player Name",50,50,300,50);
		
		
		loginCheckBox = CreateCheckBox.GetCheckBox(loginSubScenePane, 25, 267);
		
		CreateLabels.GetLabel(loginSubScenePane, "Check this box if you are a new player", 65, 265,18);
		
		
		enter.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
				buttonMusic.play();
				player = playerName.getText();
				GoToNextWindow();
				/*if(player.isEmpty()) {
					loginSubScene.moveSubScene(900, 100);
					FillDetailsWarningSubScene.moveSubScene(350, 150);
				}
				else {
					if(loginCheckBox.isSelected()) {
						isRegistered = RegisterPlayerInDataBase(player);
						if(isRegistered) {
							RegistrationConfirmationSubScene.moveSubScene(350, 150);
							loginSubScene.moveSubScene(900, 100);
						}
						else {
							RegistrationWarningSubScene.moveSubScene(350, 150);
							loginSubScene.moveSubScene(900, 100);
						}
					}
					else {
						try {
							isPlayerInDataBase = IsPlayerInDataBaseVerification(player);
							System.out.println(isPlayerInDataBase);
							if(!isPlayerInDataBase) {
								NotInDBWarningSubScene.moveSubScene(350, 150);
								loginSubScene.moveSubScene(900, 100);
							}
							else {
								
							}
						} 
						catch (Exception e) {
							System.out.println(e);
						}
						
					}*/
				}
		/*	}	*/	
		});
	}
	
	protected void GoToNextWindow() {
		UserProfileViewManager userProfileView = new UserProfileViewManager(player);
		Stage userProfileStage = userProfileView.GetStage();
		userProfileStage.show();
		playerLoginStage.hide();
	}

	public Stage GetStage() {
		return playerLoginStage;
	}
	
}

