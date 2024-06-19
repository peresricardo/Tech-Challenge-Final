package br.com.fiap.srvItem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SrvItemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SrvItemApplication.class, args);
	}

}
