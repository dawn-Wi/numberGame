package com.example.numbergame.dataSource;

import com.example.numbergame.game.GameRecord;
import com.example.numbergame.Result;

public interface DataSource {
    void tryRegister(String id, String password, String displayName, DataSourceCallback<Result> callback);

    void getId(DataSourceCallback<Result> callback);

    void tryLogin(String id, String password, DataSourceCallback<Result> callback);

    void getAllRecords(DataSourceCallback<Result> callback);

    void getMyRecords(String id, DataSourceCallback<Result> callback);

    void addRecord(GameRecord toAdd, DataSourceCallback<Result> callback);

    void addCompetitionRecord(GameRecord toAdd, DataSourceCallback<Result> callback);

    void getCompetitionRecords(DataSourceCallback<Result> callback);
}
