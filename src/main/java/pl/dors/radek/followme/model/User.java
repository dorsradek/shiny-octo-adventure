package pl.dors.radek.followme.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * Created by rdors on 2016-10-24.
 */
@Entity
@Table(name = "USER")
public class User {

    private Long id;
    private String name;
    private List<MeetingUser> meetingUsers;

    public User() {
    }

    public User(String name) {
        this.name = name;
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

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.user")
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

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        return name != null ? name.equals(user.name) : user.name == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", meetingUsers=" + meetingUsers +
                '}';
    }
}
