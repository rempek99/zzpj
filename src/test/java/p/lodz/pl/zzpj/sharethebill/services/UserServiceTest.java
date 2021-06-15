package p.lodz.pl.zzpj.sharethebill.services;


import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import p.lodz.pl.zzpj.sharethebill.entities.User;
import p.lodz.pl.zzpj.sharethebill.exceptions.NotFoundException;
import p.lodz.pl.zzpj.sharethebill.exceptions.UniqueConstaintException;
import p.lodz.pl.zzpj.sharethebill.model.UserRole;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestPropertySource(
        locations = "classpath:application-integration.properties")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class UserServiceTest {

    @Autowired
    private UserService userService;

    private User tester = new User("test", "test@mail.com", UserRole.CLIENT);
    private User tester2 = new User("another", "another@mail.com", UserRole.CLIENT);

    @Test
    @Order(1)
    void add() {
        assertThat(userService.findAll().size()).isZero();
        tester = assertDoesNotThrow(() -> userService.add(tester));
        assertThat(userService.findAll().size()).isEqualTo(1);
    }

    @Test
    @Order(2)
    void addConflicted() {
        assertThat(userService.findAll().size()).isEqualTo(1);
        assertThrows(UniqueConstaintException.class,
                () -> userService.add(tester));
        assertThat(userService.findAll().size()).isEqualTo(1);
    }


    @Test
    @Order(3)
    void findNotFound() {
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> userService.find(-1L));
        assertThat(exception).isInstanceOf(NotFoundException.UserNotFoundException.class);
    }

    @Test
    @Order(3)
    void find() {
        assertDoesNotThrow(() -> userService.find(tester.getId()));
    }

    @Test
    @Order(4)
    void updateNotFound() {
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> userService.update(-1L, tester2));
        assertThat(exception).isInstanceOf(NotFoundException.UserNotFoundException.class);
    }

    @Test
    @Order(5)
    void update() {
        assertDoesNotThrow(() -> userService.update(tester.getId(), tester2));
    }

    @Test
    @Order(6)
    void deleteNotFound() {
        assertThat(userService.findAll().size()).isEqualTo(1);
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> userService.delete(-1L));
        assertThat(userService.findAll().size()).isEqualTo(1);
    }

    @Test
    @Order(7)
    void delete() {
        assertThat(userService.findAll().size()).isEqualTo(1);
        assertDoesNotThrow(() -> userService.delete(tester.getId()));
        assertThat(userService.findAll().size()).isZero();
    }

    @AfterAll
    void cleanup(){
        assertThat(userService.findAll().size()).isZero();
    }


}