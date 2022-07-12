package com.example.numbergame.ui.MyRecord;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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

import java.util.List;

public class MyRecordListFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    private List<GameRecord> recordList;

    public MyRecordListFragment(List<GameRecord> recordList) {
        this.recordList = recordList;
    }

    public static MyRecordListFragment newInstance(int columnCount, List<GameRecord> recordList) {
        MyRecordListFragment fragment = new MyRecordListFragment(recordList);
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
        View view = inflater.inflate(R.layout.fragment_myrecord_list, container, false);

        if(view instanceof RecyclerView){
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if(mColumnCount<=1){
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }else{
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            MyRecordRecyclerViewAdapter adapter = new MyRecordRecyclerViewAdapter(recordList, new ViewModelProvider(requireActivity()).get(MyRecordViewModel.class));
            recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));
            recyclerView.setAdapter(adapter);
        }
        return view;
    }
}