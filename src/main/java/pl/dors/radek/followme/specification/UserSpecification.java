package pl.dors.radek.followme.specification;

import org.springframework.data.jpa.domain.Specification;
import pl.dors.radek.followme.model.User;

import java.util.Optional;

public class UserSpecification {

    public static Specification<User> findByName(String name) {
        return (root, query, cb) -> cb.equal(root.get("name"), name);
    }

    public static Specification<User> findOne(Optional<Long> id) {
        return (root, query, cb) -> cb.equal(root.get("id"), id.orElseThrow(() -> new IllegalArgumentException("User id can't be null")));
    }
}