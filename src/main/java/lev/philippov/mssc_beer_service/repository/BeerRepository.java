package lev.philippov.mssc_beer_service.repository;

import lev.philippov.mssc_beer_service.domain.Beer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {

    Optional<Beer> findBeerByUpc(String upc);

    Page<Beer> findAllByBeerName(String beerName, Pageable pageable);

    Page<Beer> findAllByBeerNameAndBeerStyle(String beerName, String beerStyle, Pageable pageable);

    Page<Beer> findAllByBeerStyle(String beerStyle, Pageable pageable);

    Page<Beer> findAll(Pageable pageable);

    Boolean existsByUpc(String upc);

}
