package pl.dors.radek.followme.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import pl.dors.radek.followme.model.Place;

public class PlaceSpecification {

    public static Specification<Place> findByName(String name) {
        return (root, query, cb) -> cb.equal(root.get("name"), name);
    }
}