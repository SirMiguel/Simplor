package simplor.model;

import java.io.File;

public class Document {

	private String name;
	private String textContent;
	private String fileLocation;
	private File documentFile;
	
	

	//FileChooser fileChooser = new FileChooser();
	public Document(String textContent, String fileLocation) {
		this.textContent = textContent;
		this.fileLocation = fileLocation;
	//	this.name = documentFile.getName();
		setUpFile(fileLocation);
		//this.documentFile = new File(fileLocation); 
		//new File(savingAsDocument.getFileLocation()
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
	
	public File getDocumentFile() {
		return documentFile;
	}

	public void setDocumentFile(File documentFile) {
		this.documentFile = documentFile;
	}
	
	
	public String getFileName() {
		return documentFile.getName();
	}
	
	public void setUpFile(String fileLocation) {
		try {
			this.documentFile = new File(fileLocation);
		} catch (NullPointerException e){
			System.out.println("Opening new file");
		}
	}
}

