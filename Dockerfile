FROM openjdk:11-jdk

WORKDIR /root

COPY build/libs/api-0.0.1-SNAPSHOT.jar .

CMD java -jar api-0.0.1-SNAPSHOT.jar