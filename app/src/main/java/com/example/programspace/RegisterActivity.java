package com.example.programspace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.programspace.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txt_UserRegister;
    private EditText inp_name,inp_email,inp_password,inp_des;
    private ProgressBar pb;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth=FirebaseAuth.getInstance();

        Button registerUser=findViewById(R.id.btn_register_new);
        registerUser.setOnClickListener(this);

        this.inp_name=findViewById(R.id.inp_name_register);
        this.inp_email=findViewById(R.id.inp_email_register);
        this.inp_password=findViewById(R.id.inp_password_register);
        this.inp_des=findViewById(R.id.inp_description_register);

        this.pb=findViewById(R.id.progressBar_register);

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

        String name=inp_name.toString().trim();
        String password=inp_password.toString().trim();
        String email=inp_email.toString().trim();
        String des=inp_des.toString().trim();

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
//        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//            inp_email.setError("you must enter Valid email");
//            inp_email.requestFocus();
//            return;
//        }

        pb.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            User user=new User(name,email,password,des);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this,"User has been register Successfuly",Toast.LENGTH_LONG).show();
                                        pb.setVisibility(View.GONE);

                                    }
                                    else{
                                        Toast.makeText(RegisterActivity.this,"Failed to register! Please try again",Toast.LENGTH_LONG).show();
                                        pb.setVisibility(View.GONE);


                                    }
                                }
                            });
                        }
                    }
                });

    }
}