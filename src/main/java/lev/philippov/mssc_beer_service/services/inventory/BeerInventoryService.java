package lev.philippov.mssc_beer_service.services.inventory;

import java.util.UUID;

public interface BeerInventoryService {
    Integer getQuantityOnHand(UUID beerId);
}
