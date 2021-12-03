FROM adoptopenjdk:15-jre-hotspot

RUN mkdir /app

WORKDIR /app

ADD ./orders-api/target/orders-1.0-SNAPSHOT.jar /app

EXPOSE 3000

CMD ["java", "-jar", "orders-1.0-SNAPSHOT.jar"]