package com.example.programspace.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.programspace.R;
import com.example.programspace.model.Model;
import com.example.programspace.model.Project;
import com.example.programspace.model.User;
import com.squareup.picasso.Picasso;

public class ProjectDetailsFragment extends Fragment {
    Project project;
    User owner;

    TextView project_name_tv,project_des_tv,project_duration_tv,project_username_tv, project_date_tv;
    ImageView project_image;
    Button edit_btn;





    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_details, container, false);
        int projectId = ProjectDetailsFragmentArgs.fromBundle(getArguments()).getProjectId();

        int logInUserId = getArguments().getInt("userId");

        project_name_tv=view.findViewById(R.id.projectNameDetails_Tv);
        project_des_tv=view.findViewById(R.id.projectDescriptionDetails_Tv);
        project_duration_tv=view.findViewById(R.id.projectDurationDetailsValue_Tv);
        project_username_tv=view.findViewById(R.id.projectUseNameDetailsValue_Tv);
        project_date_tv=view.findViewById(R.id.projectDateDetailsValue_Tv);
        project_image=view.findViewById(R.id.projectImgDetails_ImgV);
        edit_btn=view.findViewById(R.id.Edit_PostDetails_Btn);
        edit_btn.setVisibility(View.GONE);

        Model.instance.getProjectById(projectId,new Model.GetProjectById() {
                    @Override
                    public void onComplete(Project project) {

                        project_name_tv.setText(project.getProject_name());
                        project_des_tv.setText(project.getProject_des());
                        project_date_tv.setText(project.getCreationDate().toString());
                        project_duration_tv.setText(String.valueOf(project.getDuration()));

                        int userId=project.getProject_admin();
                        project_username_tv.setText(String.valueOf(userId));

                        if (project.getImageUrl() != null) {
                            Picasso.get().load(project.getImageUrl()).into(project_image);
                        }
                        if(logInUserId==userId) {
                            edit_btn.setVisibility(View.VISIBLE);
                        }
                        Model.instance.getUserById(userId, user -> {
                            owner = user;
                            project_username_tv.setText(user.getName());
                        });

                    }
                });



        return view;

    }


}