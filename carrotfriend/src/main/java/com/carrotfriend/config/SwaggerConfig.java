package com.carrotfriend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableWebMvc
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket swaggerApi(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(
                new ApiInfoBuilder().title("Carrot Friends")
                        .description("Find you friend with this page")
                        .license("김성훈")
                        .license("안성진")
                        .license("홍주성")
                        .licenseUrl("http://localhost:8080")
                        .version("1.0.0")
                        .build()
        ).select().apis(RequestHandlerSelectors.basePackage("com.carrotfriend.controller"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false);
    }
}
