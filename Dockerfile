FROM library/centos:7.4.1708

MAINTAINER Valentin Delaye <jonesbusy@gmail.com>

# Argument
ARG version

# Force UTF-8 stuff
ENV LANG=en_US.UTF-8 LANGUAGE=en_US:en LC_ALL=en_US.UTF-8 JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF8" JAVA_HOME=/usr/java/jdk1.8.0_151/jre backend_version=${version}

# Install Oracle JDK and set it as default (can be changed by applications)
RUN yum update -y && yum install -y curl unzip && curl -O --silent https://d3rh6fyf3sdelq.cloudfront.net/oracle/jdk/8u151/jdk-8u151-linux-x64.rpm && \ 
    rpm -i jdk-8u151-linux-x64.rpm && \
    update-alternatives --set java /usr/java/jdk1.8.0_151/jre/bin/java && yum clean all && java -version

# Add script files
ADD ["wait-for-it.sh", "run.sh", "docker-entrypoint.sh", "/"]

# Add logback
ADD logback.xml /opt/package/logback.xml

# Healthcheck
HEALTHCHECK CMD (nc localhost 48080 < /dev/null) > /dev/null 2>&1 || exit 1

# Run user
RUN adduser --system --no-create-home --shell /bin/false --gid 0 java-backend && \ 
    touch /opt/package/logback.xml && \
    chmod 660 /opt/package/logback.xml -R && \
    chown -R java-backend:root /opt/package && \
	mkdir /opt/package/logs && chmod 770 -R /opt/package/logs && \
    chown java-backend:root /run.sh /wait-for-it.sh /docker-entrypoint.sh
USER java-backend

EXPOSE 48080 
VOLUME ["/opt/package/logs"]

# Add binary
WORKDIR /opt
ADD package/java-backend-${backend_version}-service.zip /opt/java-backend-${backend_version}-service.zip

RUN unzip /opt/java-backend-${backend_version}-service.zip

WORKDIR /opt/package

ENTRYPOINT ["/docker-entrypoint.sh"]
CMD ["/run.sh"]


