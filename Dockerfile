FROM ubuntu:16.04

RUN apt-get clean && apt-get update && apt-get install -y --no-install-recommends software-properties-common
RUN add-apt-repository ppa:openjdk-r/ppa && apt-get update
RUN apt-get install -y --no-install-recommends openjdk-8-jdk ca-certificates && apt-get install -y --no-install-recommends ca-certificates-java bash curl tzdata iproute2 zip unzip wget


EXPOSE 8080
EXPOSE 8081
EXPOSE 8443
EXPOSE 8444

VOLUME /var/log/inventory

ADD ./dev-config.yml dev-config.yml
ADD ./target/inventory-management-1.0-SNAPSHOT.jar server.jar

CMD sh -c "sleep 15 ; java -jar server.jar initialize docker.yml || true ;  java -Dfile.encoding=utf-8 -XX:+${GC_ALGO-UseG1GC} -Xms${JAVA_PROCESS_MIN_HEAP-1g} -Xmx${JAVA_PROCESS_MAX_HEAP-1g} ${JAVA_OPTS} -jar server.jar server docker.yml"
