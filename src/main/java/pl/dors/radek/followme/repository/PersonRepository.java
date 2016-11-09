package pl.dors.radek.followme.repository;

import org.springframework.data.repository.CrudRepository;
import pl.dors.radek.followme.model.Person;

import java.util.List;
import java.util.Optional;

/**
 * Created by rdors on 2016-10-21.
 */
public interface PersonRepository extends CrudRepository<Person, Long> {

    List<Person> findByName(String name);

    Optional<Person> findById(Long id);

    List<Person> findAll();

}
