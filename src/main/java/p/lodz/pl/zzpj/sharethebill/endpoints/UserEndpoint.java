package p.lodz.pl.zzpj.sharethebill.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import p.lodz.pl.zzpj.sharethebill.dtos.PurchaseDto;
import p.lodz.pl.zzpj.sharethebill.dtos.UserDto;
import p.lodz.pl.zzpj.sharethebill.entities.User;
import p.lodz.pl.zzpj.sharethebill.exceptions.NotFoundException;
import p.lodz.pl.zzpj.sharethebill.exceptions.UniqueConstaintException;
import p.lodz.pl.zzpj.sharethebill.services.PurchaseService;
import p.lodz.pl.zzpj.sharethebill.services.UserService;
import p.lodz.pl.zzpj.sharethebill.utils.PurchaseConverter;
import p.lodz.pl.zzpj.sharethebill.utils.UserConverter;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserEndpoint {

    private final UserService userService;
    private final PurchaseService purchaseService;

    @Autowired
    public UserEndpoint(UserService userService, PurchaseService purchaseService) {
        this.userService = userService;
        this.purchaseService = purchaseService;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        List<User> userList = userService.findAll();
        return UserConverter.toDtoList(userList);
    }

    @GetMapping(path = "{id}")
    public UserDto getUser(@PathVariable Long id) {
        try {
            return UserConverter.toDto(userService.find(id));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        }
    }

    @PostMapping
    public UserDto registerUser(@RequestBody UserDto user) {
        try {
            return UserConverter.toDto(
                    userService.add(UserConverter.toEntity(user))
            );
        } catch (UniqueConstaintException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,e.getMessage(),e);
        }
    }

    @DeleteMapping(path = "{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

    @PutMapping("{id}")
    public UserDto updateUser(@PathVariable Long id, @Valid @RequestBody UserDto user) {
        User updatedUser = userService.update(id, UserConverter.toEntity(user));
        return UserConverter.toDto(updatedUser);
    }

    @GetMapping("allPurchases/{id}")
    public List<PurchaseDto> getPurchasesBySponsor(@PathVariable Long id){
        return PurchaseConverter.toPurchaseDtoList(purchaseService.findAllBySponsor(id));
    }
}
