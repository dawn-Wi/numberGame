package com.example.numbergame.game;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numbergame.R;
import com.example.numbergame.databinding.FragmentGameBinding;
import com.example.numbergame.login.LoginFragment;
import com.example.numbergame.login.SignupFragment;

public class GameFragment extends Fragment {

    private FragmentGameBinding binding;
    private GameViewModel gameViewModel;

    private GameRecyclerViewAdapter adapter;

    private Chronometer chronometer;
    private RecyclerView rv_numberGrid;
    private Button bt_home;
    private boolean running = false;
    private boolean correctAnswer;

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
        binding = FragmentGameBinding.inflate(inflater, container, false);
        int maxNumber = GameFragmentArgs.fromBundle(getArguments()).getMaxNumber();
        gameViewModel.setupNewGame(maxNumber);

        rv_numberGrid = binding.gameRvNumberGrid;

        rv_numberGrid.setLayoutManager(new GridLayoutManager(requireContext(), 5));
        adapter = new GameRecyclerViewAdapter(gameViewModel.getGameButtonContentList(), new GameButtonOnClickListener() {
            @Override
            public void onButtonClicked(GameButtonContent pressed) {
                gameViewModel.isNumberValid(pressed);
                if (correctAnswer) {
                    pressed.setClicked(true);
                } else {
                    pressed.setClicked(false);
                }
                adapter.notifyDataSetChanged();
            }
        });
        rv_numberGrid.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bt_home = binding.gameBtHome;
        bt_home.setVisibility(View.INVISIBLE);
        chronometer = binding.gameChronometer;
        chronometer.setFormat("%s");

        gameViewModel.isCheckNumber().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isChecked) {
                if (isChecked) {
                    correctAnswer = true;
                    adapter.notifyDataSetChanged();
                    if (gameViewModel.finishClick()) {
                        chronometer.stop();
                        running = false;
                        bt_home.setVisibility(View.VISIBLE);
                        bt_home.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                NavHostFragment.findNavController(GameFragment.this).navigate(R.id.action_gameFragment_to_navigation_gameSetting);
                            }
                        });
                        GameRecord gameRecord = new GameRecord(NumberParser.parseChronoTimeToSeconds(chronometer.getText().toString()), gameViewModel.getLoggedUserId(), gameViewModel.getMaxNumber());
                        gameViewModel.addRecord(gameRecord);
                    }
                } else {
                    correctAnswer = false;
                }
            }
        });
        gameViewModel.isFinishGame().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isFinish) {
                if(isFinish){
                    NavHostFragment.findNavController(GameFragment.this).navigateUp();
                }
            }
        });


        if (!running) {
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            running = true;
        }

    }
}