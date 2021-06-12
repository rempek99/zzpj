package p.lodz.pl.zzpj.sharethebill.services;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import p.lodz.pl.zzpj.sharethebill.dtos.PurchaseDto;
import p.lodz.pl.zzpj.sharethebill.entities.BillGroup;
import p.lodz.pl.zzpj.sharethebill.entities.Purchase;
import p.lodz.pl.zzpj.sharethebill.entities.User;
import p.lodz.pl.zzpj.sharethebill.exceptions.NotFoundException;
import p.lodz.pl.zzpj.sharethebill.exceptions.UniqueConstaintException;
import p.lodz.pl.zzpj.sharethebill.model.BillResult;
import p.lodz.pl.zzpj.sharethebill.repositories.BillGroupRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final BillGroupRepository billGroupRepository;

    private final UserService userService;

    @Autowired
    public GroupService(BillGroupRepository billGroupRepository, UserService userService) {
        this.billGroupRepository = billGroupRepository;
        this.userService = userService;
    }

    public List<BillResult> calculate(Long groupId) {
        // todo move that functionality to separated class
        BillGroup group = billGroupRepository.findById(groupId).orElseThrow(IllegalStateException::new);
        List<Purchase> purchaseList = CurrencyService.convertIntoGroupCurrency(group);

        int members = group.getMembers().size();
        List<BillResult> resultList = new ArrayList<>();
        if (members==0)
            return resultList;
        for (User user : group.getMembers()) {
            for (Purchase purchase : purchaseList) {
                double charge;
                if (!purchase.getSponsor().equals(user)) {
                    charge = purchase.getValue()/members;

                    Optional<BillResult> sameSponsorUser = resultList.stream()
                            .filter(o -> o.getSponsor() == purchase.getSponsor() && o.getUser() == user)
                            .findFirst();

                    Optional<BillResult> reverseSponsorUser = resultList.stream()
                            .filter(o -> o.getSponsor() == user && o.getUser() == purchase.getSponsor())
                            .findFirst();

                    if(sameSponsorUser.isPresent()){
                        charge += sameSponsorUser.get().getCharge();
                    }

                    if (reverseSponsorUser.isEmpty()){
                        if(sameSponsorUser.isEmpty()){
                            resultList.add(new BillResult(user, charge,purchase.getSponsor()));
                        }else {
                            resultList.get(resultList.indexOf(sameSponsorUser.get())).setCharge(charge);
                        }
                    }else {
                        if(reverseSponsorUser.get().getCharge() > charge){
                            charge =reverseSponsorUser.get().getCharge() - charge;
                            resultList.get(resultList.indexOf(reverseSponsorUser.get())).setCharge(charge);
                        }
                        else if (reverseSponsorUser.get().getCharge() < charge){
                            charge-= reverseSponsorUser.get().getCharge();
                            resultList.add(new BillResult(user, charge,purchase.getSponsor()));
                            resultList.remove(reverseSponsorUser.get());
                        }
                        else {
                            resultList.remove(reverseSponsorUser.get());
                        }
                    }

                }
            }
        }
        return resultList;
    }

    public List<BillResult> calculateFromAllGroupsForUser(Long userId){
        List<BillGroup> allGroups = findAll();
        List<BillResult> billsForUser = new ArrayList<>();
        for (BillGroup billGroup : allGroups){
            List<BillResult> billsForGroupForUser = calculate(billGroup.getId()).stream()
                    .filter(bill -> bill.getUser().getId().equals(userId))
                    .collect(Collectors.toList());
            billsForUser.addAll(billsForGroupForUser);
        }
        return  billsForUser;
    }


    public List<BillGroup> findAll() {
        return IterableUtils.toList(billGroupRepository.findAll());
    }

    public BillGroup findById(Long id) {
        return billGroupRepository.findById(id).orElse(null);
    }

    public List<User> addUser(Long userId, Long groupId) throws NotFoundException, UniqueConstaintException {
        BillGroup group = findById(groupId);
        User user = userService.find(userId);
        if (null == group)
            throw NotFoundException.createGroupNotFoundException(groupId);
        if(group.getMembers().contains(user))
            throw UniqueConstaintException.createUserAlreadyInGroupException();
        group.addMember(user);
        billGroupRepository.save(group);
        return group.getMembers();

    }

    public BillGroup createGroup(BillGroup group) {
        return billGroupRepository.save(group);
    }

    public BillGroup changeCurrency(Long groupId,String currencyCode) {
        BillGroup group = findById(groupId);
        group.setCurrencyCode(currencyCode);
        return billGroupRepository.save(group);
    }

    public BillGroup addPurchase(Long userId, Long groupId, Purchase purchase) throws UniqueConstaintException, NotFoundException {
        BillGroup group = findById(groupId);
        User user = userService.find(userId);
        if (null == group)
            throw NotFoundException.createGroupNotFoundException(groupId);
        if(!group.getMembers().contains(user))
            throw NotFoundException.createUserIsNotInGroupException(userId);
        purchase.setSponsor(user);
        group.registerPurchase(purchase);
        return billGroupRepository.save(group);
    }
}
