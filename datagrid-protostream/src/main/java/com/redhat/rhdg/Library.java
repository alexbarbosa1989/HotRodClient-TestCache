package com.redhat.rhdg;

import org.infinispan.protostream.annotations.ProtoFactory;
import org.infinispan.protostream.annotations.ProtoField;

import java.util.ArrayList;
import java.util.List;

public class Library{
	
	@ProtoField(number = 1, collectionImplementation = ArrayList.class)
	final List<Book> books;	
	
	@ProtoFactory
	Library(List<Book> books) {
		this.books = books;
	}
}