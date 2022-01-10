package lev.philippov.mssc_beer_service.bootstrap;

import lev.philippov.mssc_beer_service.domain.Beer;
import lev.philippov.mssc_beer_service.repository.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BeerPopulation implements CommandLineRunner {

    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234300019";
    public static final String BEER_3_UPC = "0083783375213";

    private final BeerRepository beerRepository;

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        if(beerRepository.count() == 0){
            this.bootstrap2();
        }
    }

    private void bootstrap() {
        if(beerRepository.count()<=2) {
            Beer beer1 = Beer.builder()
                    .beerName("Corona Extra")
                    .beerStyle("LAGER")
                    .upc(BEER_2_UPC)
                    .price(new BigDecimal("95.77"))
                    .quantityToBrew(200)
                    .build();
            Beer beer2 = Beer.builder()
                    .beerName("Franziskaner")
                    .beerStyle("WHEAT")
                    .upc(BEER_1_UPC)
                    .price(new BigDecimal("150.99"))
                    .quantityToBrew(250)
                    .build();

            Beer beer3 = Beer.builder()
                    .beerName("Spaten")
                    .beerStyle("LAGER")
                    .upc(BEER_3_UPC)
                    .price(new BigDecimal("179.935"))
                    .quantityToBrew(180)
                    .build();
            beerRepository.saveAll(List.of(beer1,beer2, beer3));
            System.out.println("Beer added: " + beerRepository.count());
        }
    }

    public void bootstrap2() {
        try {
            StringBuilder sb = new StringBuilder();
            Files.readAllLines(Path.of("src/main/resources/beer-population.sql")).forEach(sb::append);
            em.createNativeQuery(sb.toString()).executeUpdate();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
