package com.example.numbergame.ui.MyRecord;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numbergame.databinding.FragmentMyrecordBinding;
import com.example.numbergame.game.GameRecord;

import java.util.List;

public class MyRecordFragment extends Fragment {

    private FragmentMyrecordBinding binding;
    private MyRecordViewModel myRecordViewModel;
    private ConstraintLayout myFrame;
    private List<GameRecord> recordList;

//    public MyRecordFragment(){}
//
//    public static MyRecordFragment newInstance(String param1, String param2) {
//        MyRecordFragment fragment = new MyRecordFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myRecordViewModel = new ViewModelProvider(requireActivity()).get(MyRecordViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyrecordBinding.inflate(inflater, container, false);

        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myFrame=binding.myrecordClLayout;

        myRecordViewModel.getMyRecord();
        recordList = myRecordViewModel.getMyRecordList();


        FragmentManager fm = getChildFragmentManager();
        Fragment myFrag = MyRecordListFragment.newInstance(1,recordList);
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(myFrame.getId(), myFrag);
        transaction.commit();

    }
}