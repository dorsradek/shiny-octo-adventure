package pl.dors.radek.followme.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rdors on 2016-10-21.
 */
@Document
public class Meeting {

    @Id
    private String id;
    private String name;
    @DBRef
    private List<Place> places;
    @DBRef
    private List<User> users;

    public Meeting() {
    }

    public Meeting(String name, List<Place> places, List<User> users) {
        this.name = name;
        this.places = places;
        this.users = users;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Place> getPlaces() {
        if (places == null) {
            places = new ArrayList<>();
        }
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public List<User> getUsers() {
        if (users == null) {
            users = new ArrayList<>();
        }
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Meeting meeting = (Meeting) o;

        if (id != null ? !id.equals(meeting.id) : meeting.id != null) return false;
        if (name != null ? !name.equals(meeting.name) : meeting.name != null) return false;
        if (places != null ? !places.equals(meeting.places) : meeting.places != null) return false;
        return users != null ? users.equals(meeting.users) : meeting.users == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (places != null ? places.hashCode() : 0);
        result = 31 * result + (users != null ? users.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", places=" + places +
                ", users=" + users +
                '}';
    }
}
