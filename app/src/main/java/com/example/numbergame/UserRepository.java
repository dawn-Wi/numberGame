package com.example.numbergame;

import com.example.numbergame.dataSource.DataSource;
import com.example.numbergame.game.GameRecord;

public class UserRepository {
    private static volatile UserRepository INSTANCE = new UserRepository();
    private DataSource dataSource;

    private String loggedInUserId;

    public static UserRepository getInstance() {
        return INSTANCE;
    }

    public void tryRegister(final String id, final String password, final String displayName, UserRepositoryCallback<Result> callback) {
        dataSource.tryRegister(id, password, displayName, callback::onComplete);
    }

    public void tryLogin(final String id, final String password, UserRepositoryCallback<Result> callback) {
        dataSource.tryLogin(id, password, callback::onComplete);
    }

    public void getId(final UserRepositoryCallback<Result> callback) {
        dataSource.getId(callback::onComplete);
    }

    public void saveRepositoryUserId(final String userId) {
        loggedInUserId = userId;
    }

    public String sendRepositoryUserId() {
        return loggedInUserId;
    }

    public void addRecord(final GameRecord gameRecord, UserRepositoryCallback<Result> callback) {
        dataSource.addRecord(gameRecord, callback::onComplete);
    }

    public void addCompetitionRecord(final GameRecord gameRecord, UserRepositoryCallback<Result> callback){
        dataSource.addCompetitionRecord(gameRecord, callback::onComplete);
    }

    public void getCompetitionRecords(final UserRepositoryCallback callback){
        dataSource.getCompetitionRecords(callback::onComplete);
    }

    public void getMyRecord(final UserRepositoryCallback callback) {
        dataSource.getMyRecords(loggedInUserId, callback::onComplete);
    }

    public void getAllRecords(final UserRepositoryCallback callback){
        dataSource.getAllRecords(callback::onComplete);
    }

    public void setDataSource(DataSource ds) {
        this.dataSource = ds;
    }

    public interface UserRepositoryCallback<Result> {
        void onComplete(Result result);
    }

}
