package lev.philippov.mssc_beer_service.web.controllers;

import lev.philippov.mssc_beer_service.web.models.BeerDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping(path = "/api/v1/beer")
@RestController
public class BeerController {

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") UUID beerId){
            return new ResponseEntity(new BeerDto(), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createNewBeer(@RequestBody BeerDto beerDto){

    }

    @PutMapping("/{beerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBeerById(@PathVariable("beerId") UUID beerId, @RequestBody BeerDto beerDto){

    }
}
