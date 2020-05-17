package com.rahul.msscbeerservice.services;

import com.rahul.msscbeerservice.web.model.BeerDto;
import com.rahul.msscbeerservice.web.model.BeerPagedList;
import com.rahul.msscbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.UUID;

/**
 * Created by Rahul on 5/16/20
 */
@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    @Override
    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand) {
        LinkedList<BeerDto> beers = new LinkedList<>();
        beers.add(BeerDto.builder().build());
        beers.add(BeerDto.builder().build());
        beers.add(BeerDto.builder().build());
        BeerPagedList pagedList = new BeerPagedList(beers);
        return pagedList;
    }

    @Override
    public BeerDto getById(UUID beerId, Boolean showInventoryOnHand) {
        return null;
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        return null;
    }

    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
        return null;
    }

    @Override
    public BeerDto getByUpc(String upc) {
        return null;
    }
}
