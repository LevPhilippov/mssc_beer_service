package lev.philippov.mssc_beer_service.mappers;

import lev.philippov.mssc_beer_service.domain.Beer;
import lev.philippov.mssc_beer_service.web.models.BeerDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {

    BeerDto beerToBeerDto(Beer beer);

    BeerDto beerToBeerDtoEnhanced(Beer beer);

    Beer beerDtoToBeer(BeerDto beerDto);

}
