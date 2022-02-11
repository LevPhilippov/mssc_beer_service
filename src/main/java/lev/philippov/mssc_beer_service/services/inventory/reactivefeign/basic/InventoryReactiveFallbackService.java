package lev.philippov.mssc_beer_service.services.inventory.reactivefeign.basic;

import lev.philippov.mssc_beer_service.services.inventory.BeerInventoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.OffsetDateTime;
import java.util.UUID;

@Component
@Profile("reactive-basic")
@Slf4j
public class InventoryReactiveFallbackService implements InventoryReactiveFeign{

    private final BeerInventoryDto dto = BeerInventoryDto.builder()
            .id(UUID.randomUUID())
            .beerId(UUID.fromString("00000000-0000-0000-0000-000000000000"))
            .createdDate(OffsetDateTime.now())
            .lastModifiedDate(OffsetDateTime.now())
            .quantityOnHand(999)
            .build();

    @Override
    public Flux<BeerInventoryDto> getQtyOnHand(UUID beerId) {
        log.debug("************Fallback METHOD CALLED!*************");
        return Flux.just(dto);
    }
}
