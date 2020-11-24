FROM maven:3-jdk-8 as build

RUN mkdir -p /webapp
WORKDIR /webapp

# Build up Maven repo cache
COPY pom.xml .
RUN mvn -B -s /usr/share/maven/ref/settings-docker.xml dependency:resolve

# Now do actual application build (image layer cache will be used for above if no pom.xml changes)
COPY . .
RUN mvn -B -s /usr/share/maven/ref/settings-docker.xml package

FROM openjdk:8
RUN mkdir /app
COPY --from=build /webapp/target/webapp-1.0-SNAPSHOT.jar /java/webapp.jar
COPY --from=build /webapp/config.yml /java/config.yml

RUN groupadd -r java
RUN useradd -r -g java -d /java -s /sbin/nologin -c "Java runner user" java
RUN chown -R java:java /java
USER java

EXPOSE 8080
EXPOSE 8081
WORKDIR /javaapp

ENTRYPOINT ["java", "-jar", "webapp.jar"]
CMD ["server", "config.yml"]