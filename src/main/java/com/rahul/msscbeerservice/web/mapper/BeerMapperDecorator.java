package com.rahul.msscbeerservice.web.mapper;

import com.rahul.msscbeerservice.domain.Beer;
import com.rahul.msscbeerservice.services.inventory.InventoryService;
import com.rahul.msscbeerservice.web.model.BeerDto;
import org.springframework.stereotype.Component;

/**
 * Created by Rahul on 5/18/20
 */
@Component
public abstract class BeerMapperDecorator implements BeerMapper {

    private InventoryService inventoryService;

    private BeerMapper beerMapper;

    //@Autowired
    public void setBeerInventoryService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    //@Autowired
    public void setMapper(BeerMapper mapper) {
        this.beerMapper = mapper;
    }

    @Override
    public BeerDto beerToBeerDto(Beer beer) {
        return beerMapper.beerToBeerDto(beer);
    }

    @Override
    public Beer beerDtoToBeer(BeerDto beerDto) {
        return beerMapper.beerDtoToBeer(beerDto);
    }

    @Override
    public BeerDto beerToBeerDtoWithInventory(Beer beer) {
        BeerDto beerDto = beerMapper.beerToBeerDto(beer);
        // Decorate the BeerDto with quantityOnHand from inventory web service
        beerDto.setQuantityOnHand(inventoryService.getOnHandInventory(beer.getId()));
        return beerDto;
    }
}
