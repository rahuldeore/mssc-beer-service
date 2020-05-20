package com.rahul.msscbeerservice.web.mapper;

import com.rahul.msscbeerservice.domain.Beer;
import com.rahul.msscbeerservice.web.model.BeerDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * Created by Rahul on 5/14/20
 */
@Mapper (uses = DateMapper.class)
@DecoratedWith(BeerMapperDecorator.class)
@Component
public interface BeerMapper {

    BeerDto beerToBeerDto(Beer beer);

    Beer beerDtoToBeer(BeerDto beerDto);

    BeerDto beerToBeerDtoWithInventory(Beer beer);
}
