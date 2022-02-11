package lev.philippov.mssc_beer_service.services.inventory.reactivefeign.basic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.util.Base64Utils;
import reactivefeign.ReactiveFeign;
import reactivefeign.client.ReactiveHttpRequest;
import reactivefeign.client.ReactiveHttpRequestInterceptor;
import reactivefeign.client.ReactiveHttpRequestInterceptors;
import reactivefeign.webclient.WebReactiveFeign;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
@Profile("reactive-basic")
public class InventoryReactiveFeignConfig {

//    @Bean
//    ReactiveFeign request(@Value("${sfg.brewery.username}") String username,
//                          @Value("${sfg.brewery.password}") String password) {
//       return WebReactiveFeign.builder()
//                .addRequestInterceptor(ReactiveHttpRequestInterceptors
//                .addHeader("Authorization", "Basic " + Base64Utils.encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8))))
//                .build();
//    }

    @Bean
    ReactiveHttpRequestInterceptor reactiveHttpRequestInterceptor(@Value("${sfg.brewery.username}") String username,
                                                                  @Value("${sfg.brewery.password}") String password) {
        return reactiveHttpRequest -> {
            reactiveHttpRequest.headers().put("Authorization", List.of("Basic " + Base64Utils.encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8))));
            return Mono.just(reactiveHttpRequest);
        };
    }
}
