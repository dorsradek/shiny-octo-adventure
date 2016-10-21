package pl.dors.radek.followme.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by rdors on 2016-10-21.
 */
@Document
public class Meeting {

    @Id
    private String id;
    @DBRef
    private List<Place> places;

    public Meeting() {
    }

    public Meeting(List<Place> places) {
        this.places = places;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "id=" + id +
                ", places=" + places +
                '}';
    }
}
