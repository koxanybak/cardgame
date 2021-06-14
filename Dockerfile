FROM maven:3.8.1-openjdk-16 AS build
COPY . /app
WORKDIR /app
RUN mvn clean package -DskipTests -Pprod

FROM adoptopenjdk/openjdk16:alpine
COPY --from=build /app/target/cardgame-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 3001

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
