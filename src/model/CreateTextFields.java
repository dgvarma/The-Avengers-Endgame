package model;

import java.io.FileInputStream;

import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

public class CreateTextFields extends TextField {
	
	public CreateTextFields(String promptText, int xCoordinate, int yCoordinate, int width, int height) {
		setPromptText(promptText);
		setLayoutX(xCoordinate);
		setLayoutY(yCoordinate);
		setPrefWidth(width);
		setPrefHeight(height);
		SetTextFontOnLabel();
	}
	
	public void SetTextFontOnLabel() {
		setFont(Font.font("Georgia", 24));
	}
	
	public static TextField GetTextField(AnchorPane calledPane, String promptText, int xCoordinate, int yCoordinate, int width, int height) {
		CreateTextFields textField = new CreateTextFields(promptText, xCoordinate, yCoordinate, width, height);
		calledPane.getChildren().add(textField);	
		return textField;
	}
}
