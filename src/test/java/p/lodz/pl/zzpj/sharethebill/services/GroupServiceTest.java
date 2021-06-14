package p.lodz.pl.zzpj.sharethebill.services;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import p.lodz.pl.zzpj.sharethebill.entities.BillGroup;
import p.lodz.pl.zzpj.sharethebill.entities.Purchase;
import p.lodz.pl.zzpj.sharethebill.entities.User;
import p.lodz.pl.zzpj.sharethebill.exceptions.NotFoundException;
import p.lodz.pl.zzpj.sharethebill.exceptions.UniqueConstaintException;
import p.lodz.pl.zzpj.sharethebill.model.UserRole;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource(
        locations = "classpath:application-integration.properties")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GroupServiceTest {

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    private User tester = new User("test", "test@mail.com", UserRole.CLIENT);
    private BillGroup billGroup = new BillGroup("testGroup", "EUR", true);
    private Purchase purchase = new Purchase("testing", 100.0, tester);

    @BeforeAll
    void setUp() throws UniqueConstaintException {
        tester = userService.add(tester);
    }

    @Test
    @Order(1)
    void createGroup() {
        assertThat(groupService.findAll().size()).isZero();
        billGroup = groupService.createGroup(billGroup);
        assertThat(groupService.findAll().size()).isEqualTo(1);
    }

    @Test
    @Order(2)
    void findByIdNotFound() {
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> groupService.findById(-1L));
        assertThat(exception).isInstanceOf(NotFoundException.GroupNotFoundException.class);
    }


    @Test
    @Order(2)
    void findById() {
        BillGroup byId = assertDoesNotThrow(()-> groupService.findById(billGroup.getId()));
        assertThat(byId.getName()).isEqualTo(billGroup.getName());
    }

    @Test
    @Order(3)
    void addUserNotFound() {
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> groupService.addUser(-1L, billGroup.getId()));
        assertThat(exception).isInstanceOf(NotFoundException.UserNotFoundException.class);
        exception = assertThrows(NotFoundException.class,
                () -> groupService.addUser(tester.getId(), -1L));
        assertThat(exception).isInstanceOf(NotFoundException.GroupNotFoundException.class);
    }

    @Test
    @Order(4)
    void addUser() {
        assertDoesNotThrow(() ->groupService.addUser(tester.getId(),billGroup.getId()));
    }
    @Test
    @Order(5)
    void addUserUnique() {
        UniqueConstaintException exception = assertThrows(UniqueConstaintException.class,
                () -> groupService.addUser(tester.getId(),billGroup.getId()));
        assertThat(exception).isInstanceOf(UniqueConstaintException.UserAlreadyInGroupException.class);
    }

    @Test
    @Order(6)
    void addPurchaseNotFound() {
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> groupService.addPurchase(-1L,billGroup.getId(),purchase));
        assertThat(exception).isInstanceOf(NotFoundException.UserNotFoundException.class);
        exception = assertThrows(NotFoundException.class,
                () -> groupService.addPurchase(tester.getId(),-1L,purchase));
        assertThat(exception).isInstanceOf(NotFoundException.GroupNotFoundException.class);
    }
    @Test
    @Order(7)
    void addPurchase() {
        assertDoesNotThrow(() -> groupService.addPurchase(tester.getId(), this.billGroup.getId(), purchase));
    }

    @Test
    @Order(8)
    void calculateTestNotFound() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> groupService.calculate(-1L));
        assertThat(exception).isInstanceOf(NotFoundException.GroupNotFoundException.class);
    }
    @Test
    @Order(9)
    void calculateTest() {
        assertDoesNotThrow(()->groupService.calculate(billGroup.getId()));
    }


    @Test
    @Order(10)
    void disableGroupNotFound() {
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> groupService.disableGroup(-1L));
        assertThat(exception).isInstanceOf(NotFoundException.GroupNotFoundException.class);
    }

    @Test
    @Order(11)
    void disableGroup() {
        assertDoesNotThrow(() -> groupService.disableGroup(billGroup.getId()));
    }

    @Test
    void changeCurrencyNotFound() {
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> groupService.changeCurrency(-1L,"USD"));
        assertThat(exception).isInstanceOf(NotFoundException.GroupNotFoundException.class);
    }

    @Test
    void changeCurrency() {
        assertDoesNotThrow(() ->groupService.changeCurrency(billGroup.getId(), "USD"));
    }

}