package pl.dors.radek.followme.specification;

import org.springframework.data.jpa.domain.Specification;
import pl.dors.radek.followme.model.Meeting;
import pl.dors.radek.followme.model.MeetingPlace;

/**
 * Created by rdors on 2016-10-25.
 */
public class MeetingPlaceSpecification {

    public static Specification<MeetingPlace> findByMeeting(Meeting meeting) {
        return (root, query, cb) -> cb.equal(root.get("pk").get("meeting").get("id"), meeting.getId());
    }
}
