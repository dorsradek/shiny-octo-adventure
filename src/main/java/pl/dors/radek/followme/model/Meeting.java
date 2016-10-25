package pl.dors.radek.followme.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by rdors on 2016-10-21.
 */
@Entity
@Table(name = "MEETING")
public class Meeting {

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    private int id;

    @Column(name = "NAME", nullable = false, length = 20)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.meeting", cascade = CascadeType.ALL)
    private List<MeetingPlace> meetingPlaces;

    //@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.meeting", cascade = CascadeType.ALL)
    @Transient
    private List<MeetingUser> meetingUsers;

    public Meeting() {
    }

    public Meeting(String name, List<MeetingPlace> meetingPlaces, List<MeetingUser> meetingUsers) {
        this.name = name;
        this.meetingPlaces = meetingPlaces;
        this.meetingUsers = meetingUsers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MeetingPlace> getMeetingPlaces() {
        return meetingPlaces;
    }

    public void setMeetingPlaces(List<MeetingPlace> meetingPlaces) {
        this.meetingPlaces = meetingPlaces;
    }

    public List<MeetingUser> getMeetingUsers() {
        return meetingUsers;
    }

    public void setMeetingUsers(List<MeetingUser> meetingUsers) {
        this.meetingUsers = meetingUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Meeting meeting = (Meeting) o;

        if (id != meeting.id) return false;
        if (name != null ? !name.equals(meeting.name) : meeting.name != null) return false;
        if (meetingPlaces != null ? !meetingPlaces.equals(meeting.meetingPlaces) : meeting.meetingPlaces != null)
            return false;
        return meetingUsers != null ? meetingUsers.equals(meeting.meetingUsers) : meeting.meetingUsers == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (meetingPlaces != null ? meetingPlaces.hashCode() : 0);
        result = 31 * result + (meetingUsers != null ? meetingUsers.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", meetingPlaces=" + meetingPlaces +
                ", meetingUsers=" + meetingUsers +
                '}';
    }
}
