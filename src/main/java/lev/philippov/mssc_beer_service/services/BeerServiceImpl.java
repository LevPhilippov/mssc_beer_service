package lev.philippov.mssc_beer_service.services;

import lev.philippov.mssc_beer_service.domain.Beer;
import lev.philippov.mssc_beer_service.mappers.BeerMapper;
import lev.philippov.mssc_beer_service.repository.BeerRepository;
import lev.philippov.mssc_beer_service.services.exceptions.BeerNotFoundException;
import lev.philippov.mssc_beer_service.web.models.BeerDto;
import lev.philippov.mssc_beer_service.web.models.BeerDtoPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper mapper;

    @Override
    public BeerDto findBeerById(UUID beerId) {
        return mapper.beerToBeerDto(beerRepository.findById(beerId).orElseThrow(BeerNotFoundException::new));
    }

    @Override
    public BeerDtoPage findAll() {
        return null;
    }

    @Transactional
    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        return mapper.beerToBeerDto(beerRepository.save(mapper.beerDtoToBeer(beerDto)));
    }

    @Transactional
    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
        Beer beer = beerRepository.findById(beerId).orElseThrow(BeerNotFoundException::new);
        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle().name());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());
        beerRepository.save(beer);
        return mapper.beerToBeerDto(beer);
    }
}
