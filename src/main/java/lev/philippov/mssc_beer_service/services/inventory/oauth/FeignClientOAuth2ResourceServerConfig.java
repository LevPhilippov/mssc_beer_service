package lev.philippov.mssc_beer_service.services.inventory.oauth;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

import java.util.Objects;

@Configuration
@Profile("oauth")
public class FeignClientOAuth2ResourceServerConfig {

    public static final Authentication ANONYMOUS_USER_AUTHENTICATION =
            new AnonymousAuthenticationToken(
                    "key", "anonymous", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));

    //todo сменить clientid и secret на injected
    @Bean
    ClientRegistration clientRegistration(){
        return ClientRegistration
                .withRegistrationId("keycloak")
                .clientId("beerservice")
                .clientSecret("We28xussHho53PAXev6XQF1NcxNbrnr9")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
//                .authorizationUri("http://localhost:8080/auth/realms//protocol/openid-connect/auth")
                .tokenUri("http://localhost:8087/auth/realms/brewery/protocol/openid-connect/token")
//                .userInfoUri("http://localhost:8080/auth/realms/eazybankdev/protocol/openid-connect/userinfo")
//                .jwkSetUri("http://localhost:8080/auth/realms/eazybankdev/protocol/openid-connect/certs")
                .build();

    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(clientRegistration());
    }

    @Bean
    public OAuth2AuthorizedClientService auth2AuthorizedClientService(ClientRegistrationRepository clientRegistrationRepository) {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
    }

    // Create the authorized client manager and service manager using the
    // beans created and configured above
    @Bean
    public AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientServiceAndManager (
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientService authorizedClientService) {

        OAuth2AuthorizedClientProvider authorizedClientProvider =
                OAuth2AuthorizedClientProviderBuilder.builder()
                        .clientCredentials()
                        .build();

        AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager =
                new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                        clientRegistrationRepository, authorizedClientService);
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

        return authorizedClientManager;
    }


    OAuth2AccessToken getAccessToken(OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager){
        // Build an OAuth2 request for the Okta provider
        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest.withClientRegistrationId("keycloak")
                .principal(ANONYMOUS_USER_AUTHENTICATION)
                .build();

        // Perform the actual authorization request using the authorized client service and authorized client
        // manager. This is where the JWT is retrieved from the Okta servers.
        OAuth2AuthorizedClient authorizedClient = oAuth2AuthorizedClientManager.authorize(authorizeRequest);

        // Get the token from the authorized client object
        return Objects.requireNonNull(authorizedClient).getAccessToken();
    }

    @Bean
    RequestInterceptor oauth2FeignRequestInterceptor(OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager) {
        OAuth2AccessToken token = getAccessToken(oAuth2AuthorizedClientManager);
        return template -> template.header(HttpHeaders.AUTHORIZATION, "Bearer " + token.getTokenValue());
    }

}
