package com.example.numbergame.dataSource;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.numbergame.GameRecord;
import com.example.numbergame.Result;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public void getId(DataSourceCallback<Result> callback) {
        List<String> toReturn = new ArrayList<>();
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> snaps = task.getResult().getDocuments();
                            for (int i = 0; i < snaps.size(); i++) {
                                String toAdd = new String((snaps.get(i).getString("id")));
                                toReturn.add(toAdd);
                            }
                            callback.onComplete(new Result.Success<List<String>>(toReturn));
                        } else {
                            callback.onComplete(new Result.Error(task.getException()));
                        }
                    }
                });
    }

    @Override
    public void tryLogin(String id, String password, DataSourceCallback<Result> callback) {
        db.collection("users")
                .document(id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                if (document.get("password").equals(password)) {
                                    callback.onComplete(new Result.Success<String>("Success"));
                                } else {
                                    callback.onComplete(new Result.Error(new Exception("Failed")));
                                }
                            } else {
                                callback.onComplete(new Result.Error(new Exception("Failed")));
                            }
                        } else {
                            callback.onComplete(new Result.Error(new Exception("Failed")));
                        }
                    }
                });
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
