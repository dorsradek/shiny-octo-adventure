package pl.dors.radek.followme.security.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.dors.radek.followme.model.security.Authority;
import pl.dors.radek.followme.model.security.AuthorityName;

import java.util.Optional;

/**
 * Created by stephan on 20.03.16.
 */
public interface AuthorityRepository extends CrudRepository<Authority, Long> {

    @Query("SELECT a FROM Authority a where a.name = :authorityName")
    Optional<Authority> findByAuthorityName(@Param("authorityName") AuthorityName authorityName);

}
