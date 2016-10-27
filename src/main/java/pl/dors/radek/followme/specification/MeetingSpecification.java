package pl.dors.radek.followme.specification;

import org.springframework.data.jpa.domain.Specification;
import pl.dors.radek.followme.model.Meeting;
import pl.dors.radek.followme.model.User;

/**
 * Created by rdors on 2016-10-25.
 */
public class MeetingSpecification {

    public static Specification<Meeting> findByUser(User user) {
        return (root, query, cb) -> cb.equal(root.get("pk").get("user").get("id"), user.getId());
    }
}
