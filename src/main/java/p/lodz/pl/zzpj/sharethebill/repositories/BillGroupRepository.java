package p.lodz.pl.zzpj.sharethebill.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import p.lodz.pl.zzpj.sharethebill.entities.BillGroup;

@Repository
public interface BillGroupRepository extends CrudRepository<BillGroup,Long> {
    @Transactional
    @Modifying
    @Query("UPDATE  BillGroup u SET u.isActive = :isActive WHERE u.id = :id")
    void changeGroupActiveState(@Param(value = "id") Long id, @Param(value = "isActive") Boolean isActive);
}
