package com.example.numbergame.game;

import android.content.Context;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numbergame.databinding.FragmentGameBinding;
import com.example.numbergame.ui.GameSetting.GameSettingFragment;

import java.util.List;

public class GameFragment extends Fragment {

    private FragmentGameBinding binding;
    private GameViewModel gameViewModel;

    private GameRecyclerViewAdapter adapter;

    private Chronometer chronometer;
    private RecyclerView rv_numberGrid;
    private boolean running = false;

    public GameFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameViewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentGameBinding.inflate(inflater, container, false);
        int maxNumber = GameFragmentArgs.fromBundle(getArguments()).getMaxNumber();
        gameViewModel.setupNewGame(maxNumber);

        rv_numberGrid = binding.gameRvNumberGrid;

        rv_numberGrid.setLayoutManager(new GridLayoutManager(requireContext(), 5));
        adapter = new GameRecyclerViewAdapter(gameViewModel.getGameButtonContentList(), new GameButtonOnClickListener() {
            @Override
            public void onButtonClicked(GameButtonContent pressed) {
                //Viewmodel한테 이야기해서 눌러지는게 맞는지 확인...
                gameViewModel.isNumberValid(pressed);
                gameViewModel.isCheckNumber().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean isChecked) {
                        if (isChecked) {
                            pressed.setClicked(true);
                            adapter.notifyDataSetChanged();
                        } else {
                            pressed.setClicked(false);
                        }
                    }
                });
                adapter.notifyDataSetChanged();
            }
        });
        rv_numberGrid.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        chronometer = binding.gameChronometer;
        chronometer.setFormat("%s");




        if (!running) {
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            running = true;
        }

    }
}