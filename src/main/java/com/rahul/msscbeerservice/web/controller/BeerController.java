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

    @GetMapping (produces = "application/json")
    public ResponseEntity<BeerPagedList> getAllBeer(@RequestParam (value = "pageNumber", required = false)
                                                                Integer pageNumber,
                                                    @RequestParam (value = "pageSize", required = false)
                                                            Integer pageSize,
                                                    @RequestParam (value = "beerName", required = false)
                                                                String beerName,
                                                    @RequestParam (value = "beerStyle", required = false)
                                                                BeerStyleEnum beerStyle,
                                                    @RequestParam (value = "showInventoryOnHand", required = false)
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
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") UUID beerId) {
        return new ResponseEntity<>(BeerDto.builder().build(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveNewBeer(@RequestBody BeerDto beerDto) {
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity updateBeer(@PathVariable("beerId") UUID beerId, @RequestBody BeerDto beerDto) {
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
