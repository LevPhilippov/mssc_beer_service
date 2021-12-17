package lev.philippov.mssc_beer_service.services;

import lev.philippov.mssc_beer_service.domain.Beer;
import lev.philippov.mssc_beer_service.mappers.BeerMapper;
import lev.philippov.mssc_beer_service.repository.BeerRepository;
import lev.philippov.mssc_beer_service.services.exceptions.BeerNotFoundException;
import lev.philippov.mssc_beer_service.web.models.BeerDto;
import lev.philippov.mssc_beer_service.web.models.BeerDtoPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper mapper;
    private static final Integer DEFAULT_PAGE_SIZE=5;
    private static final Integer DEFAULT_PAGE_NUMBER=0;

    @Autowired
    public BeerServiceImpl(BeerRepository beerRepository, BeerMapper mapper) {
        this.beerRepository = beerRepository;
        this.mapper = mapper;
    }

    @Cacheable(value = "beerCache", key = "#beerId",condition = "#enhanceWithInventory==false")
    @Override
    public BeerDto findBeerById(UUID beerId, Boolean enhanceWithInventory) {
        System.out.println("I WAS CALLED!");
        Optional<Beer> optional = beerRepository.findById(beerId);
        return enhanceWithInventory ? mapper.beerToBeerDtoEnhanced(optional.orElseThrow(BeerNotFoundException::new)):mapper.beerToBeerDto(optional.orElseThrow(BeerNotFoundException::new));
    }

    @Cacheable(value = "beerListCache", condition = "#enhanceWithInventory==false")
    @Override
    public BeerDtoPage findAll(Integer pageSize, Integer pageNumber, String beerName, String beerStyle, Boolean enhanceWithInventory) {
        System.out.println("I WAS CALLED!");
        PageRequest pageRequest=null;
        if(!Objects.isNull(pageSize) && !Objects.isNull(pageNumber)){
            pageRequest = PageRequest.of(pageNumber, pageSize);
        } else if (!Objects.isNull(pageSize)){
            pageRequest = PageRequest.of(DEFAULT_PAGE_NUMBER, pageSize);
        } else if (!Objects.isNull(pageNumber)){
            pageRequest = PageRequest.of(pageNumber, DEFAULT_PAGE_SIZE);
        } else {
            pageRequest = PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);
        }
        Page<Beer> beers=null;

        if(!Objects.isNull(beerStyle) && !Objects.isNull(beerName)) {
            beers = beerRepository.findAllByBeerNameAndBeerStyle(beerName,beerStyle,pageRequest);
        } else if(Objects.isNull(beerName) && !Objects.isNull(beerStyle)){
            beers = beerRepository.findAllByBeerStyle(beerStyle,pageRequest);
        } else if (!Objects.isNull(beerName)) {
            beers = beerRepository.findAllByBeerName(beerName,pageRequest);
        } else {
            beers = beerRepository.findAll(pageRequest);
        }
        if(beers == null){
            return null;
        }
        List<BeerDto> collect=null;
        if(enhanceWithInventory){
           collect = beers.map(mapper::beerToBeerDtoEnhanced).stream().collect(Collectors.toList());
        }
        else {
           collect = beers.map(mapper::beerToBeerDto).stream().collect(Collectors.toList());
        }

        return new BeerDtoPage(collect,pageRequest,beers.getTotalElements());
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

    @Cacheable(value = "beerUpc", key = "#upc", condition = "#enhanceWithInventory==false")
    @Override
    public BeerDto findBeerByUpc(String upc, Boolean enhanceWithInventory) {
        Optional<Beer> optional = beerRepository.findBeerByUpc(upc);
        return enhanceWithInventory ? mapper.beerToBeerDtoEnhanced(optional.orElseThrow(BeerNotFoundException::new)):mapper.beerToBeerDto(optional.orElseThrow(BeerNotFoundException::new));
    }
}
