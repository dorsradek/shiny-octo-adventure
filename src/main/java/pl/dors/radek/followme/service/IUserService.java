package pl.dors.radek.followme.service;

import pl.dors.radek.followme.model.Person;

import java.util.List;
import java.util.Optional;

/**
 * Created by rdors on 2016-10-21.
 */
public interface IUserService {

    List<Person> findAll();

    List<Person> findByName(String name);

    Person findOne(Optional<Long> userId);
}
