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

        double sum = purchaseList.stream().mapToDouble(Purchase::getValue).sum();
        int members = group.getMembers().size();
        double difference = sum / members;
        List<BillResult> resultList = new ArrayList<>();
        for (User user : group.getMembers()) {
            double charge = 0.;
            for (Purchase purchase : purchaseList) {
                if (purchase.getSponsor().equals(user)) {
                    charge -= purchase.getValue();
                }
            }
            charge += difference;
            resultList.add(new BillResult(user, charge));
        }
        return resultList;
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

    //    public void createExampleGroup() {
//        BillGroup billGroup = new BillGroup("First Group", true);
//        User user2 = new User("noob", "client");
//        User user = new User("ziomek", "client");
//        User user3 = new User("anita", "client");
//        billGroup.addMember(user);
//        billGroup.addMember(user2);
//        billGroup.addMember(user3);
//        Purchase purchase = new Purchase("Zakupy", 200., user2);
//        Purchase purchase2 = new Purchase("Kino", 42., user2);
//        Purchase purchase3 = new Purchase("Piwo", 40., user3);
//        billGroup.registerPurchase(purchase);
//        billGroup.registerPurchase(purchase2);
//        billGroup.registerPurchase(purchase3);
//        billGroupRepository.save(billGroup);
//    }
}
