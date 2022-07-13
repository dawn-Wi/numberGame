package com.example.numbergame.ui.MyRecord;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.numbergame.databinding.ObjectMyrecordBinding;
import com.example.numbergame.game.GameRecord;

import java.util.List;


public class MyRecordRecyclerViewAdapter extends RecyclerView.Adapter<MyRecordRecyclerViewAdapter.ViewHolder> {

    private List<GameRecord> recordList;
    private MyRecordViewModel myRecordViewModel;

    public MyRecordRecyclerViewAdapter(List<GameRecord> items, MyRecordViewModel mvm) {
        recordList = items;
        myRecordViewModel = mvm;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(ObjectMyrecordBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.timestamp.setText("Time: " + Math.toIntExact(Long.parseLong("" + recordList.get(position).getTimestamp())));
        holder.userId.setText(recordList.get(position).getUserId());
        holder.buttonNum.setText("ButtonNum: " + recordList.get(position).getButtonNum());
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

    public void setRecordList(List<GameRecord> newRecordList) {
        recordList = newRecordList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView timestamp;
        public final TextView userId;
        public final TextView buttonNum;

        public ViewHolder(ObjectMyrecordBinding binding) {
            super(binding.getRoot());
            timestamp = binding.myrecordTvRecord;
            userId = binding.myrecordTvUserId;
            buttonNum = binding.myrecordTvButtonNum;
        }

        @Override
        public String toString() {
            return super.toString() + "'" + userId.getText() + "'";
        }
    }

}
