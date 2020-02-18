
FROM maven as builder

WORKDIR /tmp

COPY . .

WORKDIR /tmp/auth

RUN mvn install

FROM openjdk:8-jdk-alpine

COPY --from=builder /tmp/auth ./

ENTRYPOINT [ "java", "-jar", "/target/auth-1.0.0.jar" ]