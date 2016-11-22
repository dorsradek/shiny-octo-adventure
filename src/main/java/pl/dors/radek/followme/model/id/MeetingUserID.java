package pl.dors.radek.followme.model.id;

import pl.dors.radek.followme.model.Meeting;
import pl.dors.radek.followme.model.security.User;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MeetingUserID that = (MeetingUserID) o;

        if (meeting != null ? !meeting.equals(that.meeting) : that.meeting != null) return false;
        return user != null ? user.equals(that.user) : that.user == null;
    }

    @Override
    public int hashCode() {
        int result = meeting != null ? meeting.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MeetingUserID{" +
                "meeting=" + meeting +
                ", user=" + user +
                '}';
    }
}