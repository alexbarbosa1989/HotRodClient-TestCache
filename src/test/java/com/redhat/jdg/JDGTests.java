package com.redhat.jdg;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.configuration.NearCacheMode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class JDGTests {

    private static final String TEST_CACHE_KEY = "testCache";
    private static final String TEST_KEY = "testKey1";
    private static final String TEST_VALUE = "testValue" + new Date();

    RemoteCacheManager manager;

    @Before
    public void connect() {
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.connectionPool().maxActive(5).minIdle(3).maxWait(60000)
        .addServers("127.0.0.1:11222");
        //.addServer().host("127.0.0.1").port(11222);

        manager = new RemoteCacheManager(builder.build(), true);
    }

    @After
    public void shutdown() {
        manager.stop();
    }

    @Test
    public void testPut() {
        RemoteCache<Object, Object> cache = getCache(TEST_KEY);
        cache.put(TEST_KEY, TEST_VALUE);
        
        final Object value = cache.get(TEST_KEY);
        System.out.println("Get the value: "+ value);
        
    }

    private RemoteCache<Object, Object> getCache(String testKey) {
    	System.out.println("Requesting cache {}"+ TEST_CACHE_KEY);
        RemoteCache<Object, Object> cache = manager.getCache(TEST_CACHE_KEY);
        System.out.println("Got {}"+ cache);
        
        if(cache==null) {
            String template = null;
            System.out.println("Creating cache {}" + TEST_CACHE_KEY);
            manager.administration().createCache(TEST_CACHE_KEY, template);
            System.out.println();
        }

        return manager.getCache(TEST_CACHE_KEY);
    }
}
