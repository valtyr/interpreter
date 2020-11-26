FROM maven:3.6.3-adoptopenjdk-14

COPY . /app

WORKDIR /app

RUN mvn package

CMD ["java", "-jar", "target/mariaskal-0.0.1-SNAPSHOT.jar", "--server.port=6969"]
