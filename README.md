# HotRodClient-TestCache
Data Grid cache encoding tests:

- datagrid-jboss-marshalling=implements the generic GenericJBossMarshaller to store and retreive data.
- datagrid-protostream=implements protostream to store and retreive structured data. 

Requires a running RHDG instance (RHDG 8.3 or upper)
Current branch was developed and tested using Data Grid 8.4 and OpenJDK 11

More info about cache encoding and marshalling can be found in the [Data Grid Documentation](https://access.redhat.com/documentation/en-us/red_hat_data_grid/8.4/html-single/cache_encoding_and_marshalling/index#cache-encoding)