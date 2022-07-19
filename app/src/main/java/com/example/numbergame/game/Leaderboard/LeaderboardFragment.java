package com.example.numbergame.game.Leaderboard;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.numbergame.R;
import com.example.numbergame.databinding.FragmentLeaderboardBinding;
import com.example.numbergame.game.GameRecord;

import java.util.List;
import java.util.Map;

public class LeaderboardFragment extends Fragment {

    private FragmentLeaderboardBinding binding;
    private LeaderboardViewModel leaderboardViewModel;
    private ConstraintLayout cl_layout;
    private List<GameRecord> recordList;

    private TextView tv_rankLabel;
    private TextView tv_nameLabel;
    private TextView tv_timeLabel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        leaderboardViewModel = new ViewModelProvider(requireActivity()).get(LeaderboardViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLeaderboardBinding.inflate(inflater, container, false);
        cl_layout = binding.leaderboardClLayout;
        tv_rankLabel = binding.leaderboardTvRankLabel;
        tv_nameLabel= binding.leaderboardTvNameLabel;
        tv_timeLabel = binding.leaderboardTvTimeLabel;

        init();

        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            leaderboardViewModel.loadCompetitionRecord();
        }
        recordList = leaderboardViewModel.getCompetitionRecordList();

        FragmentManager fm = getChildFragmentManager();
        Fragment myFrag = LeaderboardListFragment.newInstance(1, recordList);
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(cl_layout.getId(), myFrag);
        transaction.commit();
    }
}