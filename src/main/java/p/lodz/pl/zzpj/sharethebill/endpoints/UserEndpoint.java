package p.lodz.pl.zzpj.sharethebill.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import p.lodz.pl.zzpj.sharethebill.dtos.UserDto;
import p.lodz.pl.zzpj.sharethebill.entities.User;
import p.lodz.pl.zzpj.sharethebill.services.UserService;
import p.lodz.pl.zzpj.sharethebill.utils.UserConverter;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/user")
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

    @GetMapping(path = "{id}")
    public UserDto getUser(@PathVariable Long id) {
        User user = userService.find(id);
        return UserConverter.toDto(userService.find(id));
    }

    @PostMapping
    public UserDto registerUser(@RequestBody User user) {
        User createdUser = userService.add(user);
        return UserConverter.toDto(user);
    }

    @DeleteMapping(path = "{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

    @PutMapping("{id}")
    public UserDto updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        User updatedUser = userService.update(id, user);
        return UserConverter.toDto(updatedUser);
    }
}
