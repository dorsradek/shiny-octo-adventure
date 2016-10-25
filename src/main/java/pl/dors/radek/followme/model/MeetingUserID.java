package pl.dors.radek.followme.model;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class MeetingUserID implements java.io.Serializable {

    private Meeting meeting;
    private User user;

    @ManyToOne
    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}