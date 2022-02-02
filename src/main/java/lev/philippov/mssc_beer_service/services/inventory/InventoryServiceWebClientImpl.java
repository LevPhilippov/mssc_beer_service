package lev.philippov.mssc_beer_service.services.inventory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
//@ConfigurationProperties(value = "sfg.brewery", ignoreUnknownFields = true)
@Primary
public class InventoryServiceWebClientImpl implements BeerInventoryService {

    private final WebClient webClient;

    public static final String INVENTORY_API="/api/v2/beer/{beerId}/inventory";

    @Value("${sfg.brewery.beer-inventory-service-url}")
    private String beerInventoryServiceUrl;

    public InventoryServiceWebClientImpl(@Value("${sfg.brewery.username}") String username,
                                         @Value("${sfg.brewery.password}") String password,
                                         @Value("${sfg.brewery.beer-inventory-service-url}") String beerInventoryServiceUrl)
    {
        this.webClient = WebClient.builder().baseUrl(beerInventoryServiceUrl)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create().wiretap(true)))
                .build();
    }

    @Override
    public Integer getQuantityOnHand(UUID beerId) {
        List<BeerInventoryDto> inventoryList = new ArrayList<>();
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(INVENTORY_API).build(beerId.toString()))
                .accept(MediaType.APPLICATION_NDJSON)
                .retrieve()
                .bodyToFlux(BeerInventoryDto.class)
                .log("Receiving value: ")
                .collectList()
                .map(list -> Objects.requireNonNull(inventoryList).stream().mapToInt(BeerInventoryDto::getQuantityOnHand).sum())
                .block();
    }
}
