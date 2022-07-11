package com.example.numbergame;

import com.example.numbergame.dataSource.DataSource;
import com.example.numbergame.dataSource.DataSourceCallback;
import com.example.numbergame.game.GameRecord;

public class UserRepository {
    private static volatile UserRepository INSTANCE = new UserRepository();
    private DataSource dataSource;

    private String loggedUserId;

    public static UserRepository getInstance() {
        return INSTANCE;
    }

    public void tryRegister(final String id, final String password, final String displayName, UserRepositoryCallback<Result> callback) {
        dataSource.tryRegister(id, password, displayName, callback::onComplete);
    }

    public void tryLogin(final String id, final String password, UserRepositoryCallback<Result> callback){
        dataSource.tryLogin(id, password,callback::onComplete);
    }

    public void getId(final UserRepositoryCallback<Result> callback) {
        dataSource.getId(callback::onComplete);
    }

    public void saveRepositoryUserId(final String userId){
        loggedUserId = userId;
    }

    public String sendRepositoryUserId(){
        return loggedUserId;
    }

    public void addRecord(final GameRecord gameRecord, UserRepositoryCallback<Result> callback){
        dataSource.addRecord(gameRecord, callback::onComplete);
    }

    public void setDataSource(DataSource ds) {
        this.dataSource = ds;
    }

    public interface UserRepositoryCallback<Result> {
        void onComplete(Result result);
    }

}
