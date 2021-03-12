package com.mroczkowski.map.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public enum Category {
    SECRET, FAVOURITE();

    String roleName;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    Category() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

/*    @OneToOne(mappedBy = "role")
    private AppUser appUser;*/
}

