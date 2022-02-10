package lev.philippov.mssc_beer_service.services.inventory.reactivefeign.basic;

import lev.philippov.mssc_beer_service.services.inventory.BeerInventoryDto;
import lev.philippov.mssc_beer_service.services.inventory.basic.BeerInventoryFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

@FeignClient(value = "inventory-failover-service")
@Profile("reactive-basic")
public interface InventoryReactiveFallback {

    @GetMapping("/inventory-failover")
    ResponseEntity<List<BeerInventoryDto>> getQtyOnHand();

}
