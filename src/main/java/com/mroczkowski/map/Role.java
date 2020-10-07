package com.mroczkowski.map;

import javax.persistence.*;

public enum Role {
    USER("ROLE_USER"), ADMIN("ROLE_ADMIN");


    String roleName;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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
