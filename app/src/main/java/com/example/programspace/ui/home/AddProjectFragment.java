package com.example.programspace.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.programspace.R;
import com.example.programspace.model.Model;
import com.example.programspace.model.Project;

import java.util.Date;


public class AddProjectFragment extends Fragment {

    EditText nameEt;
    EditText descriptionEt;
    EditText durationEt;
    CheckBox cbVol;
    Button saveBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_add_project, container, false);
        nameEt = view.findViewById(R.id.inp_ProjectName);
        descriptionEt = view.findViewById(R.id.inp_ProjectDescription);
        durationEt = view.findViewById(R.id.inp_ProjectDuration);
        cbVol = view.findViewById(R.id.volunteer_cb);
        saveBtn = view.findViewById(R.id.postProject_Bt);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        return view;
    }

    private void save() {
        saveBtn.setEnabled(false);


        String name = nameEt.getText().toString();
        String desc = descriptionEt.getText().toString();
        String durationString= durationEt.getText().toString();
        int duration= Integer.parseInt(durationString);
        boolean flagVol = cbVol.isChecked();
        Date date= new Date();
        Project project = new Project(1,name,desc,duration,flagVol,date);

        Model.instance.addProject(project,()->{
            Navigation.findNavController(nameEt).navigateUp();
        });

    }
}