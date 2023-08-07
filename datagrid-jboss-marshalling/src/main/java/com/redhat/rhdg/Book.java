package com.redhat.rhdg;

import java.io.Serializable;

public class Book implements Serializable {
	
	String title;
	String description;
	int publicationYear;	
	
	Book(String title, String description, int publicationYear) {
		this.title = title;
		this.description = description;
		this.publicationYear = publicationYear;
	}
}