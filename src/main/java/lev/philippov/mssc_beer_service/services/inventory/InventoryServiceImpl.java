package lev.philippov.mssc_beer_service.services.inventory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
@ConfigurationProperties(value = "sfg.brewery", ignoreUnknownFields = true)
@Profile("!local-discovery")
public class InventoryServiceImpl implements BeerInventoryService {

    private final RestTemplate restTemplate;

    protected static final String INVENTORY_API="/api/v1/beer/{beerId}/inventory";

    private String beerInventoryServiceUrl;

    public InventoryServiceImpl(RestTemplateBuilder builder, @Value("${sfg.brewery.username}")
    String username, @Value("${sfg.brewery.password}") String password) {
        this.restTemplate = builder.basicAuthentication(username,password).build();
    }

    public void setBeerInventoryServiceUrl(String beerInventoryServiceUrl) {
        this.beerInventoryServiceUrl = beerInventoryServiceUrl;
    }

    @PostConstruct
    public void init(){
        log.debug("Injected param of inventory service url is : {}",beerInventoryServiceUrl);
    }

    @Override
    public Integer getQuantityOnHand(UUID beerId) {
        ResponseEntity<List<BeerInventoryDto>> exchange = restTemplate.exchange(beerInventoryServiceUrl + INVENTORY_API, HttpMethod.GET, RequestEntity.EMPTY, new ParameterizedTypeReference<List<BeerInventoryDto>>() {
        }, beerId);
        return Objects.requireNonNull(exchange.getBody()).stream().mapToInt(BeerInventoryDto::getQuantityOnHand).sum();
    }
}
