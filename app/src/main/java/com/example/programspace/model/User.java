package com.example.programspace.model;

import android.media.Image;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Entity
public class User {

    @PrimaryKey
    @NonNull
    private int id;
    String name= "",email="",password="";
    Image user_image;
    String description;
    //List<Project> myProjects; //Project I am Admin
    //List<Project> otherProjects; //Project I an joined
    //List<TechSkill> mySkills;

    public  User(){}

    public User(String name, String email, String password, String description) {
      //  this.id = IdGenerator.instance.getUserNextId();
        this.name = name;
        this.email = email;
        this.password = password;
        //this.user_image = user_image;
        this.description = description;
        //this.mySkills = mySkills;
        //myProjects= new LinkedList<Project>();
        //otherProjects= new LinkedList<Project>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Image getUser_image() {
        return user_image;
    }

    public void setUser_image(Image user_image) {
        this.user_image = user_image;
    }


    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("id",String.valueOf(id));
        json.put("name",name);
        json.put("password",password);
        json.put("email",email);
        json.put("description",description);
        json.put("image",user_image);
        //json.put("user's projects", myProjects);
        //json.put("user's skills", mySkills);
        //json.put("other projects",otherProjects);

        return json;
    }
    public static User create(Map<String, Object> json) {
       int id = (int) json.get("id");
       String name = (String) json.get("name");
       String password = (String) json.get("password");
       String email = (String) json.get("email");
       String description = (String) json.get("description");
       Image image = (Image) json.get("image");

       User user = new User(name,email,password,description);
       user.setId(id);

       return user;
    }
}
