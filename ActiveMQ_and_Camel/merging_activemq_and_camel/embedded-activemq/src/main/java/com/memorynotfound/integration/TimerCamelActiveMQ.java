/**
*** https://github.com/apache/camel/blob/master/examples/camel-example-spring-boot-activemq/src/main/java/sample/camel/SampleAutowiredAmqRoute.java
**/
package com.memorynotfound.integration;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class TimerCamelActiveMQ extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("activemq:foo")
            .to("log:sample");

        //from("timer:bar")
        from("timer:initial//start?period=2000")
            .setBody(constant("Hello from Camel"))
            .to("activemq:foo");
    }

}
