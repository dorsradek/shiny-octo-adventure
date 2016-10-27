package pl.dors.radek.followme.model.id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.dors.radek.followme.model.Meeting;
import pl.dors.radek.followme.model.Place;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class MeetingPlaceID implements java.io.Serializable {

    @JsonIgnore
    private Meeting meeting;
    private Place place;

    @ManyToOne
    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    @ManyToOne
    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

}