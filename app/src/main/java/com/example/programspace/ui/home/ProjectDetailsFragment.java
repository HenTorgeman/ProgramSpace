package com.example.programspace.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.programspace.R;
import com.example.programspace.model.Model;
import com.example.programspace.model.Project;
import com.example.programspace.model.User;

public class ProjectDetailsFragment extends Fragment {
    Project project;
    User owner;
    int userId;
    TextView project_name_tv,project_des_tv,project_duration_tv,project_username_tv, project_date_tv;





    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_details, container, false);
        int projectId = ProjectDetailsFragmentArgs.fromBundle(getArguments()).getProjectId();

        project_name_tv=view.findViewById(R.id.projectNameDetails_Tv);
        project_des_tv=view.findViewById(R.id.projectDescriptionDetails_Tv);
        project_duration_tv=view.findViewById(R.id.projectDurationDetailsValue_Tv);
        project_username_tv=view.findViewById(R.id.projectUseNameDetailsValue_Tv);
        project_date_tv=view.findViewById(R.id.projectDateDetailsValue_Tv);

        Model.instance.getProjectById(projectId,new Model.GetProjectById() {
                    @Override
                    public void onComplete(Project project) {
                        project_name_tv.setText(project.getProject_name());
                        project_des_tv.setText(project.getProject_des());
                        project_date_tv.setText(project.getCreationDate().toString());
                        project_duration_tv.setText(String.valueOf(project.getDuration()));

                        userId=project.getProject_admin();
                        project_username_tv.setText(String.valueOf(userId));

                        Model.instance.getUserById(userId, user -> {
                            owner = user;
                            project_username_tv.setText(user.getName());
                        });





//                        getProjectUser(userId);



                    }
                });



        return view;

    }

//    public void getProjectUser(int userId){
//
//        Model.instance.getUserById(userId,new Model.GetUserById() {
//            @Override
//            public void onComplete(User user) {
//               owner=user;
//                project_username_tv.setText(user.getName());
//            }
//        });
//
//    }




//    interface OnItemClickListener{
//        void onItemClick(int position);
//    }

//    class MyViewHolder extends RecyclerView.ViewHolder{
//
//
//        public MyViewHolder(@NonNull View itemView, HomeFragment.OnItemClickListener listener) {
//            super(itemView);
//
//            TextView nameTv,descriptionTv,projectDurationTV;
//
//            nameTv = itemView.findViewById(R.id.projectName_TV);
//            descriptionTv = itemView.findViewById(R.id.projectDescription_TV);
//            projectDurationTV = itemView.findViewById(R.id.projectDuration_TV);
//
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int pos = getAdapterPosition();
//                    listener.onItemClick(pos);
//                }
//            });
//
//        }
//    }


//    class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
//        OnItemClickListener listener;
//        public void setOnItemClickListener(OnItemClickListener listener){
//            this.listener = listener;
//        }
//
//        @NonNull
//        @Override
//        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = getLayoutInflater().inflate(R.layout.project_list_row,parent,false);
//            MyViewHolder holder = new MyViewHolder(view, (HomeFragment.OnItemClickListener) listener);
//            return holder;
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//
//            holder.nameTv.setText(project.getProject_name());
//            holder.descriptionTv.setText(project.getProject_des());
//
//        }
//
//
//    }

}