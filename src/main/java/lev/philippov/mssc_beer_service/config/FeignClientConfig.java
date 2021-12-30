package lev.philippov.mssc_beer_service.config;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;

@Configuration
public class FeignClientConfig {

    @Bean
    BasicAuthRequestInterceptor basicAuthRequestInterceptor(@Value("${sfg.brewery.username}") String username,
                                                               @Value("${sfg.brewery.password}") String password) {
        return new BasicAuthRequestInterceptor(username,password);
    }
}
