package com.noofinc.inventory;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
			.info(new Info()
				.title("Inventory API")
				.description("API for managing inventory")
				.version("1.0.0")
				.termsOfService("Inventory API terms of service")
				.contact(new io.swagger.v3.oas.models.info.Contact().email("jimternet@email.com"))
				.license(new io.swagger.v3.oas.models.info.License().name("Apache License").url("apache lic url or something"))
			);
	}
}