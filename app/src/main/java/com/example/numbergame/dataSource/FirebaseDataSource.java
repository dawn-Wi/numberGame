package com.example.numbergame.dataSource;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.numbergame.GameRecord;
import com.example.numbergame.Result;
import com.example.numbergame.dataSource.DataSource;
import com.example.numbergame.dataSource.DataSourceCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FirebaseDataSource implements DataSource {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void tryRegister(String id, String password, String displayName, DataSourceCallback<Result> callback) {
        Map<String, String> user = new HashMap<>();
        user.put("id", id);
        user.put("password", password);
        user.put("displayName", displayName);

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

    @Override
    public void tryLogin(String id, String password, DataSourceCallback<Result> callback) {

    }

    @Override
    public void getAllRecords(DataSourceCallback<Result> callback) {

    }

    @Override
    public void getMyRecords(String id, DataSourceCallback<Result> callback) {

    }

    @Override
    public void addRecord(GameRecord toAdd, DataSourceCallback<Result> callback) {

    }
}
