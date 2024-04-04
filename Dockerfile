FROM openjdk:17
EXPOSE 7001
ADD target/encrypt-decrypt.jar encrypt-decrypt.jar
ENTRYPOINT ["java","-jar","/encrypt-decrypt.jar"]