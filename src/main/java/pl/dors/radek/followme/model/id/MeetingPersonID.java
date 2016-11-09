package pl.dors.radek.followme.model.id;

import pl.dors.radek.followme.model.Meeting;
import pl.dors.radek.followme.model.Person;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class MeetingPersonID implements java.io.Serializable {

    private Meeting meeting;
    private Person person;

    @ManyToOne
    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    @ManyToOne
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}