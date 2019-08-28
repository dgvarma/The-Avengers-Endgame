package view;

import dao.UserProfileDAO;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import model.CreateButtons;
import model.CreateLabels;
import model.CreateSubScene;
import model.CreateTextFields;

public class UserProfileViewManager extends UserProfileDAO{
	private static final int SCENE_WIDTH = 800;
	private static final int SCENE_HEIGHT = 600;
	
	private AnchorPane userProfilePane;
	private AnchorPane userViewPane;
	private AnchorPane scorePane;
	private AnchorPane settingsPane, editPane, deletePane;
	private Scene userProfileScene;
	private Stage userProfileStage;
	
	private Boolean isScorePaneOn = false;
	private Boolean isSettingsPaneOn = false;
	private Boolean isDeletePaneOn = false;
	private Boolean isEditPaneOn = false;
	
	private Button logout;
	private Button exit;
	private Button viewProfile;
	private Button gameStart;
	private Button score;
	private Button settings, ok, noDelete, yes, noEdit;
	private Button editProfile, deleteProfile, change, delete;
	private String editedName;
	
	private Label scoreLabel, settingsLabel, name, deleteConfirmation;
	
	private TextField nameFieldToBeEdited;
	private static String userName;
	private boolean isUpdated;
	private Button guidelines;
	private AnchorPane guidelinesPane;
	private Button okInGuidelines;
	protected boolean isGuidelinesOn;

	
	public UserProfileViewManager(String playerName) {
		
		AudioClip buttonMusic = new AudioClip(getClass().getResource("/view/resources/ButtonClick.wav").toString());
		
		userName = playerName;
		userProfilePane = new AnchorPane();
		userProfilePane.setStyle("-fx-background-image: url('/view/resources/userLoginBackground.png')");
		Image ironManHulkBusterImage = new Image(getClass().getResourceAsStream("/view/resources/ironManHulkBuster.png"));
		ImageView ironManHulkBusterView = new ImageView(ironManHulkBusterImage);
		ironManHulkBusterView.setLayoutX(0);
		ironManHulkBusterView.setLayoutY(150);
		userProfilePane.getChildren().add(ironManHulkBusterView);
		Image thanosImage = new Image(getClass().getResourceAsStream("/view/resources/thanos.png"));
		ImageView thanosImageView = new ImageView(thanosImage);
		thanosImageView.setLayoutX(500);
		thanosImageView.setLayoutY(150);
		userProfilePane.getChildren().add(thanosImageView);
		
		ImageView backgroundTitle = new ImageView("/view/resources/userBackground.png");
		backgroundTitle.setLayoutX(300);
		backgroundTitle.setLayoutY(130);
		backgroundTitle.setOpacity(0.8);
		userProfilePane.getChildren().add(backgroundTitle);
		
		userProfileScene = new Scene(userProfilePane, SCENE_WIDTH, SCENE_HEIGHT);
		userProfileStage = new Stage();
		userProfileStage.getIcons().add(new Image("/model/resources/gameIcon.png"));
		userProfileStage.setResizable(false);
		userProfileStage.setScene(userProfileScene);
		CreateSubScene userProfileSubScene = new CreateSubScene(250,300,280,-300);
		userProfileSubScene.setOpacity(0.9);
		userViewPane = userProfileSubScene.GetSubScenePane();
		
		userProfilePane.getChildren().add(userProfileSubScene);
		
		guidelines = CreateButtons.GetButtons(userProfilePane, "GUIDELINES", 310, 440, 190, 49, "red");
		
		
		
		score = CreateButtons.GetButtons(userViewPane, "SCORES", 30, 50,190,49,"red");
		settings = CreateButtons.GetButtons(userViewPane, "SETTINGS", 30, 150,190,49,"red");
		
		CreateSubScene userSettingsSubScene = new CreateSubScene(230,300,-250,125);
		userSettingsSubScene.setOpacity(0.9);
		settingsPane = userSettingsSubScene.GetSubScenePane();
		CreateSubScene userScoreSubScene = new CreateSubScene(230, 300, 900, 125);
		userScoreSubScene.setOpacity(0.9);
		scorePane = userScoreSubScene.GetSubScenePane();
		
		CreateLabels.GetLabel(scorePane, "Won", 95, 175, 22);
		CreateLabels.GetLabel(scorePane, "Played", 85, 75, 22);
		
		Line seperationLine = new Line(15,50,215,50);
		seperationLine.setStyle("-fx-stroke: #D4AF37");
		scorePane.getChildren().add(seperationLine);
		
		int numberOfGamesPlayed = GetNumberOfGamesPlayed(playerName);
		String numberOfPlays = Integer.toString(numberOfGamesPlayed);
		CreateLabels.GetLabel(scorePane, numberOfPlays, 105, 125, 24);
		
		int numberOfGamesWon = GetNumberOfGamesWon(playerName);
		String numberOfWins = Integer.toString(numberOfGamesWon);
		CreateLabels.GetLabel(scorePane,numberOfWins , 105, 225, 24);
		
		CreateLabels.GetLabel(scorePane, "SCORES", 75, 15, 24);
		CreateLabels.GetLabel(settingsPane, "SETTINGS", 60, 5, 24);
		userProfilePane.getChildren().add(userScoreSubScene);
		userProfilePane.getChildren().add(userSettingsSubScene);
		
		editProfile = CreateButtons.GetButtons(settingsPane, "EDIT", 20, 90,190,49,"red");
		deleteProfile = CreateButtons.GetButtons(settingsPane, "DELETE", 20, 170,190,49,"red");
		
		CreateSubScene userEditSubScene = new CreateSubScene(480, 300, 900, 125);
		userEditSubScene.setOpacity(0.9);
		userProfilePane.getChildren().add(userEditSubScene);
		editPane = userEditSubScene.GetSubScenePane();
		
		CreateSubScene userDeleteSubScene = new CreateSubScene(480, 300, 900, 125);
		userDeleteSubScene.setOpacity(0.9);
		userProfilePane.getChildren().add(userDeleteSubScene);
		deletePane = userDeleteSubScene.GetSubScenePane();
		
		CreateLabels.GetLabel(deletePane, "Do you want to delete your profile?", 25, 75, 18);
		
		CreateLabels.GetLabel(editPane,"NAME", 30, 80, 24);
		nameFieldToBeEdited = CreateTextFields.GetTextField(editPane,userName, 130, 70, 300, 50);
		
		ok = CreateButtons.GetButtons(userViewPane, "OK", 100, 225,49,49,"green");

		ok.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				buttonMusic.play();
				if(isScorePaneOn || isSettingsPaneOn) {
					userScoreSubScene.moveSubScene(900, 125);
					userSettingsSubScene.moveSubScene(-250, 125);
				}
				userProfileSubScene.moveSubScene(280, -300);
			}
			
		});
		
		change = CreateButtons.GetButtons(editPane, "CHANGE", 145, 200,190,49,"red");
		
		change.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				buttonMusic.play();
				userEditSubScene.moveSubScene(900, 125);
				userProfileSubScene.moveSubScene(280, 125);
				
				editedName = nameFieldToBeEdited.getText();
				
				isUpdated = ChangeTheNameOfPlayer(editedName, userName);
				if(isUpdated) {
					userName = editedName;
				}
				if(isScorePaneOn) {
					userScoreSubScene.moveSubScene(550, 125);
				}		
			}		
		});
		noEdit = CreateButtons.GetButtons(editPane, "NO", 360, 200, 49, 49, "green");
		
		noEdit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				buttonMusic.play();
				userEditSubScene.moveSubScene(900, 125);
				userProfileSubScene.moveSubScene(280, 125);
				if(isScorePaneOn) {
					userScoreSubScene.moveSubScene(550, 125);
				}
			}		
		});
		
		yes = CreateButtons.GetButtons(deletePane, "DELETE", 145, 200,190,49,"red");
		
		yes.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				buttonMusic.play();
				DeleteProfile(userName);
				GoToLoginWindow();
			}
		});
		
		noDelete = CreateButtons.GetButtons(deletePane, "NO", 360, 200, 49, 49, "green");
		
		noDelete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				buttonMusic.play();
				userDeleteSubScene.moveSubScene(900, 125);
				userProfileSubScene.moveSubScene(280, 125);
				if(isScorePaneOn) {
					userScoreSubScene.moveSubScene(550, 125);
				}		
			}		
		});
		
		deleteProfile.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				buttonMusic.play();
				if(isEditPaneOn) {
					userEditSubScene.moveSubScene(900, 125);
				}
				if(isScorePaneOn) {
					userScoreSubScene.moveSubScene(900, 125);
				}
				userProfileSubScene.moveSubScene(900, 125);
				userDeleteSubScene.moveSubScene(280, 125);
				isDeletePaneOn = true;
			}
		});
		editProfile.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				buttonMusic.play();
				if(isScorePaneOn) {
					userScoreSubScene.moveSubScene(900, 125);
				}
				if(isDeletePaneOn) {
					userDeleteSubScene.moveSubScene(900, 125);
				}
				userProfileSubScene.moveSubScene(900, 125);
				userEditSubScene.moveSubScene(280, 125);
				isEditPaneOn = true;
			}
		});
		score.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				buttonMusic.play();
				userScoreSubScene.moveSubScene(550, 125);
				isScorePaneOn = true;
			}
		});
		settings.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				buttonMusic.play();
				userSettingsSubScene.moveSubScene(30, 125);
				isSettingsPaneOn = true;
			}
		});
		logout = CreateButtons.GetButtons(userProfilePane, "LOGOUT", 50, 25,190,49,"red");
		logout.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				buttonMusic.play();
				GoToLoginWindow();
			}
		});
		
		exit = CreateButtons.GetButtons(userProfilePane, "EXIT", 575, 25,190,49,"red");
		exit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				buttonMusic.play();
				Platform.exit();
				
			}
			
		});
		
		viewProfile = CreateButtons.GetButtons(userProfilePane, "PROFILE", 310, 25,190,49,"red");
		
		viewProfile.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				buttonMusic.play();
				userProfileSubScene.moveSubScene(280, 125);
				
			}
		});
		
		guidelines.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				buttonMusic.play();
				GuideLines guidelines = new GuideLines(userName);
				Stage guidelinesStage = guidelines.GetStage();
				guidelinesStage.show();
				userProfileStage.hide();
			}
		});
		
		
		gameStart = CreateButtons.GetButtons(userProfilePane, "START", 310, 500,190,49,"red");
		gameStart.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				buttonMusic.play();
				GameViewManager gameView = new GameViewManager(playerName);
				gameView.CreateNewGame(userProfileStage);
				UpdateNumberOfGamesPlayed(userName);
			}
		});
	}
	
	public void GoToLoginWindow() {
		PlayerLoginViewManager playerLoginView = new PlayerLoginViewManager();
		Stage playerLoginStage = playerLoginView.GetStage();
		playerLoginStage.show();
		userProfileStage.hide();
	}
	
	public Stage GetStage() {
		return userProfileStage;
	}

}
