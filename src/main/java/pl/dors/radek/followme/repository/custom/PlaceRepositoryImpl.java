package pl.dors.radek.followme.repository.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import pl.dors.radek.followme.model.Place;

import java.util.List;

/**
 * Created by rdors on 2016-10-24.
 */
public class PlaceRepositoryImpl implements PlaceRepositoryCustom {

    @Autowired
    private MongoTemplate template;

    @Override
    public List<Place> findByNameCriteria(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));

        return template.find(query, Place.class);
    }
}
