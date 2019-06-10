How to run
*Mail server (FakeSMTP) should be running on port 25

Method 01 - by running jars
1. maven install the project, 2 jar files will be created [client.jar, server.jar]
2. run server : java -jar server.jar
3. run client : java -jar client.jar n t (where 'n' is no of request & 't' is no of threads - ex: java -jar client.jar 100 10)

Method 02 - by using IDE
1. run main method in SocketServerRunner.java to start server
2. run main method in SocketClientRunner.java with n and t arguments to run client