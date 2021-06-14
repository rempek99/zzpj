package p.lodz.pl.zzpj.sharethebill.endpoints;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.server.ResponseStatusException;
import p.lodz.pl.zzpj.sharethebill.dtos.BillGroupWithMembersAndPurchasesDto;
import p.lodz.pl.zzpj.sharethebill.dtos.PurchaseDto;
import p.lodz.pl.zzpj.sharethebill.dtos.UserDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestPropertySource(
        locations = "classpath:application-integration.properties")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BillGroupEndpointTest {

    @Autowired
    private BillGroupEndpoint billGroupEndpoint;

    @Autowired
    private UserEndpoint userEndpoint;

    private Long assignedId;

    private BillGroupWithMembersAndPurchasesDto billGroup =
            BillGroupWithMembersAndPurchasesDto
                    .builder()
                    .name("Test")
                    .isActive(true)
                    .currencyCode("EUR")
                    .build();

    private final UserDto users[] = {
            UserDto
                    .builder()
                    .email("test1@mail.com")
                    .login("tester1")
                    .role("CLIENT")
                    .build(),
            UserDto
                    .builder()
                    .email("test2@mail.com")
                    .login("tester2")
                    .role("CLIENT")
                    .build(),
            UserDto
                    .builder()
                    .email("test3@mail.com")
                    .login("tester3")
                    .role("CLIENT")
                    .build()
    };

    private final PurchaseDto purchases[] = {
            PurchaseDto
                    .builder()
                    .value(100.0)
                    .description("testing...")
                    .title("Test1")
                    .build(),
            PurchaseDto
                    .builder()
                    .value(200.0)
                    .description("testing...")
                    .title("Test2")
                    .build(),
            PurchaseDto
                    .builder()
                    .value(300.0)
                    .description("testing...")
                    .title("Test3")
                    .build()
    };

    @BeforeAll
    void setUp(){
        users[0] =userEndpoint.registerUser(users[0]);
        users[1] =userEndpoint.registerUser(users[1]);
        users[2] =userEndpoint.registerUser(users[2]);
    }
//
//    @Test
//    @Order(1)
//    void createGroupTest() {
//        //given
//        assertThat(billGroupEndpoint.getAll().size()).isZero();
//
//        //when
//        assignedId = billGroupEndpoint.createBillGroup(billGroup).getId();
//
//        //then
//        List<BillGroupWithMembersAndPurchasesDto> billGroupEndpointAll = billGroupEndpoint.getAll();
//        assertThat(billGroupEndpointAll.size()).isEqualTo(1);
//    }
//    @Test
//    @Order(2)
//    void addUserNotFoundTest() {
//        //given
//        assertThat(billGroupEndpoint.getAll().size()).isEqualTo(1);
//
//        // #1
//        //when
//        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
//                () -> billGroupEndpoint.addUser(-1L, assignedId));
//
//        //then
//        assertThat(exception.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
//
//        // #2
//        //when
//        exception = assertThrows(ResponseStatusException.class,
//                () -> billGroupEndpoint.addUser(users[0].getId(), -1L));
//
//        //then
//        assertThat(exception.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
//    }
//
//    @Test
//    @Order(3)
//    void addUserConflictTest() {
//        //given
//        assertThat(billGroupEndpoint.getAll().size()).isEqualTo(1);
//
//        //when
//        billGroupEndpoint.addUser(users[0].getId(),assignedId);
//        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
//                () -> billGroupEndpoint.addUser(users[0].getId(), assignedId));
//
//        //then
//        assertThat(exception.getStatus()).isEqualTo(HttpStatus.CONFLICT);
//        assertThat(billGroupEndpoint.getAll().get(0).getMembers().size()).isEqualTo(1);
//    }
//
//    @Test
//    @Order(4)
//    void addUserTest() {
//        //given
//        assertThat(billGroupEndpoint.getAll().size()).isEqualTo(1);
//        users[1] =userEndpoint.registerUser(users[1]);
//        users[2] =userEndpoint.registerUser(users[2]);
//
//        //when
//        billGroupEndpoint.addUser(users[1].getId(),assignedId);
//        billGroupEndpoint.addUser(users[2].getId(),assignedId);
//
//        //then
//        assertThat(billGroupEndpoint.getAll().get(0).getMembers().size()).isEqualTo(3);
//    }
//    @Test
//    @Order(5)
//    void addPurchaseNotFoundTest() {
//        //given
//        assertThat(billGroupEndpoint.getAll().size()).isEqualTo(1);
//        assertThat(billGroupEndpoint.getAll().get(0).getMembers().size()).isEqualTo(3);
//
//        //when
//        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
//                () -> billGroupEndpoint.addPurchase(-1L, assignedId, purchases[0]));
//
//        //then
//        assertThat(exception.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
//    }
    @Test
    @Order(6)
    void addPurchaseTest() {
        //given
        assignedId = billGroupEndpoint.createBillGroup(billGroup).getId();
//        assertThat(billGroupEndpoint.getAll().size()).isEqualTo(1);
        assertThat(billGroupEndpoint.getAll().get(0).getMembers().size()).isEqualTo(3);

        users[0] =userEndpoint.registerUser(users[0]);
        users[1] =userEndpoint.registerUser(users[1]);
        users[2] =userEndpoint.registerUser(users[2]);

        //when
        billGroupEndpoint.addPurchase(users[0].getId(), assignedId, purchases[0]);
        billGroupEndpoint.addPurchase(users[0].getId(), assignedId, purchases[1]);
        billGroupEndpoint.addPurchase(users[2].getId(), assignedId, purchases[2]);

        //then
        assertThat(billGroupEndpoint.getAll().get(0).getPurchases().size()).isEqualTo(3);
    }
}
