package p.lodz.pl.zzpj.sharethebill.services;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import p.lodz.pl.zzpj.sharethebill.entities.Purchase;
import p.lodz.pl.zzpj.sharethebill.repositories.PurchaseRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public List<Purchase> findAll(){
        return IterableUtils.toList(purchaseRepository.findAll());
    }

    public List<Purchase> findAllBySponsor(Long id){
       return IterableUtils.toList(purchaseRepository.findAll())
               .stream()
               .filter(i-> i.getSponsor().getId().equals(id))
               .collect(Collectors.toList());
    }
}
