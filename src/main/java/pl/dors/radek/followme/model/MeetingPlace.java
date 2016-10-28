package pl.dors.radek.followme.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.dors.radek.followme.model.id.MeetingPlaceID;

import javax.persistence.*;

/**
 * Created by rdors on 2016-10-25.
 */
@Entity
@Table(name = "MEETING_PLACE")
@AssociationOverrides({
        @AssociationOverride(name = "pk.meeting",
                joinColumns = @JoinColumn(name = "MEETING_ID")),
        @AssociationOverride(name = "pk.place",
                joinColumns = @JoinColumn(name = "PLACE_ID"))})
public class MeetingPlace {

    @JsonIgnore
    private MeetingPlaceID pk = new MeetingPlaceID();
    private boolean owner;

    @EmbeddedId
    public MeetingPlaceID getPk() {
        return pk;
    }

    public void setPk(MeetingPlaceID pk) {
        this.pk = pk;
    }

    @Column(name = "OWNER")
    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    @JsonIgnore
    @Transient
    public Meeting getMeeting() {
        return getPk().getMeeting();
    }

    public void setMeeting(Meeting meeting) {
        getPk().setMeeting(meeting);
    }

    @Transient
    public Place getPlace() {
        return getPk().getPlace();
    }

    public void setPlace(Place place) {
        getPk().setPlace(place);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MeetingPlace that = (MeetingPlace) o;

        return pk != null ? pk.equals(that.pk) : that.pk == null;
    }

    @Override
    public int hashCode() {
        return pk != null ? pk.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "MeetingPlace{" +
                "pk=" + pk +
                ", owner=" + owner +
                '}';
    }
}
