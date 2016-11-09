package pl.dors.radek.followme.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.dors.radek.followme.model.id.MeetingPersonID;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by rdors on 2016-10-25.
 */
@Entity
@Table(name = "MEETING_PERSON")
@AssociationOverrides({
        @AssociationOverride(name = "pk.meeting",
                joinColumns = @JoinColumn(name = "MEETING_ID")),
        @AssociationOverride(name = "pk.person",
                joinColumns = @JoinColumn(name = "PERSON_ID"))})
public class MeetingPerson {

    @JsonIgnore
    private MeetingPersonID pk = new MeetingPersonID();
    private boolean owner;
    private double x;
    private double y;
    private LocalDateTime lastUpdate;
    //private UserStatus userStatus;

    @EmbeddedId
    public MeetingPersonID getPk() {
        return pk;
    }

    public void setPk(MeetingPersonID pk) {
        this.pk = pk;
    }

    @Column(name = "OWNER")
    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    @Column(name = "X")
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    @Column(name = "Y")
    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Column(name = "LAST_UPDATE")
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

//    public UserStatus getUserStatus() {
//        return userStatus;
//    }

//    public void setUserStatus(UserStatus userStatus) {
//        this.userStatus = userStatus;
//    }

    @JsonIgnore
    @Transient
    public Meeting getMeeting() {
        return getPk().getMeeting();
    }

    public void setMeeting(Meeting meeting) {
        getPk().setMeeting(meeting);
    }

    @Transient
    public Person getUser() {
        return getPk().getPerson();
    }

    public void setUser(Person person) {
        getPk().setPerson(person);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MeetingPerson that = (MeetingPerson) o;

        return pk != null ? pk.equals(that.pk) : that.pk == null;
    }

    @Override
    public int hashCode() {
        return pk != null ? pk.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "MeetingPerson{" +
                "pk=" + pk +
                ", owner=" + owner +
                '}';
    }

}
