package com.example.programspace.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Display;

import androidx.annotation.RequiresApi;
import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.programspace.MyApplication;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import kotlin.collections.AbstractMutableList;

public class Model {
    public static final Model instance = new Model();
    Executor executor= Executors.newFixedThreadPool(1);
    Handler mainThread = HandlerCompat.createAsync(Looper.getMainLooper());

    ModelFireBase modelFireBase =  new ModelFireBase();

    private Model(){
        projectListLoadingState.setValue(ProjectListLoadingState.loaded);
    }
    public enum ProjectListLoadingState {
        loading,
        loaded
    }


    MutableLiveData<ProjectListLoadingState> projectListLoadingState = new MutableLiveData<ProjectListLoadingState>();

    public MutableLiveData<ProjectListLoadingState> getProjectListLoadingState() {
        return projectListLoadingState;
    }


    MutableLiveData<List<Project>> projectslist = new MutableLiveData<List<Project>>();
    public LiveData<List<Project>>getAllProjects(){
        if(projectslist.getValue() == null ){refreshProjectList();}
        return projectslist;
    }

    public void refreshProjectList(){
        projectListLoadingState.setValue(ProjectListLoadingState.loading);


        Long lastUpdateDate = MyApplication.getContext().getSharedPreferences("TAG", Context.MODE_PRIVATE).getLong("StudentsLastUpdateDate", 0);

        executor.execute(() -> {
            List<Project> stList = AppLocalDb.db.projectDao().getAll();
            projectslist.postValue(stList);
        });

        // firebase get all updates since lastLocalUpdateDate
        modelFireBase.getAllProjects(lastUpdateDate,new ModelFireBase.GetAllProjectsListener() {
            @Override
            public void OnComplete(List<Project> list) {
                // add all records to the local db
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        Long lud = new Long(0);
                        Log.d("TAG", "fb returned " + list.size());
                        for (Project project : list) {
                            AppLocalDb.db.projectDao().insertAll(project);
                            if (lud < project.getUpdateDate()) {
                                lud = project.getUpdateDate();
                            }
                        }
                        // update last local update date
                        MyApplication.getContext()
                                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                                .edit()
                                .putLong("StudentsLastUpdateDate", lud)
                                .commit();

                        //return all data to caller
                        List<Project> stList = AppLocalDb.db.projectDao().getAll();
                        projectslist.postValue(stList);
                        projectListLoadingState.postValue(ProjectListLoadingState.loaded);
                    }
                });
            }
        });
    }

    public interface AddProjectListener{
        void OnComplete();
    }
    public void addProject(Project project, AddProjectListener listener){
        modelFireBase.addProject(project, listener);
        refreshProjectList();
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


    public interface GetProjectById {
        void onComplete(Project project);
    }

    public User getProjectById(int projectId, GetProjectById listener) {
        modelFireBase.getProjectById(projectId,listener);
        return null;
    }

    public interface saveProjectImageListener{
        void onComplete(String url);
    }
    public void saveProjectImage(Bitmap imageBitmap, String imageName, saveProjectImageListener listener) {
        modelFireBase.saveProjectImage(imageBitmap,imageName,listener);
    }

    public interface saveUserImageListener{
        void onComplete(String url);
    }
    public void saveUserImage(Bitmap imageBitmap, String imageName, saveUserImageListener listener) {
        modelFireBase.saveUserImage(imageBitmap,imageName,listener);
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
