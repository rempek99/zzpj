package p.lodz.pl.zzpj.sharethebill.utils;

import p.lodz.pl.zzpj.sharethebill.dtos.PurchaseWithUserDto;
import p.lodz.pl.zzpj.sharethebill.entities.Purchase;

import java.util.List;
import java.util.stream.Collectors;

public class PurchaseConverter {


    public static List<PurchaseWithUserDto> toDtoList(List<Purchase> purchaseList) {
        return purchaseList
                .stream()
                .map(PurchaseConverter::toDto)
                .collect(Collectors.toList());
    }

    public static PurchaseWithUserDto toDto(Purchase purchase) {
        return PurchaseWithUserDto
                .builder()
                .id(purchase.getId())
                .title(purchase.getTitle())
                .description(purchase.getDescription())
                .value(purchase.getValue())
                .sponsor(UserConverter.toDto(purchase.getSponsor()))
                .build();
    }
}
