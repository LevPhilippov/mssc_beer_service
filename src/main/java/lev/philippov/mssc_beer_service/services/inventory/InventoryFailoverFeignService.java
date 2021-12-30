package lev.philippov.mssc_beer_service.services.inventory;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "inventory-failover-service")
@Profile("local-discovery")
public interface InventoryFailoverFeignService {

    @RequestMapping(method = RequestMethod.GET, path = "/inventory-failover")
    ResponseEntity<List<BeerInventoryDto>> getQtyOnHand();

}
