package com.example.spring_boot_crud;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class open_config {
@Bean
    public OpenAPI mycustomconif(){
    return new OpenAPI().info(
            new Info().title("Testing crud api")
                    .description("by apurv")
    );
}
}
