package lev.philippov.mssc_beer_service.services.inventory.reactivefeign.basic;

import lev.philippov.mssc_beer_service.services.inventory.BeerInventoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Profile("reactive-basic")
@Slf4j
public class InventoryReactiveFallbackService implements InventoryReactiveFeign{

    private final InventoryReactiveFallback inventoryReactiveFallback;

    @Override
    public Flux<BeerInventoryDto> getQtyOnHand(UUID beerId) {
        log.debug("************Fallback METHOD CALLED!*************");
        List<BeerInventoryDto> body = inventoryReactiveFallback.getQtyOnHand().getBody();
        System.out.println(body);
        BeerInventoryDto[] objects = (BeerInventoryDto[])body.toArray();
        return Flux.just(objects);
    }
}
