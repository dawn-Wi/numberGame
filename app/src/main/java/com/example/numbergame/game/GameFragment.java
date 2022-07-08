package com.example.numbergame.game;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.numbergame.GameGridFragment;
import com.example.numbergame.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GameFragment extends Fragment {
    private GameViewModel gameViewModel;

    private Chronometer chronometer;
    private ConstraintLayout linearLayout;
    private int maxNumber;
    private String[] numberButton;
    private boolean running = false;

    public GameFragment() {
        // Required empty public constructor
    }


    public static GameFragment newInstance(String param1, String param2) {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameViewModel = new ViewModelProvider(this).get(GameViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        chronometer = view.findViewById(R.id.game_chronometer);
        linearLayout = view.findViewById(R.id.linearLayout);
        chronometer.setFormat("%s");


        maxNumber = Integer.parseInt(gameViewModel.getMaxNumber().toString());

        numberButton = new String[maxNumber];
        for(int i =1;i<maxNumber;i++){
            int randomNum=(int)(Math.random()*maxNumber);
            numberButton[i]=String.valueOf(randomNum);

        }

        if (!running) {
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            running = true;
        }

        FragmentManager fm = getChildFragmentManager();
        Fragment myFrag = GameGridFragment.newInstance(5, numberButton);
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(linearLayout.getId(), myFrag);
        transaction.commit();


    }
}