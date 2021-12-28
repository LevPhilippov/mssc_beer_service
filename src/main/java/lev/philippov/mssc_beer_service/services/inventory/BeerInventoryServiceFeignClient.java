package lev.philippov.mssc_beer_service.services.inventory;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;

@Service
@FeignClient(name = "inventory-service")
public interface BeerInventoryServiceFeignClient {

    @RequestMapping(method = RequestMethod.GET, path = InventoryServiceImpl.INVENTORY_API)
    ResponseEntity<List<BeerInventoryDto>> getQtyOnHand(@PathVariable(name = "beerId") UUID beerId);
}
