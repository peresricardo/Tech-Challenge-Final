package br.com.fiap.srvpagamento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SrvPagamentoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SrvPagamentoApplication.class, args);
    }

}
