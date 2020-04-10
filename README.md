# HotRodClient-TestCache
HotRodClient-TestCache (JDG 7.1 Upper)

Basic HotRod Client to tests distributed RHDG cache.

1- Donwload or clone the project

~~~
git clone https://github.com/alexbarbosa1989/HotRodClient-TestCache
~~~

2- Download RHDG 7.3.1 in Red Hat Customer Portal or Infinispan Server 9.4.15

3- (Optional - The code will add the cache if doesn't exists in the Data Grid cluster) Add a distributed cache named testCache in clustered.xml file into Infinispan Subsystem:

~~~
<distributed-cache name="testCache"/>
~~~

4- Start the RHDG instance:

~~~
${RHDG-HOME}/bin/standalone.sh -c clustered.xml
~~~

5- Into the project directory execute maven test command calling the JDGTests.java class:

~~~
mvn -Dtest=JDGTests test
~~~

6- Look into the test output if got the stored value "testValue" + new Date():

~~~
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running com.redhat.jdg.JDGTests
Mar 28, 2020 5:37:57 PM org.infinispan.client.hotrod.impl.protocol.Codec20 readNewTopologyAndHash
INFO: ISPN004006: Server sent new topology view (id=1, age=0) containing 1 addresses: [127.0.0.1:11222]
Mar 28, 2020 5:37:57 PM org.infinispan.client.hotrod.RemoteCacheManager actualStart
INFO: ISPN004021: Infinispan version: 9.4.15.Final-redhat-00001
Requesting cache {}testCache
Mar 28, 2020 5:37:57 PM org.infinispan.client.hotrod.impl.protocol.Codec20 readNewTopologyAndHash
INFO: ISPN004006: Server sent new topology view (id=1, age=0) containing 1 addresses: [127.0.0.1:11222]
Got {}org.infinispan.client.hotrod.impl.RemoteCacheImpl@3967e60c
Get the value: testValueSat Mar 28 17:37:56 COT 2020
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.538 sec
~~~


7- If want to change the RHDG/Infinispan version can edit the pom.xml in the  ****dependencyManagement**** section:

~~~
    <dependencyManagement>
        <dependencies>
            <!--JDG 7.3-->
            <dependency>
                <groupId>org.infinispan</groupId>
                <artifactId>infinispan-bom</artifactId>
                <version>9.4.15.Final-redhat-00001</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            
            <!--JDG 7.1-->
            <!--<dependency>
                <groupId>org.infinispan</groupId>
                <artifactId>infinispan-bom</artifactId>
                <version>8.4.0.Final-redhat-2</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>-->
        </dependencies>
    </dependencyManagement>
~~~
