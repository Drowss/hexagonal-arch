package com.drow.trazabilidad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TrazabilidadApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrazabilidadApplication.class, args);
	}

}
