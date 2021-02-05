FROM openjdk:8
ADD target/spring-security-otp-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD java -jar spring-security-otp-0.0.1-SNAPSHOT.jar
