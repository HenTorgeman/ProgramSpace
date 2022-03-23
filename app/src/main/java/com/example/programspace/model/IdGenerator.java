package com.example.programspace.model;

import android.view.Display;

import androidx.lifecycle.LiveData;

import java.util.List;

public class IdGenerator {


    int userNextId;
    int projectNextId;
    LiveData<List<Project>> data;



    public static final IdGenerator instance =new IdGenerator();

    private IdGenerator(){

        data= Model.instance.getAllProjects();
        projectNextId=data.getValue().size();
       Model.instance.getAllUsers(new Model.GetAllUsersListener() {
            @Override
            public void OnComplete(List<User> list) {
                userNextId=list.size();
            }
        });

//        Model.instance.getAllProjects(new Model.GetAllProjectsListener() {
//            @Override
//            public void OnComplete(List<Project> list) {
//                projectNextId=list.size();
//            }
//        });
    }

    public int getUserNextId() {
       this.userNextId=userNextId+1;
        return userNextId;
    }

    public int getProjectNextId() {
        this.projectNextId=userNextId+1;
        return userNextId;
    }


}
