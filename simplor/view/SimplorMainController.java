package simplor.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import java.util.regex.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import simplor.MainApp;
import simplor.model.Document;


public class SimplorMainController {
	private MainApp mainApp;
	@FXML
	MenuBar menuBar = new MenuBar();
	@FXML
	Menu file = new Menu();
	@FXML
	MenuItem newFile = new MenuItem();
	@FXML
	MenuItem open = new MenuItem();
	@FXML 
	MenuItem save = new MenuItem();
	@FXML
	MenuItem saveAs = new MenuItem();
	@FXML 
	MenuItem close = new MenuItem();
	@FXML
	TextArea editorArea = new TextArea();
	@FXML
	Label filename = new Label();
	@FXML
	Label cNum = new Label();
	@FXML 
	Label wNum = new Label();

	
	Document workingDocument = new Document(null, null);

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		
	}
	
	//Updates the title of the scene to a new title (supposed to be filename of current file)
	public void updateSceneTitle(String newTitle) {
		filename.setText(newTitle);
	}
	
	
	 @FXML
	  private void handleKeyPressed(KeyEvent ke){
	    cNum.setText("" + editorArea.getLength());
	    wNum.setText(""+ calcNumWords(editorArea.getText()));
	  }
	 
	 
	 //TODO Make this work
	 public int calcNumWords(String textToSearch) {
		
		int numWords = 0;
		int nextWordLocation = 0;
		while (nextWordLocation > -1) {
				nextWordLocation = findNextWord(textToSearch);
				System.out.println(nextWordLocation);
				if (nextWordLocation != -1) {
					textToSearch = textToSearch.substring(nextWordLocation + 1);
				}
				numWords++;
		}
		
		return numWords;
	}
	
	  public int findNextWord(String textToSearch) {
		 
		  String startWords ="\\s+ ";
		  String endWords = " \\s+.";
		  String midWords = " \\s+ ";
		  
		  return textToSearch.indexOf(midWords);
		 /* if (textToSearch.indexOf(startWords) != -1 ) {
			  return textToSearch.indexOf(startWords);
		  } else if (textToSearch.indexOf(midWords) != -1 ) {
			  return textToSearch.indexOf(midWords);
		  } if (textToSearch.indexOf(endWords) != -1) {
			  return textToSearch.indexOf(endWords);
		  } else {
			  return -1;
		  }*/
	  }
	 
	@FXML
	public void handleClose() {
		close.setOnAction( new EventHandler <ActionEvent>() {
			public void handle (ActionEvent event) {
				System.exit(0);
			}
		});
	}
	
	@FXML
	public void handleOpen() {
		open.setOnAction( new EventHandler <ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Document openingDocument = openFile();
				workingDocument = openingDocument;
				editorArea.setText(openingDocument.getTextContent());
				updateSceneTitle(workingDocument.getFileName());
			}	
		});
	}
	
	
	
	
	
	public Document openFile() {
		FileChooser fChooser = new FileChooser();
		
		String fileLocation = fChooser.showOpenDialog(mainApp.getStage()) + "" ;
		String openingFileText = "";
		Document openingDocument = new Document(openingFileText, fileLocation);
		
		try {
			Scanner sc = new Scanner(openingDocument.getDocumentFile());
			while ( sc.hasNextLine() ) {
				openingFileText += sc.nextLine() + "\n";
			}	
			sc.close();
		}catch (IOException e) {
			System.out.println("ERROR");
		}
		openingDocument.setTextContent(openingFileText);
		cNum.setText("" + editorArea.getLength());
		
		return openingDocument;
	}
	
	public void setStageTitle(String title) {
		
	}
	
	@FXML 
	public void handleSaveAs() {
		saveAs.setOnAction( new EventHandler <ActionEvent>() {
			@Override
			public void handle( ActionEvent event ) {
				saveAs(workingDocument);
				updateSceneTitle(workingDocument.getFileName());
			}
		});
	}
	
	@FXML 
	public void saveActive() {
		if (editorArea.onKeyTypedProperty() != null) {
			save.setDisable(false);
		};
	}
	
	@FXML
	public void handleSave() {
		save.setOnAction( new EventHandler <ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (fileExists(workingDocument)) {
					save(workingDocument);
				} else {
					saveAs(workingDocument);
				}					
				updateSceneTitle(workingDocument.getFileName());
				System.out.println("Saved file");
			}
			
		});
	}
	
	public boolean isContentUpdated() {
		if (workingDocument.getTextContent().equals(editorArea.getText())) {
			return false;
		} else {
			return true;
		}
	}
	
	//Checks whether the current file being worked on has already exists on the file system
	public boolean fileExists(Document document){
		if (document.getFileLocation() == null  ) {
			return false;
		} else {
			return true;
		}
	}
	
	//Carries out the actual writing of the file to a new location.
	public void saveAs(Document savingAsDocument) {
		setDocument(savingAsDocument);
		try {
			PrintWriter pOut = new PrintWriter(savingAsDocument.getDocumentFile());
			pOut.println(savingAsDocument.getTextContent());
			pOut.close();
		} catch (IOException e) {
			//e.printStackTrace();
		}
		
	}
	
	//Sets the document up for saving (setting location etc.) and handles if the user cancels saving
	public void setDocument(Document updatedDocument) {
		FileChooser fChooser = new FileChooser();
		try {
			updatedDocument.setFileLocation(fChooser.showSaveDialog(mainApp.getStage()).toString());
			updatedDocument.setDocumentFile(new File(updatedDocument.getFileLocation()));
			updatedDocument.setTextContent(editorArea.getText());
		} catch (NullPointerException e){
			System.out.println("Cancelled save.");
			
		}
	}
	
	
	//Saves the contents of the text area to an already existing document;
	public void save(Document savingDocument) {
		try {
			PrintWriter pOut = new PrintWriter(savingDocument.getDocumentFile());
			savingDocument.setTextContent(editorArea.getText());
			pOut.println(savingDocument.getTextContent());
			pOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
}
