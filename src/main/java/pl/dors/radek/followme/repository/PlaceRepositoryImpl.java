package pl.dors.radek.followme.repository;

import org.springframework.beans.factory.annotation.Autowired;
import pl.dors.radek.followme.model.Place;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rdors on 2016-10-24.
 */
public class PlaceRepositoryImpl implements PlaceRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Place> findByNameCriteria(String name) {
        return new ArrayList<>();
    }
}
