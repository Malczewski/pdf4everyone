package ua.malczewski.pdf4everyone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Pdf4everyoneApplication {

	public static void main(String[] args) {
		SpringApplication.run(Pdf4everyoneApplication.class, args);
	}

}
