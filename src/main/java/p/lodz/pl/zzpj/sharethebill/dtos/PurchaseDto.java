package p.lodz.pl.zzpj.sharethebill.dtos;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
public class PurchaseDto {

    private final Long id;

    @NotNull
    private final String title;

    @NotNull
    private final Double value;

    private final String description;
}
