package view;

import java.util.HashMap;

import dao.GameDAO;
import dao.UserProfileDAO;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.CreateButtons;
import model.CreateLabels;
import model.CreateSubScene;

@SuppressWarnings("deprecation")
public class GameViewManager extends GameDAO{
	
	AudioClip gameMusic = new AudioClip(getClass().getResource("/view/resources/gameMusic.mp3").toString());
	AudioClip buttonMusic = new AudioClip(getClass().getResource("/view/resources/ButtonClick.wav").toString());
	private AnchorPane gamePane, exitPane, pausePane;
	private Scene gameScene;
	private Stage gameStage;
	
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	
	private ProgressBar ironManEnergy, thanosEnergy;

	private Button pause, exit, play, noExit, yesExit, goBack;
	
	private HashMap<String, Boolean> currentlyActiveKeys = new HashMap<>();
	
	private AnimationTimer gameTimer;
	
	private int velocityOfThanos = 8, velocityOfIronMan = 5;
	private double velocityOfBlast = 25;

	private Group thanosMovements = new Group();
	
	private Timeline thanosAnimation;
	
	private int numberOfTimesHitByBlueAttack = 0;
	private int numberOfTimesHitByIronManBlast = 0;
	private int numberOfTimesKillBlastIsUsed = 0;
	
	private boolean isLeftKeyPressed, isRightKeyPressed, isUpKeyPressed, isDownKeyPressed;
	private boolean isExitOn = false, isPauseOn = false, isGameSceneOn = true;
	private boolean isBlastOn, isIronManOn,isThanosOn, isIronManBlastOn, isBlastViewOn;
	private boolean isKillBlastViewOn;
	private boolean isIronManKillBlastOn,isthanosBlueAttackOn,isthanosRedAttackOn;
	private boolean isCollisionDetected = false;
	private boolean isthanosKillOn;
	private boolean isIronMandead, isThanosdead;
	private boolean isGameEnd;
	private boolean isThanosHitByIronManBlast;
	private boolean isThanosHitByIronManKillBlast;
	private boolean isKillBlastOn;
	private boolean isIronManHitByBlueAttack,isIronManHitByRedAttack, isThanosHit,isIronManHit;
	
	private ImageView ironManView = new ImageView("/view/resources/ironManFloating.png");
	private ImageView thanosFlyingView = new ImageView("/view/resources/thanosFloating.png");
	private ImageView blastView = new ImageView("/view/resources/ironManBlast.png");
	private ImageView ironManBlastView = new ImageView("/view/resources/ironManAttack.png");
	private ImageView thanosRedAttackview = new ImageView("/view/resources/thanosRedAttack.png");
	private ImageView thanosBlueAttackview = new ImageView("/view/resources/thanosBlueAttack.png");
	private ImageView ironManKillBlastView = new ImageView("/view/resources/ironManKillBlast.png");
	private ImageView killBlastView = new ImageView("/view/resources/killBlast.png");
	private ImageView thanosBlueBlastView = new ImageView("/view/resources/blueAttack.png");
	private ImageView thanosRedBlastView = new ImageView("/view/resources/redAttack.png");
	private ImageView ironManHit = new ImageView(new Image("/view/resources/ironManTakingAHit.png"));
	private ImageView meteorBlast = new ImageView(new Image("/view/resources/meteorBlast.png"));
	private ImageView thanosHitView = new ImageView(new Image("/view/resources/thanosTakingHit.png"));
	private ImageView thanosKillView = new ImageView("/view/resources/thanosFullPower.png");
	private ImageView ironManDeathView = new ImageView("/view/resources/ironManDeath.png");
	private ImageView ThanosDeathView = new ImageView("/view/resources/thanosDeath.png");

	private AnchorPane winPane, defeatPane;
	CreateSubScene winScene, defeatScene;
	private Button playAgain;
	
	private static String userName;
	
