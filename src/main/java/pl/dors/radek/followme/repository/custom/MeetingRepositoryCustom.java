package pl.dors.radek.followme.repository.custom;

import pl.dors.radek.followme.model.Meeting;
import pl.dors.radek.followme.model.User;

import java.util.List;

/**
 * Created by rdors on 2016-10-24.
 */
public interface MeetingRepositoryCustom {

    List<Meeting> findAllByUser(User user);
}
