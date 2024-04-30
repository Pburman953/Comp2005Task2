package com.example.Comp2005_a;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public static RestTemplate restTemplate() {
        return new RestTemplate();
    }
    @Bean
    public ApiController apiController(){return new ApiController(restTemplate());}
    public static String apiURL = "https://web.socem.plymouth.ac.uk/COMP2005/api";
    @Bean
    public maternityAPIService maternityAPIService(RestTemplate restTemplate) {
        return new maternityAPIService(restTemplate, apiURL);
    }
}
