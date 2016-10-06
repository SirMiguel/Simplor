package simplor;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import simplor.view.SimplorMainController;

public class MainApp extends Application{
	
	private Stage primaryStage;
	private VBox root; 
	//private Document document;
	
	public static void main(String[] args) {
		launch();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Simplor");
		initSimplorMainController();
	}
	
	public void initSimplorMainController(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/SimplorMain.fxml"));
			
			root = (VBox) loader.load();
			
			Scene scene = new Scene( root );
			primaryStage.setScene(scene);
			
			SimplorMainController controller = (SimplorMainController) loader.getController();
			controller.setMainApp(this);
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Stage getStage() {
		return primaryStage;
	}
	
	public void setStageTitle(String stageTitle) {
		primaryStage.setTitle(stageTitle);
	}
	
}
