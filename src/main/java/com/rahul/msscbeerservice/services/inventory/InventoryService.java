package com.rahul.msscbeerservice.services.inventory;

import java.util.UUID;

/**
 * Created by Rahul on 5/18/20
 */
public interface InventoryService {

    Integer getOnHandInventory(UUID beerId);
    
}
