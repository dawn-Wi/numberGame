package com.example.numbergame.ui.Leaderboard;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.numbergame.R;
import com.example.numbergame.game.GameRecord;
import com.example.numbergame.ui.MyRecord.MyRecordListFragment;

import java.util.List;

public class LeaderboardListFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    private List<GameRecord> recordList;
    private LeaderboardViewModel leaderboardViewModel;
    private LeaderboardRecyclerViewAdapter adapter;

    public LeaderboardListFragment() {
    }

    public LeaderboardListFragment(List<GameRecord> recordList) {this.recordList=recordList;}

    public static LeaderboardListFragment newInstance(int columnCount, List<GameRecord> recordList) {
        LeaderboardListFragment fragment = new LeaderboardListFragment(recordList);
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null)
        {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leaderboard_list, container, false);
        leaderboardViewModel = new ViewModelProvider(requireActivity()).get(LeaderboardViewModel.class);

        if(view instanceof RecyclerView){
            Context context = view.getContext();
            RecyclerView recyclerView= (RecyclerView) view;
            if(mColumnCount<=1){
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }else{
                recyclerView.setLayoutManager(new GridLayoutManager(context,mColumnCount));
            }
            adapter = new LeaderboardRecyclerViewAdapter(recordList,new ViewModelProvider(requireActivity()).get(LeaderboardViewModel.class));
            recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        leaderboardViewModel.allRecordsLoaded().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoaded) {
                if(isLoaded){
                    adapter.setRecordList(leaderboardViewModel.getAllRecordList());
                }
            }
        });
    }
}