package br.org.fitec;

import java.net.UnknownHostException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import br.org.fitec.configuration.JpaConfiguration;

//@Import(JpaConfiguration.class)
@SpringBootApplication(scanBasePackages= {"br.org.fitec"})
public class App {

    public static void main(String[] args) throws UnknownHostException {

        SpringApplication app = new SpringApplication(App.class);
        app.run(args);
    }
}
