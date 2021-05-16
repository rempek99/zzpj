package p.lodz.pl.zzpj.sharethebill.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;
import p.lodz.pl.zzpj.sharethebill.utils.serializers.CurrencyDeserializer;
import java.time.LocalDate;
import java.util.Map;


//http://data.fixer.io/api/latest?access_key=652eecd3a28ccb3fd4d23bcfa5823c35&symbols=PLN
//{
//    "success": true,
//    "timestamp": 1621184764,
//    "base": "EUR",
//    "date": "2021-05-16",
//    "rates": {
//        "PLN": 4.528903
//    }
//}

@Getter
@Builder
public class CurrencyDto {

    private Boolean success;
    private Long timestamp;
    private String base;
    private LocalDate date;
    @JsonDeserialize(using = CurrencyDeserializer.class)
    private Map<String, Float> rates;
}

