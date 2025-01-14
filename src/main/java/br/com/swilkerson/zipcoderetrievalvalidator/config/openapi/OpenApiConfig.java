package br.com.swilkerson.zipcoderetrievalvalidator.config.openapi;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    private static final String APP_NAME = "Zipcode-retrieval-validator";
    private static final String CURRENT_VERSION = "1.0.0";
    private static final String DESCRIPTION = "Documentation for Zipcode retrieval validator app";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(APP_NAME)
                        .version(CURRENT_VERSION)
                        .description(DESCRIPTION));
    }
}