package lev.philippov.mssc_beer_service.bootstrap;

import lev.philippov.mssc_beer_service.domain.Beer;
import lev.philippov.mssc_beer_service.repository.BeerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class BeerPopulation implements CommandLineRunner {

    private final BeerRepository beerRepository;

    public BeerPopulation(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        this.bootstrap();
    }

    private void bootstrap() {
        if(beerRepository.count()==0) {
            Beer beer1 = Beer.builder()
                    .beerName("Corona Extra")
                    .beerStyle("LAGER")
                    .upc(1234567890L)
                    .price(new BigDecimal("95.77"))
                    .quantityOnHands(6)
                    .build();
            Beer beer2 = Beer.builder()
                    .beerName("Franziskaner")
                    .beerStyle("WHEAT")
                    .upc(1234567890L)
                    .price(new BigDecimal("150.99"))
                    .quantityOnHands(4)
                    .build();
            beerRepository.saveAll(List.of(beer1,beer2));
            System.out.println("Beer added: " + beerRepository.count());
        }
    }

}
