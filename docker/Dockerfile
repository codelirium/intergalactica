FROM openjdk:8-jre

MAINTAINER codelirium


RUN mkdir -p /app/scripts

COPY ./docker/scripts/wait-for-it.sh /app/scripts

COPY ./docker/scripts/start.sh /app/scripts


RUN mkdir -p /opt/intergalactica

COPY ./target/intergalactic-unit-service.jar /opt/intergalactica


RUN mkdir -p /var/cache/api/intergalactica


RUN chmod +x /app/scripts/start.sh

ENTRYPOINT ["/app/scripts/start.sh"]
