FROM openjdk:8-jre-alpine
LABEL maintainer=andrew.bowden@minigeek.org
ENV KARAF_VERSION=4.0.10
ENV CAMEL_VERSION=2.15.1
ENV HAWTIO_VERISION=1.4.58

RUN apk add --update curl && \
	rm -rf /var/cache/apk/*

RUN mkdir -p /opt/karaf
ADD http://apache.melbourneitmirror.net/karaf/${KARAF_VERSION}/apache-karaf-${KARAF_VERSION}.tar.gz /opt/karaf/
WORKDIR /opt/karaf/
RUN tar --strip-components=1 -C /opt/karaf -xzf apache-karaf-${KARAF_VERSION}.tar.gz;
WORKDIR /opt/karaf/
RUN bin/start && \
	sleep 10 && \
	bin/client feature:repo-add camel ${CAMEL_VERSION} && \
	sleep 5 && \
	bin/client feature:install camel && \
	sleep 10 && \
	bin/client feature:repo-add hawtio ${HAWTIO_VERISION} && \
	sleep 5 && \
	bin/client feature:install hawtio

	
#COPY /config/org.ops4j.pax.logging.cfg /opt/karaf/etc/
EXPOSE 8181 8101 1099 44444
ENTRYPOINT ["/opt/karaf/bin/karaf", "start"]
