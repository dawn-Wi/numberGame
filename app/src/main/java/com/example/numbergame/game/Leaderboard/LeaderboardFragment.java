package com.example.numbergame.game.Leaderboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.numbergame.databinding.FragmentLeaderboardBinding;
import com.example.numbergame.game.GameRecord;

import java.util.List;

public class LeaderboardFragment extends Fragment {

    private FragmentLeaderboardBinding binding;
    private LeaderboardViewModel leaderboardViewModel;
    private ConstraintLayout cl_layout;
    private List<GameRecord> recordList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        leaderboardViewModel = new ViewModelProvider(requireActivity()).get(LeaderboardViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLeaderboardBinding.inflate(inflater, container, false);
        cl_layout = binding.leaderboardClLayout;

        init();

        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void init(){
        leaderboardViewModel.loadAllRecord();
        recordList = leaderboardViewModel.getAllRecordList();
        FragmentManager fm = getChildFragmentManager();
        Fragment myFrag = LeaderboardListFragment.newInstance(1,recordList);
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(cl_layout.getId(), myFrag);
        transaction.commit();
    }
}