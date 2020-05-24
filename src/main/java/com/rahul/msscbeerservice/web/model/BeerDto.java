package com.rahul.msscbeerservice.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Created by Rahul on 12/15/19
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BeerDto implements Serializable {

    static final long serialVersionUID = 2628614552275288476L;

    @Null
    private UUID id;

    @Null
    private Integer version;

    @NotBlank
    private String beerName;

    @NotNull
    private BeerStyleEnum beerStyle;

    @Positive
    @NotNull
    private Long upc;

    @Positive
    @NotNull
    private BigDecimal price;

    private Integer quantityOnHand;

    @Null
    private OffsetDateTime createDate;

    @Null
    private OffsetDateTime lastModifiedDate;
}
