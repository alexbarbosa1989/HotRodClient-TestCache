# HotRodClient-TestCache
HotRodClient-TestCache (Current branch code is based on WFink Playground repository https://github.com/wfink/infinispan.playground.encoding/tree/main/JBossMarshalling)


Need a running RHDG instance (RHDG 8.3 or upper)

Then execute:
~~~
mvn clean package exec:exec
~~~

The app automatically create a cache and execute the query. The connection, cache and query details can modified in the RHDGTests.java class
