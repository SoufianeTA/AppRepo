
package ma.exampl.imagineapp.model;

import java.io.Serializable;

public class Library implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id ; 
	private String libraryName ;
	private String libraryAuthorFullName;
	private String dateOfCreation;
	private String language ;
	private String description ;
	private int direction ;
	
	public Library() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLibraryName() {
		return libraryName;
	}
	public void setLibraryName(String libraryName) {
		this.libraryName = libraryName;
	}
	public String getLibraryAuthorFullName() {
		return libraryAuthorFullName;
	}
	public void setLibraryAuthorFullName(String libraryAuthorFullName) {
		this.libraryAuthorFullName = libraryAuthorFullName;
	}
	public String getDateOfCreation() {
		return dateOfCreation;
	}
	public void setDateOfCreation(String dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	} 
	
	

}
