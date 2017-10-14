FROM jenkins/jenkins:2.73.1-slim

# continue setup as root
USER root

# install packages
RUN apt-get update
RUN apt-get install --no-install-recommends -y vim iputils-ping procps
RUN apt-get clean

# solve problem with trend charts
RUN sed -i 's/assistive_technologies.*/#assistive_technologies=org.GNOME.Accessibility.AtkWrapper/' /etc/java-8-openjdk/accessibility.properties