package p.lodz.pl.zzpj.sharethebill.services;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import p.lodz.pl.zzpj.sharethebill.entities.User;
import p.lodz.pl.zzpj.sharethebill.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll(){
        return IterableUtils.toList(userRepository.findAll());
    }

    public User find(Long id) {
        return userRepository.findById(id).orElseThrow(
               () -> new IllegalStateException("user not found")
        );
    }

    public User add(User user) {
        Optional<User> userOptional = userRepository.
                findUserByEmailIgnoreCase(user.getEmail());
        if (userOptional.isPresent()) {
            throw new IllegalStateException("email taken");
        }
        return userRepository.save(user);
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalStateException("user does not exist");
        }
        userRepository.deleteById(id);
    }

    public User update(Long id, User newUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("user not found " + id));

        user.setLogin(newUser.getLogin());
        user.setEmail(newUser.getEmail());
        user.setRole(newUser.getRole());

        return userRepository.save(user);
    }
}
