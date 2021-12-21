package lev.philippov.mssc_beer_service.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lev.philippov.mssc_beer_service.config.JmsConfig;
import lev.philippov.mssc_beer_service.domain.Beer;
import lev.philippov.mssc_beer_service.events.BrewBeerEvent;
import lev.philippov.mssc_beer_service.mappers.BeerMapper;
import lev.philippov.mssc_beer_service.repository.BeerRepository;
import lev.philippov.mssc_beer_service.services.inventory.BeerInventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BreweryServiceImpl implements BreweryService {

    private final BeerRepository beerRepository;
    private final BeerInventoryService service;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper beerMapper;

    @Override
    @Scheduled(fixedRate = 5000)  //every 5 seconds
    public void checkForLowInventory() {
        List<Beer> beers = beerRepository.findAll();
        for (Beer beer : beers) {
            Integer quantityOnHand = service.getQuantityOnHand(beer.getId());
            if(beer.getMinOnHand()<=quantityOnHand){
                jmsTemplate.convertAndSend(JmsConfig.BREWERY_BEER_QUEUE, BrewBeerEvent.builder().beerDto(beerMapper.beerToBeerDto(beer)).build());
            }
        }
    }
}
