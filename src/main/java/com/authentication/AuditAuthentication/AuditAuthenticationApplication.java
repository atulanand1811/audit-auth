package com.authentication.AuditAuthentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableEurekaClient
@SpringBootApplication
@CrossOrigin(origins = "*")

public class AuditAuthenticationApplication {

	/**
	 * Main function to bootstrap spring application as standalone application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(AuditAuthenticationApplication.class, args);
	}

	/**
	 * Docket bean's its select() method returns an instance of ApiSelectorBuilder,
	 * which provides a way to control the endpoints exposed by Swagger
	 * 
	 * @return
	 */
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.authentication.AuditAuthentication")).build();
	}

}
