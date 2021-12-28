package lev.philippov.mssc_beer_service.services.inventory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Profile("local-discovery")
public class BeerInventoryFeignService implements BeerInventoryService {

    private final BeerInventoryServiceFeignClient beerInventoryServiceFeignClient;

    @Override
    public Integer getQuantityOnHand(UUID beerId) {
        ResponseEntity<List<BeerInventoryDto>> entity = beerInventoryServiceFeignClient.getQtyOnHand(beerId);
        log.debug("********Responce throwgh open feign was received!**********");
        return Objects.requireNonNull(entity.getBody()).stream().mapToInt(BeerInventoryDto::getQuantityOnHand).sum();
    }
}
