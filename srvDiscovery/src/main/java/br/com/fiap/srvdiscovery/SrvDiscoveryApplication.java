package br.com.fiap.srvdiscovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SrvDiscoveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(SrvDiscoveryApplication.class, args);
    }

}
