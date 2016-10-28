package pl.dors.radek.followme.service;

import pl.dors.radek.followme.model.Place;

import java.util.List;

/**
 * Created by rdors on 2016-10-21.
 */
public interface IPlaceService {

    List<Place> findAll();

    List<Place> findByName(String name);
}
