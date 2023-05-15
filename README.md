# HotRodClient-TestCache
HotRodClient-TestCache (Current branch code is based on WFink Playgorund repository https://github.com/wfink/infinispan.playground.encoding/tree/main/JBossMarshalling)


Need a running RHDG instance (RHDG 8.3 or upper)

Then execute:
~~~
mvn clean package exec:java
~~~

In this case, the `pom.xml` already contains the required JAVA_OPTIONS to run correctly with JDK 17 (Current repo was tested with both JDK 11 and 17):
~~~
--add-opens java.base/java.util=ALL-UNNAMED --add-opens java.base/java.util.concurrent=ALL-UNNAMED"
~~~

Alternativey, can be used the **MAVEN_OPTS** environment variable to set the `--add-opens` options:
~~~
export MAVEN_OPTS="--add-opens java.base/java.util=ALL-UNNAMED --add-opens java.base/java.util.concurrent=ALL-UNNAMED"

mvn clean package exec:java
~~~

The app automatically create a cache and execute the query. The connection, cache and query details can modified in the RHDGTests.java class
