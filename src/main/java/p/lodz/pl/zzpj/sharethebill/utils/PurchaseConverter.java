package p.lodz.pl.zzpj.sharethebill.utils;

import p.lodz.pl.zzpj.sharethebill.dtos.PurchaseDto;
import p.lodz.pl.zzpj.sharethebill.dtos.PurchaseWithUserDto;
import p.lodz.pl.zzpj.sharethebill.entities.Purchase;

import java.util.List;
import java.util.stream.Collectors;

public class PurchaseConverter {

    private PurchaseConverter() {
    }

    public static List<PurchaseWithUserDto> toDtoWithUserList(List<Purchase> purchaseList) {
        return purchaseList
                .stream()
                .map(PurchaseConverter::toDtoWithUser)
                .collect(Collectors.toList());
    }

    public static PurchaseWithUserDto toDtoWithUser(Purchase purchase) {
        return PurchaseWithUserDto
                .builder()
                .id(purchase.getId())
                .title(purchase.getTitle())
                .description(purchase.getDescription())
                .value(purchase.getValue())
                .sponsor(UserConverter.toDto(purchase.getSponsor()))
                .build();
    }

    public static Purchase toEntity(PurchaseDto purchase) {
        return new Purchase(purchase.getTitle(),
                purchase.getValue(),
                purchase.getDescription());
    }
}
