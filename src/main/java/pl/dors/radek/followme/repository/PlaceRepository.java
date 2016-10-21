package pl.dors.radek.followme.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.dors.radek.followme.model.Place;

/**
 * Created by rdors on 2016-10-21.
 */
public interface PlaceRepository extends MongoRepository<Place, Long> {

}
