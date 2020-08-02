package com.redhat.jdg;

import java.util.Date;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JDGTests {

    private static final String TEST_CACHE_NAME = "testCache";
    private static final String TEST_KEY = "testKey1";
    private static final String TEST_VALUE = "testValue" + new Date();
    
    private static final String HOST1 = "127.0.0.1";
    private static final int PORT1 = 11222; 
//    private static final String HOST2 = "127.0.0.1";
//    private static final int PORT2 = 11322; 
    
    RemoteCacheManager manager;

    @Before
    public void connect() {
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.connectionPool().maxActive(5).minIdle(3).maxWait(60000)
        .addServer().host(HOST1).port(PORT1)
//        .addServer().host(HOST2).port(PORT2)
        .validate()
        ;
        
        manager = new RemoteCacheManager(builder.build(), true);
    }

    @After
    public void shutdown() {
        manager.stop();
    }

    @Test
    public void testPut() {
        RemoteCache<String, String> cache = getCache(TEST_CACHE_NAME);
        cache.put(TEST_KEY, TEST_VALUE);
        
        final String value = cache.get(TEST_KEY);
        System.out.println("Get the value: "+ value);
        
    }

   /**
     * TESTED CACHE OPTIONS        
     * org.infinispan.LOCAL
     * org.infinispan.DIST_SYNC           
     */
    private RemoteCache<String, String> getCache(String cacheName) {
    	System.out.println("Requesting cache: "+ cacheName);
        RemoteCache<Object, Object> cache = manager.getCache(cacheName);
        System.out.println("Got: "+ cache);
        
        if(cache==null) {
            System.out.println("Creating cache: " + cacheName);
            String template = "org.infinispan.DIST_SYNC";
            manager.administration().createCache(cacheName, template);
            System.out.println("cache "+cacheName+" created");
        }

        return manager.getCache(cacheName);
    }
}
