package com.example.programspace.model;

import android.media.Image;

import androidx.room.TypeConverter;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public class Converters {

    @TypeConverter
    public String fromImageToString(Image image){
        return new Gson().toJson(image);
    }

    @TypeConverter
    public Image fromStringToImage(String stringImage){
        return new Gson().fromJson(stringImage,Image.class);
    }

    @TypeConverter
    public String fromUserToString(User user){
        return new Gson().toJson(user);
    }

    @TypeConverter
    public User fromStringToUser(String stringUser){
        return new Gson().fromJson(stringUser,User.class);
    }

    @TypeConverter
    public String fromDateToString(Date date){
        return new Gson().toJson(date);
    }

    @TypeConverter
    public Date fromStringToDate(String stringDate){
        return new Gson().fromJson(stringDate,Date.class);
    }

    @TypeConverter
    public String fromProjectListToString(List<Project> projectList) {
        if (projectList == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Project>>() {}.getType();
        String json = gson.toJson(projectList, type);
        return json;
    }

    @TypeConverter
    public List<Project> fromStringToProjectList(String stringList){
        if (stringList == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Project>>() {}.getType();
        List<Project> projectList = gson.fromJson(stringList, type);
        return projectList;
    }




}
