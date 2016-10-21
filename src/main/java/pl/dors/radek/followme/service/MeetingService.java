package pl.dors.radek.followme.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dors.radek.followme.model.Meeting;
import pl.dors.radek.followme.repository.MeetingRepository;

import java.util.stream.Stream;

/**
 * Created by rdors on 2016-10-21.
 */
@Service
public class MeetingService implements IMeetingService {

    private MeetingRepository meetingRepository;

    @Autowired
    public MeetingService(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    @Override
    public Stream<Meeting> findAll() {
        return meetingRepository.findAll().stream();
    }
}
