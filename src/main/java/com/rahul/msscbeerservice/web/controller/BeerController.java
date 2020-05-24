package com.rahul.msscbeerservice.web.controller;

import com.rahul.msscbeerservice.services.BeerService;
import com.rahul.msscbeerservice.web.model.BeerDto;
import com.rahul.msscbeerservice.web.model.BeerPagedList;
import com.rahul.msscbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Created by Rahul on 12/15/19
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/beer/api/v1/beer")
public class BeerController {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 10;

    private final BeerService beerService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<BeerPagedList> getAllBeer(@RequestParam(value = "pageNumber", required = false)
                                                                Integer pageNumber,
                                                    @RequestParam(value = "pageSize", required = false)
                                                            Integer pageSize,
                                                    @RequestParam(value = "beerName", required = false)
                                                                String beerName,
                                                    @RequestParam(value = "beerStyle", required = false)
                                                                BeerStyleEnum beerStyle,
                                                    @RequestParam(value = "showInventoryOnHand", required = false)
                                                                Boolean showInventoryOnHand) {

        if (showInventoryOnHand == null) {
            showInventoryOnHand = false;
        }
        if (pageNumber == null || pageNumber < 0) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }
        if (pageSize == null || pageNumber < 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        BeerPagedList beerPagedList = beerService.listBeers(beerName, beerStyle, PageRequest.of(pageNumber, pageSize),
                showInventoryOnHand);

        return new ResponseEntity<BeerPagedList>(beerPagedList, HttpStatus.OK);
    }

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") UUID beerId,
                                               @RequestParam(value = "showInventoryOnHand", required = false)
                                                       Boolean showInventoryOnHand) {
        if (showInventoryOnHand == null) {
            showInventoryOnHand = false;
        }
        BeerDto beer = beerService.getById(beerId, showInventoryOnHand);
        if (beer == null) {
            return new ResponseEntity<>(beer, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(beer, HttpStatus.OK);
    }

    @GetMapping (path = "/upc/{upc}")
    public ResponseEntity<BeerDto> getBeerByUPC(@PathVariable (name = "upc") String upc,
                                @RequestParam (name = "showInventoryOnHand", required = false)
                                        Boolean showInventoryOnHand) {
        BeerDto beerDto = beerService.getByUpc(upc, showInventoryOnHand);
        return new ResponseEntity<BeerDto>(beerDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveNewBeer(@RequestBody BeerDto beerDto) {
        if (beerDto != null) {
            beerService.saveNewBeer(beerDto);
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{beerId}")
    public ResponseEntity<BeerDto> updateBeer(@PathVariable("beerId") UUID beerId, @RequestBody BeerDto beerDto) {
        BeerDto updateBeer = beerService.updateBeer(beerId, beerDto);
        if (updateBeer != null) {
            return new ResponseEntity(updateBeer, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{beerId}")
    public ResponseEntity deleteBeer(@PathVariable UUID beerId) {
        if (beerService.deleteBeer(beerId)) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
