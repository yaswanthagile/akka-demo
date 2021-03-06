FROM openjdk
ARG USER=developer

RUN useradd -ms /bin/bash $USER
WORKDIR /home/$USER
USER $USER

COPY target/akka-1.0-SNAPSHOT-allinone.jar /home/$USER

EXPOSE 1800
CMD java -jar akka-1.0-SNAPSHOT-allinone.jar

