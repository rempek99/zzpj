package p.lodz.pl.zzpj.sharethebill.services;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import p.lodz.pl.zzpj.sharethebill.entities.User;
import p.lodz.pl.zzpj.sharethebill.exceptions.NotFoundException;
import p.lodz.pl.zzpj.sharethebill.exceptions.UniqueConstaintException;
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

    public List<User> findAll() {
        return IterableUtils.toList(userRepository.findAll());
    }

    public User find(Long id) throws NotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent())
            return user.get();
        else
            throw NotFoundException.createUserNotFoundException(id);
    }

    public User add(User user) throws UniqueConstaintException {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("EMAIL"))
                throw UniqueConstaintException.createEmailTakenException(user.getEmail());
            else
                throw UniqueConstaintException.createLoginTakenException(user.getLogin());
        }
    }

    public void delete(Long id) throws NotFoundException.UserNotFoundException {
        if (!userRepository.existsById(id)) {
            throw NotFoundException.createUserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    public User update(Long id, User newUser) throws NotFoundException, UniqueConstaintException{
        User user = userRepository.findById(id)
                .orElseThrow(() -> NotFoundException.createUserNotFoundException(id));
        user.setLogin(newUser.getLogin());
        user.setEmail(newUser.getEmail());
        user.setRole(newUser.getRole());
        try {
            return userRepository.save(user);
        }catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("EMAIL"))
                throw UniqueConstaintException.createEmailTakenException(user.getEmail());
            else
                throw UniqueConstaintException.createLoginTakenException(user.getLogin());
        }
    }
}
