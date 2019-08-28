package view;



import dao.GetConnectionToDataBase;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.CreateButtons;

@SuppressWarnings("deprecation")
public class GameEntryViewManager extends GetConnectionToDataBase{
	private static final int SCENE_WIDTH = 800;
	private static final int SCENE_HEIGHT = 600;
	
	private AnchorPane gameEntryPane;
	private Scene gameEntryScene;
	private Stage gameEntryStage;
	private Button playButton;
	
	
	private ImageView startScreen4= new ImageView("/view/resources/startScreen4.png");
	private ImageView startScreen5= new ImageView("/view/resources/startSceneBackground1.png");
	
	public GameEntryViewManager() {
		gameEntryPane = new AnchorPane();
		gameEntryScene = new Scene(gameEntryPane,SCENE_WIDTH,SCENE_HEIGHT);
		gameEntryStage = new Stage();

		gameEntryStage.getIcons().add(new Image("/model/resources/gameIcon.png"));
		gameEntryStage.setResizable(false);
		gameEntryStage.setScene(gameEntryScene);
		
		gameEntryPane.getChildren().add(startScreen4);
		startScreen4.setOpacity(0);
		gameEntryPane.getChildren().add(startScreen5);
		startScreen5.setOpacity(0);
		
		AudioClip entryMusic = new AudioClip(getClass().getResource("/view/resources/EntryMusic.wav").toString());
		entryMusic.play();
		
		Image gameTitle = new Image("/view/resources/gameTitle.png");
		ImageView gameTitleView = new ImageView(gameTitle);
		gameTitleView.setLayoutX(123);
		gameTitleView.setLayoutY(25);
		
		FadeTransition first = new FadeTransition(Duration.seconds(6),startScreen4);
		first.setFromValue(1.0);
		first.setToValue(0.5);
		
		FadeTransition second = new FadeTransition(Duration.seconds(6),startScreen5);
		second.setFromValue(0.0);
		second.setToValue(1.0);
		
		SequentialTransition backgroundSequence = new SequentialTransition(first,second);
		backgroundSequence.play();
		
		Thread displayPlay = new Thread(new Runnable() {
			@Override
			public void run() {
				playButton = CreateButtons.GetButtons(gameEntryPane,"PLAY",300,450,190,49,"red");
				playButton.setOpacity(0.0);
				FadeTransition playFade = new FadeTransition(Duration.seconds(5),playButton);
				playFade.setFromValue(0.0);
				playFade.setToValue(1.0);
				playFade.play();	
				playButton.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						GameViewManager game = new GameViewManager("Goutham");
						game.CreateNewGame(gameEntryStage);
						entryMusic.stop();
						gameEntryStage.hide();
					}
				});
			}
			
		});
		
		Thread titleDisplay = new Thread(new Runnable() {
			@Override
			public void run() {
				FadeTransition titleFade = new FadeTransition(Duration.seconds(6),gameTitleView);
				titleFade.setFromValue(0.0);
				titleFade.setToValue(1.0);
				titleFade.play();	
				gameEntryPane.getChildren().add(gameTitleView);
			}
		});
		
		delayOfTitleButton(11000,titleDisplay);
		delayOfPlayButton(17000, displayPlay);
	}
	
	public static void delayOfTitleButton(int delayMs, Runnable toRun){
	    Thread displayTitleThread = new Thread(() ->{
	        try { 
	        	Thread.sleep(delayMs);
	        }
	        catch(InterruptedException sleepException)
	        {
	        	System.out.println(sleepException);
	        }
	        Platform.runLater(toRun);
	    });
	    displayTitleThread.setDaemon(true);
	    displayTitleThread.start();
	}


	public static void delayOfPlayButton(int delayMs, Runnable toRun){
	    Thread displayPlayThread = new Thread(() ->{
	        try { 
	        	Thread.sleep(delayMs);
	        }
	        catch(InterruptedException sleepException)
	        {
	        	System.out.println(sleepException);
	        }
	        Platform.runLater(toRun);
	    });
	    displayPlayThread.setDaemon(true);
	    displayPlayThread.start();
	}
	
	public Stage GetStage() {
		return gameEntryStage;
	}
}

