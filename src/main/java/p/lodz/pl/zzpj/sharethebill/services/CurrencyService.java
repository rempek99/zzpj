package p.lodz.pl.zzpj.sharethebill.services;

import p.lodz.pl.zzpj.sharethebill.dtos.CurrencyDto;
import p.lodz.pl.zzpj.sharethebill.entities.BillGroup;
import p.lodz.pl.zzpj.sharethebill.entities.Purchase;

import java.math.BigDecimal;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public  class CurrencyService {

    public static Double getCurrencyByCode(String currencyCode){
        CurrencyDto data = given()
                .when()
                .pathParam("currencyCode", currencyCode)
                .get("http://data.fixer.io/api/latest?access_key=652eecd3a28ccb3fd4d23bcfa5823c35&symbols={currencyCode}")
                .then()
                .statusCode(200)
                .contentType(JSON)
                .extract()
                .body()
                .as(CurrencyDto.class);

        Float result = data.getRates().get(currencyCode);
        return new BigDecimal(String.valueOf(result)).doubleValue();
    }


    public static List<Purchase> convertIntoGroupCurrency(BillGroup group) {

        List<Purchase> purchaseList = group.getPurchases();
        for (Purchase purchase : purchaseList) {
           purchase.setValue( purchase.getValue() * CurrencyService.getCurrencyByCode(group.getCurrencyCode()));
        }
            return purchaseList;
    }
}
