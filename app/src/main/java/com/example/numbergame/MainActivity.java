package com.example.numbergame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.numbergame.dataSource.FirebaseDataSource;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDataSource ds = new FirebaseDataSource();
        UserRepository.getInstance().setDataSource(ds);
    }
}