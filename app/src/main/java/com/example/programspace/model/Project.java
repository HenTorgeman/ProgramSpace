package com.example.programspace.model;

import android.media.Image;

import androidx.annotation.InspectableProperty;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Project {

    @PrimaryKey
    @NonNull
    private int id ;
    int project_admin_id;
    int collaborator_id; //Starting from null, Once user approve it join to the project

    String project_name;
    String project_des;

    int duration;
    boolean volunteer;// True=volunteer/ False=PayCheck
    boolean isDeleted;

    Date creationDate;
    Date closeDate;


    //projectStatus status;
    //postStatus post_status;
    //List<TechSkill> requirements_skills;


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
    public Project(int project_admin_id, String project_name, String project_des, int duration, boolean volunteer, Date creationDate ) {
        this.id=IdGenerator.instance.getProjectNextId();
        this.project_admin_id = project_admin_id;
        this.collaborator_id = 0;
        this.project_name = project_name;
        this.project_des = project_des;
        this.duration = duration;
        this.volunteer = volunteer;
        this.isDeleted = false;
        this.creationDate = new Date();
        this.closeDate =null;
        //this.status = status;
        //this.post_status = post_status;
        //this.requirements_skills = requirements_skills;
    }
    public Project(int project_admin_id,int collaborator_id, String project_name, String project_des,int duration, boolean volunteer, boolean isDeleted, Date creationDate, Date closeDate) {
        this.project_admin_id = project_admin_id;
        this.collaborator_id = collaborator_id;
        this.project_name = project_name;
        this.project_des = project_des;
        this.duration = duration;
        this.volunteer = volunteer;
        this.isDeleted = isDeleted;
        this.creationDate = creationDate;
        this.closeDate = closeDate;
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

    //public List<TechSkill> getRequirements_skills() {return requirements_skills;}

    //public void setRequirements_skills(List<TechSkill> requirements_skills) { this.requirements_skills = requirements_skills; }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put("id",String.valueOf(id));
        json.put("project_admin_id",String.valueOf(project_admin_id));
        json.put("collaborator_id",String.valueOf(collaborator_id));
        json.put("project_name",project_name);
        json.put("project_des",project_des);
        json.put("duration",String.valueOf(duration));
        json.put("volunteer",volunteer);
        json.put("isDeleted",isDeleted);
        json.put("creationDate",creationDate);
        json.put("closeDate",closeDate);


        return json;

    }
    public static Project create(Map<String, Object> data) {
        int id = Integer.parseInt((String)data.get("id"));
        int project_admin_id = Integer.parseInt((String)data.get("project_admin_id"));
        int collaborator_id = Integer.parseInt((String)data.get("collaborator_id"));
        String project_name = (String)data.get("project_name");
        String project_des = (String)data.get("project_des");
        int duration = Integer.parseInt((String)data.get("duration"));
        boolean volunteer =(boolean)data.get("volunteer");
        boolean isDeleted =(boolean)data.get("isDeleted");
        Timestamp timeCreation = (Timestamp)data.get("creationDate");
        Date creationDate = timeCreation.toDate();
        Timestamp timeClose = (Timestamp)data.get("creationDate");
        Date closeDate = timeClose.toDate();

        Project project =new Project(project_admin_id,collaborator_id,project_name,project_des,duration,volunteer,isDeleted,creationDate,closeDate);
        project.setId(id);

        return project;


    }
}
