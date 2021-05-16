package p.lodz.pl.zzpj.sharethebill.utils;

import p.lodz.pl.zzpj.sharethebill.dtos.BillGroupWithMembersAndPurchasesDto;
import p.lodz.pl.zzpj.sharethebill.dtos.BillGroupWithPurchasesDto;
import p.lodz.pl.zzpj.sharethebill.entities.BillGroup;

import java.util.List;
import java.util.stream.Collectors;

public class BillGroupConverter {

    private BillGroupConverter() {
    }


    public static List<BillGroupWithMembersAndPurchasesDto> toDtoWithMembersAndPurchasesList(List<BillGroup> billGroupList) {
        return billGroupList
                .stream()
                .map(BillGroupConverter::toDtoWithMembersAndPurchases)
                .collect(Collectors.toList());
    }

    public static BillGroupWithMembersAndPurchasesDto toDtoWithMembersAndPurchases(BillGroup billGroup) {
        return BillGroupWithMembersAndPurchasesDto
                .builder()
                .id(billGroup.getId())
                .isActive(billGroup.getIsActive())
                .name(billGroup.getName())
                .members(UserConverter.toDtoList(billGroup.getMembers()))
                .purchases(PurchaseConverter.toDtoWithUserList(billGroup.getPurchases()))
                .build();
    }

    public static BillGroup toEntity(BillGroupWithMembersAndPurchasesDto group) {
        return new BillGroup(group.getId(), group.getName(), group.getIsActive());

    }

    public static BillGroupWithPurchasesDto toDtoWithPurchases(BillGroup billGroup) {
        return BillGroupWithPurchasesDto
                .builder()
                .id(billGroup.getId())
                .isActive(billGroup.getIsActive())
                .name(billGroup.getName())
                .purchases(PurchaseConverter.toDtoWithUserList(billGroup.getPurchases()))
                .build();
    }
}
