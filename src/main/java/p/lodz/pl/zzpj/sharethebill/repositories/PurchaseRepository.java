package p.lodz.pl.zzpj.sharethebill.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import p.lodz.pl.zzpj.sharethebill.entities.Purchase;

@Repository
public interface PurchaseRepository extends CrudRepository<Purchase,Long> {
}
