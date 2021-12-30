package lev.philippov.mssc_beer_service.services.inventory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
@Profile("local-discovery")
public class BeerInventoryFallback implements BeerInventoryFeignService{

    private final InventoryFailoverFeignService inventoryFailoverFeignService;

    @Override
    public ResponseEntity<List<BeerInventoryDto>> getQtyOnHand(UUID beerId) {
        log.debug("***********Trying to return failover response!************");
//        return new ResponseEntity(List.of(new BeerInventoryDto()),HttpStatus.OK);
        return inventoryFailoverFeignService.getQtyOnHand();
    }
}