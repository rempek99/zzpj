package p.lodz.pl.zzpj.sharethebill.endpoints;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.junit.Before;
import p.lodz.pl.zzpj.sharethebill.repositories.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@TestPropertySource(
        locations = "classpath:application-integration.properties")
@SpringBootTest
public class UserEndpointTest {

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() {
        userRepository.deleteAll();

        assertThat(userRepository.count()).isEqualTo(0);
    }

    @Test
    public void shouldPassUsingH2() {
        //given

        //when

        //then
        assertThat(true);
    }

}
