package com.example.numbergame.game.CompetitionGame;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;

import com.example.numbergame.R;
import com.example.numbergame.databinding.FragmentCompetitiongameBinding;
import com.example.numbergame.game.GameButtonContent;
import com.example.numbergame.game.GameButtonOnClickListener;
import com.example.numbergame.game.GameRecord;
import com.example.numbergame.game.NumberParser;
import com.example.numbergame.game.PlayGame.GameFragmentArgs;

public class CompetitionGameFragment extends Fragment {

    private FragmentCompetitiongameBinding binding;
    private CompetitionGameViewModel competitionGameViewModel;

    private CompetitionGameRecyclerViewAdapter adapter;
    private RecyclerView rv_numberGrid;
    private Button bt_home;

    private Chronometer chronometer;
    private boolean answerCorrect;
    private long pauseTime, reStartTime = 0;

    public CompetitionGameFragment() {

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putParcelable("key", rv_numberGrid.getLayoutManager().onSaveInstanceState());
        outState.putLong("Time", chronometer.getBase());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        competitionGameViewModel = new ViewModelProvider(requireActivity()).get(CompetitionGameViewModel.class);

        int maxNumber = GameFragmentArgs.fromBundle(getArguments()).getMaxNumber();

        if(savedInstanceState!=null){
            competitionGameViewModel.resume();
            reStartTime = competitionGameViewModel.getReStartTime();
        }else{
            competitionGameViewModel.setupNewGame(maxNumber);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCompetitiongameBinding.inflate(inflater,container, false);

        chronometer = binding.competitionChronometer;
        rv_numberGrid = binding.competitionRvNumberGrid;
        bt_home = binding.competitionBtHome;

        init();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //region 다른코드
        bt_home.setVisibility(View.INVISIBLE);
        chronometer.setFormat("%s");
        //endregion

        //region Observer
        competitionGameViewModel.isPressedButtonCorrect().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
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

        competitionGameViewModel.getGameState().observe(getViewLifecycleOwner(), new Observer<CompetitionGameViewModel.GameState>() {
            @Override
            public void onChanged(CompetitionGameViewModel.GameState gameState) {
                if (gameState == CompetitionGameViewModel.GameState.PLAYING) {
                    if (reStartTime <= 0) {
                        chronometer.setBase(SystemClock.elapsedRealtime());
                    } else {
                        if (savedInstanceState != null) {
                            chronometer.setBase(savedInstanceState.getLong("Time"));
                        } else {
                            chronometer.setBase(SystemClock.elapsedRealtime() - competitionGameViewModel.getPauseTime());
                        }
                    }
                    chronometer.start();
                } else if (gameState ==CompetitionGameViewModel.GameState.PAUSE) {
                    reStartTime = SystemClock.elapsedRealtime() - pauseTime;
                    competitionGameViewModel.setReStartTime(reStartTime);
                } else if (gameState ==CompetitionGameViewModel.GameState.FINISHED) {
                    reStartTime = 0;
                    chronometer.stop();
                    bt_home.setVisibility(View.VISIBLE);
                    GameRecord gameRecord = new GameRecord(NumberParser.parseChronoTimeToSeconds(chronometer.getText().toString()), competitionGameViewModel.getLoggedUserId(), competitionGameViewModel.getMaxNumber());
                    competitionGameViewModel.addCompetitionRecord(gameRecord);
                }
            }
        });
        //endregion

        //region Listeners
        bt_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(CompetitionGameFragment.this).navigate(R.id.action_competitionGameFragment_to_navigation_gameSetting);
            }
        });

        Navigation.findNavController(view)
                .addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
                    @Override
                    public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                        if (navDestination.getId() == R.id.gameFragment) {
                            competitionGameViewModel.resume();
                        }
                    }
                });

        //endregion
    }

    @Override
    public void onPause() {
        super.onPause();
        pauseTime = SystemClock.elapsedRealtime() - chronometer.getBase();
        competitionGameViewModel.setPauseTime(pauseTime);
        competitionGameViewModel.gamePause();
    }

    private void init() {
        rv_numberGrid.setLayoutManager(new GridLayoutManager(requireContext(), 5));
        adapter = new CompetitionGameRecyclerViewAdapter(competitionGameViewModel.getGameButtonContentList(), new GameButtonOnClickListener() {
            @Override
            public void onButtonClicked(GameButtonContent pressed) {
                competitionGameViewModel.checkPressedButtonIsCorrect(pressed);
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