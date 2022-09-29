package com.kcss.kcss.application.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI(@Value("${springdoc.version}") String springdocVersion) {
        Info info = new Info()
                .title("K-CSS 결제 관리 API")
                .version(springdocVersion)
                .description("계정, 결제, 그룹 데이터를 등록하고 집계합니다.");

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
