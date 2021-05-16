package p.lodz.pl.zzpj.sharethebill.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import p.lodz.pl.zzpj.sharethebill.dtos.BillGroupDto;
import p.lodz.pl.zzpj.sharethebill.dtos.BillResultDto;
import p.lodz.pl.zzpj.sharethebill.dtos.UserDto;
import p.lodz.pl.zzpj.sharethebill.exceptions.NotFoundException;
import p.lodz.pl.zzpj.sharethebill.services.GroupService;
import p.lodz.pl.zzpj.sharethebill.utils.BillGroupConverter;
import p.lodz.pl.zzpj.sharethebill.utils.BillResultConverter;
import p.lodz.pl.zzpj.sharethebill.utils.UserConverter;

import java.util.List;

@RestController
@RequestMapping("group")
public class BillGroupEndpoint {

    @Autowired
    GroupService groupService;

    @PostMapping("addUser/{groupId}")
    public List<UserDto> addUser(@RequestBody UserDto user, @PathVariable Long groupId){
        try {
            return UserConverter.toDtoList(
                    groupService.addUser(UserConverter.toEntity(user), groupId)
            );
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        }
    }

    @GetMapping("calculate/{groupId}")
    public List<BillResultDto> calculate(@PathVariable Long groupId){
        return BillResultConverter.toDtoList(groupService.calculate(groupId));
    }

    @GetMapping("all")
    public List<BillGroupDto> getAll(){
        return BillGroupConverter.toDtoList(groupService.findAll());
    }


    //    @GetMapping("example")
//    public String createExample(){
//        groupService.createExampleGroup();
//        return "Example group created";
//    }
}
