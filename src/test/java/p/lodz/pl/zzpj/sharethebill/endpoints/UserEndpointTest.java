package p.lodz.pl.zzpj.sharethebill.endpoints;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.server.ResponseStatusException;
import p.lodz.pl.zzpj.sharethebill.dtos.UserDto;
import p.lodz.pl.zzpj.sharethebill.exceptions.UniqueConstaintException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestPropertySource(
        locations = "classpath:application-integration.properties")
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class UserEndpointTest {

    @Autowired
    private UserEndpoint userEndpoint;

    private final UserDto user = UserDto
            .builder()
            .email("test@mail.com")
            .login("tester")
            .role("CLIENT")
            .build();

    private long assignedId = 1;


    @BeforeAll
    public static void setUp() {

    }

    @Test
    @Order(1)
    void createUserTest() {
        //given
        assertThat(userEndpoint.getAllUsers().size()).isZero();

        //when
        assignedId = userEndpoint.registerUser(user).getId();

        //then
        assertThat(userEndpoint.getAllUsers().size()).isEqualTo(1);
    }

    @Test
    @Order(2)
    void createUserConflictTest() {
        //given
        assertThat(userEndpoint.getAllUsers().size()).isEqualTo(1);

        //when
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> userEndpoint.registerUser(user));

        //then
        assertThat(exception.getStatus()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    @Order(3)
    void getUserTest() {
        //given
        assertThat(userEndpoint.getAllUsers().size()).isEqualTo(1);

        //when
        UserDto foundUser = userEndpoint.getUser(assignedId);

        //then
        assertThat(foundUser.getLogin()).isEqualTo(user.getLogin());
    }

    @Test
    @Order(4)
    void getUserFailedTest() {
        //given
        assertThat(userEndpoint.getAllUsers().size()).isEqualTo(1);

        //when
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> userEndpoint.getUser(-1L));

        //then
        assertThat(exception.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Order(5)
    void updateUserNotFoundTest() {
        //given
        assertThat(userEndpoint.getAllUsers().size()).isEqualTo(1);
        UserDto newUser = UserDto
                .builder()
                .email("test@mail.com")
                .login("tester")
                .role("CLIENT")
                .build();

        //when

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () ->userEndpoint.updateUser(-1L,newUser));

        //then
        assertThat(exception.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Order(6)
    void updateUserConflictTest() {
        //given
        UserDto secondUser = UserDto
                .builder()
                .email("test2@mail.com")
                .login("tester2")
                .role("CLIENT")
                .build();

        UserDto newUser = UserDto
                .builder()
                .email("changed@mail.com")
                .login("tester2")
                .role("CLIENT")
                .build();
        userEndpoint.registerUser(secondUser);
        assertThat(userEndpoint.getAllUsers().size()).isEqualTo(2);
        //when

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () ->userEndpoint.updateUser(assignedId,newUser));

        //then
        assertThat(exception.getStatus()).isEqualTo(HttpStatus.CONFLICT);
    }
    @Test
    @Order(7)
    void updateUserTest() {
        //given
        UserDto newUser = UserDto
                .builder()
                .email("changed@mail.com")
                .login("changedLogin")
                .role("CLIENT")
                .build();
        assertThat(userEndpoint.getAllUsers().size()).isEqualTo(2);
        //when

        UserDto updatedUser = userEndpoint.updateUser(assignedId, newUser);

        //then
        assertThat(updatedUser.getLogin()).isEqualTo(newUser.getLogin());
    }
    @Test
    @Order(8)
    void deleteUserNotFoundTest() {
        //given
        assertThat(userEndpoint.getAllUsers().size()).isEqualTo(2);
        //when

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () ->userEndpoint.deleteUser(-1L));

        //then
        assertThat(exception.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(userEndpoint.getAllUsers().size()).isEqualTo(2);
    }
    @Test
    @Order(9)
    void deleteUserTest() {
        //given
        assertThat(userEndpoint.getAllUsers().size()).isEqualTo(2);
        //when

       userEndpoint.deleteUser(assignedId);

        //then
        assertThat(userEndpoint.getAllUsers().size()).isEqualTo(1);
    }
}
