package lev.philippov.mssc_beer_service.services.inventory.oauth;

import lev.philippov.mssc_beer_service.services.inventory.BeerInventoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
@Profile("oauth")
public class BeerInventoryFallbackServiceImpl implements BeerInventoryFeignService {

    private final InventoryFailoverOAuth2FeignService inventoryFailoverFeignService;

    @Override
    public ResponseEntity<List<BeerInventoryDto>> getQtyOnHand(UUID beerId) {
        log.debug("***********Trying to return failover response!************");
        return inventoryFailoverFeignService.getQtyOnHand();
    }
}
