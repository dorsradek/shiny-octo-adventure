package pl.dors.radek.followme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.dors.radek.followme.model.User;

/**
 * Created by rdors on 2016-10-21.
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
}
