package lev.philippov.mssc_beer_service.repository;

import lev.philippov.mssc_beer_service.domain.Beer;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID> {
}