	public GameViewManager(String playerName) {
		
		userName = playerName;
		
		gameMusic.setVolume(0.3);
		gameMusic.play();
		
		gamePane = new AnchorPane();
		gamePane.setStyle("-fx-background-image: url('view/resources/gameBackground.png')");
		gameScene = new Scene(gamePane, WIDTH, HEIGHT);
		gameStage = new Stage();
		gameStage.getIcons().add(new Image("/model/resources/gameIcon.png"));
		gameStage.setResizable(false);
		gameStage.setScene(gameScene);
		
		pause = CreateButtons.GetButtons(gamePane, "PAUSE", 50, 20, 190, 49, "red");
		
		CreateSubScene pauseScene = new CreateSubScene(750,70,900,520);
		pausePane = pauseScene.GetSubScenePane();
		gamePane.getChildren().add(pauseScene);
		
		pause.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				buttonMusic.play();
				thanosAnimation.pause();
				gameTimer.stop();
				if(isGameSceneOn) {
					if(! isExitOn) {
						isPauseOn = true;
						pauseScene.moveSubScene(30, 520);
					}
				}
			}
		});
		
		CreateLabels.GetLabel(pausePane, "Game is Paused", 140, 25, 22);
		
		play = CreateButtons.GetButtons(pausePane, "PLAY", 450, 10, 190, 49, "red");
		
		play.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				buttonMusic.play();
				pauseScene.moveSubScene(900, 520);
				isPauseOn = false;
				thanosAnimation.play();
				gameTimer.start();
			}
		});
		
		exit = CreateButtons.GetButtons(gamePane, "EXIT", 554, 20, 190, 49, "red");
		
		CreateSubScene exitScene = new CreateSubScene(750,70,900,520);
		exitPane = exitScene.GetSubScenePane();
		gamePane.getChildren().add(exitScene);
		
		exit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				buttonMusic.play();
				isExitOn = true;
				if(isPauseOn) {
					pauseScene.moveSubScene(900, 520);
				}
				exitScene.moveSubScene(30, 520);
				thanosAnimation.pause();
				gameTimer.stop();
			}	
		});
		
		CreateLabels.GetLabel(exitPane, "Are you sure you want to exit?", 60, 25, 16);
		
		yesExit = CreateButtons.GetButtons(exitPane, "YES", 450, 10, 190, 49, "red");
		
		yesExit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				buttonMusic.play();
				if(!isGameEnd) {
					UpdateNumberOfGames(userName);
				}
				Platform.exit();
			}
		});
		
		noExit = CreateButtons.GetButtons(exitPane, "NO", 650, 10, 49, 49, "green");
		
		noExit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				buttonMusic.play();
				exitScene.moveSubScene(900, 520);
				if(isGameSceneOn) {
					isPauseOn = true;
					pauseScene.moveSubScene(30, 520);
				}
				isExitOn = false;
			}
		});
		
		winScene = new CreateSubScene(750,70,900,520);
		winPane = winScene.GetSubScenePane();
		gamePane.getChildren().add(winScene);
		
		CreateLabels.GetLabel(winPane, "You Killed Thanos!", 100, 20, 28);
		
		goBack = CreateButtons.GetButtons(winPane, "GO BACK", 500, 10, 190, 49, "red");
		
		goBack.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				buttonMusic.play();
				UserProfileViewManager userProfileView = new UserProfileViewManager(playerName);
				Stage userProfileStage = userProfileView.GetStage();
				userProfileStage.show();
				gameStage.hide();	
			}
		});
		
		defeatScene = new CreateSubScene(750,70,900,520);
		defeatPane = defeatScene.GetSubScenePane();
		gamePane.getChildren().add(defeatScene);
		
		CreateLabels.GetLabel(defeatPane, "You Lost", 300, 20, 28);
		
		playAgain = CreateButtons.GetButtons(defeatPane, "Play Again", 500, 10, 190, 49, "red");
		
		playAgain.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				buttonMusic.play();
				GameViewManager gameView = new GameViewManager(userName);
				gameView.CreateNewGame(gameStage);
				UserProfileDAO.UpdateNumberOfGamesPlayed(userName);
			}
		});
		
		CreateKeyListeners();
	}
	
	private void GetIronMan() {
		ironManView.setLayoutX(55);
		ironManView.setLayoutY(100);
		gamePane.getChildren().add(ironManView);
		isIronManOn = true;
	}
	
	private void CreateKeyListeners() {
		 gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if(isCollisionDetected) {
					event.consume();
					isCollisionDetected = false;
					return;
				}
				String codeString = event.getCode().toString();
				if(!currentlyActiveKeys.containsKey(codeString)) {
					currentlyActiveKeys.put(codeString, true);
					if(event.getCode() == KeyCode.RIGHT) {
						isRightKeyPressed = true;
					}	
					else if(event.getCode() == KeyCode.LEFT) {
						isLeftKeyPressed = true;
					}
					else if(event.getCode() == KeyCode.UP) {
						isUpKeyPressed = true;
					}
					else if(event.getCode() == KeyCode.DOWN) {
						isDownKeyPressed = true;
					}
					else if(event.getCode() == KeyCode.A) {
						if(! isBlastOn) {
							GetBlast();
							if(!isIronManBlastOn) {
								gamePane.getChildren().add(ironManBlastView);
								isIronManBlastOn = true;
							}
							if(isIronManOn) {
								gamePane.getChildren().remove(ironManView);
								isIronManOn = false;
							}
							if(!isBlastViewOn) {
								gamePane.getChildren().add(blastView);
								isBlastOn = true;
								isBlastViewOn = true;
							}
						}
					}
					else if(event.getCode() == KeyCode.CONTROL) {
						if(numberOfTimesKillBlastIsUsed < 3) {
							if(!isKillBlastOn) {
								GetKillBlast();
								if(!isIronManKillBlastOn) {
									gamePane.getChildren().add(ironManKillBlastView);
									isIronManKillBlastOn = true;
								}
								if(isIronManOn) {
									gamePane.getChildren().remove(ironManView);
									isIronManOn = false;
								}
								if(!isKillBlastViewOn) {
									gamePane.getChildren().add(killBlastView);
									isKillBlastOn = true;
									isKillBlastViewOn = true;
								}
							}
							numberOfTimesKillBlastIsUsed += 1;
						}	
					}
				}
			}		
		});
		gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				
				currentlyActiveKeys.remove(event.getCode().toString());
				if(event.getCode() == KeyCode.RIGHT) {
					isRightKeyPressed = false;
				}
				else if(event.getCode() == KeyCode.LEFT) {
					isLeftKeyPressed = false;
				}
				else if(event.getCode() == KeyCode.UP) {
					isUpKeyPressed = false;
				}
				else if(event.getCode() == KeyCode.DOWN) {
					isDownKeyPressed = false;
				}
				else if(event.getCode() == KeyCode.A) {
					if(isIronManBlastOn) {
						gamePane.getChildren().remove(ironManBlastView);
						isIronManBlastOn = false;
					}
					if(isBlastViewOn) {
						isBlastViewOn = false;
					}
					if(! isIronManOn) {
						gamePane.getChildren().add(ironManView);
						isIronManOn = true;
					}
				}
				else if(event.getCode() == KeyCode.CONTROL) {
					if(isIronManKillBlastOn) {
						gamePane.getChildren().remove(ironManKillBlastView);
						isIronManKillBlastOn = false;
					}
					if(isKillBlastViewOn) {
						isKillBlastViewOn = false;
					}
					if(!isIronManOn) {
						gamePane.getChildren().add(ironManView);
						isIronManOn = true;
					}
				}
			}			
		});
	}

	public void GameLoop() {
		gameTimer = new AnimationTimer() {		
			@Override
			public void handle(long now) {
				if(!isGameEnd) {
					MoveIronMan();
					MoveThanos();
				}
				MoveBlast();
				MoveKillBlast();
				MoveThanosBlueAttack();
				MoveThanosRedAttack();
				DetectCollision();
				ReduceLife();
			}
		};
		gameTimer.start();
	}

	protected void MoveKillBlast() {
		if(isKillBlastOn) {
			killBlastView.setLayoutX(killBlastView.getLayoutX() + velocityOfBlast);
			if(killBlastView.getLayoutX() >= 900) {
				gamePane.getChildren().remove(killBlastView);
				isKillBlastOn = false;
			}
		}	
	}
	
	protected void MoveBlast() {
		if(isBlastOn) {
			blastView.setLayoutX(blastView.getLayoutX() + velocityOfBlast);
			if(blastView.getLayoutX() >= 820) {
				gamePane.getChildren().remove(blastView);
				isBlastOn = false;
			}
		}
	}

	protected void DetectCollision() {
		if(ironManView.getBoundsInParent().intersects(thanosBlueBlastView.getBoundsInParent())) {
			isCollisionDetected = true;
			isIronManHitByBlueAttack = true;
			if(isthanosBlueAttackOn) {
				gamePane.getChildren().remove(thanosBlueBlastView);
				isthanosBlueAttackOn = false;
			}
			thanosBlueBlastView.setLayoutX(900);
			IronManTakesAStraightHit();
		}
		else if(ironManView.getBoundsInParent().intersects(thanosRedBlastView.getBoundsInParent())) {
			isCollisionDetected = true;
			isIronManHitByRedAttack = true;
			if(isthanosRedAttackOn) {
				gamePane.getChildren().remove(thanosRedBlastView);
				isthanosRedAttackOn = false;
			}
			thanosRedBlastView.setLayoutY(-900);
			IronManTakesATopHit();
		}
		else if(thanosMovements.getBoundsInParent().intersects(blastView.getBoundsInParent())) {
			isCollisionDetected = true;
			isThanosHitByIronManBlast = true;
			gamePane.getChildren().remove(blastView);
			blastView.setLayoutX(-100);
			ThanosTakesABlastHit();
		}
		else if(thanosMovements.getBoundsInParent().intersects(killBlastView.getBoundsInParent())) {
			isCollisionDetected = true;
			isThanosHitByIronManKillBlast = true;
			gamePane.getChildren().remove(killBlastView);
			killBlastView.setLayoutX(900);
			ThanosTakesABlastHit();
		}
		else if((thanosMovements.getLayoutX() - ironManView.getLayoutX())<=100 && Math.abs(thanosMovements.getLayoutY()- ironManView.getLayoutY())<=50) {
			isGameEnd = true;
			isCollisionDetected = true;
			thanosAnimation.stop();
			thanosKillView.setLayoutX(thanosMovements.getLayoutX());
			thanosKillView.setLayoutY(thanosMovements.getLayoutY());
			gamePane.getChildren().remove(thanosMovements);
			if(!isthanosKillOn) {
				gamePane.getChildren().add(thanosKillView);
				isthanosKillOn = true;
			}
			IronManDeath();	
		}
	}
	
	private void ReduceLife() {
		if(isIronManHitByRedAttack) {
			ironManEnergy.setProgress(ironManEnergy.getProgress()-0.1);
			isIronManHitByRedAttack = false;
		}
		else if(isIronManHitByBlueAttack) {
			numberOfTimesHitByBlueAttack  += 1;
			if(numberOfTimesHitByBlueAttack == 3) {
				ironManEnergy.setProgress(ironManEnergy.getProgress()-0.1);
				numberOfTimesHitByBlueAttack = 0;
			}
			isIronManHitByBlueAttack = false;
		}
		else if(isThanosHitByIronManBlast) {
			numberOfTimesHitByIronManBlast += 1;
			if(numberOfTimesHitByIronManBlast == 6) {
				thanosEnergy.setProgress(thanosEnergy.getProgress()-0.1);
				numberOfTimesHitByIronManBlast = 0;
			}
			isThanosHitByIronManBlast = false;
		}
		else if(isThanosHitByIronManKillBlast) {
			thanosEnergy.setProgress(thanosEnergy.getProgress()-0.1);
			isThanosHitByIronManKillBlast = false;
		}
		if(ironManEnergy.getProgress() <= 0.4 && ironManEnergy.getProgress()>0.0) {
			ironManEnergy.setStyle("-fx-accent: red");
		}
		if(ironManEnergy.getProgress() <= 0.0) {
			isGameEnd = true;
			thanosAnimation.stop();
			gamePane.getChildren().remove(ironManEnergy);
			IronManDeath();
		}
		if(thanosEnergy.getProgress() <= 0.4 && thanosEnergy.getProgress()>0.0) {
			thanosEnergy.setStyle("-fx-accent: red");
		}
		if(thanosEnergy.getProgress() <= 0.0) {
			isGameEnd = true;
			thanosAnimation.stop();
			gamePane.getChildren().remove(thanosEnergy);
			ThanosDeath();
		}
	}
	
	private void ThanosDeath() {
		gameMusic.stop();
		ThanosDeathView.setLayoutX(thanosMovements.getLayoutX());
		ThanosDeathView.setLayoutY(thanosMovements.getLayoutY());
		gamePane.getChildren().remove(thanosMovements);
		if(!isThanosdead) {
			gamePane.getChildren().add(ThanosDeathView);
			isThanosdead = true;
		}
		gameTimer.stop();
		winScene.moveSubScene(30, 520);
		UpdateNumberOfWins(userName);
	}

	private void IronManDeath() {
		gameMusic.stop();
		ironManDeathView.setLayoutX(ironManView.getLayoutX());
		ironManDeathView.setLayoutY(ironManView.getLayoutY());
		gamePane.getChildren().remove(ironManView);
		if(!isIronMandead) {
			gamePane.getChildren().add(ironManDeathView);
			isIronMandead = true;
		}
		gameTimer.stop();
		defeatScene.moveSubScene(30,520);
	}
	
	private void ThanosTakesABlastHit() {
		TimelineBuilder.create().cycleCount(1)
		.keyFrames(
				new KeyFrame(Duration.seconds(0), new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						if(!isThanosdead) {
							thanosHitView.setLayoutX(thanosMovements.getLayoutX());
							thanosHitView.setLayoutY(thanosMovements.getLayoutY());
							if(isThanosOn) {
								gamePane.getChildren().remove(thanosMovements);
								isThanosOn = false;
							}
							if(!isThanosHit) {
								gamePane.getChildren().add(thanosHitView);
								isThanosHit = true;
							}	
						}
					}		
				}),
				new KeyFrame(Duration.seconds(0.5),new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						if(!isThanosdead) {
							if(isThanosHit) {
								gamePane.getChildren().remove(thanosHitView);
								isThanosHit = false;
							}
							if(!isThanosOn) {
								gamePane.getChildren().add(thanosMovements);
								isThanosOn = true;
							}
						}
					}
				})	
				).build().play();
	}

	protected void IronManTakesAStraightHit() {
		TimelineBuilder.create().cycleCount(1)
		.keyFrames(
				new KeyFrame(Duration.seconds(0), new EventHandler<ActionEvent>() {


					@Override
					public void handle(ActionEvent event) {
						if(!isIronMandead) {
							ironManHit.setLayoutX(ironManView.getLayoutX());
							ironManHit.setLayoutY(ironManView.getLayoutY());
							if(isIronManOn) {
								gamePane.getChildren().remove(ironManView);
								isIronManOn = false;
							}
							if(!isIronManHit) {
								gamePane.getChildren().add(ironManHit);
								isIronManHit = true;
							}
						}
					}	
				}),
				new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						if(!isIronMandead) {
							if(isIronManHit) {
								gamePane.getChildren().remove(ironManHit);
								isIronManHit = false;
							}
							if(!isIronManOn) {
								gamePane.getChildren().add(ironManView);
								isIronManOn = true;
							}
						}
					}
				})
				).build().play();
	}
	
	private void GetLifeBars() {
		ironManEnergy = new ProgressBar(1);
		ironManEnergy.setStyle("-fx-accent: green");
		ironManEnergy.setLayoutX(50);
		ironManEnergy.setLayoutY(75);
		ironManEnergy.setPrefWidth(300);
		ironManEnergy.setPrefHeight(20);
		ImageView ironManEnergyView = new ImageView("/view/resources/ironManEnergy.png");
		ironManEnergyView.setLayoutX(15);
		ironManEnergyView.setLayoutY(65);
		gamePane.getChildren().add(ironManEnergyView);
		gamePane.getChildren().add(ironManEnergy);
		
		thanosEnergy = new ProgressBar(1);
		thanosEnergy.setStyle("-fx-accent: green");
		thanosEnergy.setLayoutX(470);
		thanosEnergy.setLayoutY(75);
		thanosEnergy.setPrefWidth(300);
		thanosEnergy.setPrefHeight(20);
		ImageView thanosEnergyView = new ImageView("/view/resources/thanosEnergy.png");
		thanosEnergyView.setLayoutX(425);
		thanosEnergyView.setLayoutY(63);
		gamePane.getChildren().add(thanosEnergyView);
		gamePane.getChildren().add(thanosEnergy);
	}
	
	protected void IronManTakesATopHit() {
		TimelineBuilder.create().cycleCount(1)
		.keyFrames(
				new KeyFrame(Duration.seconds(0), new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						if(!isIronMandead) {
							meteorBlast.setLayoutX(ironManView.getLayoutX() - 70);
							meteorBlast.setLayoutY(ironManView.getLayoutY() - 60);
							if(isIronManOn) {
								gamePane.getChildren().remove(ironManView);
								isIronManOn = false;
							}
							if(!isIronManHit) {
								gamePane.getChildren().add(meteorBlast);
								isIronManHit = true;
							}
						}	
					}	
				}),
				new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						if(!isIronMandead) {
							if(!isIronManOn) {
								gamePane.getChildren().add(ironManView);
								isIronManOn = true;
							}
							if(isIronManHit) {
								gamePane.getChildren().remove(meteorBlast);
								isIronManHit = false;
							}
						}
					}
				})
				).build().play();
	}
	
	private void MoveThanos() {
		if(thanosMovements.getLayoutY()<120 || thanosMovements.getLayoutY()>400) {
			velocityOfThanos = -velocityOfThanos;
		}
		thanosMovements.setLayoutY(thanosMovements.getLayoutY() + velocityOfThanos);
	}
	
	private void MoveIronMan() {	
		if(isRightKeyPressed) {
			if(ironManView.getLayoutX() <= 680) {
				ironManView.setLayoutX(ironManView.getLayoutX() + velocityOfIronMan);
			}
		}
		else if(isLeftKeyPressed) {
			if(ironManView.getLayoutX() >= 20) {
				ironManView.setLayoutX(ironManView.getLayoutX() - velocityOfIronMan);
			}
		}
		else if(isUpKeyPressed) {
			if(ironManView.getLayoutY() >= 120) {
				ironManView.setLayoutY(ironManView.getLayoutY() - velocityOfIronMan);
			}
		}
		else if(isDownKeyPressed) {
			if(ironManView.getLayoutY() <= 400) {
				ironManView.setLayoutY(ironManView.getLayoutY() + velocityOfIronMan);
			}
		}
	}

	public void CreateNewGame(Stage userProfileStage) {
		userProfileStage.hide();
		GetIronMan();
		GetThaons();
		GameLoop();
		GetLifeBars();
		gameStage.show();
	}
	
	private void GetKillBlast() {
		ironManKillBlastView.setLayoutX(ironManView.getLayoutX());
		ironManKillBlastView.setLayoutY(ironManView.getLayoutY());
		killBlastView.setLayoutX(ironManKillBlastView.getLayoutX() + 42);
		killBlastView.setLayoutY(ironManKillBlastView.getLayoutY() + 5);
	}

	private void GetBlast() {
		ironManBlastView.setLayoutX(ironManView.getLayoutX());
		ironManBlastView.setLayoutY(ironManView.getLayoutY());
		blastView.setLayoutX(ironManBlastView.getLayoutX() + 80);
		blastView.setLayoutY(ironManBlastView.getLayoutY() + 10);
	}

	private void GetThaons() {
		isThanosOn = true;
		thanosMovements = new Group(thanosFlyingView);
		thanosMovements.setLayoutX(600);
		thanosMovements.setLayoutY(120);
		thanosAnimation = TimelineBuilder.create().cycleCount(Animation.INDEFINITE)
		.keyFrames(
				new KeyFrame(Duration.seconds(0.5), new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						thanosMovements.getChildren().setAll(thanosFlyingView);				
					}	
				}),
				new KeyFrame(Duration.seconds(1.1), new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						thanosMovements.getChildren().setAll(thanosRedAttackview);
						GetThanosRedAttack();
						if(!isthanosRedAttackOn) {
							gamePane.getChildren().add(thanosRedBlastView);
							isthanosRedAttackOn = true;
						}
					}
				}),
				new KeyFrame(Duration.seconds(1.6), new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						thanosMovements.getChildren().setAll(thanosFlyingView);	
					}		
				}),
				new KeyFrame(Duration.seconds(2.1), new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						thanosMovements.getChildren().setAll(thanosBlueAttackview);
						GetThanosBlueAttack();
						if(!isthanosBlueAttackOn) {
							gamePane.getChildren().add(thanosBlueBlastView);
							isthanosBlueAttackOn = true;
						}
					}		
				}),
				new KeyFrame(Duration.seconds(2.6), new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						thanosMovements.getChildren().setAll(thanosFlyingView);	
					}		
				}),
				new KeyFrame(Duration.seconds(3.1), new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						thanosMovements.getChildren().setAll(thanosRedAttackview);
						GetThanosRedAttack();
						if(!isthanosRedAttackOn) {
							gamePane.getChildren().add(thanosRedBlastView);
							isthanosRedAttackOn = true;
						}
					}
				}),
				new KeyFrame(Duration.seconds(3.6), new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						thanosMovements.getChildren().setAll(thanosFlyingView);	
					}		
				}),
				new KeyFrame(Duration.seconds(4.1), new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						thanosMovements.getChildren().setAll(thanosBlueAttackview);
						GetThanosBlueAttack();
						if(!isthanosBlueAttackOn) {
							gamePane.getChildren().add(thanosBlueBlastView);
							isthanosBlueAttackOn = true;
						}
					}		
				})
				).build();
		thanosAnimation.play();
		gamePane.getChildren().add(thanosMovements);
	}

	protected void MoveThanosBlueAttack() {
		if(isthanosBlueAttackOn) {
			thanosBlueBlastView.setLayoutX(thanosBlueBlastView.getLayoutX() - 10);
			if(thanosBlueBlastView.getLayoutX() <= -600) {
				gamePane.getChildren().remove(thanosBlueBlastView);
				isthanosBlueAttackOn = false;
			}
		}	
	}
	
	protected void MoveThanosRedAttack() {
		if(isthanosRedAttackOn) {
			thanosRedBlastView.setLayoutY(thanosRedBlastView.getLayoutY() + 8);
			if(thanosRedBlastView.getLayoutY() >= 620) {
				gamePane.getChildren().remove(thanosRedBlastView);
				isthanosRedAttackOn = false;
			}
		}
	}
	
	protected void GetThanosRedAttack() {
		thanosRedBlastView.setLayoutX(ironManView.getLayoutX());
		thanosRedBlastView.setLayoutY(-150);
	}

	protected void GetThanosBlueAttack() {
		thanosBlueBlastView.setLayoutX(530);
		thanosBlueBlastView.setLayoutY(thanosMovements.getLayoutY() + 20);	
	}	
}
