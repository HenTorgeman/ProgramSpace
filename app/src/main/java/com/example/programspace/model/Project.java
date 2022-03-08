package com.example.programspace.model;

import android.media.Image;

import androidx.annotation.InspectableProperty;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.List;

@Entity
public class Project {

    @PrimaryKey
    @NonNull
    int id ;
    int project_admin_id;
    int collaborator_id; //Starting from null, Once user approve it join to the project

    String project_name;
    String project_des;

    Image project_image;

    int duration;
    boolean volunteer;// True=volunteer/ False=PayCheck



    Date creationDate;
    Date closeDate;


    //projectStatus status;
    //postStatus post_status;
    List<TechSkill> requirements_skills;


   /* enum projectStatus {
        DRAFT,
        OPEN,
        CLOSE
    }
    enum postStatus {
        SHARE,
        HIDE,
    }
*/
    public Project(){};

    //added constructor
    public Project(int project_admin_id, String project_name, String project_des, int duration, boolean volunteer, Date creationDate, Date closeDate, List<TechSkill> requirements_skills) {
        this.id=IdGenerator.instance.getProjectNextId();
        this.project_admin_id = project_admin_id;
        this.project_name = project_name;
        this.project_des = project_des;
        this.duration = duration;
        this.volunteer = volunteer;
        this.creationDate = creationDate;
        this.closeDate = closeDate;
        //this.status = status;
        //this.post_status = post_status;
        this.requirements_skills = requirements_skills;
    }


    public String getProject_name() {
        return project_name;
    }

    public String getProject_des() {
        return project_des;
    }
    public int getProject_admin() {
        return project_admin_id;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public void setProject_admin(int project_admin_id) {
        this.project_admin_id = project_admin_id;
    }

    public int getCollaborator() {return collaborator_id;}

    public void setCollaborator(int collaborator) {this.collaborator_id = collaborator_id;}

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public void setProject_des(String project_des) {
        this.project_des = project_des;
    }

    public Image getProject_image() {
        return project_image;
    }

    public void setProject_image(Image project_image) {
        this.project_image = project_image;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isVolunteer() {
        return volunteer;
    }

    public void setVolunteer(boolean volunteer) {
        this.volunteer = volunteer;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    /*public projectStatus getStatus() {
        return status;
    }

    public void setStatus(projectStatus status) {
        this.status = status;
    }

    public postStatus getPost_status() {
        return post_status;
    }

    public void setPost_status(postStatus post_status) {
        this.post_status = post_status;
    }*/

    public List<TechSkill> getRequirements_skills() {
        return requirements_skills;
    }

    public void setRequirements_skills(List<TechSkill> requirements_skills) {
        this.requirements_skills = requirements_skills;
    }
}
