package p.lodz.pl.zzpj.sharethebill.repositories;

import org.springframework.data.repository.CrudRepository;
import p.lodz.pl.zzpj.sharethebill.entities.Purchase;

public interface PurchaseRepository extends CrudRepository<Purchase,Long> {
}
