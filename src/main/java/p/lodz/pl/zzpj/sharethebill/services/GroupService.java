package p.lodz.pl.zzpj.sharethebill.services;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import p.lodz.pl.zzpj.sharethebill.entities.BillGroup;
import p.lodz.pl.zzpj.sharethebill.entities.User;
import p.lodz.pl.zzpj.sharethebill.repositories.BillGroupRepository;

import java.util.List;

@Service
public class GroupService {

    @Autowired
    private BillGroupRepository billGroupRepository;

    public void createExampleGroup(){
        BillGroup billGroup = new BillGroup("First Group", true);
        User user = new User("ziomek", "client");
        billGroup.addMember(user);
        billGroupRepository.save(billGroup);
    }

    public List<BillGroup> findAll() {
        return IterableUtils.toList(billGroupRepository.findAll());
    }
}
