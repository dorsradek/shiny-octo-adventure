package pl.dors.radek.followme.repository;

import pl.dors.radek.followme.model.Place;

import java.util.List;

/**
 * Created by rdors on 2016-10-24.
 */
public interface PlaceRepositoryCustom {

    List<Place> findByNameCriteria(String name);

}
