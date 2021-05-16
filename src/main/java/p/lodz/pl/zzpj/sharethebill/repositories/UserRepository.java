package p.lodz.pl.zzpj.sharethebill.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import p.lodz.pl.zzpj.sharethebill.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    List<User> findByRole(String login);
    Optional<User> findById(Long id);
    Optional<User> findUserByEmailIgnoreCase(String email);

    Optional<User> findUserByLoginIgnoreCase(String email);
}
