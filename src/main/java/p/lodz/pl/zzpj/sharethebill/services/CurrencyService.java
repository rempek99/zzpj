package p.lodz.pl.zzpj.sharethebill.services;

import org.springframework.stereotype.Component;
import p.lodz.pl.zzpj.sharethebill.dtos.CurrencyDto;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

@Component
public class CurrencyService implements CurrencyPuller{

    public Double getCurrencyByCode(String currencyCode, CurrencyDto actualRates){
        Float result = actualRates.getRates().get(currencyCode);
        return new BigDecimal(String.valueOf(result)).doubleValue();
    }

    @Override
    public CurrencyDto pullCurrency() {
        return given()
                .when()
                .get("http://data.fixer.io/api/latest?access_key=652eecd3a28ccb3fd4d23bcfa5823c35")
                .then()
                .statusCode(200)
                .contentType(JSON)
                .extract()
                .body()
                .as(CurrencyDto.class);
    }
}
