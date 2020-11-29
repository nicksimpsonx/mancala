package com.simpsonn.mancala.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * configuration for the interactive api documentation. whilst the application
 * is running these docs can be seen at
 * http://localhost:8080/swagger-ui.html <br>
 * the game can be played using the interactive documentation too
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

   @Bean
   public Docket productApi() {
	   
      return new Docket(DocumentationType.SWAGGER_2).select()
         .apis(RequestHandlerSelectors.basePackage("com.simpsonn.mancala"))
         .build()
         .apiInfo(apiInfo());
   }	
   
   private ApiInfo apiInfo() {
	   
       return new ApiInfoBuilder()
           .title("6 Stone Kalah")
           .description("Play this classic strategy game with a friend using your favourite Rest client!")
           .version("0.0.1")
           .license("Apache License Version 2.0")
           .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
           .build();
   }

}
