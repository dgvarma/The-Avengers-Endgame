package model;

import java.io.FileInputStream;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CreateButtons extends Button{
	private static final String UNCLICKED_BUTTON = "-fx-shadow-highlight-color: transparent;"
			+ "-fx-background-radius:5px;"
			+ "-fx-focus-color:red; "
			+ "-fx-backgorund-color: transparent; "
			+ "-fx-background-image: url('/model/resources/redButtonUnclicked.png')";
	private static final String CLICKED_BUTTON = "-fx-shadow-highlight-color: transparent;"
			+ "-fx-background-radius:5px;"
			+ "-fx-focus-color:red; "
			+ "-fx-background-color: transparent; "
			+ "-fx-background-image: url('/model/resources/redButtonClicked.png')";
	private static final String UNCLICKED_GREEN_BUTTON = "-fx-shadow-highlight-color: transparent;"
			+ "-fx-background-radius:5px;"
			+ "-fx-focus-color:green; "
			+ "-fx-backgorund-color: transparent; "
			+ "-fx-background-image: url('/model/resources/greenButtonUnclicked.png')";
	private static final String CLICKED_GREEN_BUTTON = "-fx-shadow-highlight-color: transparent;"
			+ "-fx-background-radius:5px;"
			+ "-fx-focus-color:green; "
			+ "-fx-background-color: transparent; "
			+ "-fx-background-image: url('/model/resources/greenButtonClicked.png')";

	public CreateButtons(String buttonText, int xCoordinate, int yCoordinate, int width, int height, String colorName) {
		setText(buttonText);
		setTextFill(Color.WHITE);
		setPrefWidth(width);
		setPrefHeight(height);
		setLayoutX(xCoordinate);
		setLayoutY(yCoordinate);
		SetTextFontOnButton(colorName);
		if(colorName.equals("red")) {
			setStyle(UNCLICKED_BUTTON);
		}
		else {
			setStyle(UNCLICKED_GREEN_BUTTON);
		}
		InitializeButtonListeners(colorName);		
	}
	public static Button GetButtons(AnchorPane calledPane, String buttonText, int xCoordinate, int yCoordinate, int width, int height, String colorName) {
		CreateButtons button = new CreateButtons(buttonText, xCoordinate, yCoordinate, width, height, colorName);
		calledPane.getChildren().add(button);
		return button;
	}
	
	private void SetTextFontOnButton(String colorName) {
		if(colorName.equals("red")) {
			setFont(Font.font("Georgia", FontWeight.BOLD, 22));
		}
		else {
			setFont(Font.font("Georgia",FontWeight.BOLD, 14));
		}
	}
	
	private void SetButtonClickedStyle(String colorName) {
		if(colorName.equals("red")) {
			setStyle(CLICKED_BUTTON);
		}
		else {
			setStyle(CLICKED_GREEN_BUTTON);
		}
		setPrefHeight(45);
		setLayoutY(getLayoutY() + 4);
	}
	
	private void SetButtonUnclickedStyle(String colorName) {
		if(colorName.equals("red")) {
			setStyle(UNCLICKED_BUTTON);
		}
		else {
			setStyle(UNCLICKED_GREEN_BUTTON);
		}
		setPrefHeight(49);
		setLayoutY(getLayoutY() - 4);
	}
	
	private void InitializeButtonListeners(String colorName) {
		
		setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.getButton().equals(MouseButton.PRIMARY))
					SetButtonClickedStyle(colorName);
			}		
		});
		
		setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.getButton().equals(MouseButton.PRIMARY))
					SetButtonUnclickedStyle(colorName);
			}		
		});
		
		setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				setEffect(new DropShadow());				
			}			
		});
		
		setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				setEffect(null);				
			}		
		});	
	}
}
