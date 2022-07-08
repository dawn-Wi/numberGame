package com.example.numbergame;

import com.example.numbergame.dataSource.DataSource;

public class UserRepository {
    private static volatile UserRepository INSTANCE = new UserRepository();
    private DataSource dataSource;

    private String inputMaxNumber;

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

    public void setMaxNumber(final String maxNumber, UserRepositoryCallback<Result>callback){
        inputMaxNumber=maxNumber;
        callback.onComplete(new Result.Success<String>("Success"));
    }

    public void getMaxNumber(UserRepositoryCallback<Result>callback){
        callback.onComplete(new Result.Success<String>(inputMaxNumber));
    }

    public void setDataSource(DataSource ds) {
        this.dataSource = ds;
    }

    public interface UserRepositoryCallback<Result> {
        void onComplete(Result result);
    }

}
