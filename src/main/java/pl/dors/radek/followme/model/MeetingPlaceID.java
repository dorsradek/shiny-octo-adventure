package pl.dors.radek.followme.model;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class MeetingPlaceID implements java.io.Serializable {

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