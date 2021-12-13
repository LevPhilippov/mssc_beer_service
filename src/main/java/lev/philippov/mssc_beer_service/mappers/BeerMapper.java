package lev.philippov.mssc_beer_service.mappers;

import lev.philippov.mssc_beer_service.domain.Beer;
import lev.philippov.mssc_beer_service.web.models.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {

    BeerDto beerToBeerDto(Beer beer);

    Beer beerDtoToBeer(BeerDto beerDto);

}
