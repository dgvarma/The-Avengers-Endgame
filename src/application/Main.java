package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import view.GameEntryViewManager;

public class Main extends Application {
	@Override
	public void start(Stage entryStage) {
		try {
			GameEntryViewManager gameEntryView = new GameEntryViewManager();
			entryStage = gameEntryView.GetStage();
			entryStage.show();
		} 
		catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
