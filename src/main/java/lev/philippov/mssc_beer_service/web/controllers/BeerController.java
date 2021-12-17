package lev.philippov.mssc_beer_service.web.controllers;

import lev.philippov.mssc_beer_service.services.BeerService;
import lev.philippov.mssc_beer_service.web.models.BeerDto;
import lev.philippov.mssc_beer_service.web.models.BeerDtoPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping(path = "/api/v1/beer")
@RestController
public class BeerController {

    private final BeerService beerService;

    @Autowired
    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }


    @GetMapping(produces = "application/json")
    public BeerDtoPage findPageableBeers(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                         @RequestParam(value = "pageNUmber",required = false) Integer pageNumber,
                                         @RequestParam(value = "beerName", required = false) String beerName,
                                         @RequestParam(value = "beerStyle", required = false) String beerStyle,
                                         @RequestParam(value = "enhanceWithInventory", required = false, defaultValue = "false") Boolean enhanceWithInventory ){
        return beerService.findAll(pageSize,pageNumber,beerName,beerStyle, enhanceWithInventory);
    }

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") UUID beerId){
        BeerDto beerById = beerService.findBeerById(beerId);
        return new ResponseEntity<BeerDto>(beerById, HttpStatus.OK);
    }

    @GetMapping("/upc/{upc}")
    public ResponseEntity<BeerDto> getBeerByUpc(@PathVariable("upc") String upc){
        BeerDto beerById = beerService.findBeerByUpc(upc);
        return new ResponseEntity<BeerDto>(beerById, HttpStatus.OK);
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
