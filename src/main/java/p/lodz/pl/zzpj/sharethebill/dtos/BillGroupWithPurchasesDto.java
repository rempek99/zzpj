package p.lodz.pl.zzpj.sharethebill.dtos;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(toBuilder = true)
public class BillGroupWithPurchasesDto {
    private final Long id;
    private final String name;
    private final Boolean isActive;
    private String currencyCode = "EUR";
    private final List<PurchaseWithUserDto> purchases;
}
