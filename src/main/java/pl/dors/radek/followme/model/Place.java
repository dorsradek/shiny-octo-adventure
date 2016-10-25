package pl.dors.radek.followme.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by rdors on 2016-10-21.
 */
@Entity
@Table(name = "PLACE")
public class Place {

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "NAME", nullable = false, length = 20)
    private String name;

    @Column(name = "X")
    private double x;

    @Column(name = "Y")
    private double y;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.place", cascade = CascadeType.ALL)
    private List<MeetingPlace> meetingPlaces;

    public Place() {
    }

    public Place(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
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

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public List<MeetingPlace> getMeetingPlaces() {
        return meetingPlaces;
    }

    public void setMeetingPlaces(List<MeetingPlace> meetingPlaces) {
        this.meetingPlaces = meetingPlaces;
    }

    @Override
    public String toString() {
        return "Place{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

}
