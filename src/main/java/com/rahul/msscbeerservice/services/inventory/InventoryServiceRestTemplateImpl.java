package com.rahul.msscbeerservice.services.inventory;

import com.rahul.msscbeerservice.web.model.BeerDto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

/**
 * Created by Rahul on 5/18/20
 */
@Component
@ConfigurationProperties(prefix = "sfg.brewery")
public class InventoryServiceRestTemplateImpl implements InventoryService {

    //Injected using lombok generated constructor
    private final RestTemplate restTemplate;

    // Configured by spring configuration annotation from application.properties
    private String inventoryServiceHost;
    private String inventoryPath;

    public InventoryServiceRestTemplateImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void setInventoryServiceHost(String inventoryServiceHost) {
        this.inventoryServiceHost = inventoryServiceHost;
    }

    public void setInventoryPath(String inventoryPath) {
        this.inventoryPath = inventoryPath;
    }

    @Override
    public Integer getOnHandInventory(UUID beerId) {
        String URL = inventoryServiceHost + inventoryPath;
        ResponseEntity<List<BeerDto>> beerListResponseEntity = restTemplate.exchange(URL, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<BeerDto>>() {},beerId); // TODO: don't understand this very well
        Integer quantitySum = beerListResponseEntity.getBody()
                .stream()
                .mapToInt(BeerDto::getQuantityOnHand)
                .sum();

        return quantitySum;
    }

}
