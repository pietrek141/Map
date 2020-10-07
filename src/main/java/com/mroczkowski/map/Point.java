package com.mroczkowski.map;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class Point {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private double lat;
    private double lon;
    private String name;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="user_id")
    private AppUser appUser;

    public Point() {
    }

    public Point(double lat, double lon, String name, AppUser appUser) {
        this.lat = lat;
        this.lon = lon;
        this.name = name;
        this.appUser = appUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    @Override
    public String toString() {
        return "Point{" +
                "id=" + id +
                ", lat=" + lat +
                ", lon=" + lon +
                ", name='" + name + '\'' +
                ", appUser=" + appUser +
                '}';
    }
}
