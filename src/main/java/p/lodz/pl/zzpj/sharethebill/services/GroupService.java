package p.lodz.pl.zzpj.sharethebill.services;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import p.lodz.pl.zzpj.sharethebill.dtos.BillResultDto;
import p.lodz.pl.zzpj.sharethebill.entities.BillGroup;
import p.lodz.pl.zzpj.sharethebill.entities.Purchase;
import p.lodz.pl.zzpj.sharethebill.entities.User;
import p.lodz.pl.zzpj.sharethebill.model.BillResult;
import p.lodz.pl.zzpj.sharethebill.model.UserRole;
import p.lodz.pl.zzpj.sharethebill.repositories.BillGroupRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    private BillGroupRepository billGroupRepository;

    public void createExampleGroup() {
        BillGroup billGroup = new BillGroup("First Group", true);
        User user = new User("ziomek", "z@mek.com",UserRole.CLIENT);
        User user2 = new User("noob", "n@ob.com", UserRole.CLIENT);
        User user3 = new User("anita", "an@ta.com" ,UserRole.CLIENT);
        billGroup.addMember(user);
        billGroup.addMember(user2);
        billGroup.addMember(user3);
        Purchase purchase = new Purchase("Zakupy", 200., user2);
        Purchase purchase2 = new Purchase("Kino", 42., user2);
        Purchase purchase3 = new Purchase("Piwo", 40., user3);
        billGroup.registerPurchase(purchase);
        billGroup.registerPurchase(purchase2);
        billGroup.registerPurchase(purchase3);
        billGroupRepository.save(billGroup);
    }

    public List<BillGroup> findAll() {
        return IterableUtils.toList(billGroupRepository.findAll());
    }

    public List<BillResult> calculate(Long groupId) {
        BillGroup group = billGroupRepository.findById(groupId).orElseThrow(IllegalStateException::new);
        List<Purchase> purchaseList = group.getPurchases();
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
            resultList.add(new BillResult(user,charge));
        }
        return resultList;
    }
}
