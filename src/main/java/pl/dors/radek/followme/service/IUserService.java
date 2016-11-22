package pl.dors.radek.followme.service;

import pl.dors.radek.followme.model.security.User;

import java.util.List;

/**
 * Created by rdors on 2016-10-21.
 */
public interface IUserService {

    List<User> findAll();

    User findByUsername(String username);

    User findById(long userId);

    List<User> findAllExceptUsername(String username);

    User updateLocation(String username, Double x, Double y);
}
