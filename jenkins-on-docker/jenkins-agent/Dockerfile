FROM openjdk:8-jdk

# install packages
RUN apt-get update
RUN apt-get install --no-install-recommends -y openssh-server vim iputils-ping procps
RUN apt-get clean

# create jenkins user
RUN groupadd -g 1000 jenkins
RUN useradd -d /home/jenkins -u 1000 -g 1000 -m -s /bin/bash jenkins

# set working directory
WORKDIR /home/jenkins

# copy gpg key
COPY jenkins.asc passphrase.txt /home/jenkins/
RUN chown -Rf jenkins:jenkins jenkins.asc passphrase.txt

# continue as jenkins
USER jenkins

# set up gpg
RUN mkdir .gnupg
RUN chmod 0700 -R .gnupg
RUN echo allow-loopback-pinentry > .gnupg/gpg-agent.conf
RUN echo "use-agent\npinentry-mode loopback" > .gnupg/gpg.conf
RUN gpg --no-tty --passphrase-file passphrase.txt --import jenkins.asc
RUN rm jenkins.asc passphrase.txt

# configure git for jenkins user
RUN git config --global user.email "jenkins@deliverymind.co.uk"
RUN git config --global user.name "Jenkins"

# continue as root
USER root

# configure maven
RUN mkdir .m2
COPY settings.xml .m2/settings.xml
RUN chown -Rf jenkins:jenkins .m2

# configure gradle
RUN mkdir .gradle
COPY gradle.properties .gradle/
RUN chown -Rf jenkins:jenkins .gradle

# configure ssh
RUN mkdir /var/run/sshd
RUN mkdir .ssh
COPY id_rsa_jenkins.pub .ssh/authorized_keys
RUN chown -Rf jenkins:jenkins .ssh
RUN chmod 0700 -R .ssh

# start ssh
CMD exec /usr/sbin/sshd -De
EXPOSE 22