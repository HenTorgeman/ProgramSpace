package com.example.programspace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

                //The Actual Btn.click event Listener
                open_profile();
            }
        });


    }

    private void open_profile(){
        Intent intent=new Intent(this,UserProfileActivity.class);
        startActivity(intent);
    }
    private void open_register(){
        Intent intent=new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
}