package br.org.fitec.configuration;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.stereotype.Component;

@Component
public class TomcatConfiguration implements EmbeddedServletContainerCustomizer{

    public static final Logger logger = LoggerFactory.getLogger(TomcatConfiguration.class);
    
    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        InetAddress ip;
        try {
            int port = Integer.valueOf(System.getenv("TOM_PORT"));
            ip = Inet4Address.getLocalHost();
            container.setAddress(ip);
            container.setPort(port);
            container.setContextPath("");
            logger.info("********************************************************");
            logger.info("******************** TOMCAT ADDRESS ********************");
            logger.info("Tomcat container on: " + ip.toString() + ":" + port);
            logger.info("******************** TOMCAT ADDRESS ********************");
            logger.info("********************************************************");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    
}
