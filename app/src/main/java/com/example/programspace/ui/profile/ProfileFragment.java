package com.example.programspace.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.programspace.R;
import com.example.programspace.databinding.FragmentProfileBinding;
import com.example.programspace.model.Model;
import com.example.programspace.model.User;
import com.squareup.picasso.Picasso;


public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private FragmentProfileBinding binding;
    ImageView profile_Image;
    TextView name,description;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        int logInUserId = getArguments().getInt("userId");
        //userId = logInUserId;

        name= root.findViewById(R.id.UserName_Profile_tv);
        description = root.findViewById(R.id.AboutUser_Profile_tv);
        profile_Image = root.findViewById(R.id.PhotoUser_Profile_iv);

        Model.instance.getUserById(logInUserId, user -> {
           name.setText(user.getName());
           description.setText(user.getDescription());
            if (user.getImageUrl() != null) {
                Picasso.get().load(user.getImageUrl()).into(profile_Image);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}