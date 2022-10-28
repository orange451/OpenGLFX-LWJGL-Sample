package dev.anarchy.jotepad.data;

import java.io.File;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Document {
	private String filepath;
	
	private String content;
	
	private BooleanProperty modifiedProperty;
	
	public Document() {
		this.modifiedProperty = new SimpleBooleanProperty(false);
	}
	
	/**
	 * @return BooleanProperty indicating the modified state of the document.
	 */
	public BooleanProperty modifiedProperty() {
		return this.modifiedProperty;
	}
	
	/**
	 * @return the modified state of the document
	 */
	public boolean isModified() {
		return this.modifiedProperty().get();
	}
	
	/**
	 * Set the modified state of the document.
	 */
	public void setModified(boolean modified) {
		this.modifiedProperty().set(modified);
	}
	
	/**
	 * @return The filepath for this document.
	 */
	public String getFilePath() {
		return this.filepath;
	}
	
	/**
	 * Set the filepath for this document.
	 */
	public void setFilePath(String filepath) {
		this.filepath = filepath;
	}
	
	/**
	 * @return the content of this document.
	 */
	public String getContent() {
		return this.content;
	}
	
	/**
	 * Set the content of the document.
	 */
	public void setContent(String string) {
		this.content = string;
	}
	
	/**
	 * @return the file backed by this document.
	 */
	public File getFile() {
		if ( this.getFilePath() == null )
			return null;
		
		return new File(this.getFilePath());
	}
}
