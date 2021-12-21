package lev.philippov.mssc_beer_service.events;

import lev.philippov.mssc_beer_service.web.models.BeerDto;

public class BrewBeerEvent extends BeerEvent{
    public BrewBeerEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
