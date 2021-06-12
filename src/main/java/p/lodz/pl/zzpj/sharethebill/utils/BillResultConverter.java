package p.lodz.pl.zzpj.sharethebill.utils;

import p.lodz.pl.zzpj.sharethebill.dtos.BillResultDto;
import p.lodz.pl.zzpj.sharethebill.model.BillResult;

import java.util.List;
import java.util.stream.Collectors;

public class BillResultConverter {

    private BillResultConverter() {
    }

    public static List<BillResultDto> toDtoList(List<BillResult> billResultList) {
        return billResultList
                .stream()
                .map(BillResultConverter::toDto)
                .collect(Collectors.toList());
    }

    private static BillResultDto toDto(BillResult billResult) {
        return BillResultDto
                .builder()
                .user(UserConverter.toDto(billResult.getUser()))
                .charge(billResult.getCharge())
                .sponsor(UserConverter.toDto(billResult.getSponsor()))
                .build();

    }
}
