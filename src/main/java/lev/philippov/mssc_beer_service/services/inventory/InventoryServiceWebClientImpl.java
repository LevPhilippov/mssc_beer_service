package lev.philippov.mssc_beer_service.services.inventory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@Slf4j
//@ConfigurationProperties(value = "sfg.brewery", ignoreUnknownFields = true)
@Profile("default")
public class  InventoryServiceWebClientImpl implements BeerInventoryService {

    private final WebClient webClient;

    public static final String INVENTORY_API="/api/v2/beer/{beerId}/inventory";

    @Value("${sfg.brewery.beer-inventory-service-url}")
    private String beerInventoryServiceUrl;
    @Value("${sfg.brewery.username}")
    private String username;
    @Value("${sfg.brewery.password}")
    private String password;

    public InventoryServiceWebClientImpl(@Value("${sfg.brewery.beer-inventory-service-url}")
                                                 String beerInventoryServiceUrl) {
        this.webClient = WebClient.builder().baseUrl(beerInventoryServiceUrl)
//                .filter(ExchangeFilterFunctions
//                        .basicAuthentication(username, password))
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create().wiretap(true)))
                .build();
    }

    @Override
    public Integer getQuantityOnHand(UUID beerId) {
        List<BeerInventoryDto> inventoryList = new ArrayList<>();
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(INVENTORY_API).build(beerId.toString()))
//                .header("Authorization", "Basic " + HttpHeaders.encodeBasicAuth(username,password, StandardCharsets.UTF_8))
                .headers(httpHeaders -> httpHeaders.setBasicAuth(username,password))
                .accept(MediaType.APPLICATION_NDJSON)
                .retrieve()
                .bodyToFlux(BeerInventoryDto.class)
                .log("Receiving value: ")
                .collectList()
                .map(list -> Objects.requireNonNull(inventoryList).stream().mapToInt(BeerInventoryDto::getQuantityOnHand).sum())
                .block();
    }
}
