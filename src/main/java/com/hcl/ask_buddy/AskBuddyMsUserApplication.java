package com.hcl.ask_buddy;

import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;

import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;

import springfox.documentation.spi.service.contexts.SecurityContext;


@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
@EnableScheduling
public class AskBuddyMsUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(AskBuddyMsUserApplication.class,
				args);
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.securityContexts(Collections.singletonList(securityContext()))
				.securitySchemes(List.of(apiKey()))
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfo(
				"Api Documentation",
				"Api Documentation",
				"1.0",
				"urn:tos",
				new springfox.documentation.service.Contact("Pavani", "localhost.com", "sindiripavani01@gmail.com"),
				"Apache 2.0",
				"https://www.apache.org/licenses/LICENSE-2.0",
				new ArrayList<>());
	}
	private ApiKey apiKey() {
		return new ApiKey("JWT", "Authorization", "Header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).build();
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return List.of(new SecurityReference("JWT", authorizationScopes));
	}
}


