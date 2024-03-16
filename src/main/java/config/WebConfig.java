package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public void addCoreMappings (CorsRegistry registry){
        registry.addMapping("/**").allowedOrigins("*")
              //  .allowedHeaders("Authorization", "Content-Type", "Accept")
                .allowedMethods("GET", "POST", "PUT");
    }

}

