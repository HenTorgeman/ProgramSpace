package com.example.programspace.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.programspace.R;
import com.example.programspace.model.Model;
import com.example.programspace.model.Project;
import com.example.programspace.model.User;
import com.squareup.picasso.Picasso;

public class EditProjectFragment extends Fragment {

    Project project;
    User owner;

    TextView project_name_tv,project_des_tv,project_duration_tv;
    CheckBox isVolunteer_cb;
    ImageView project_image;
    Button save_btn;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_project, container, false);
        int projectId = ProjectDetailsFragmentArgs.fromBundle(getArguments()).getProjectId();

        int logInUserId = getArguments().getInt("userId");


        project_name_tv=view.findViewById(R.id.projecrNameEdit_Et);
        project_des_tv=view.findViewById(R.id.projecrDesEdit_Et);
        project_duration_tv=view.findViewById(R.id.projectDurationDetailsValue_Tv);
        project_image=view.findViewById(R.id.projectImgDetails_ImgV);
        isVolunteer_cb=view.findViewById(R.id.volunteerEdit_cb);
        save_btn=view.findViewById(R.id.Save_PostEdit_Btn);

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String new_project_name_tv,new_project_des_tv,new_project_duration_tv;
                boolean new_isVolunteer_cb;
                String new_project_image;

                new_project_name_tv=project_name_tv.getText().toString();
                new_project_des_tv=project_des_tv.getText().toString();
                new_isVolunteer_cb=isVolunteer_cb.isChecked();
                new_project_duration_tv=project_duration_tv.getText().toString();

                Model.instance.getProjectById(projectId,new Model.GetProjectById() {
                    @Override
                    public void onComplete(Project project) {

                        project.setProject_name(new_project_name_tv);
                        project.setProject_des(new_project_des_tv);

                    }
                });



               // Navigation.findNavController(view).navigate(ProjectDetailsFragmentDirections.actionProjectDetailsFragmentToEditProjectFragment(projectId));
            }
        });

        Model.instance.getProjectById(projectId,new Model.GetProjectById() {
            @Override
            public void onComplete(Project project) {

                project_name_tv.setText(project.getProject_name());
                project_des_tv.setText(project.getProject_des());
                project_duration_tv.setText(String.valueOf(project.getDuration()));
                if(project.isVolunteer()){
                    isVolunteer_cb.toggle();
                }

            }
        });



        return view;

    }



}