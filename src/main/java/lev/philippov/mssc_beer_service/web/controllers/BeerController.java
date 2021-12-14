package lev.philippov.mssc_beer_service.web.controllers;

import lev.philippov.mssc_beer_service.services.BeerService;
import lev.philippov.mssc_beer_service.web.models.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/beer")
@RestController
public class BeerController {

    private final BeerService beerService;

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") UUID beerId){
        return new ResponseEntity<BeerDto>(beerService.findBeerById(beerId), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createNewBeer(@Valid @RequestBody BeerDto beerDto){
        beerService.saveNewBeer(beerDto);
    }

    @PutMapping("/{beerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBeerById(@PathVariable("beerId") UUID beerId, @Valid @RequestBody BeerDto beerDto){
        beerService.updateBeer(beerId,beerDto);
    }
}
