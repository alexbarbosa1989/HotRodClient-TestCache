package com.redhat.rhdg;


import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ClientIntelligence;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.impl.ConfigurationProperties;
import org.infinispan.jboss.marshalling.commons.GenericJBossMarshaller;

import java.util.HashMap;
import java.util.Map;

public class RHDGTests {
    
   public static final String USER = "admin";
   public static final String PASSWORD = "admin";

   public static final String TUTORIAL_CACHE_NAME = "books";
   public static final String HOST = "127.0.0.1";

   public static final String TUTORIAL_CACHE_CONFIG =
         "<distributed-cache name=\"CACHE_NAME\">\n"
         + "    <encoding media-type=\"application/x-jboss-marshalling\"/>\n"
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
    builder.marshaller(new GenericJBossMarshaller());

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

   // Connect to the server
   RemoteCacheManager client = new RemoteCacheManager(builder.build());

   // Get the books cache, create it if needed with the default configuration
   RemoteCache<String, Book> bookCache = client.getCache("books");

   // Create the books dataset to be stored in the cache
   Map<String, Book> book = new HashMap<>();
   book.put("Key1", new Book("Book1", "Description1", 2022));
   book.put("Key2", new Book("Book2", "Description2", 2023));

   // Put all the values in the cache
   bookCache.putAll(book);

   Book searchBook = bookCache.get("Key1");
   // Print the results
   System.out.println("My Boook: "+searchBook.title);

   // Stop the client and release all resources
   client.stop();
}

}
