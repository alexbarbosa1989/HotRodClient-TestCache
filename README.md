# HotRodClient-TestCache
HotRodClient-TestCache (Current branch code is based on Data Grid Tutorial https://github.com/redhat-developer/redhat-datagrid-tutorials/tree/RHDG_8.3.1/infinispan-remote/query)


Need a running RHDG instance (RHDG 8.3 or upper)

Then execute:
~~~
mvn clean package exec:exec
~~~


The app automatically create a cache and execute the query. The connection, cache and query details can modified in RHDGTests.java
