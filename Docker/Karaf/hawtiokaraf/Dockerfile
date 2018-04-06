FROM openjdk:8-jre-alpine
LABEL maintainer=andrew.bowden@minigeek.org
ENV KARAF_VERSION=4.1.5
ENV CAMEL_VERSION=2.18.4
ENV HAWTIO_VERISION=1.5.8

RUN apk add --update curl && \
	rm -rf /var/cache/apk/*

RUN mkdir -p /opt/karaf && \
	cd /opt/karaf && \
	curl -O http://apache.melbourneitmirror.net/karaf/${KARAF_VERSION}/apache-karaf-${KARAF_VERSION}.tar.gz && \
	tar --strip-components=1 -C /opt/karaf -xzf apache-karaf-${KARAF_VERSION}.tar.gz && \
	rm apache-karaf-*.tar.gz

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

RUN bin/start && \
	sleep 10 && \
	bin/client feature:install jms && \
	sleep 5 && \
	bin/client feature:repo-add activemq && \
	sleep 5 && \
	bin/client feature:install activemq-broker

	
#COPY /config/org.ops4j.pax.logging.cfg /opt/karaf/etc/
EXPOSE 8181 8101 1099 44444
ENTRYPOINT ["/opt/karaf/bin/karaf", "start"]
