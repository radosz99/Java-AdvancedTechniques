[![Codacy Badge](https://app.codacy.com/project/badge/Grade/4915729429104b9b8ae5f4267049206b)](https://www.codacy.com/manual/radosz99/Programming-in-Java-Advanced-Techniques?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=radosz99/Programming-in-Java-Advanced-Techniques&amp;utm_campaign=Badge_Grade)

**<p align="center"> Programming in Java - advanced techniques </p>**
_________________________________
**<p align="center"> Wrocław University of Science and Technology </p>**
**<p align="center"> Computer Science, Faculty of Electronics, 6 semester </p>**
<p align="center"> Radosław Lis, 241385 </p>

# Table of Contents
- [General info](#desc)
- [Prerequisites](#pre)
- [Applications info](#inf)
  *  [cw1](#cw1)
  *  [cw2](#cw2)
  *  [cw3](#cw3)
  *  [cw4](#cw4)
  *  [cw5](#cw5)
  *  [cw6](#cw6)
  *  [cw7](#cw7)
  *  [cw8](#cw8)
  *  [cw9](#cw9)
  *  [cw10](#cw10)
  *  [cw11](#cw11)
- [Eclipse configurations](#conf)
  *  [JavaFX](#jav)
  *  [e(fx)clipse](#fxc)
  *  [Scene Builder](#scene)
  *  [UTF-8 Encoding](#utf)
  *  [Common errors](#err)

<a name="desc"></a>
# General info
Applications made for university course `Programming in Java - advanced techniques`.
______________________________
[cw1](#cw1) - Sorting algorithms  
[cw2](#cw2) - Desktop sorting application using sorting methods from [cw1](#cw1)  
[cw3](#cw3) - Console application for JVM research (different heap size, reference types) using ReflectionAPI  
[cw4](#cw4) - Custom JavaBean component with all property types  
[cw5](#cw5) - RMI application - clients who want to sort their data by available sorting servers  
[cw6](#cw6) - Desktop application for managing bike trips. Using JDBC, MySQL and JAXB  
[cw7](#cw7) - Simulation of ring network by using SOAP technology and TCP/IP Sockets  
[cw8](#cw8) - Java Security app - policytool, Cipher, digital signature etc.  
[cw9](#cw9) - Desktop application deployed by using JavaWS, JNLP and Apache Tomcat for playing in 5,5,4-game  
[cw10](#cw10) - Minimax algorithm with alpha-beta prunings in 5,5,4-game using Javascript (Nashorn) and C++ (JNI)  
[cw11](#cw11) - Little transformation of `cw3` with Spring Beans and JMX  

<a name="pre"></a>
# Prerequisites
- IDE for Java (e.g. [Eclipse](https://www.eclipse.org/downloads/), [Intellij IDEA](https://www.jetbrains.com/idea/download/#section=windows))
 
 <a name="inf"></a>
# Applications info

 <a name="cw1"></a>
## cw1

### Technologies 
- Java

### Description
Application provides an interface for sort list of *IElement* objects (*FloatElement* or *IntElement*) consisting of key and value.
Currently available algorithms:
- **Quick Sort**,
- **Counting Sort**,
- **Pigeonhole Sort**,
- **Insert Sort**.

You can simple add some algorithm by using the current convention (inheritance from *AbstractSorter* abstract class).

### Running
Create a list in *Test.java* and run by choose one of algorithm to sort it.

 <a name="cw2"></a>
## cw2

### Technologies 
- [JavaFX (SDK 11.0.2)](https://gluonhq.com/products/javafx/)

### Description
Desktop, internationalized JavaFX application using algorithms from [cw1](#cw1) for sorting lists. You can load data from a file (rules in *Menu* bar), add manually or generate using random methods. Depending on the region there are showed other decimal separators (e.g. `','` in Poland or `'.'` in USA), other format of current dates and obviously other strings by using `Resource Bundles`. You can choose:
- **Poland** (by default),
- **USA**,
- **England**,
- **France**,
- **Germany**,
- **Japan**.

Application shows current number of elements and by using Choice Format class supports the right declension of words in all language versions. There is also a great feature for saving sorted lists to `.txt` files in a format readen by the app.

### Running
1. Use cw2. jar file and in command line (**xyz** is your path to javafx-sdk):
```
java -jar --module-path xyz\javafx-sdk-11.0.2\lib --add-modules=javafx.controls,javafx.fxml cw2.jar
```
2. Import all files into the project and [configure build path](#jav)

### Screenshot
<p align="center">
<img src="https://github.com/radosz99/java-advanced-techniques/blob/master/cw2/screen.png" width=80%/>
</p>

 <a name="cw3"></a>
## cw3

### Technologies 
- [Maven](https://maven.apache.org/download.cgi)

### Running
```
$ git clone https://github.com/radosz99/Programming-in-Java-Advanced-Techniques.git
$ cd Programming-in-Java-Advanced-Techniques
$ cd cw3
$ mvn install
$ cd target
$ java *A* *B* -Djava.awt.headless=true -jar cw3-1.0-SNAPSHOT-jar-with-dependencies.jar *C* *D* *E* *F* *H*
```
VM options (not required)
- A = minimum heap size for JVM in megabytes, e.g. `-Xms128m`,
- B = maximum heap size for JVM in megabytes, e.g. `-Xmx256m`,

Arguments (required):
- C = number of seeds (lists to sort), e.g. `500`,
- D = number of elements in seed, e.g. `8000`,
- E = number of sorting threads, e.g. `16`,
- F = type of reference to store a key (seed) in the map - cache - `SOFT`, `HARD` or `WEAK`,
- H = type of reference to store a value (dataset) in the map - cache - `SOFT`, `HARD` or `WEAK`.

### Description
Console, multi-threaded application for testing JVM (*Java Virtual Machine*) capabilities. Synchronized threads try to sort (with algorithms from [cw1](#cw1)) selected (random generated) datasets identified by a *seed*. Appropriate classes with sorting methods are dynamically loaded during the program execution from `cw1.jar` by using *ClassLoader* and *reflection mechanism*. Sorted datasets are putting in *cache* ([ReferenceMap](http://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/map/ReferenceMap.html)) by using chosen references - `WEAK`, `SOFT` or `HARD`, depending on which *Garbage Collector* may delete dataset from memory. By setting another parameters (mainly *B* - maximum heap size for JVM) many behaviours can be observed - such as segmentation faults (`OutOfMemoryError`) if references are `HARD` or nearly 100% misses if the maximum size is low and references are `WEAK`, and finally, smooth, continuous running by using `SOFT` references. Application generates every 3 seconds raport which gives information about cache misses and overall sorted datasets compared to stored datasets in the cache at this moment.

By the following call you set maximum JVM memory to 256 megabytes and run 16 threads that are trying to sort 500 seeds with 8000 elements each and put it to the cache. Seed reference is `WEAK` and dataset references are `SOFT`. Below - in the console output - it can be observed that declared references provides constant number of datasets stored in the cache, despite the continuous sorts performed by the threads:
```
$ java -Xms128m -Xmx256m -Djava.awt.headless=true -jar cw3-1.0-SNAPSHOT-jar-with-dependencies.jar 500 8000 16 WEAK HARD
```
<p align="center">
<img src="https://github.com/radosz99/java-advanced-techniques/blob/master/cw3/screen.png" width=80% />
</p>

 <a name="cw4"></a>
## cw4

### Technologies 
- [JavaBeans](https://pl.wikipedia.org/wiki/JavaBeans)
- [WindowBuilder](https://www.eclipse.org/windowbuilder/)
- [JFontChooser](https://osdn.net/projects/jfontchooser/)

### Description

JavaBean with three types of properties:
- Simple Property - BeanString
- Bound Property - BeanValue
- Constrained Property - BeanFont

### Running
Add `MyBean.jar` from `cw4/Bean` to `cw4/BeanUsageExample` build path and have fun with such a great technology as Java Beans are!  

You can change bean properties by modifying `Bean`, `BeanInfo` and `Controller` in `cw4/bean/...` and then:
```
$ javac Bean.java BeanInfo.java Controller.java
$ jar cf MyBean.jar Bean.class BeanInfo.class Controller.class
```
Originally made in Eclipse IDE. Each update of the bean properties implies the need to refresh the project.  


### Screenshot
Exemplary screenshot with stack trace of an error caused of an attempt to change *beanFont* property to a blacklisted font (fell on poor Cambria). That property is constrained and when it is about to change, the listeners are consulted about the change and they say that Cambria is not suitable:
<p align="center">
<img src="https://github.com/radosz99/java-advanced-techniques/blob/master/cw4/screen.png" width=80% />
</p>

 <a name="cw5"></a>
## cw5

### Technologies 
- [Maven](https://maven.apache.org/download.cgi)
- [JavaFX (SDK 11.0.2)](https://gluonhq.com/products/javafx/)
- [RMI](https://docs.oracle.com/javase/7/docs/technotes/guides/rmi/hello/hello-world.html)

### Description
Application simulates process of running distributed applications by using RMI.  

Client applications (`ClientApplication.java`) allow you to generate random data and sort them using a server (`ServerApplication.java`) selected from the server list obtained thanks to the Central (`CentralApplication.java`).

### Running
To test distributed applications:
1. Configure VM arguments as it is described [here](#jav) in 3rd point
2. Run exactly once `cw5/src/rmi/CentralApplication.java` - it is the Central (list of servers)
3. Run `cw5/src/rmi/ClientApplication.java` as many as you want - it is the Client
4. Choose algorithm and run `cw5/src/rmi/ServerApplication.java` as many as you want - it is the Server 
5. Generate data, sort it by using one of registered servers, whatever you want to do

The order in which clients and servers are started doesn't matter. If you want to run more than one instance of Client or Server you must choose `Allow parallel run` in `Edit Configurations` in IntelliJ.

### Screenshot

<p align="center">
<img src="https://github.com/radosz99/java-advanced-techniques/blob/master/cw5/screen.png" />
</p>

<a name="cw6"></a>
## cw6

### Technologies 
- [JavaFX (SDK 11.0.2)](https://gluonhq.com/products/javafx/)
- [MySQL](https://dev.mysql.com/downloads/)
- [JDBC](https://docs.oracle.com/javase/tutorial/jdbc/basics/index.html)
- [JAXB](https://en.wikipedia.org/wiki/Java_Architecture_for_XML_Binding)

### Description
Basic JDBC desktop application in JavaFX for managing bike trips. By using JAXB it allows serializing and unserializing object to write it in XML files.

### Running
Create MySQL database and run script from `cw6/skrypt.txt` for some data to test the application.

### Screenshot

<p align="center">
<img src="https://github.com/radosz99/java-advanced-techniques/blob/master/cw6/screen.png" width=70% />
</p>

<a name="cw7"></a>
## cw7

### Technologies 
- [JavaFX (SDK 11.0.2)](https://gluonhq.com/products/javafx/)
- [SOAP](https://www.w3schools.com/xml/xml_soap.asp)
- [Sockets](https://docs.oracle.com/javase/tutorial/networking/sockets/index.html)

### Description
Application simulates working of ring network topology. There is a finite number of working applications that can send and receive messages from others running aplications.  
Ring topology works at follows:
<p align="center">
A -> B -> C -> D -> A
</p>
Messages are sending by SOAP and can be send as Unicast or Broadcast. 

### Running
Choose `Allow parallel run` in `Edit Configurations` in IntelliJ and run as many apps as you want. To close the ring you must click `Zamknij pierścień` button in ultimately last application.
### Screenshot

<p align="center">
<img src="https://github.com/radosz99/java-advanced-techniques/blob/master/cw7/screen.png" width=100% />
</p>

<a name="cw8"></a>
## cw8

### Technologies 
- [Maven](https://maven.apache.org/download.cgi)

### Description
Fun with Java Security.

### Generating keys for encryption
```
$ openssl genrsa -out keypair.pem 2048
$ openssl rsa -in keypair.pem -outform DER -pubout -out public.der
$ openssl pkcs8 -topk8 -nocrypt -in keypair.pem -outform DER -out private.der
```
### Jar signing
```
$ keytool -genkey -alias signJar -keystore my-store
$ jarsigner -keystore my-store -signedjar cw1signed.jar cw1.jar signJar 
```
### Creating policy
By using `policytool` add grants and save it in `mypolicy` file.
```
grant {
  permission java.lang.RuntimePermission "createClassLoader";
};

grant {
  permission java.io.FilePermission "<<ALL FILES>>", "write";
};

grant {
  permission java.io.FilePermission "<<ALL FILES>>", "read";
};
```

### Other stuff
- Exporting public key - `keytool -export -keystore my-store -alias signJar -file PublicKey.cer`
- Importing public key - `keytool -import -alias foreign -file PublicKey.cer -keystore receiver-store`
- Displaying  certificate - `keytool -printcert -file PublicKey.cer`
- Displaying key from store - `keytool -list -keystore my-store -alias signJar`
- Verifying jar - `jarsigner -verify cw1signed.jar`

### Running
In project main folder (create keys in TARGET folder) before running it):
```
$ mvn install
$ cd target
$ java -Djava.security.manager -Djava.security.policy=mypolicy -jar cw8-1.0-SNAPSHOT.jar
```

### Screenshot
<p align="center">
<img src="https://github.com/radosz99/java-advanced-techniques/blob/master/cw8/screen.png" width=100% />
</p>


<a name="cw9"></a>
## cw9

### Technologies 
- [Maven](https://maven.apache.org/download.cgi)

### Description
*Application as a foundation for cw10*.    
Currently in `cw9` folder we have `cw10`. To see `cw9` you must go back to commit `b186556`.

[5,5,4-game](https://en.wikipedia.org/wiki/M,n,k-game) in JavaFX. You can play with computer (random algorithm) or with other person on the same app (left and right clicks).

### Running
In project main folder:
```
$ mvn install 
$ cd target
$ keytool -genkey -alias signJar -keystore my-store (once)
$ jarsigner -keystore my-store -signedjar game.jar game-1.0-SNAPSHOT.jar signJar
```
Then move `game.jar` and `Game.jnlp` to `C:\Program Files\Apache Software Foundation\Tomcat 9.0\webapps\ROOT` and:
```
$ cd C:\Program Files\Apache Software Foundation\Tomcat 9.0\bin
$ startup
$ javaws http://localhost:8080/Game.jnlp
```
### Screenshot
<p align="center">
<img src="https://github.com/radosz99/java-advanced-techniques/blob/master/cw9/screens/screen_cw9.png" width=100% />
</p>

<a name="cw10"></a>
## cw10

### Technologies 
- [Maven](https://maven.apache.org/download.cgi)

### Description
**Currently all files are in cw9/ folder.** 

[5,5,4-game](https://en.wikipedia.org/wiki/M,n,k-game) in JavaFX using Javascript Script Engine and JNI. You (X) play with AI (O).

### Running
Build JNI library in `resources` folder by typing:
```
$ ./build_library.sh
```
Then open project in IntelliJ, import JavaFX libs and in `Edit configurations` in `VM options` type (`xyz` are appriopriate system paths):
```
$ -Djava.library.path=...xyz/resources/ --module-path "...xyz/javafx-sdk-11.0.2/lib" --add-modules=javafx.controls,javafx.fxml
```
Then simply run `Main.java`.

### Screenshot
<p align="center">
<img src="https://github.com/radosz99/java-advanced-techniques/blob/master/cw9/screens/screen_cw10.png" width=70% />
</p>

<a name="cw11"></a>
## cw11

### Technologies 
- [Maven](https://maven.apache.org/download.cgi)
- [JMX](http://actimem.com/java/jmx-spring)
- [Spring](https://spring.io/)
- [MBean](https://docs.oracle.com/javase/tutorial/jmx/mbeans/index.html)

### Description
Idea of the application is the same as in [cw3](#cw3), algorithms are loaded using Reflection API, but the app is configured as MBean by using Spring annotations and attributes of this bean (cache size and number of sorting threads) can be dynamically changed during the runtime in e.g. `jconsole` tool.

### Running
Simply open project in IDE (e.g. IntelliJ) and run main function in `main/java/pl/advanced/Main.java`. Then open terminal, type `jconsole` and connect with the running process:

<p align="center">
<img src="https://github.com/radosz99/java-advanced-techniques/blob/master/cw11/screens/1.png" width=40% />
</p>

Then go to the `MBeans` section, expand `cw11` and test Bean methods by setting size (`1=<`) of cache (amount of stored keys with datasets) and number (`0=<`) of sorting threads.
<p align="center">
<img src="https://github.com/radosz99/java-advanced-techniques/blob/master/cw11/screens/2.png" width=75% />
</p>

You can observe flow of the application in the console logs:
```
Thread no 0, seed no 167, QuickSort algorithm, execution time is 1 ms
Thread no 4, seed no 311, QuickSort algorithm, execution time is 2 ms
Thread no 3, seed no 364, InsertSort algorithm, execution time is 139 ms
Thread no 5, seed no 110, CountingSort algorithm, execution time is 77 ms
Thread no 9, seed no 238, QuickSort algorithm, execution time is 1 ms
Thread no 8, seed no 487, CountingSort algorithm, execution time is 69 ms
Thread no 6, seed no 93, CountingSort algorithm, execution time is 71 ms
Thread no 3, seed no 102, PigeonHoleSort algorithm, execution time is 379 us
```

<a name="conf"></a>
# Eclipse configurations

<a name="jav"></a>
## JavaFX
1. Go **Window** -> **Preferences** -> **Java** -> **Build Path** -> **User Libraries** -> **New** and write JAVAFX\_11. Then left click on JAVAFX_11, click Add External JARs... 
and add all JARs from `...\javafx-sdk-11.0.2\lib`.
2. Right click on the project and go **Build Path** -> **Configure Build Path** -> **Add Library** -> **User Library** -> **Next** and choose JAVAFX_11.
3. Go **Run** -> **Run Configurations** -> **Arguments** and in VM arguments field write:
```
--module-path "xyz\javafx-sdk-11.0.2\lib" --add-modules=javafx.controls,javafx.fxml
```
(**xyz** to your path to javafx-sdk). Then **Apply** -> **Close**.

___________________________________
<a name="fxc"></a>
## e(fx)clipse
1. Go **Help** -> **Install New Software**, click Add and in `Location` field paste 
```
https://download.eclipse.org/efxclipse/runtime-nightly/site/
```
2. Go **Help** -> **Install New Software**, click Add and in `Location` field paste 
```
https://download.eclipse.org/efxclipse/updates-nightly/site/
```
3. Restart IDE
___________________________________
<a name="scene"></a>
## Scene Builder
1. Go to [**Gluon** site](https://gluonhq.com/products/scene-builder/#download) and in section **Download Scene Builder for Java 11** download version for your OS.
2. Go **Window** -> **Preferences** -> **JavaFX** and in `SceneBuilder executable` field click browse and find your .exe file (`...\SceneBuilder\SceneBuilder.exe`) and in *`JavaFX 11+ SDK` field click browse and find your sdk folder (`...\javafx-sdk-11.0.2`)
___________________________________
<a name="utf"></a>
## UTF-8 Encoding
1. Go **Window** -> **Preferences** -> **General** -> **Workspace** -> and in `Text file encoding` field choose **Other** -> **UTF-8**.

___________________________________
<a name="err"></a>
## Common errors
If any of this:
```
Exception in thread "WindowsNativeRunloopThread" java.lang.NoSuchMethodError: 
Exception in thread "JavaFX Application Thread" java.lang.NullPointerException
```

In the enviroment variable JAVA_HOME set the folder on Java 11 JDK (`C:\Program Files\Java\jdk-11.0.6`).  

If any of this:
```
java.lang.IllegalAccessError: class com.sun.javafx.fxml.FXMLLoaderHelper (in unnamed module @0x552f46e4) cannot access class com.sun.javafx.util.Utils (in module javafx.graphics) because module javafx.graphics does not export com.sun.javafx.util to unnamed module
```
```
Error: JavaFX runtime components are missing, and are required to run this application
```
Configure VM argument as it is described [here](#jav) in 3rd point.
