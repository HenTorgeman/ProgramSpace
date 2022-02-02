package com.example.programspace.model.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.programspace.R;
import com.example.programspace.databinding.FragmentHomeBinding;
import com.example.programspace.model.Model;
import com.example.programspace.model.Project;

import java.util.List;


public class HomeFragment extends Fragment {

    List<Project> data;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        data = Model.instance.getAllProjects(); //using instance

        RecyclerView list = view.findViewById(R.id.projectlist_rv);
        list.setHasFixedSize(true);

        list.setLayoutManager(new LinearLayoutManager(getContext()));

        com.example.programspace.model.ui.home.HomeFragment.MyAdapter adapter = new com.example.programspace.model.ui.home.HomeFragment.MyAdapter();
        list.setAdapter(adapter);

        //add project button
        /*View viewById = view.findViewById(R.id.studentlist_add_btn);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(v).navigate(R.id.action_nav_home_to_add_student);
            }
        });*/

        //on project click in list
        /*adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String id = data.get(position).getId();
                Navigation.findNavController(view).navigate(HomeFragmentDirections.actionNavHomeToStudentDetailsFragment2(id,Integer.toString(position)));

            }
        });*/
        return view;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nameTv;
        TextView pronameTV;
        TextView descriptionTV;


        public MyViewHolder(@NonNull View itemView, com.example.programspace.model.ui.home.HomeFragment.OnItemClickListener listener) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.list_row_name);
            pronameTV = itemView.findViewById(R.id.list_row_project_name);
            descriptionTV = itemView.findViewById(R.id.list_row_description);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    listener.onItemClick(pos);
                }
            });
        }
    }

    interface OnItemClickListener{
        void onItemClick(int position);
    }
    class MyAdapter extends RecyclerView.Adapter<com.example.programspace.model.ui.home.HomeFragment.MyViewHolder>{

        com.example.programspace.model.ui.home.HomeFragment.OnItemClickListener listener;
        public void setOnItemClickListener(com.example.programspace.model.ui.home.HomeFragment.OnItemClickListener listener){
            this.listener = listener;
        }

        @NonNull
        @Override
        public com.example.programspace.model.ui.home.HomeFragment.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.project_list_row,parent,false);
            com.example.programspace.model.ui.home.HomeFragment.MyViewHolder holder = new com.example.programspace.model.ui.home.HomeFragment.MyViewHolder(view,listener);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull com.example.programspace.model.ui.home.HomeFragment.MyViewHolder holder, int position) {
            Project project = data.get(position);
            holder.nameTv.setText(project.getProject_name());
            holder.descriptionTV.setText(project.getProject_des());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }
}