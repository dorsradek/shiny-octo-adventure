package pl.dors.radek.followme.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rdors on 2016-10-21.
 */
@Entity
@Table(name = "MEETING")
public class Meeting {

    private Long id;
    private String name;
    private LocalDateTime lastUpdate;
    private boolean active;
    private List<MeetingPlace> meetingPlaces = new ArrayList<>();
    private List<MeetingUser> meetingUsers = new ArrayList<>();

    public Meeting() {
    }

    public Meeting(String name) {
        this.name = name;
    }

    public Meeting(String name, List<MeetingPlace> meetingPlaces, List<MeetingUser> meetingUsers) {
        this.name = name;
        this.meetingPlaces = meetingPlaces;
        this.meetingUsers = meetingUsers;
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "NAME", nullable = false, length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "LAST_UPDATE")
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Column(name = "ACTIVE")
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.meeting", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<MeetingPlace> getMeetingPlaces() {
        return meetingPlaces;
    }

    public void setMeetingPlaces(List<MeetingPlace> meetingPlaces) {
        this.meetingPlaces = meetingPlaces;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.meeting", cascade = CascadeType.ALL)
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

        if (id != null ? !id.equals(meeting.id) : meeting.id != null) return false;
        return name != null ? name.equals(meeting.name) : meeting.name == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", meetingPlaces=" + meetingPlaces +
                ", meetingUsers=" + meetingUsers +
                '}';
    }
}
