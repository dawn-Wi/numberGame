package com.example.numbergame.game;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.numbergame.R;

public class GameSettingFragment extends Fragment {


    public GameSettingFragment() {
        // Required empty public constructor
    }


    public static GameSettingFragment newInstance(String param1, String param2) {
        GameSettingFragment fragment = new GameSettingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_setting, container, false);
    }
}