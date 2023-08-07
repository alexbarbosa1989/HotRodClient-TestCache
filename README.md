# HotRodClient-TestCache
Data Grid cache encoding tests:

- datagrid-jboss-marshalling: implements the generic GenericJBossMarshaller to store and retrieve data.
- datagrid-protostream: implements protostream to store and retrieve structured data. 

The current project requires a running RHDG instance (RHDG 8.3 or upper). It was developed and tested using Data Grid 8.4 and OpenJDK 11.

To clone the current project's branch:
~~~
git clone -b RHDG_8.4 https://github.com/alexbarbosa1989/HotRodClient-TestCache/
~~~


More info about cache encoding and marshalling can be found in the [Data Grid Documentation](https://access.redhat.com/documentation/en-us/red_hat_data_grid/8.4/html-single/cache_encoding_and_marshalling/index#cache-encoding)
