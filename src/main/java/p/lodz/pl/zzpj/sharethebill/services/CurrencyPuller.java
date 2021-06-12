package p.lodz.pl.zzpj.sharethebill.services;

import p.lodz.pl.zzpj.sharethebill.dtos.CurrencyDto;
import p.lodz.pl.zzpj.sharethebill.entities.BillGroup;
import p.lodz.pl.zzpj.sharethebill.entities.Purchase;

import java.util.List;

public interface CurrencyPuller {
    CurrencyDto pullCurrency();
    Double getCurrencyByCode(String currencyCode, CurrencyDto actualRates);
//    public List<Purchase> convertIntoGroupCurrency(BillGroup group, CurrencyDto actualRates);
}
