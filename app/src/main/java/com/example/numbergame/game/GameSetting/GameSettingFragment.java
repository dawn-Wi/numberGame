package com.example.numbergame.game.GameSetting;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.numbergame.R;
import com.example.numbergame.databinding.FragmentGamesettingBinding;

import java.util.ArrayList;
import java.util.Locale;

public class GameSettingFragment extends Fragment {

    private FragmentGamesettingBinding binding;
    private GameSettingViewModel gameSettingViewModel;

    private EditText et_maxNumber;
    private TextView tv_orLabel;
    private Button bt_gameStart;
    private Button bt_competition;

    private long pressedTime = 0;

    public GameSettingFragment() {
    }

    public static GameSettingFragment newInstance() {
        GameSettingFragment fragment = new GameSettingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameSettingViewModel = new ViewModelProvider(requireActivity()).get(GameSettingViewModel.class);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (System.currentTimeMillis() > pressedTime + 2000) {
                    pressedTime = System.currentTimeMillis();
                    Toast.makeText(requireContext(), "Press once more to exit", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Exit the app", Toast.LENGTH_SHORT).show();
                    requireActivity().finish();
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGamesettingBinding.inflate(inflater, container, false);
        et_maxNumber = binding.gameSettingEtMaxNumber;
        tv_orLabel = binding.gamesettingTvOrLabel;
        bt_gameStart = binding.gameSettingBtGameStart;
        bt_competition = binding.gamesettingBtCompetitionButton;
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle saveInstanceState) {
        super.onViewCreated(view, saveInstanceState);

        gameSettingViewModel.initGameFormState();

        //region viewModel
        gameSettingViewModel.getGameSettingFormState().observe(getViewLifecycleOwner(), new Observer<GameSettingFormState>() {
            @Override
            public void onChanged(GameSettingFormState gameSettingFormState) {
                if (gameSettingFormState.getMaxNumberErrorMessage() != null) {
                    et_maxNumber.setError(gameSettingFormState.getMaxNumberErrorMessage());
                }
                gameSettingViewModel.setMaxNumberText(et_maxNumber.getText().toString());
                bt_gameStart.setEnabled(gameSettingFormState.isFieldsValid());
            }
        });
        //endregion

        //region Listener
        et_maxNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                gameSettingViewModel.onMaxNumberTextChanged(editable.toString());
            }
        });

        bt_gameStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameSettingFragmentDirections.ActionNavigationGameSettingToGameFragment action = GameSettingFragmentDirections.actionNavigationGameSettingToGameFragment();
                action.setMaxNumber(gameSettingViewModel.getMaxNumber());
                NavHostFragment.findNavController(GameSettingFragment.this).navigate(action);
            }
        });

        bt_competition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameSettingFragmentDirections.ActionNavigationGameSettingToCompetitionGameFragment action = GameSettingFragmentDirections.actionNavigationGameSettingToCompetitionGameFragment();
                action.setMaxNumber(25);
                NavHostFragment.findNavController(GameSettingFragment.this).navigate(action);
            }
        });

        //endregion
    }

}