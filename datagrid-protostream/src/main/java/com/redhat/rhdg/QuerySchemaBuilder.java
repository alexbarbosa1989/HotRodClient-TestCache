package com.redhat.rhdg;

import org.infinispan.protostream.GeneratedSchema;
import org.infinispan.protostream.annotations.AutoProtoSchemaBuilder;

// book.proto file is generated in the compilation automatically
// book_sample must be defined as indexed-entity in the RHDG server cache "books"
@AutoProtoSchemaBuilder(
	      includeClasses = {
	            Book.class
	      },
	      schemaPackageName = "books")
public interface QuerySchemaBuilder extends GeneratedSchema {
}