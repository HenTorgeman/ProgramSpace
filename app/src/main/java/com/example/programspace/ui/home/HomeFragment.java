package com.example.programspace.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.programspace.R;
import com.example.programspace.databinding.FragmentHomeBinding;
import com.example.programspace.model.Model;
import com.example.programspace.model.Project;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class HomeFragment extends Fragment {

    HomeViewModel viewModel;
    int userId;
    FragmentHomeBinding binding;
    MyAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        int temp = getArguments().getInt("userId");
        userId = temp;
        swipeRefreshLayout = root.findViewById(R.id.projectlist_swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(() -> Model.instance.refreshProjectList());



        RecyclerView list = root.findViewById(R.id.projectlist_rv);
        list.setHasFixedSize(true);

        list.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter= new MyAdapter();
        list.setAdapter(adapter);



        //on project click in list
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                int id = viewModel.getData().getValue().get(position).getId();
                Navigation.findNavController(root).navigate(HomeFragmentDirections.actionNavigationHomeToProjectDetailsFragment(id));

            }
        });

        viewModel.getData().observe(getViewLifecycleOwner(), projects -> Refresh());
        swipeRefreshLayout.setRefreshing(Model.instance.getProjectListLoadingState().getValue() == Model.ProjectListLoadingState.loading);
        Model.instance.getProjectListLoadingState().observe(getViewLifecycleOwner(), projectListLoadingState -> {
            if(projectListLoadingState == Model.ProjectListLoadingState.loading){
                swipeRefreshLayout.setRefreshing(true);
            }else{
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return root;
    }

    private void Refresh() {
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
        //swipe
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView avatarImv;
        ImageView projectImv;
        TextView nameTv;
        TextView pronameTV;
        TextView descriptionTV;
        TextView monthesTV;
        TextView dateTV;
        CheckBox volCB;


        public MyViewHolder(@NonNull View itemView, com.example.programspace.ui.home.HomeFragment.OnItemClickListener listener) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.list_row_name);
            pronameTV = itemView.findViewById(R.id.list_row_project_name);
            descriptionTV = itemView.findViewById(R.id.list_row_description);
            monthesTV = itemView.findViewById(R.id.list_row_monthesnum);
            dateTV = itemView.findViewById(R.id.list_row_date_tv);
            volCB = itemView.findViewById(R.id.list_row_volcb);
            avatarImv = itemView.findViewById(R.id.list_row_avatar_imv);
            projectImv = itemView.findViewById(R.id.list_row_project_imgv);

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
    class MyAdapter extends RecyclerView.Adapter<com.example.programspace.ui.home.HomeFragment.MyViewHolder>{

        com.example.programspace.ui.home.HomeFragment.OnItemClickListener listener;
        public void setOnItemClickListener(com.example.programspace.ui.home.HomeFragment.OnItemClickListener listener){
            this.listener = listener;
        }

        @NonNull
        @Override
        public com.example.programspace.ui.home.HomeFragment.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.project_list_row,parent,false);
            com.example.programspace.ui.home.HomeFragment.MyViewHolder holder = new com.example.programspace.ui.home.HomeFragment.MyViewHolder(view,listener);
            return holder;
        }

        //TODO::add the poster information in oncomplete get user put function bind
        @Override
        public void onBindViewHolder(@NonNull com.example.programspace.ui.home.HomeFragment.MyViewHolder holder, int position) {
            Project project = viewModel.getData().getValue().get(position);
            Date date = project.getCreationDate();
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            String datest = formatter.format(date);
            holder.dateTV.setText(datest);
            holder.volCB.setChecked(project.isVolunteer());
            holder.pronameTV.setText(project.getProject_name());
            holder.descriptionTV.setText(project.getProject_des());
            holder.monthesTV.setText(Integer.toString(project.getDuration()));
            holder.avatarImv.setImageResource(R.drawable.profile);
            if (project.getImageUrl() != null) {
                Picasso.get().load(project.getImageUrl()).into(holder.projectImv);
            }
        }

        @Override
        public int getItemCount() {
            if(viewModel.getData().getValue()==null)
                return 0;
            return viewModel.getData().getValue().size();
        }
    }

}