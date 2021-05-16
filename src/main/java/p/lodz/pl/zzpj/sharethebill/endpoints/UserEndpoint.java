package p.lodz.pl.zzpj.sharethebill.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import p.lodz.pl.zzpj.sharethebill.dtos.UserDto;
import p.lodz.pl.zzpj.sharethebill.entities.User;
import p.lodz.pl.zzpj.sharethebill.services.UserService;
import p.lodz.pl.zzpj.sharethebill.utils.UserConverter;

import java.util.List;

@RestController
@RequestMapping("user")
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
    @PostMapping("create")
    public UserDto createUser(@RequestBody UserDto user){
        return UserConverter.toDto(
                userService.createUser(UserConverter.toEntity(user))
        );
    }
}
