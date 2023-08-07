package com.redhat.rhdg;

import org.infinispan.protostream.annotations.ProtoFactory;
import org.infinispan.protostream.annotations.ProtoField;

public class Book{
	
	@ProtoField(number = 1)
	String title;

	@ProtoField(number = 2)
	String description;

	@ProtoField(number = 3, defaultValue= "2023")
	int publicationYear;	
	
	@ProtoFactory
	Book(String title, String description, int publicationYear) {
		this.title = title;
		this.description = description;
		this.publicationYear = publicationYear;
	}
}