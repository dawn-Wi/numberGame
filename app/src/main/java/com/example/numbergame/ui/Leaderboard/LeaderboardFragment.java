package com.example.numbergame.ui.Leaderboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numbergame.databinding.FragmentLeaderboardBinding;
import com.example.numbergame.ui.MyRecord.MyRecordRecyclerViewAdapter;
import com.example.numbergame.ui.MyRecord.MyRecordViewModel;

public class LeaderboardFragment extends Fragment {

    private FragmentLeaderboardBinding binding;
    private MyRecordViewModel myRecordViewModel;

    private MyRecordRecyclerViewAdapter adapter;
    private RecyclerView rv_recordLinear;

    public LeaderboardFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myRecordViewModel = new ViewModelProvider(requireActivity()).get(MyRecordViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLeaderboardBinding.inflate(inflater, container, false);

        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}