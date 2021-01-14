package com.training.advancedlab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
@EnableSwagger2
public class AdvancedLabApplication {

	public static void main(String[] args) { SpringApplication.run(AdvancedLabApplication.class, args); }

	@Bean
	public Docket swaggerConfiguration() {
		String groupName = "Swagger Documentation";
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.paths(PathSelectors.ant("/user/*"))
				.apis(RequestHandlerSelectors.basePackage("com.training"))
				.build()
				.groupName(groupName)
				.apiInfo(apiDetails());
	}

	private ApiInfo apiDetails() {
		return new ApiInfo(
			"Advanced Lab API",
			"An API build for the Advanced Java Lab",
			"1.0",
			"Free to use",
			new Contact("Fulop Alex", "https://dreadbits.com", "fulopalex2000@gmail.com"),
			"API License",
			"https://dreadbits.com",
			Collections.emptyList());
	}
}
