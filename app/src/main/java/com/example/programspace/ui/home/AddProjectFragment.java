package com.example.programspace.ui.home;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.programspace.R;
import com.example.programspace.databinding.FragmentAddProjectBinding;
import com.example.programspace.databinding.FragmentHomeBinding;
import com.example.programspace.model.Model;
import com.example.programspace.model.Project;
import com.example.programspace.model.User;

import java.io.IOException;
import java.util.Date;


public class AddProjectFragment extends Fragment {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int SELECT_IMAGE = 2;
    EditText nameEt;
    EditText descriptionEt;
    EditText durationEt;
    CheckBox cbVol;
    Button saveBtn;
    Bitmap imageBitmap;
    ImageView avatarImv;
    ImageButton camBtn;
    ImageButton galleryBtn;

    int userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =inflater.inflate(R.layout.fragment_add_project, container, false);
//        User user = Model.instance.getUserById(getArguments().getInt("userId"));

        userId=getArguments().getInt("userId");

        nameEt = view.findViewById(R.id.inp_ProjectName);
        descriptionEt = view.findViewById(R.id.inp_ProjectDescription);
        durationEt = view.findViewById(R.id.inp_ProjectDuration);
        cbVol = view.findViewById(R.id.volunteer_cb);
        saveBtn = view.findViewById(R.id.Edit_PostDetails_Btn);
        avatarImv = view.findViewById(R.id.add_project_im);

        camBtn = view.findViewById(R.id.add_project_cam_btn);
        galleryBtn = view.findViewById(R.id.add_project_gallery_btn);


        camBtn.setOnClickListener(v -> {
            openCamera();

        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        galleryBtn.setOnClickListener(v -> {
            openGallery();

        });

        return view;
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECT_IMAGE);
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAPTURE){
            if(resultCode == RESULT_OK){
                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");
                avatarImv.setImageBitmap(imageBitmap);
            }
        }else if(requestCode == SELECT_IMAGE){
            if(resultCode == RESULT_OK) {
                if (data != null) {
                    try {
                        imageBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                        avatarImv.setImageBitmap(imageBitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void save() {
        saveBtn.setEnabled(false);

        String name = nameEt.getText().toString();
        String desc = descriptionEt.getText().toString();
        String durationString= durationEt.getText().toString();
        int duration= Integer.parseInt(durationString);
        boolean flagVol = cbVol.isChecked();
        Date date= new Date();
        Project project = new Project(userId,name,desc,duration,flagVol,date);
        if(imageBitmap != null){
        Model.instance.saveProjectImage(imageBitmap, String.valueOf(project.getId()) + ".jpg", url -> {
            project.setImageUrl(url);
            Model.instance.addProject(project, () -> {
                Navigation.findNavController(nameEt).navigateUp();
            });
        });
        }else {
            Model.instance.addProject(project, () -> {
                Navigation.findNavController(nameEt).navigateUp();
            });
        }
    }
}