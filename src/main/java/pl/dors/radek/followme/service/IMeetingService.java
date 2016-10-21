package pl.dors.radek.followme.service;

import pl.dors.radek.followme.model.Meeting;

import java.util.stream.Stream;

/**
 * Created by rdors on 2016-10-21.
 */
public interface IMeetingService {

    Stream<Meeting> findAll();
}
