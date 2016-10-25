package pl.dors.radek.followme.service;

import pl.dors.radek.followme.model.User;

import java.util.List;

/**
 * Created by rdors on 2016-10-21.
 */
public interface IUserService {

    List<User> findAll();

    List<User> findByName(String name);
}
