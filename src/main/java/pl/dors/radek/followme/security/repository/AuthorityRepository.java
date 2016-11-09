package pl.dors.radek.followme.security.repository;

import org.springframework.data.repository.CrudRepository;
import pl.dors.radek.followme.model.security.Authority;
import pl.dors.radek.followme.model.security.User;

/**
 * Created by stephan on 20.03.16.
 */
public interface AuthorityRepository extends CrudRepository<Authority, Long> {

}
