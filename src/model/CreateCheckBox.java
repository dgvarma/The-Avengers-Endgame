package model;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;

public class CreateCheckBox extends CheckBox{
	
	
	public CreateCheckBox(int xCoordinate, int yCoordinate) {
		setLayoutX(xCoordinate);
		setLayoutY(yCoordinate);
		setSelected(false);
	}
	
	public static CheckBox GetCheckBox(AnchorPane calledPane, int xCoordinate, int yCoordinate) {
		CreateCheckBox checkBox = new CreateCheckBox(xCoordinate, yCoordinate);
		calledPane.getChildren().add(checkBox);
		return checkBox;
	}

}
