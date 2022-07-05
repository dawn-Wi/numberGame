package com.example.numbergame;

public interface DataSourceCallback<Result> {
    void onComplete(Result result);
}