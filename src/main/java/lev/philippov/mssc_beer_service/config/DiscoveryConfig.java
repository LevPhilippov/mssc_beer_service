package lev.philippov.mssc_beer_service.config;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableDiscoveryClient
@Profile("local-discovery")
public class DiscoveryConfig {
}
