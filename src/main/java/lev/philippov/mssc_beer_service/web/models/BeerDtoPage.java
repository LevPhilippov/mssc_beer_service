package lev.philippov.mssc_beer_service.web.models;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class BeerDtoPage extends PageImpl<BeerDto> {

    public BeerDtoPage(List<BeerDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public BeerDtoPage(List<BeerDto> content) {
        super(content);
    }
}
