package lev.philippov.mssc_beer_service.events;

import lev.philippov.mssc_beer_service.web.models.BeerDto;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class BeerEvent implements Serializable {

    static final long serialVersionUID = 6389927906783071796L;

    private final BeerDto beerDto;


}
