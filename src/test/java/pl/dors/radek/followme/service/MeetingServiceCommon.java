package pl.dors.radek.followme.service;

import org.mockito.Mockito;
import pl.dors.radek.followme.model.Meeting;
import pl.dors.radek.followme.model.MeetingPerson;
import pl.dors.radek.followme.model.Person;
import pl.dors.radek.followme.model.Place;
import pl.dors.radek.followme.repository.MeetingRepository;
import pl.dors.radek.followme.repository.PersonRepository;
import pl.dors.radek.followme.repository.PlaceRepository;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;

/**
 * Created by rdors on 2016-10-26.
 */
public class MeetingServiceCommon {

    private static long id;

    public static void setUp(MeetingRepository meetingRepository, PersonRepository personRepository, PlaceRepository placeRepository) throws Exception {
        Mockito.when(meetingRepository.findAll())
                .thenReturn(Arrays.asList(new Meeting("M1")));
        Mockito.when(placeRepository.save(any(Place.class)))
                .then(i -> {
                    Place place = i.getArgumentAt(0, Place.class);
                    if (place.getId() == null) {
                        place.setId(id++);
                    }
                    return i;
                });
        Mockito.when(personRepository.save(any(Person.class)))
                .then(i -> {
                    Person person = i.getArgumentAt(0, Person.class);
                    if (person.getId() == null) {
                        person.setId(id++);
                    }
                    return i;
                });
        Mockito.when(meetingRepository.save(any(Meeting.class)))
                .then(i -> {
                    Meeting meeting = i.getArgumentAt(0, Meeting.class);
                    if (meeting.getId() == null) {
                        meeting.setId(id++);
                    }
                    return i;
                });
        Meeting m1 = new Meeting("M1");
        m1.setId(44L);
        Mockito.when(meetingRepository.findOne(anyLong()))
                .thenReturn(m1);
    }

    public static void findAll(IMeetingService meetingService) throws Exception {
        assertThat(meetingService.findAll("U1")).hasSize(1);
        assertThat(meetingService.findAll("U1").get(0).getName()).isEqualTo("M1");
    }

    public static void save(IMeetingService meetingService) throws Exception {
        Meeting meeting = new Meeting("M1");
        Person person = new Person("U1");
        Place place = new Place("P1", 1, 2);
        MeetingPerson meetingPerson = new MeetingPerson();
        meetingPerson.setUser(person);
        meeting.setPlace(place);
        meeting.getMeetingPersons().add(meetingPerson);
        meetingService.save(meeting);
        assertThat(meeting.getMeetingPersons()).hasSize(1);
        assertThat(meeting.getMeetingPersons().get(0).getUser().getName()).isEqualTo("U1");
        assertThat(meeting.getMeetingPersons().get(0).getUser().getId()).isNotNull();
        assertThat(meeting.getPlace().getId()).isNotNull();
    }

}
