package com.redhat.rhdg;


import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ClientIntelligence;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.impl.ConfigurationProperties;
import org.infinispan.protostream.GeneratedSchema;

import java.util.ArrayList;
import java.util.List;

import static org.infinispan.query.remote.client.ProtobufMetadataManagerConstants.PROTOBUF_METADATA_CACHE_NAME;

public class RHDGTests {
    
   public static final String USER = "admin";
   public static final String PASSWORD = "admin";

   public static final String TUTORIAL_CACHE_NAME = "library";
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
    builder.addServer().host("127.0.0.1").port(ConfigurationProperties.DEFAULT_HOTROD_PORT)
          .security()
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
   RemoteCache<String, Library> twoListCache = client.getCache(TUTORIAL_CACHE_NAME);

   Book bin1 = new Book("Book1", "Description1", 2020);
   Book bin2 = new Book("Book2", "Description2", 2021);

   Book bin3 = new Book("Book3", "Description3", 2022);
   Book bin4 = new Book("Book4", "Description4", 2023);

   ArrayList<Book> listOne = new ArrayList<>();
   ArrayList<Book> listTwo = new ArrayList<>();
   listOne.add(bin1);
   listOne.add(bin2);
   listTwo.add(bin3);
   listTwo.add(bin4);

   Library lib1 = new Library(listOne);
   Library lib2 = new Library(listTwo);

   // Add first list of bins to map
   twoListCache.put("one", lib1);     
   // Access the first list in the cache and compare with the original list
   twoListCache.get("one");

   // Add first list of bins to map
   twoListCache.put("two", lib2);     
   // Access the first list in the cache and compare with the original list
   twoListCache.get("two");

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
