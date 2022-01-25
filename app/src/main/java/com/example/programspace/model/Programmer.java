package com.example.programspace.model;

import java.util.List;

public class Programmer extends User {

    public Programmer(String description) {
        this.description = description;
    }

    public Programmer(String name, String email, String password, String description) {
        super(name, email, password);
        this.description = description;
    }

    public String description;
    public List<Project> myProjects; //Project I am Admin
    public List<Project> otherProjects; //Project I an joined
    public List<TechSkills> mySkills;

}
