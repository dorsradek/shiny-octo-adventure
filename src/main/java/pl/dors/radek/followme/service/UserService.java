package pl.dors.radek.followme.service;

import org.springframework.stereotype.Service;
import pl.dors.radek.followme.model.Person;
import pl.dors.radek.followme.repository.PersonRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Created by rdors on 2016-10-21.
 */
@Service
@Transactional
public class UserService implements IUserService {

    private PersonRepository personRepository;

    public UserService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public List<Person> findByName(String name) {
        return personRepository.findByName(name);
    }

    @Override
    public Person findOne(Optional<Long> userId) {
        return personRepository.findById(userId.orElseThrow(() -> new IllegalArgumentException("Person id can't be null")))
                .orElseThrow(() -> new RuntimeException("Person not found"));
    }

}
