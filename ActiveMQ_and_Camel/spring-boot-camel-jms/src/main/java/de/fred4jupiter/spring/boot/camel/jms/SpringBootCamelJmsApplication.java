package de.fred4jupiter.spring.boot.camel.jms;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.activemq.security.AuthenticationUser;
import org.apache.activemq.security.SimpleAuthenticationPlugin;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.jms.JmsConfiguration;
import org.apache.camel.spring.CamelBeanPostProcessor;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.apache.activemq.broker.BrokerPlugin;
import org.apache.activemq.broker.BrokerService;

import java.util.ArrayList;
import java.util.List;

import javax.jms.ConnectionFactory;

@SpringBootApplication
@EnableScheduling
public class SpringBootCamelJmsApplication {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private MyCamelRoutes myCamelRoutes;

    @Bean(destroyMethod = "stop")
    public CamelContext camelContext(ActiveMQComponent activeMQComponent) throws Exception {
        SpringCamelContext camelContext = new SpringCamelContext(applicationContext);
        camelContext.disableJMX();

        camelContext.addRoutes(myCamelRoutes);

        camelContext.addComponent("activemq", activeMQComponent);
        camelContext.start();
        return camelContext;
    }

    /**
     * For using Camel annotations in spring beans.
     *
     * @param camelContext
     * @param applicationContext
     * @return
     */
    @Bean
    public CamelBeanPostProcessor camelBeanPostProcessor(CamelContext camelContext, ApplicationContext applicationContext) {
        CamelBeanPostProcessor processor = new CamelBeanPostProcessor();
        processor.setCamelContext(camelContext);
        processor.setApplicationContext(applicationContext);
        return processor;
    }

    @Bean
    public ProducerTemplate producerTemplate(CamelContext camelContext) {
        return camelContext.createProducerTemplate();
    }

    @Bean
    public ActiveMQConnectionFactory coreConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("vm://localhost?broker.persistent=false");
        connectionFactory.setUserName("admin");
        connectionFactory.setPassword("admin");
        return connectionFactory;
    }

    @Bean
    public JmsConfiguration jmsConfiguration(ActiveMQConnectionFactory coreConnectionFactory) {
        JmsConfiguration jmsConfiguration = new JmsConfiguration();
        ConnectionFactory connectionFactory = new PooledConnectionFactory(coreConnectionFactory);
        jmsConfiguration.setConnectionFactory(connectionFactory);
        return jmsConfiguration;
    }

    @Bean
    public ActiveMQComponent activeMQComponent(JmsConfiguration jmsConfiguration) {
        ActiveMQComponent component = new ActiveMQComponent();
        component.setConfiguration(jmsConfiguration);
        return component;
    }
    
    
    
    

    public static void main(String[] args) throws Exception {
    	// Trying to add users
    	List<AuthenticationUser> users = new ArrayList<>();
    	users.add(new AuthenticationUser("ab", "ab", "admin"));
    	users.add(new AuthenticationUser("admin", "admin", "admin"));
    	SimpleAuthenticationPlugin plugin = new SimpleAuthenticationPlugin(users);
    	BrokerPlugin[] plugins = new BrokerPlugin[] { plugin };
    	
    	
      // Modified based on
      // https://examples.javacodegeeks.com/enterprise-java/jms/apache-activemq-brokerservice-example/
      //  http://activemq.apache.org/how-do-i-embed-a-broker-inside-a-connection.html

      BrokerService broker = new BrokerService();
      broker.setUseJmx(true);
      broker.addConnector("tcp://localhost:61616");
      
      // add the users
      broker.setPlugins(plugins);
      
      broker.start();
      System.out.println("Broker Started!!!");
      // now lets wait forever to avoid the JVM terminating immediately
      /*Object lock = new Object();
      synchronized (lock) {
        lock.wait();
      }*/
        SpringApplication.run(SpringBootCamelJmsApplication.class, args);
    }
}
