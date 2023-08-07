package com.redhat.rhdg;


import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ClientIntelligence;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.impl.ConfigurationProperties;
import org.infinispan.protostream.GeneratedSchema;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;
import org.infinispan.client.hotrod.Search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.infinispan.query.remote.client.ProtobufMetadataManagerConstants.PROTOBUF_METADATA_CACHE_NAME;

public class RHDGTests {
    
   public static final String USER = "admin";
   public static final String PASSWORD = "admin";

   public static final String TUTORIAL_CACHE_NAME = "books";
   public static final String HOST = "127.0.0.1";

   public static final String TUTORIAL_CACHE_CONFIG =
         "<distributed-cache name=\"CACHE_NAME\">\n"
         + "    <encoding media-type=\"application/x-protostream\"/>\n"
         + "</distributed-cache>";
    
     /**
    * Returns the configuration builder with the connection information
    *
    * @return a Configuration Builder with the connection config
    */
   public static final ConfigurationBuilder connectionConfig() {
    ConfigurationBuilder builder = new ConfigurationBuilder();
    builder.addServer().host("127.0.0.1").port(ConfigurationProperties.DEFAULT_HOTROD_PORT).security()
          .authentication()
          //Add user credentials.
          .username(USER)
          .password(PASSWORD);

    // Docker 4 Mac Workaround. Don't use BASIC intelligence in production
    builder.clientIntelligence(ClientIntelligence.BASIC);

    // Make sure the remote cache is available.
    // If the cache does not exist, the cache will be created
    builder.remoteCache(TUTORIAL_CACHE_NAME)
          .configuration(TUTORIAL_CACHE_CONFIG.replace("CACHE_NAME", TUTORIAL_CACHE_NAME));
    return builder;
 }

 public static void main(String[] args) throws Exception {

   ConfigurationBuilder builder = connectionConfig();

   // Add the Protobuf serialization context in the client
   builder.addContextInitializer(new QuerySchemaBuilderImpl());

   // Connect to the server
   RemoteCacheManager client = new RemoteCacheManager(builder.build());

   // Create and add the Protobuf schema in the server
   addPersonSchema(client);

   // Get the books cache, create it if needed with the default configuration
   RemoteCache<String, Book> bookCache = client.getCache(TUTORIAL_CACHE_NAME);

   // Create the persons dataset to be stored in the cache
   Map<String, Book> book = new HashMap<>();
   book.put("Key1", new Book("Book1", "Description1", 2022));
   book.put("Key2", new Book("Book2", "Description2", 2023));

   // Put all the values in the cache
   bookCache.putAll(book);

   // Get a query factory from the cache
   QueryFactory queryFactory = Search.getQueryFactory(bookCache);

   // Create a query with lastName parameter
   Query query = queryFactory.create("FROM books.Book WHERE publicationYear = :year");

   // Set the parameter value
   query.setParameter("year",2022);

   // Execute the query
   List<Book> bookList = query.execute().list();

   // iterate query result list for verifying
	for (Book itbook : bookList) {
			System.out.println(itbook.title);
			System.out.println(itbook.publicationYear);
	}

   // Print the results
   System.out.println(bookList);

   // Stop the client and release all resources
   client.stop();
}

private static void addPersonSchema(RemoteCacheManager cacheManager) {
   // Retrieve metadata cache
   RemoteCache<String, String> metadataCache =
         cacheManager.getCache(PROTOBUF_METADATA_CACHE_NAME);

   // Define the new schema on the server too
   GeneratedSchema schema = new QuerySchemaBuilderImpl();
   metadataCache.put(schema.getProtoFileName(), schema.getProtoFile());
}

}
