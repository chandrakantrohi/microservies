package com.eazybytes.accounts;

import com.eazybytes.accounts.dto.AccountContactInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableConfigurationProperties(value = {AccountContactInfoDto.class})
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Account micro service rest API documentation",
				description = "Eazy Banck Account micro service rest API documentation",
				version = "v1",
				contact = @Contact(
						name = "Chandrakant Rohi",
						email = "chandrakant.rohi111@gmail.com",
						url = "example.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "example.com"


				)

		),
		externalDocs = @ExternalDocumentation(
				description = "Account micro service rest API documentation",
				url = "example.com"
		)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
