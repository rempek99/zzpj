package p.lodz.pl.zzpj.sharethebill.utils;

import p.lodz.pl.zzpj.sharethebill.dtos.BillGroupDto;
import p.lodz.pl.zzpj.sharethebill.entities.BillGroup;

import java.util.List;
import java.util.stream.Collectors;

public class BillGroupConverter {
    public static List<BillGroupDto> toDtoList(List<BillGroup> billGroupList) {
        return billGroupList
                .stream()
                .map(BillGroupConverter::toDto)
                .collect(Collectors.toList());
    }

    public static BillGroupDto toDto(BillGroup billGroup) {
        return BillGroupDto
                .builder()
                .id(billGroup.getId())
                .isActive(billGroup.getIsActive())
                .name(billGroup.getName())
                .members(UserConverter.toDtoList(billGroup.getMembers()))
                .purchases(PurchaseConverter.toDtoList(billGroup.getPurchases()))
                .build();
    }
}
