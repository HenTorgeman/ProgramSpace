package com.example.programspace.model;


import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ModelFireBase {
    FirebaseFirestore db =  FirebaseFirestore.getInstance();


    public interface GetAllProjectsListener{
        void OnComplete(List<Project> list);
    }

    public void getAllProjects(Long lastUpdateDate, GetAllProjectsListener listener) {
        db.collection("projects")
                .whereGreaterThanOrEqualTo("lud",new Timestamp(lastUpdateDate,0))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Project> list = new LinkedList<Project>();
                        if(task.isSuccessful()){
                            QuerySnapshot querySnapshot= task.getResult();
                            for (QueryDocumentSnapshot doc : querySnapshot){
                                Project project = Project.create(doc.getData());
                                if(project != null){
                                    list.add(project);
                                }
                            }
                        }
                        listener.OnComplete(list);
                        listener.OnComplete(list);
                    }
                });
    }

    public void addProject(Project project, Model.AddProjectListener listener) {
        Map<String, Object> json = project.toJson();

        // Add a new document with a generated ID
        db.collection("projects")
                .document(String.valueOf(project.getId()))
                .set(json)
                .addOnSuccessListener(unused -> listener.OnComplete())
                .addOnFailureListener(e -> listener.OnComplete());
    }

    public void getAllUsers(Model.GetAllUsersListener listener) {
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<User> list = new LinkedList<User>();
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot doc : task.getResult()){
                                User user = User.create(doc.getData());
                                if(user != null){
                                    list.add(user);
                                }
                            }
                        }
                        listener.OnComplete(list);
                    }
                });
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

    public void getUserById(int userId, Model.GetUserById listener) {

        db.collection("users")
                .document(String.valueOf(userId))
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        User user = null;
                        if (task.isSuccessful() & task.getResult()!= null){
                            user = user.create(task.getResult().getData());
                        }
                        listener.onComplete(user);
                    }
                });


    }

    public void getProjectById(int projectId, Model.GetProjectById listener) {
        db.collection("projects")
                .document(String.valueOf(projectId))
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        Project prj = null;
                        if (task.isSuccessful() & task.getResult()!= null){
                            prj= prj.create(task.getResult().getData());
                        }
                        listener.onComplete(prj);
                    }
                });



    }


    /**
     * Firebase Storage
     */
    FirebaseStorage storage = FirebaseStorage.getInstance();


    public void saveProjectImage(Bitmap imageBitmap, String imageName, Model.saveProjectImageListener listener) {
        StorageReference storageRef = storage.getReference();
        StorageReference imgRef = storageRef.child("project_pictures/" + imageName);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imgRef.putBytes(data);
        uploadTask.addOnFailureListener(exception -> listener.onComplete(null))
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imgRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            Uri downloadUrl = uri;
                            listener.onComplete(downloadUrl.toString());
                        });
                    }
                });
    }

    public void saveUserImage(Bitmap imageBitmap, String imageName, Model.saveUserImageListener listener) {
        StorageReference storageRef = storage.getReference();
        StorageReference imgRef = storageRef.child("user_avatars/" + imageName);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imgRef.putBytes(data);
        uploadTask.addOnFailureListener(exception -> listener.onComplete(null))
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imgRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            Uri downloadUrl = uri;
                            listener.onComplete(downloadUrl.toString());
                        });
                    }
                });
    }



}
