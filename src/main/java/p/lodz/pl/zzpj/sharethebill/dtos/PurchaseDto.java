package p.lodz.pl.zzpj.sharethebill.dtos;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PurchaseDto {

    private final Long id;

    private final String title;

    private final Double value;

    private final String description;

    private final List<UserDto> participants;
}
