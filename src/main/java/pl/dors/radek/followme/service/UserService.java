package pl.dors.radek.followme.service;

import org.springframework.stereotype.Service;
import pl.dors.radek.followme.model.security.User;
import pl.dors.radek.followme.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by rdors on 2016-10-21.
 */
@Service
@Transactional
public class UserService implements IUserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String name) {
        return userRepository.findByUsername(name)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User findOne(Optional<Long> userId) {
        return userRepository.findById(userId.orElseThrow(() -> new IllegalArgumentException("User id can't be null")))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<User> findAllExceptUsername(String username) {
        return userRepository.findAll().stream().filter(user -> !user.getUsername().equals(username)).collect(Collectors.toList());
    }

}
