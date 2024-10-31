package com.humanbooster.authent.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiDocumentationConfiguration {

    @Bean
    public OpenAPI apiDocConfig() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes
                        ("Bearer Authentication",createAPIKeyScheme()))
                .info(new Info()
                        .title("Business Case")
                        .description("API de gestion des clients")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Nicolas Flak")
                                .email("nicolas.flak@gmail.com")));
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

//    @Bean
//    public OpenAPI apiDocConfig() {
//        return new OpenAPI().addSecurityItem(new SecurityRequirement().
//                        addList("Bearer Authentication"))
//                .components(new Components().addSecuritySchemes
//                        ("Bearer Authentication",createAPIKeyScheme()))
//                .info(new Info()
//                        .title("Sun Saver Documentation")
//                        .contact(new Contact().email("dleroux@stagiaire-humanbooster.com").name("David LEROUX")
//                        )
//                        .version("V1.1.1")
//                        .description("API Business Case Gestion des utilisateurs")
//
//                );
//    }
//    private SecurityScheme createAPIKeyScheme(){
//        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
//                .bearerFormat("JWT")
//                .scheme("bearer");
//    }
}