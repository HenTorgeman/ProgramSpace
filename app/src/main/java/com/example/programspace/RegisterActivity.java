package com.example.programspace;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.programspace.model.Model;
import com.example.programspace.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

import java.util.List;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int SELECT_IMAGE = 2;
    private EditText inp_name,inp_email,inp_password,inp_des;
    private ProgressBar pb;

    private FirebaseAuth mAuth;
    Bitmap imageBitmap;
    ImageView avatarImv;
    ImageButton camBtn;
    ImageButton galleryBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth=FirebaseAuth.getInstance();

        Button registerUser=(Button) findViewById(R.id.btn_register_new);
        registerUser.setOnClickListener(this);

        inp_name=(EditText) findViewById(R.id.inp_name_register);
        inp_email=(EditText) findViewById(R.id.inp_email_register);
        inp_password=(EditText) findViewById(R.id.inp_password_register);
        inp_des=(EditText) findViewById(R.id.inp_description_register);


        pb=(ProgressBar) findViewById(R.id.progressBar_register);
        avatarImv = findViewById(R.id.register_imagev);
        camBtn = findViewById(R.id.register_cam_bt);
        galleryBtn = findViewById(R.id.register_gallery_bt);

        camBtn.setOnClickListener(v -> {
            openCamera();

        });
        galleryBtn.setOnClickListener(v -> {
            openGallery();

        });
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
                        imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                        avatarImv.setImageBitmap(imageBitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_register_new:
                RegisterUser();
                break;

        }
    }

    private void RegisterUser() {
        String name=inp_name.getText().toString().trim();
        String password=inp_password.getText().toString().trim();
        String email=inp_email.getText().toString().trim();
        String des=inp_des.getText().toString().trim();

        if(name.isEmpty()){
            inp_name.setError("you must enter user name!");
            inp_name.requestFocus();
            return;
        }

        if(password.isEmpty()){
            inp_password.setError("you must enter password!");
            inp_password.requestFocus();
            return;
        }

        if(password.length()<6){
            inp_password.setError("you must enter password within 6 characters minimum!");
            inp_password.requestFocus();
            return;
        }

        if(email.isEmpty()){
            inp_email.setError("you must enter email!");
            inp_email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            inp_email.setError("you must enter Valid email");
            inp_email.requestFocus();
            return;
        }

        pb.setVisibility(View.VISIBLE);

        Model.instance.getAllUsers(new Model.GetAllUsersListener() {
            @Override
            public void OnComplete(List<User> list) {

                int id=list.size()+1;
                User user=new User(id,name,email,password,des);

                if(imageBitmap != null){
                    Model.instance.saveUserImage(imageBitmap, String.valueOf(user.getId()) + ".jpg", url -> {
                        user.setImageUrl(url);
                        Model.instance.addUser(user,()->{
                            pb.setVisibility(View.GONE);
                            openFeed(id);
                        });
                    });
                }else {
                    Model.instance.addUser(user,()->{
                        pb.setVisibility(View.GONE);
                        openFeed(id);

                    });
                }




                        }
                    });

    }

    private void openFeed(int id) {
        Intent intent=new Intent(this, ContentMainActivity.class);
        startActivity(intent);

    }

}