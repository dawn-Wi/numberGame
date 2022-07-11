package com.example.numbergame.game;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.numbergame.R;

public class GameGridFragment extends Fragment {
    GameViewModel gameViewModel;

    private static final String ARG_COLUMN_COUNT="column-count";
    private int mColumnCount=1;
    String[] numberString;



    public GameGridFragment(String[] numberButton) {
        this.numberString= numberButton;
    }

    public static GameGridFragment newInstance(int columnCount, String[] numberButton) {
        GameGridFragment fragment = new GameGridFragment(numberButton);
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT,columnCount);
        fragment.setArguments(args);
        return fragment;
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
        View view =  inflater.inflate(R.layout.fragment_game_grid, container, false);

        if(view instanceof RecyclerView){
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            recyclerView.setLayoutManager(new GridLayoutManager(context,5));
            GameRecyclerViewAdapter adapter = new GameRecyclerViewAdapter(requireContext(),numberString);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}