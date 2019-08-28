package model;

import java.io.FileInputStream;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class CreateLabels extends Label{
	
	public CreateLabels(String labelText, int xCoordinate, int yCoordinate, int fontSize) {
		setText(labelText);
		setLayoutX(xCoordinate);
		setLayoutY(yCoordinate);
		setTextFill(Color.WHITE);
		setTextFontOnLabel(fontSize);
	}
	public static void GetLabel(AnchorPane calledPane, String labelText, int xCoordinate, int yCoordinate, int fontSize) {
		CreateLabels label = new CreateLabels(labelText, xCoordinate, yCoordinate,fontSize);
		calledPane.getChildren().add(label);
	}
	
	public void setTextFontOnLabel(int fontSize) {
		setFont(Font.font("Georgia", fontSize));
	}
}
