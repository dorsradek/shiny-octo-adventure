package pl.dors.radek.followme.service;

import pl.dors.radek.followme.model.security.User;

import java.util.List;
import java.util.Optional;

/**
 * Created by rdors on 2016-10-21.
 */
public interface IUserService {

    List<User> findAll();

    User findByUsername(String name);

    User findOne(Optional<Long> userId);

    List<User> findAllExceptUsername(String username);
}
