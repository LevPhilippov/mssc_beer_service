package lev.philippov.mssc_beer_service.services.inventory.reactivefeign.basic;

import feign.Headers;
import lev.philippov.mssc_beer_service.services.inventory.BeerInventoryDto;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;

import java.util.UUID;

@ReactiveFeignClient(value = "inventory-service", fallback = InventoryReactiveFallbackService.class, configuration = InventoryReactiveFeignConfig.class)
@Profile("reactive-basic")
@Headers({ "Accept: application/ndjson" })
public interface InventoryReactiveFeign {

    @GetMapping("/api/v2/beer/{beerId}/inventory")
    Flux<BeerInventoryDto> getQtyOnHand(@PathVariable("beerId") UUID beerId);

}
