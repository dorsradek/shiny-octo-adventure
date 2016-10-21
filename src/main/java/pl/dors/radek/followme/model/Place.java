package pl.dors.radek.followme.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by rdors on 2016-10-21.
 */
@Document
public class Place {

    @Id
    private String id;
    private GeoJsonPoint location;
    private String name;

    public Place() {
    }

    public Place(GeoJsonPoint location, String name) {
        this.location = location;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GeoJsonPoint getLocation() {
        return location;
    }

    public void setLocation(GeoJsonPoint location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Place{" +
                "id='" + id + '\'' +
                ", location=" + location +
                ", name='" + name + '\'' +
                '}';
    }
}
