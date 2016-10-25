package pl.dors.radek.followme.repository;

import org.springframework.beans.factory.annotation.Autowired;
import pl.dors.radek.followme.model.Meeting;
import pl.dors.radek.followme.model.User;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rdors on 2016-10-24.
 */
public class MeetingRepositoryImpl implements MeetingRepositoryCustom {

    @Autowired
    EntityManager entityManager;

    @Override
    public List<Meeting> findAllByUser(User user) {
        return new ArrayList<>();
    }
}
