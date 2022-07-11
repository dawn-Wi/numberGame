package com.example.numbergame.game;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.numbergame.databinding.FragmentGameBinding;

public class GameFragment extends Fragment
{

    private FragmentGameBinding binding;
    private GameViewModel gameViewModel;

    private Chronometer chronometer;
    private ConstraintLayout linearLayout;
    private boolean running = false;

    public GameFragment()
    {
        // Required empty public constructor
    }

    public static GameFragment newInstance(String param1, String param2)
    {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        gameViewModel = new ViewModelProvider(this).get(GameViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        binding = FragmentGameBinding.inflate(inflater, container, false);
        int maxNumber = GameFragmentArgs.fromBundle(getArguments()).getMaxNumber();
        gameViewModel.setupNewGame(maxNumber);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        chronometer = binding.gameChronometer;
        linearLayout = binding.linearLayout;
        chronometer.setFormat("%s");

        if (!running)
        {
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            running = true;
        }

        FragmentManager fm = getChildFragmentManager();
        Fragment myFrag = GameGridFragment.newInstance(5, gameViewModel.getGameButtonContentList());
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(linearLayout.getId(), myFrag);
        transaction.commit();
    }
}