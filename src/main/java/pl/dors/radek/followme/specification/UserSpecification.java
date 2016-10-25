package pl.dors.radek.followme.specification;

import org.springframework.data.jpa.domain.Specification;
import pl.dors.radek.followme.model.User;

public class UserSpecification {

    public static Specification<User> findByName(String name) {
        return (root, query, cb) -> cb.equal(root.get("name"), name);
    }
}