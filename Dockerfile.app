FROM eclipse-temurin:17

RUN apt-get update && apt-get install -y vim

EXPOSE 7070

ENTRYPOINT exec java -jar /opt/app/app.jar -XX:ActiveProcessorCount=6 -Xmx2048m