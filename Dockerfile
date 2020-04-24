FROM gradle:6.2-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build -x test --no-daemon

FROM openjdk:11-jre-slim

EXPOSE 8081

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/spring-boot-application.jar
COPY --from=build /home/gradle/src/build/resources/main/* /app/

RUN set -eux; \
	apt-get update; \
	apt-get install -y --no-install-recommends redir curl iputils-ping tcpdump; \
	rm -rf /var/lib/apt/lists/*

ADD docker/entrypoint.sh /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]

CMD ["java","-jar","/app/spring-boot-application.jar"]