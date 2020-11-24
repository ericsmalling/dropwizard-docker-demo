FROM maven:3-jdk-8

RUN mkdir -p /webapp
WORKDIR /webapp

# Build up Maven repo cache
COPY pom.xml .
RUN mvn -B dependency:resolve

# Now do actual application build (image layer cache will be used for above if no pom.xml changes)
COPY . .
RUN mvn -B package

EXPOSE 8080
EXPOSE 8081

ENTRYPOINT ["java", "-jar", "target/webapp-1.0-SNAPSHOT.jar"]
CMD ["server", "config.yml"]