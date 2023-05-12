# HotRodClient-TestCache
HotRodClient-TestCache (Current branch code is based Mr Wolf Playgorund https://github.com/wfink/infinispan.playground.encoding/tree/main/JBossMarshalling)


Need a running RHDG instance (RHDG 8.3 or upper)

Then execute:
~~~
mvn clean package exec:java
~~~

In this case, the `pom.xml` already contains the required JAVA_OPTIONS to run correctly with JDK 17:
~~~
--add-opens java.base/java.util=ALL-UNNAMED --add-opens java.base/java.util.concurrent=ALL-UNNAMED"
~~~

The app automatically create a cache and execute the query. The connection, cache and query details can modified in the RHDGTests.java class
