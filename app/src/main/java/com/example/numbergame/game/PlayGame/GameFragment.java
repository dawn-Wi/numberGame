package com.example.numbergame.game.PlayGame;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numbergame.R;
import com.example.numbergame.databinding.FragmentGameBinding;
import com.example.numbergame.game.GameButtonContent;
import com.example.numbergame.game.GameButtonOnClickListener;
import com.example.numbergame.game.GameRecord;
import com.example.numbergame.game.NumberParser;

public class GameFragment extends Fragment {

    private FragmentGameBinding binding;
    private GameViewModel gameViewModel;

    private GameRecyclerViewAdapter adapter;
    private ConstraintLayout cl_layout;
    private RecyclerView rv_numberGrid;
    private Button bt_home;

    private Chronometer chronometer;
    private boolean answerCorrect;
    private long pauseTime, reStartTime=0;

    public GameFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameViewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);
        int maxNumber = GameFragmentArgs.fromBundle(getArguments()).getMaxNumber();
        gameViewModel.setupNewGame(maxNumber);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGameBinding.inflate(inflater, container, false);

        cl_layout = binding.gameClLayout;
        rv_numberGrid = binding.gameRvNumberGrid;
        bt_home = binding.gameBtHome;

        init();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //region 다른코드
        bt_home.setVisibility(View.INVISIBLE);
        chronometer = binding.gameChronometer;
        chronometer.setFormat("%s");

        //endregion

        //region Observer
        gameViewModel.isPressedButtonCorrect().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isCorrect) {
                if (isCorrect) {
                    answerCorrect = true;
                    adapter.notifyDataSetChanged();

                } else {
                    answerCorrect = false;
                }
            }
        });

        gameViewModel.getGameState().observe(getViewLifecycleOwner(), new Observer<GameViewModel.GameState>() {
            @Override
            public void onChanged(GameViewModel.GameState gameState) {
                if (gameState == GameViewModel.GameState.PLAYING) {
                    if(reStartTime<=0){
                        chronometer.setBase(SystemClock.elapsedRealtime());

                    }else{
                        chronometer.setBase(SystemClock.elapsedRealtime()-gameViewModel.getPauseTime());
                    }
                    chronometer.start();
                } else if (gameState == GameViewModel.GameState.PAUSE) {
                    reStartTime = SystemClock.elapsedRealtime() - pauseTime;
                } else if (gameState == GameViewModel.GameState.FINISHED) {
                    reStartTime=0;
                    chronometer.stop();
                    bt_home.setVisibility(View.VISIBLE);
                    GameRecord gameRecord = new GameRecord(NumberParser.parseChronoTimeToSeconds(chronometer.getText().toString()), gameViewModel.getLoggedUserId(), gameViewModel.getMaxNumber());
                    gameViewModel.addRecord(gameRecord);
                }
            }
        });
        //endregion

        //region Listeners
        bt_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(GameFragment.this).navigate(R.id.action_gameFragment_to_navigation_gameSetting);
            }
        });

        Navigation.findNavController(view)
                .addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
                    @Override
                    public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                        if (navDestination.getId() == R.id.gameFragment) {
                            gameViewModel.resume();
                        }
                    }
                });
        //endregion
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("pause", "onPause: pause");
        pauseTime = SystemClock.elapsedRealtime() - chronometer.getBase();
        gameViewModel.setPauseTime(pauseTime);
        gameViewModel.gamePause();
    }

    private void init() {
        rv_numberGrid.setLayoutManager(new GridLayoutManager(requireContext(), 5));
        adapter = new GameRecyclerViewAdapter(gameViewModel.getGameButtonContentList(), new GameButtonOnClickListener() {
            @Override
            public void onButtonClicked(GameButtonContent pressed) {
                gameViewModel.checkPressedButtonIsCorrect(pressed);
                if (answerCorrect) {
                    pressed.setClicked(true);
                } else {
                    pressed.setClicked(false);
                }
                adapter.notifyDataSetChanged();
            }
        });
        rv_numberGrid.setAdapter(adapter);
    }


}