package com.example.programspace.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.programspace.model.Model;
import com.example.programspace.model.Project;

import java.util.List;

public class HomeViewModel extends ViewModel {
    LiveData<List<Project>> data;

    public HomeViewModel(){
        this.data = Model.instance.getAllProjects();
    }

    public  LiveData<List<Project>> getData() {
        return data;
    }


}