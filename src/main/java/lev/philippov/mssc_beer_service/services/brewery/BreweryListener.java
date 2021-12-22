package lev.philippov.mssc_beer_service.services.brewery;

import lev.philippov.mssc_beer_service.config.JmsConfig;
import lev.philippov.mssc_beer_service.domain.Beer;
import guru.sfg.brewery.model.events.BrewBeerEvent;
import guru.sfg.brewery.model.events.NewInventoryEvent;
import lev.philippov.mssc_beer_service.repository.BeerRepository;
import guru.sfg.brewery.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BreweryListener {

    private final BeerRepository repository;
    private final JmsTemplate jmsTemplate;

    @Transactional
    @JmsListener(destination=JmsConfig.BREWERY_BEER_QUEUE)
    public void listen(@Payload BrewBeerEvent brewBeerEvent){
        BeerDto beerDto = brewBeerEvent.getBeerDto();
        Beer beer = repository.getById(beerDto.getId());
        beerDto.setQuantityOnHand(beer.getQuantityToBrew());
        log.debug("Brewed : {}, QON is: {}",beer.getMinOnHand(), beerDto.getQuantityOnHand());
        jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE, new NewInventoryEvent(beerDto));
    }
}
