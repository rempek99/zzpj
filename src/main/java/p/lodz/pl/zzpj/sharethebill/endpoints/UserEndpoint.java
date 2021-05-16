package p.lodz.pl.zzpj.sharethebill.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import p.lodz.pl.zzpj.sharethebill.dtos.UserDto;
import p.lodz.pl.zzpj.sharethebill.entities.User;
import p.lodz.pl.zzpj.sharethebill.services.UserService;
import p.lodz.pl.zzpj.sharethebill.utils.UserConverter;

import java.util.List;

@RestController
public class UserEndpoint {

    private final UserService userService;

    @Autowired
    public UserEndpoint(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        List<User> userList = userService.findAll();
        return UserConverter.toDtoList(userList);
    }
    @PostMapping
    public UserDto createUser(UserDto user){
        return UserConverter.toDto(
                userService.createUser(UserConverter.toEntity(user))
        );
    }
}
