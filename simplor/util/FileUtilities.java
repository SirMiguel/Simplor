package simplor.util;


import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FileUtilities {

	//private File documentFile;
	
	public static String findFileLocation() {
		Stage fileChooserStage = new Stage();
		FileChooser fileChooser = new FileChooser();
		return "" + fileChooser.showOpenDialog(fileChooserStage);
	}
	
	public static String createNewFile() {
		Stage fileChooserStage = new Stage();
		FileChooser fileChooser = new FileChooser();
		return "" + fileChooser.showSaveDialog(fileChooserStage);
	}
	
	
}
