package com.example.numbergame.ui.MyRecord;

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

import com.example.numbergame.databinding.FragmentMyrecordBinding;
import com.example.numbergame.game.GameRecord;

import java.util.List;

public class MyRecordFragment extends Fragment {

    private FragmentMyrecordBinding binding;
    private MyRecordViewModel myRecordViewModel;
    private ConstraintLayout cl_myFrame;
    private List<GameRecord> recordList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myRecordViewModel = new ViewModelProvider(requireActivity()).get(MyRecordViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyrecordBinding.inflate(inflater, container, false);
        cl_myFrame =binding.myrecordClLayout;

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
        transaction.replace(cl_myFrame.getId(), myFrag);
        transaction.commit();
    }
}