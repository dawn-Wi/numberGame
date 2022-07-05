package com.example.numbergame.dataSource;

import com.example.numbergame.GameRecord;
import com.example.numbergame.Result;

public interface DataSource {
    void tryRegister(String id, String password, String displayName, DataSourceCallback<Result> callback);
    void tryLogin(String id, String password, DataSourceCallback<Result> callback);
    void getAllRecords(DataSourceCallback<Result> callback);
    void getMyRecords(String id, DataSourceCallback<Result> callback);
    void addRecord(GameRecord toAdd, DataSourceCallback<Result> callback);
}
