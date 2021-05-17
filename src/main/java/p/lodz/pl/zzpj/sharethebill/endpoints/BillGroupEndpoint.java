package p.lodz.pl.zzpj.sharethebill.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import p.lodz.pl.zzpj.sharethebill.dtos.*;
import p.lodz.pl.zzpj.sharethebill.entities.Purchase;
import p.lodz.pl.zzpj.sharethebill.exceptions.NotFoundException;
import p.lodz.pl.zzpj.sharethebill.exceptions.UniqueConstaintException;
import p.lodz.pl.zzpj.sharethebill.services.GroupService;
import p.lodz.pl.zzpj.sharethebill.services.PurchaseService;
import p.lodz.pl.zzpj.sharethebill.utils.BillGroupConverter;
import p.lodz.pl.zzpj.sharethebill.utils.BillResultConverter;
import p.lodz.pl.zzpj.sharethebill.utils.PurchaseConverter;
import p.lodz.pl.zzpj.sharethebill.utils.UserConverter;

import java.util.List;

@RestController
@RequestMapping("group")
public class BillGroupEndpoint {


    private final GroupService groupService;
    private final PurchaseService purchaseService;

    @Autowired
    public BillGroupEndpoint(GroupService groupService, PurchaseService purchaseService) {
        this.groupService = groupService;
        this.purchaseService = purchaseService;
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
        } catch (UniqueConstaintException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,e.getMessage(),e);
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

    @GetMapping("calculate/{groupId}")
    public List<BillResultDto> calculate(@PathVariable Long groupId){
        return BillResultConverter.toDtoList(groupService.calculate(groupId));
    }

    @GetMapping("all")
    public List<BillGroupWithMembersAndPurchasesDto> getAll(){
        return BillGroupConverter.toDtoWithMembersAndPurchasesList(groupService.findAll());
    }

    @GetMapping("allPurchases/{id}")
    public List<PurchaseWithUserDto> getAllPurchasesForGroup(@PathVariable Long id){
        return PurchaseConverter.toDtoWithUserList(groupService.purchasesByGroup(id));
    }

    @GetMapping("allPurchases/{groupId}/{userId}")
    public List<PurchaseWithUserDto> getAllPurchasesForGroupAndUser(@PathVariable Long groupId, @PathVariable Long userId){
        return PurchaseConverter.toDtoWithUserList(groupService.purchasesByGroupAndUser(groupId,userId));
    }

    @GetMapping("allPurchases")
    public List<PurchaseWithUserDto> getAllPurchasesForGroupAndUser(){
        return PurchaseConverter.toDtoWithUserList(purchaseService.findAll());
    }


    //    @GetMapping("example")
//    public String createExample(){
//        groupService.createExampleGroup();
//        return "Example group created";
//    }
}
