package pl.dors.radek.followme.repository.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import pl.dors.radek.followme.model.Meeting;
import pl.dors.radek.followme.model.User;

import java.util.List;

/**
 * Created by rdors on 2016-10-24.
 */
public class MeetingRepositoryImpl implements MeetingRepositoryCustom {

    @Autowired
    MongoTemplate template;

    @Override
    public List<Meeting> findAllByUser(User user) {
        Query query = new Query();
        query.addCriteria(Criteria.where("users.id").is(user.getId()));
        return template.find(query, Meeting.class);
    }
}
