package lev.philippov.mssc_beer_service.services.inventory.reactivefeign.basic;

import lev.philippov.mssc_beer_service.services.inventory.BeerInventoryDto;
import lev.philippov.mssc_beer_service.services.inventory.BeerInventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Phaser;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
@Profile("reactive-basic")
@Slf4j
public class InventoryReactiveFeignService implements BeerInventoryService {

    private final InventoryReactiveFeign inventoryReactiveFeign;

    @Override
    public Integer getQuantityOnHand(UUID beerId) {
        log.debug("*****Invoked main feign service!*****");
        AtomicInteger result = new AtomicInteger();
        Phaser phaser = new Phaser(1);

        inventoryReactiveFeign.getQtyOnHand(beerId)
                .collectList().map(list -> list.stream()
                        .mapToInt(dto-> dto.getQuantityOnHand())
                        .sum())
                .subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                result.set(integer);
            }
        }, throwable -> {
        }, phaser::arrive);
        phaser.awaitAdvance(phaser.getPhase());
        log.debug("RESULT IS: " + result.get());
        return result.get();
    }
}
