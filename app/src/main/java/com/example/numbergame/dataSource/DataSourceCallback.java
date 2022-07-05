package com.example.numbergame.dataSource;

public interface DataSourceCallback<Result> {
    void onComplete(Result result);
}