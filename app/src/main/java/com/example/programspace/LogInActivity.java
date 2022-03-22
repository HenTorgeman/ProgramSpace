package com.example.programspace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.programspace.model.Model;
import com.example.programspace.model.User;

import org.w3c.dom.Text;

public class LogInActivity extends AppCompatActivity {

    EditText inp_Email,inp_Password;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inp_Email = findViewById(R.id.inp_login_email);
        inp_Password = findViewById(R.id.inp_login_password);
        progressBar = findViewById(R.id.main_ProgressBar);
        progressBar.setVisibility(View.GONE);

        Button register_btn=findViewById(R.id.btn_register);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //The Actual Btn.click event Listener
                open_register();
            }
        });



        Button login_btn=findViewById(R.id.btn_login);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_btn.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);
                String email=inp_Email.getText().toString().trim();
                String password=inp_Password.getText().toString().trim();

                if(email.isEmpty()){
                    progressBar.setVisibility(View.GONE);
                    inp_Email.setError("You must enter email!");
                    inp_Email.requestFocus();
                    login_btn.setEnabled(true);
                    return;
                }

                if(password.isEmpty()){
                    progressBar.setVisibility(View.GONE);
                    inp_Password.setError("You must enter password!");
                    inp_Password.requestFocus();
                    login_btn.setEnabled(true);
                    return;
                }

                //not working
                /*Model.instance.getUserByEmail(email, user -> {
                    if(user== null){
                        progressBar.setVisibility(View.GONE);
                        inp_Email.setError("User not found please register!");
                    }else {
                        if (user.getPassword() == password) {
                            open_app(user.getId());
                        } else {
                            inp_Password.setError("Incorrect password!");
                        }
                    }
                });*/

                open_app(1);


            }
        });


    }


    private void open_app(int userId){
        Intent intent=new Intent(this, ContentMainActivity.class);
        Bundle b = new Bundle();

        b.putInt("userId", userId); //Your id
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
    }
    private void open_register(){
        Intent intent=new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
}