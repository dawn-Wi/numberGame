package com.example.numbergame.game;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numbergame.R;

import java.util.List;

public class GameGridFragment extends Fragment
{
    private static final String ARG_COLUMN_COUNT = "column-count";
    private GameViewModel gameViewModel;
    private int mColumnCount = 1;
    private List<GameButtonContent> gameButtonContents;
    private GameRecyclerViewAdapter adapter;

    public GameGridFragment(List<GameButtonContent> gameButtonContents)
    {
        this.gameButtonContents = gameButtonContents;
    }

    public static GameGridFragment newInstance(int columnCount, List<GameButtonContent> gameButtonContents)
    {
        GameGridFragment fragment = new GameGridFragment(gameButtonContents);
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        gameViewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_grid, container, false);

        if (view instanceof RecyclerView)
        {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            recyclerView.setLayoutManager(new GridLayoutManager(context, 5));
            adapter = new GameRecyclerViewAdapter(gameButtonContents, new GameButtonOnClickListener()
            {
                @Override
                public void onButtonClicked(GameButtonContent pressed)
                {
                    //Viewmodel한테 이야기해서 눌러지는게 맞는지 확인...
                    pressed.setClicked(true);
                    adapter.notifyDataSetChanged();
                }
            });
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
    }
}