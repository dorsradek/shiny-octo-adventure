package pl.dors.radek.followme.service;

import org.springframework.stereotype.Service;
import pl.dors.radek.followme.model.User;
import pl.dors.radek.followme.repository.UserRepository;
import pl.dors.radek.followme.specification.UserSpecification;

import javax.transaction.Transactional;
import java.util.List;

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
    public List<User> findByName(String name) {
        return userRepository.findAll(UserSpecification.findByName(name));
    }

}
