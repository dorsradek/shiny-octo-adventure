package pl.dors.radek.followme.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by rdors on 2016-10-21.
 */
@Entity
@Table(name = "PLACE")
public class Place {

    private Long id;
    private String name;
    private double x;
    private double y;
    private Meeting meeting;

    public Place() {
    }

    public Place(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
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

    @Column(name = "NAME", length = 60)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEETING_ID")
    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Place place = (Place) o;

        if (Double.compare(place.x, x) != 0) return false;
        if (Double.compare(place.y, y) != 0) return false;
        if (id != null ? !id.equals(place.id) : place.id != null) return false;
        return name != null ? name.equals(place.name) : place.name == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        temp = Double.doubleToLongBits(x);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", meeting=" + meeting +
                '}';
    }

}
