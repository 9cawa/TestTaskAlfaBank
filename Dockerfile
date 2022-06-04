FROM openjdk:8-alpine

COPY build/libs/TestTaskAlfaBank-1.0-SNAPSHOT.jar /alfa-test-task.jar

CMD ["java","-jar","/alfa-test-task.jar"]