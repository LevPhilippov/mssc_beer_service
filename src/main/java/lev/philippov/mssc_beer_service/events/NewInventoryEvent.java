package lev.philippov.mssc_beer_service.events;

import lev.philippov.mssc_beer_service.web.models.BeerDto;

public class NewInventoryEvent extends BeerEvent{
    public NewInventoryEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
