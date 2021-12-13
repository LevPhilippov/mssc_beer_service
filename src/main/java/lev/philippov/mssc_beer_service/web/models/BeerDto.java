package lev.philippov.mssc_beer_service.web.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BeerDto {
    @Null
    private UUID id;
    @NotNull
    private String beerName;
    @NotNull
    private BeerStyleEnum beerStyle;
    @NotNull
    @Positive
    private Long upc;

    private Integer quantityOnHands;
    @NotNull
    @Positive
    private BigDecimal price;
    @Null
    private Integer version;
    @Null
    private OffsetDateTime createdDate;
    @Null
    private OffsetDateTime lastModifiedDate;
}
