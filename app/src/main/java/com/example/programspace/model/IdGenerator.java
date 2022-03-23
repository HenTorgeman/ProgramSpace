package com.example.programspace.model;

import java.util.List;

public class IdGenerator {
    int userNextId;
    int projectNextId;


    public static final IdGenerator instance =new IdGenerator();

    private IdGenerator(){

       Model.instance.getAllUsers(new Model.GetAllUsersListener() {
            @Override
            public void OnComplete(List<User> list) {
                userNextId=list.size();
            }
        });

        Model.instance.getAllProjects(new Model.GetAllProjectsListener() {
            @Override
            public void OnComplete(List<Project> list) {
                projectNextId=list.size();
            }
        });
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
