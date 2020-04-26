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
- [Eclipse configurations](#conf)
  *  [JavaFX](#jav)
  *  [e(fx)clipse](#fxc)
  *  [Scene Builder](#scene)
  *  [UTF-8 Encoding](#utf)
  *  [Common errors](#err)

<a name="desc"></a>
# General info
Applications made for university course *Programming in Java - advanced techniques*.
______________________________
[cw1](#cw1) - Sorting algorithms  
[cw2](#cw2) - Desktop sorting application using sorting methods from [cw1](#cw1)  
[cw3](#cw3) - Console application for JVM research (different heap size, reference types) using ReflectionAPI  
[cw4](#cw4) - Custom JavaBean component with all property types  
[cw5](#cw5) - RMI application - clients who want to sort their data by available sorting servers  

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
Desktop, internationalized JavaFX application using algorithms from [cw1](#cw1) for sorting lists. You can load data from a file (rules in *Menu* bar), add manually or generate using random methods. Depending on the region there are showed other decimal separators (e.g. '*,*' in Poland or '*.*' in USA), other format of current dates and obviously other strings by using *Resource Bundles*. You can choose:
- **Poland** (by default),
- **USA**,
- **England**,
- **France**,
- **Germany**,
- **Japan**.

Application shows current number of elements and by using Choice Format class supports the right declension of words in all language versions. There is also a great feature for saving sorted lists to *.txt* files in a format readen by the app.

### Running
1. Use cw2. jar file and in command line (**xyz** is your path to javafx-sdk):
```
java -jar --module-path xyz\javafx-sdk-11.0.2\lib --add-modules=javafx.controls,javafx.fxml cw2.jar
```
2. Import all files into the project and [configure build path](#jav)

### Screenshot
<p align="center">
<img src="https://i.imgur.com/Y0g066j.png" width=80%/>
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
- A = minimum heap size for JVM in megabytes, e.g. *-Xms128m*,
- B = maximum heap size for JVM in megabytes, e.g. *-Xmx256m*,

Arguments (required):
- C = number of seeds (lists to sort), e.g. *500*,
- D = number of elements in seed, e.g. *8000*,
- E = number of sorting threads, e.g. *16*,
- F = type of reference to store a key (seed) in the map - cache - *SOFT*, *HARD* or *WEAK*,
- H = type of reference to store a value (dataset) in the map - cache - *SOFT*, *HARD* or *WEAK*.

### Description
Console, multi-threaded application for testing JVM (*Java Virtual Machine*) capabilities. Synchronized threads try to sort (with algorithms from [cw1](#cw1)) selected (random generated) datasets identified by a *seed*. Appropriate classes with sorting methods are dynamically loaded during the program execution from *cw1.jar* by using *ClassLoader* and *reflection mechanism*. Sorted datasets are putting in *cache* ([ReferenceMap](http://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/map/ReferenceMap.html)) by using chosen references - *WEAK*, *SOFT* or *HARD*, depending on which *Garbage Collector* may delete dataset from memory. By setting another parameters (mainly *B* - maximum heap size for JVM) many behaviours can be observed - such as segmentation faults (*OutOfMemoryError*) if references are *HARD* or nearly 100% misses if the maximum size is low and references are *WEAK*, and finally, smooth, continuous running by using *SOFT* references. Application generates every 3 seconds raport which gives information about cache misses and overall sorted datasets compared to stored datasets in the cache at this moment.

By the following call you set maximum JVM memory to 256 megabytes and run 16 threads that are trying to sort 500 seeds with 8000 elements each and put it to the cache. Seed reference is *WEAK* and dataset references are *SOFT*. Below - in the console output - it can be observed that declared references provides constant number of datasets stored in the cache, despite the continuous sorts performed by the threads:
```
$ java -Xms128m -Xmx256m -Djava.awt.headless=true -jar cw3-1.0-SNAPSHOT-jar-with-dependencies.jar 500 8000 16 WEAK HARD
```
<p align="center">
<img src="https://i.imgur.com/BHAE72Q.png" width=80% />
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
Add *MyBean.jar* from *cw4/Bean* to *cw4/BeanUsageExample* build path and have fun with such a great technology as Java Beans are!  

You can change bean properties by modifying *Bean*, *BeanInfo* and *Controller* in *cw4/bean* and then:
```
$ javac Bean.java BeanInfo.java Controller.java
$ jar cf MyBean.jar Bean.class BeanInfo.class Controller.class
```
Originally made in Eclipse IDE. Each update of the bean properties implies the need to refresh the project.  


### Screenshot
Exemplary screenshot with stack trace of an error caused of an attempt to change *beanFont* property to a blacklisted font (fell on poor Cambria). That property is constrained and when it is about to change, the listeners are consulted about the change and they say that Cambria is not suitable:
<p align="center">
<img src="https://i.imgur.com/A9kezF4.png" width=80% />
</p>

 <a name="cw5"></a>
## cw5

### Technologies 
- [Maven](https://maven.apache.org/download.cgi)
- [JavaFX (SDK 11.0.2)](https://gluonhq.com/products/javafx/)
- [RMI](https://docs.oracle.com/javase/7/docs/technotes/guides/rmi/hello/hello-world.html)

### Description
Application simulates process of running distributed applications by using RMI.  

Client applications (*ClientApplication.java*) allow you to generate random data and sort them using a server (*ServerApplication.java*) selected from the server list obtained thanks to the Central (*CentralApplication.java*).

### Running
To test distributed applications:
1. Configure VM arguments as it is described [here](#jav) in 3rd point
2. Run exactly once *cw5/src/rmi/CentralApplication.java* - it is the Central (list of servers)
3. Run *cw5/src/rmi/ClientApplication.java* as many as you want - it is the Client
4. Choose algorithm and run *cw5/src/rmi/ServerApplication.java* as many as you want - it is the Server 
5. Generate data, sort it by using one of registered servers, whatever you want to do

The order in which clients and servers are started doesn't matter. If you want to run more than one instance of Client or Server you must choose *Allow run in parallel* in *Edit Configurations* in IntelliJ.

### Screenshot

<p align="center">
<img src="https://i.imgur.com/5iEW1pW.png" />
</p>


<a name="conf"></a>
# Eclipse configurations

<a name="jav"></a>
## JavaFX
1. Go **Window** -> **Preferences** -> **Java** -> **Build Path** -> **User Libraries** -> **New** and write JAVAFX\_11. Then left click on JAVAFX_11, click Add External JARs... 
and add all JARs from *...\javafx-sdk-11.0.2\lib*.
2. Right click on the project and go **Build Path** -> **Configure Build Path** -> **Add Library** -> **User Library** -> **Next** and choose JAVAFX_11.
3. Go **Run** -> **Run Configurations** -> **Arguments** and in VM arguments field write:
```
--module-path "xyz\javafx-sdk-11.0.2\lib" --add-modules=javafx.controls,javafx.fxml
```
(**xyz** to your path to javafx-sdk). Then **Apply** -> **Close**.

___________________________________
<a name="fxc"></a>
## e(fx)clipse
1. Go **Help** -> **Install New Software**, click Add and in *Location* field paste 
```
https://download.eclipse.org/efxclipse/runtime-nightly/site/
```
2. Go **Help** -> **Install New Software**, click Add and in *Location* field paste 
```
https://download.eclipse.org/efxclipse/updates-nightly/site/
```
3. Restart IDE
___________________________________
<a name="scene"></a>
## Scene Builder
1. Go to [**Gluon** site](https://gluonhq.com/products/scene-builder/#download) and in section **Download Scene Builder for Java 11** download version for your OS.
2. Go **Window** -> **Preferences** -> **JavaFX** and in *SceneBuilder executable* field click browse and find your .exe file (*...\SceneBuilder\SceneBuilder.exe*) and in *JavaFX 11+ SDK* field click browse and find your sdk folder (*...\javafx-sdk-11.0.2*)
___________________________________
<a name="utf"></a>
## UTF-8 Encoding
1. Go **Window** -> **Preferences** -> **General** -> **Workspace** -> and in *Text file encoding* field choose **Other** -> **UTF-8**.

___________________________________
<a name="err"></a>
## Common errors
If any of this:
```
Exception in thread "WindowsNativeRunloopThread" java.lang.NoSuchMethodError: 
Exception in thread "JavaFX Application Thread" java.lang.NullPointerException
```

In the enviroment variable JAVA_HOME set the folder on Java 11 JDK (*C:\Program Files\Java\jdk-11.0.6*)
