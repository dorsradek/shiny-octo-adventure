package pl.dors.radek.followme.service.impl;

import org.springframework.stereotype.Service;
import pl.dors.radek.followme.model.security.User;
import pl.dors.radek.followme.repository.UserRepository;
import pl.dors.radek.followme.service.IUserService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
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
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User findById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<User> findAllExceptUsername(String username) {
        return userRepository.findAll().stream().filter(user -> !user.getUsername().equals(username)).collect(Collectors.toList());
    }

    @Override
    public User updateLocation(String username, Double x, Double y) {
        if (x == null || y == null) {
            throw new IllegalArgumentException("X or Y cant be null");
        }
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setX(x);
        user.setY(y);
        user.setLastUpdate(LocalDateTime.now());
        return userRepository.save(user);
    }

}
