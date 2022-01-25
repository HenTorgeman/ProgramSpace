package com.example.programspace.model;

import java.util.Date;
import java.util.List;

public class Project {

    public Programmer project_admin;
    public Programmer collaborator; //Starting from null, Once user approve it join to the project

    public String project_name;
    public String project_des;

    public int duration;
    public boolean volunteer;// True=volunteer/ False=PayCheck

    public Date creationDate;
    public Date closeDate;

    public projectStatus status;
    public postStatus post_status;

    public List<TechSkills> requirements_skills;


    enum projectStatus {
        DRAFT,
        OPEN,
        CLOSE
    }
    enum postStatus {
        SHARE,
        HIDE,
    }

}
