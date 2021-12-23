package lev.philippov.mssc_beer_service.services.order.listeners;

import guru.sfg.brewery.model.BeerOrderDto;
import guru.sfg.brewery.model.BeerOrderLineDto;
import lev.philippov.mssc_beer_service.repository.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BeerOrderValidator {

    private final BeerRepository beerRepository;

    @Transactional
    public Boolean validate(BeerOrderDto dto){
        Boolean isValid=true;
        Set<String> upcs = dto.getBeerOrderLines().stream().map(BeerOrderLineDto::getUpc).collect(Collectors.toSet());
        //ineffective operations
        for (String upc : upcs) {
            isValid &= beerRepository.existsByUpc(upc);
        }
        return isValid;
    }
}
