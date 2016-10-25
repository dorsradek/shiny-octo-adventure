package pl.dors.radek.followme.model;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class MeetingPlaceID implements java.io.Serializable {

    @ManyToOne
    private Meeting meeting;

    @ManyToOne
    private Place place;

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

}