FROM maven:3.6.3-openjdk-16
RUN mkdir job4j_tracker-1
WORKDIR job4j_tracker-1
COPY . .
Run mvn package -Dmaven.test.skip=true
CMD ["java", "-jar", "target/memTracker.jar"]
