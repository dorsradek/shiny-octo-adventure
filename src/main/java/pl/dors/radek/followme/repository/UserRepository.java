package pl.dors.radek.followme.repository;

import org.springframework.data.repository.CrudRepository;
import pl.dors.radek.followme.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Created by rdors on 2016-10-21.
 */
public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByName(String name);

    Optional<User> findById(Long id);

    List<User> findAll();

}
