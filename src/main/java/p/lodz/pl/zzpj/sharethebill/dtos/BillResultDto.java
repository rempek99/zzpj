package p.lodz.pl.zzpj.sharethebill.dtos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BillResultDto {

    private UserDto user;
    private Double charge;

}
