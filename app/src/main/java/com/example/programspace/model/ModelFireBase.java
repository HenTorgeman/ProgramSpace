package com.example.programspace.model;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class ModelFireBase {
    FirebaseFirestore db =  FirebaseFirestore.getInstance();


    public void getAllProjects(Model.GetAllProjectsListener listener) {
        db.collection("projects")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    }
                });
    }

    public void addProject(Project project, Model.AddProjectListener listener) {
    }

    public void getAllUsers(Model.GetAllUsersListener listener) {
    }

    public void addUser(User user, Model.AddUserListener listener) {
        Map<String, Object> json = user.toJson();

        // Add a new document with a generated ID
        db.collection("users")
                .document(String.valueOf(user.getId()))
                .set(json)
               .addOnSuccessListener(unused -> listener.OnComplete())
                .addOnFailureListener(e -> listener.OnComplete());
    }

    public void getAllTechSkills(Model.GetAllTechskillsListener listener) {
    }
}
