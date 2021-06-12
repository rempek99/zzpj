package p.lodz.pl.zzpj.sharethebill.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import p.lodz.pl.zzpj.sharethebill.dtos.CurrencyDto;
import p.lodz.pl.zzpj.sharethebill.entities.BillGroup;
import p.lodz.pl.zzpj.sharethebill.entities.Purchase;

import java.time.LocalDate;
import java.util.List;

@Service
public class CurrencyServiceProxy implements CurrencyPuller {

    @Autowired
    CurrencyService currencyService;

    private CurrencyDto cachedRates;

    @Override
    public CurrencyDto pullCurrency() {
        if(null == cachedRates || cachedRates.getDate().isBefore(LocalDate.now().minusDays(1)))
            cachedRates = currencyService.pullCurrency();
        return cachedRates;
    }

    @Override
    public Double getCurrencyByCode(String currencyCode, CurrencyDto actualRates) {
        return currencyService.getCurrencyByCode(currencyCode,pullCurrency());
    }

    public List<Purchase> convertIntoGroupCurrency(BillGroup group) {
        List<Purchase> purchaseList = group.getPurchases();
        for (Purchase purchase : purchaseList) {
            purchase.setValue( purchase.getValue() * getCurrencyByCode(group.getCurrencyCode(), pullCurrency()));
        }
        return purchaseList;
    }
}
