# A demo credit card fraud detection algorithm.

-------------
Design Notes:
-------------
Reading requirements, basically we need to find:  
sum(transaction amount) > threshold group by credit card number and filter by given date  

Using JAVA SE 8's new streaming feature it's easier to get this problem solved then previous versions.


---------------------
System Requirements
---------------------
Application tested on Mac OSX 10.10 and 10.11  
Requires:  
JDK 1.8+  
MAVEN 3.0+

------------------------------------
To run tests on this application:
------------------------------------
```
mvn clean test
```