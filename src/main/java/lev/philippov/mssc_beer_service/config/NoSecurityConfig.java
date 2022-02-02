package lev.philippov.mssc_beer_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@Profile("basic")
@EnableWebFluxSecurity
public class NoSecurityConfig {

    @Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		http
			.authorizeExchange(exchanges -> exchanges
			    .anyExchange().permitAll()
			);
		return http.build();
	}


}
//@Bean
//	public MapReactiveUserDetailsService userDetailsService() {
//		UserDetails user = User.withDefaultPasswordEncoder()
//			.username("user")
//			.password("user")
//			.roles("USER")
//			.build();
//		return new MapReactiveUserDetailsService(user);
//	}
//
//	@Bean
//	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
//		http
//			.authorizeExchange(exchanges -> exchanges
//			    .anyExchange().authenticated()
//			)
//			.httpBasic(withDefaults())
//			.formLogin(withDefaults());
//		return http.build();
//	}