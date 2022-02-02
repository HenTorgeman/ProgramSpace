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

    public Programmer getProject_admin() {
        return project_admin;
    }

    public String getProject_name() {
        return project_name;
    }

    public String getProject_des() {
        return project_des;
    }

    public Date creationDate;
    public Date closeDate;

    public projectStatus status;
    public postStatus post_status;

    //added constructor
    public Project(Programmer project_admin, Programmer collaborator, String project_name, String project_des, int duration, boolean volunteer, Date creationDate, Date closeDate, projectStatus status, postStatus post_status, List<TechSkills> requirements_skills) {
        this.project_admin = project_admin;
        this.collaborator = collaborator;
        this.project_name = project_name;
        this.project_des = project_des;
        this.duration = duration;
        this.volunteer = volunteer;
        this.creationDate = creationDate;
        this.closeDate = closeDate;
        this.status = status;
        this.post_status = post_status;
        this.requirements_skills = requirements_skills;
    }

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
