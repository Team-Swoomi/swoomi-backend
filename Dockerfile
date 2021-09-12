#FROM openjdk:11.0.11
#ARG JAR_FILE=build/libs/*.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]

#FROM openjdk:11.0.11 as builder
#
#COPY gradlew .
#
#COPY gradle gradle
#
#COPY build.gradle .
#
#COPY settings.gradle .
#
#COPY src src
#
#RUN chmod +x ./gradlew
#
#RUN ./gradlew bootJar
#
FROM openjdk:11.0.11

#COPY --from=builder build/libs/*.jar app.jar

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} /app.jar

COPY backend_swoomi_me.p12 /backend_swoomi_me.p12

COPY src/main/resources/application-real_private.yml /application-real_private.yml

EXPOSE 443

ENTRYPOINT ["java","-jar",\
"-Dspring.config.location=classpath:/application.yml,classpath:/application-real.yml,/application-real_private.yml",\
"-Dspring.profiles.active=real,real_private",\
"/app.jar"]