package lev.philippov.mssc_beer_service.services;

import guru.sfg.brewery.model.BeerDto;
import guru.sfg.brewery.model.BeerDtoPage;

import java.util.UUID;

public interface BeerService {

    BeerDto findBeerById(UUID beerId, Boolean enhanceWithInventory);

    BeerDto findBeerByUpc(String upc, Boolean enhanceWithInventory);

    BeerDtoPage findAll(Integer pageSize, Integer pageNumber, String beerName, String beerStyle, Boolean enhanceWithInventory);

    BeerDto saveNewBeer(BeerDto beerDto);

    BeerDto updateBeer(UUID beerID, BeerDto beerDto);
}
