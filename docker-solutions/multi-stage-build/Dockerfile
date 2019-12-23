# source files not included

FROM openjdk:8-jdk-alpine AS dev

ADD gradle/wrapper/* /sources/gradle/wrapper/
ADD src /sources/src/
ADD build.gradle /sources
ADD gradle.properties /sources
ADD gradlew /sources
ADD settings.gradle /sources

WORKDIR /sources
RUN ./gradlew clean jar --no-daemon --info

FROM openjdk:8-jre-alpine AS prod
COPY --from=dev /sources/build/libs/simple-app-*.jar simple-app.jar
CMD java -jar simple-app.jar

EXPOSE 8080