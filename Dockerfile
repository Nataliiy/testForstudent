FROM openjdk:11
ADD /target/periodicals-0.0.1-SNAPSHOT.jar periodicals-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "periodicals-0.0.1-SNAPSHOT.jar"]