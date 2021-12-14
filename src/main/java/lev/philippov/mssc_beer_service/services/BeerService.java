package lev.philippov.mssc_beer_service.services;

import lev.philippov.mssc_beer_service.web.models.BeerDto;
import lev.philippov.mssc_beer_service.web.models.BeerDtoPage;

import java.util.UUID;

public interface BeerService {

    BeerDto findBeerById(UUID beerId);

    BeerDtoPage findAll();

    BeerDto saveNewBeer(BeerDto beerDto);

    BeerDto updateBeer(UUID beerID, BeerDto beerDto);
}
