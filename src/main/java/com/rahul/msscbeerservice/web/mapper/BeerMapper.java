package com.rahul.msscbeerservice.web.mapper;

import com.rahul.msscbeerservice.domain.Beer;
import com.rahul.msscbeerservice.web.model.BeerDto;
import org.mapstruct.Mapper;

/**
 * Created by Rahul on 5/14/20
 */
@Mapper (uses = DateMapper.class)
public interface BeerMapper {

    BeerDto BeerToBeerDto(Beer beer);

    Beer BeerDtoToBeer(BeerDto beerDto);
}
