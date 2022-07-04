package com.example.numbergame;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FirebaseDataSource {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void tryRegister(String id, String password, String displayname, DataSourceCallback<Result> callback){
        Map<String,String> user = new HashMap<>();
        user.put("id",id);
        user.put("password",password);
        user.put("displayname",displayname);

        db.collection("users")
                .document(id)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("datasource", "onSuccess: firestore finish");
                        callback.onComplete(new Result.Success<String>("Success"));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("datasource", "onSuccess: firestore not finish");
                        callback.onComplete(new Result.Error(new Exception("Failed")));
                    }
                });
    }

    public interface DataSourceCallback<T>{
        void onComplete(T result);
    }

}
