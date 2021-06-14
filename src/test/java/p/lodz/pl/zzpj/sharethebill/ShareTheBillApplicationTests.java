package p.lodz.pl.zzpj.sharethebill;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import p.lodz.pl.zzpj.sharethebill.services.CurrencyService;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-integration.properties")
class ShareTheBillApplicationTests {

    @Test
    void currencyTest() {
        assertEquals(1,CurrencyService.getCurrencyByCode("EUR"));

    }

}
