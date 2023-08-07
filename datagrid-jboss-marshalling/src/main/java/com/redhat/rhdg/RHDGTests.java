package com.redhat.rhdg;


import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ClientIntelligence;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.impl.ConfigurationProperties;
import org.infinispan.jboss.marshalling.commons.GenericJBossMarshaller;

import java.util.ArrayList;
import java.util.List;

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
    builder.addServer().host("127.0.0.1").port(ConfigurationProperties.DEFAULT_HOTROD_PORT)
          .security()
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
   RemoteCache<String, List<Book>> twoListCache = client.getCache(TUTORIAL_CACHE_NAME);

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
   // Add first list of bins to map
   twoListCache.put("one", listOne);     
   // Access the first list in the cache and compare with the original list
   twoListCache.get("one");

   // Add first list of bins to map
   twoListCache.put("two", listTwo);     
   // Access the first list in the cache and compare with the original list
   twoListCache.get("two");

   // Stop the client and release all resources
   client.stop();
}

}
