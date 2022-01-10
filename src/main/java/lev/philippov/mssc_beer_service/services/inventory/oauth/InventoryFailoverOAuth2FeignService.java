package lev.philippov.mssc_beer_service.services.inventory.oauth;

import lev.philippov.mssc_beer_service.services.inventory.BeerInventoryDto;
import lev.philippov.mssc_beer_service.services.inventory.oauth.FeignClientOAuth2ResourceServerConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "inventory-failover-service", configuration = FeignClientOAuth2ResourceServerConfig.class)
@Profile("oauth")
public interface InventoryFailoverOAuth2FeignService {

    @RequestMapping(method = RequestMethod.GET, path = "/inventory-failover")
    ResponseEntity<List<BeerInventoryDto>> getQtyOnHand();

}
