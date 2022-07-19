package com.example.numbergame.game.MyRecord;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.numbergame.databinding.FragmentMyrecordBinding;
import com.example.numbergame.game.GameRecord;

import java.util.List;

public class MyRecordFragment extends Fragment {

    private FragmentMyrecordBinding binding;
    private MyRecordViewModel myRecordViewModel;

    private FrameLayout fl_list;
    private TextView tv_nameLabel;
    private TextView tv_buttonNumLabel;
    private TextView tv_timeLabel;

    private List<GameRecord> recordList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myRecordViewModel = new ViewModelProvider(requireActivity()).get(MyRecordViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyrecordBinding.inflate(inflater, container, false);
        fl_list =binding.myrecordFlList;
        tv_nameLabel = binding.myrecordTvNameLabel;
        tv_buttonNumLabel = binding.myrecordTvButtomNumLabel;
        tv_timeLabel = binding.myrecordTvTimeLabel;

        init();

        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void init(){
        myRecordViewModel.loadMyRecord();
        recordList = myRecordViewModel.getMyRecordList();
        FragmentManager fm = getChildFragmentManager();
        Fragment myFrag = MyRecordListFragment.newInstance(1,recordList);
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(fl_list.getId(), myFrag);
        transaction.commit();
    }
}