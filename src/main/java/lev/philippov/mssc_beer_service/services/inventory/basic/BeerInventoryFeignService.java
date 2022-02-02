package lev.philippov.mssc_beer_service.services.inventory.basic;

import lev.philippov.mssc_beer_service.services.inventory.BeerInventoryDto;
import lev.philippov.mssc_beer_service.services.inventory.InventoryServiceWebClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "inventory-service", fallback = BeerInventoryFallback.class, configuration = FeignClientBasicConfig.class)
@Profile("basic")
public interface BeerInventoryFeignService {

    @RequestMapping(method = RequestMethod.GET, path = InventoryServiceWebClientImpl.INVENTORY_API)
    ResponseEntity<List<BeerInventoryDto>> getQtyOnHand(@PathVariable(name = "beerId") UUID beerId);

}
