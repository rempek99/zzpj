package p.lodz.pl.zzpj.sharethebill.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import p.lodz.pl.zzpj.sharethebill.dtos.*;
import p.lodz.pl.zzpj.sharethebill.exceptions.NotFoundException;
import p.lodz.pl.zzpj.sharethebill.exceptions.UniqueConstaintException;
import p.lodz.pl.zzpj.sharethebill.services.GroupService;
import p.lodz.pl.zzpj.sharethebill.utils.BillGroupConverter;
import p.lodz.pl.zzpj.sharethebill.utils.BillResultConverter;
import p.lodz.pl.zzpj.sharethebill.utils.PurchaseConverter;
import p.lodz.pl.zzpj.sharethebill.utils.UserConverter;

import java.util.List;

@RestController
@RequestMapping("group")
public class BillGroupEndpoint {


    private final GroupService groupService;

    @Autowired
    public BillGroupEndpoint(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("addUser/{groupId}/{userId}")
    public List<UserDto> addUser(@PathVariable Long userId, @PathVariable Long groupId){
        try {
            return UserConverter.toDtoList(
                    groupService.addUser(userId, groupId)
            );
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        } catch (UniqueConstaintException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,e.getMessage(),e);
        }
    }
    @PostMapping("addPurchase/{groupId}/{userId}")
    public BillGroupWithPurchasesDto addPurchase(@PathVariable Long userId, @PathVariable Long groupId,
                                                 @RequestBody PurchaseDto purchase){
        try {
            return
                    BillGroupConverter.toDtoWithPurchases(
                    groupService.addPurchase(userId,groupId, PurchaseConverter.toEntity(purchase))
                    );
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        }
    }


    @PostMapping("create")
    public BillGroupWithMembersAndPurchasesDto createBillGroup(@RequestBody BillGroupWithMembersAndPurchasesDto group){
        return BillGroupConverter.toDtoWithMembersAndPurchases(
                groupService.createGroup(BillGroupConverter.toEntity(group))
        );
    }

    @PutMapping("changeCurrency/{groupId}/{currencyCode}")
    public BillGroupWithMembersAndPurchasesDto changeCurrency( @PathVariable Long groupId, @PathVariable String currencyCode){

        return BillGroupConverter.toDtoWithMembersAndPurchases(
                groupService.changeCurrency(groupId, currencyCode)
        );
    }

    @GetMapping("calculate/{groupId}")
    public List<BillResultDto> calculate(@PathVariable Long groupId){
        return BillResultConverter.toDtoList(groupService.calculate(groupId));
    }

    @GetMapping("all")
    public List<BillGroupWithMembersAndPurchasesDto> getAll(){
        return BillGroupConverter.toDtoWithMembersAndPurchasesList(groupService.findAll());
    }

    @GetMapping("calculateAllForUser/{userId}")
    public List<BillResultDto> calculateForUser(@PathVariable Long userId){
        return BillResultConverter.toDtoList(groupService.calculateFromAllGroupsForUser(userId));
    }

    @PutMapping("disableGroup/{groupId}")
    public void disableGroup(@PathVariable Long groupId){
        groupService.disableGroup(groupId);
    }

}
