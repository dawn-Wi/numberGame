package com.example.numbergame.dataSource;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.numbergame.game.GameRecord;
import com.example.numbergame.Result;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
        List<GameRecord> toReturn = new ArrayList<>();
        db.collection("totalRecords")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<DocumentSnapshot> snaps = task.getResult().getDocuments();
                        for (int i = 0; i < snaps.size(); i++) {
                            GameRecord toAdd = new GameRecord((snaps.get(i).getLong("timeStamp")), snaps.get(i).getString("userId"), snaps.get(i).getLong("buttonNum").intValue());
                            toReturn.add(toAdd);
                        }
                        callback.onComplete(new Result.Success<List<GameRecord>>(toReturn));
                    } else {
                        callback.onComplete(new Result.Error(task.getException()));
                    }
                });
    }

    @Override
    public void getMyRecords(String loggedId, DataSourceCallback<Result> callback) {
        List<GameRecord> toReturn = new ArrayList<>();
        db.collection("totalRecords")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<DocumentSnapshot> snaps = task.getResult().getDocuments();
                        for (int i = 0; i < snaps.size(); i++) {
                            if (snaps.get(i).getString("userId").equals(loggedId)) {
                                GameRecord toAdd = new GameRecord((snaps.get(i).getLong("timeStamp")), loggedId, snaps.get(i).getLong("buttonNum").intValue());
                                toReturn.add(toAdd);
                            }
                        }
                        callback.onComplete(new Result.Success<List<GameRecord>>(toReturn));
                    } else {
                        callback.onComplete(new Result.Error(task.getException()));
                    }
                });
    }

    @Override
    public void addRecord(GameRecord toAdd, DataSourceCallback<Result> callback) {
        Map<String, Object> record = new HashMap<String, Object>();
        record.put("timeStamp", toAdd.getTimestamp());
        record.put("userId", toAdd.getUserId());
        record.put("buttonNum", toAdd.getButtonNum());

        db.collection("totalRecords")
                .add(record);
    }

    @Override
    public void addCompetitionRecord(GameRecord toAdd, DataSourceCallback<Result> callback) {
        Map<String, Object> record = new HashMap<String, Object>();
        record.put("timeStamp", toAdd.getTimestamp());
        record.put("userId", toAdd.getUserId());
        record.put("buttonNum", toAdd.getButtonNum());

        db.collection("competitionRecords")
                .add(record);
    }

    @Override
    public void getCompetitionRecords(DataSourceCallback<Result> callback) {
        List<GameRecord> toReturn = new ArrayList<>();
        db.collection("competitionRecords")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<DocumentSnapshot> snaps = task.getResult().getDocuments();
                        for (int i = 0; i < snaps.size(); i++) {
                            GameRecord toAdd = new GameRecord((snaps.get(i).getLong("timeStamp")), snaps.get(i).getString("userId"), snaps.get(i).getLong("buttonNum").intValue());
                            toReturn.add(toAdd);
                        }
                        callback.onComplete(new Result.Success<List<GameRecord>>(toReturn));
                    } else {
                        callback.onComplete(new Result.Error(task.getException()));
                    }
                });
    }
}
