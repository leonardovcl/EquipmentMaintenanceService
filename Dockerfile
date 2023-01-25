FROM ibm-semeru-runtimes:open-17-jre-focal
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
CMD java -XX:MaxRAM=70m -jar app.jar