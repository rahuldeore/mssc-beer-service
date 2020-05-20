package com.rahul.msscbeerservice.services;

import com.rahul.msscbeerservice.domain.Beer;
import com.rahul.msscbeerservice.repositories.BeerRepository;
import com.rahul.msscbeerservice.web.mapper.BeerMapper;
import com.rahul.msscbeerservice.web.model.BeerDto;
import com.rahul.msscbeerservice.web.model.BeerPagedList;
import com.rahul.msscbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Rahul on 5/16/20
 */
@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;

    private final BeerMapper beerMapper;

    @Override
    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand) {
        List<BeerDto> beerDtos = new LinkedList<>();

        List<Beer> beers = beerRepository.findAll();
        Page<Beer> beerPage;

        BeerPagedList beerPagedList;

        if (!StringUtils.isEmpty(beerName) && beerStyle != null && !StringUtils.isEmpty(beerStyle.toString())) {
            //search both
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle.toString(), pageRequest);
        } else if (!StringUtils.isEmpty(beerName)) {
            //search beer_service name
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        } else if (StringUtils.isEmpty(beerName) && beerStyle != null && !StringUtils.isEmpty(beerStyle.toString())) {
            //search beer_service style
            beerPage = beerRepository.findAllByBeerStyle(beerStyle.toString(), pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        beerPagedList = new BeerPagedList(beerPage
                .getContent()
                .stream()
                .map(showInventoryOnHand == true ?
                        beerMapper::beerToBeerDtoWithInventory :
                        beerMapper::beerToBeerDto)
                .collect(Collectors.toList()),
                PageRequest
                        .of(beerPage.getPageable().getPageNumber(),
                                beerPage.getPageable().getPageSize()),
                beerPage.getTotalElements());

        return beerPagedList;
    }

    @Override
    public BeerDto getById(UUID beerId, Boolean showInventoryOnHand) {
        Optional<Beer> beerOptional = beerRepository.findById(beerId);
        if (beerOptional.isEmpty()) {
            return null;
        } else {
            BeerDto beerDto = showInventoryOnHand == true ? beerMapper.beerToBeerDtoWithInventory(beerOptional.get()) :
                    beerMapper.beerToBeerDto(beerOptional.get());
            return beerDto;
        }
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        Beer newBeer = beerMapper.beerDtoToBeer(beerDto);
        beerRepository.save(newBeer);
        return beerDto;
    }

    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
        Optional<Beer> beerOptional = beerRepository.findById(beerId);
        Beer beer;
        if (beerOptional.isPresent()) {
            beer = beerOptional.get();
            beer.setBeerName(beerDto.getBeerName());
            beer.setBeerStyle(beerDto.getBeerStyle().toString());
            beer.setPrice(beerDto.getPrice());
            beer.setMinOnHand(beerDto.getQuantityOnHand());
            beerRepository.save(beer);
            return beerMapper.beerToBeerDto(beer);
        } else {
            return null;
        }
    }

    @Override
    public BeerDto getByUpc(String upc) {
        Beer beer = beerRepository.findByUpc(upc);
        if (beer != null) {
            return beerMapper.beerToBeerDto(beer);
        } else {
            return null;
        }
    }

    @Override
    public Boolean deleteBeer(UUID beerId) {
        Optional<Beer> beerOptional = beerRepository.findById(beerId);
        if (beerOptional.isPresent()) {
            beerRepository.deleteById(beerId);
            return true;
        } else {
            return false;
        }
    }
}
