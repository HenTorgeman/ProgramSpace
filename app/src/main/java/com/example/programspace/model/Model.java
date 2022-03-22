package com.example.programspace.model;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.RequiresApi;
import androidx.core.os.HandlerCompat;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {
    public static final Model instance = new Model();
    Executor executor= Executors.newFixedThreadPool(1);
    Handler mainThread = HandlerCompat.createAsync(Looper.getMainLooper());

    ModelFireBase modelFireBase =  new ModelFireBase();




    public interface saveProjectImageListener{
        void onComplete(String url);
    }
    public void saveProjectImage(Bitmap imageBitmap, String imageName, saveProjectImageListener listener) {
        modelFireBase.saveProjectImage(imageBitmap,imageName,listener);
    }


    public interface GetAllProjectsListener{
        void OnComplete(List<Project> list);
    }
    public void getAllProjects(GetAllProjectsListener listener){
        modelFireBase.getAllProjects(listener);

    }

    public interface AddProjectListener{
        void OnComplete();
    }
    public void addProject(Project project, AddProjectListener listener){
        modelFireBase.addProject(project, listener);
    }

    public interface GetAllUsersListener{
        void OnComplete(List<User> list);
    }
    public void getAllUsers(GetAllUsersListener listener){
        modelFireBase.getAllUsers(listener);


    }


    public interface AddUserListener{
        void OnComplete();
    }
    public void addUser(User user, AddUserListener listener){
        modelFireBase.addUser( user,  listener);

    }

    public interface GetUserById {
        void onComplete(User user);
    }

    public User getUserById(int userId, GetUserById listener) {
        modelFireBase.getUserById(userId, listener);
        return null;
    }

    public interface GetUserByEmail{
        void onComplete(User user);
    }

    public User getUserByEmail(String userEmail, GetUserByEmail listener) {
        modelFireBase.getUserByEmail(userEmail, listener);
        return null;
    }

    public interface GetAllTechskillsListener{
        void OnComplete(List<TechSkill> list);
    }
    public void getAllTechSkills(GetAllTechskillsListener listener){
        modelFireBase.getAllTechSkills(listener);

    }

    public interface GetProjectById {
        void onComplete(Project project);
    }

    public User getProjectById(int projectId, GetProjectById listener) {
        modelFireBase.getProjectById(projectId,listener);
        return null;
    }


    /*public void editProject(Project project, int index) {
        data.set(index, project);
    }

    public Project getProjectById(String projectid) {
        for (Project p:data
        ) {
            if (p.getId().equals(studentId)){
                return p;
            }
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void removeStudent(String id) {
        data.removeIf(s -> s.getId().equals(id));
    }*/
}
