package com.example.programspace.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Model {
    public static final Model instance = new Model();

    private Model(){
        Programmer pro = new Programmer("Testuser", "Testuse@email", "123456", "about");
        for(int i=0;i<1;i++){
            Project p = new Project(pro,null,"Testproject"+i,"about",10,false,new Date(),new Date(),null,null,null);
            data.add(p);
        }
    }

    List<Project> data = new LinkedList<Project>();

    public List<Project> getAllProjects(){
        return data;
    }

    public void addProject(Project project){
        data.add(project);
    }

    public void editProject(Project project, int index) {
        data.set(index, project);
    }

    /*public Project getProjectById(String projectid) {
        for (Project p:data
        ) {
            if (p.getId().equals(studentId)){
                return p;
            }
        }
        return null;
    }*/

    /*@RequiresApi(api = Build.VERSION_CODES.N)
    public void removeStudent(String id) {
        data.removeIf(s -> s.getId().equals(id));
    }*/
}
