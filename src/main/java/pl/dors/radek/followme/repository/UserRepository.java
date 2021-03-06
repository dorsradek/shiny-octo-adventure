package pl.dors.radek.followme.repository;

import org.springframework.data.repository.CrudRepository;
import pl.dors.radek.followme.model.security.User;

import java.util.List;
import java.util.Optional;

/**
 * Created by stephan on 20.03.16.
 */
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findById(long id);

    List<User> findAll();

}
