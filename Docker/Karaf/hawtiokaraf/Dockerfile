FROM java:8u66
MAINTAINER soucianceeqdamrashti <souciance.eqdam.rashti@gmail.com>
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64
ENV KARAF_VERSION=4.0.10
RUN mkdir /opt/karaf
ADD http://apache.melbourneitmirror.net/karaf/${KARAF_VERSION}/apache-karaf-${KARAF_VERSION}.tar.gz /opt/karaf/
WORKDIR /opt/karaf/
RUN tar --strip-components=1 -C /opt/karaf -xzf apache-karaf-${KARAF_VERSION}.tar.gz;
WORKDIR /opt/karaf/
RUN bin/start && \
	sleep 10 && \
	bin/client feature:repo-add camel 2.15.1 && \
	sleep 5 && \
	bin/client feature:install camel && \
	sleep 10 && \
	bin/client feature:repo-add hawtio 1.4.58 && \
	sleep 5 && \
	bin/client feature:install hawtio
#COPY /config/org.ops4j.pax.logging.cfg /opt/karaf/etc/
EXPOSE 8181
ENTRYPOINT ["/opt/karaf/bin/karaf", "start"]
