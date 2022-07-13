package com.example.numbergame.ui.Leaderboard;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numbergame.databinding.ObjectLeaderboardBinding;
import com.example.numbergame.databinding.ObjectMyrecordBinding;
import com.example.numbergame.game.GameRecord;

import java.util.List;

public class LeaderboardRecyclerViewAdapter extends RecyclerView.Adapter<LeaderboardRecyclerViewAdapter.ViewHolder> {
    private List<GameRecord> recordList;
    private LeaderboardViewModel leaderboardViewModel;

    public LeaderboardRecyclerViewAdapter(List<GameRecord> items, LeaderboardViewModel lvm){
        recordList=items;
        leaderboardViewModel=lvm;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return new ViewHolder(ObjectLeaderboardBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position){
        holder.timestamp.setText("Time: " + Math.toIntExact(Long.parseLong("" + recordList.get(position).getTimestamp())));
        holder.userId.setText(recordList.get(position).getUserId());
        holder.buttonNum.setText("ButtonNum: " + recordList.get(position).getButtonNum());
    }

    @Override
    public int getItemCount(){return recordList.size();}

    public void setRecordList(List<GameRecord> newRecordList){
        recordList = newRecordList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView timestamp;
        public final TextView userId;
        public final TextView buttonNum;

        public ViewHolder(ObjectLeaderboardBinding binding){
            super(binding.getRoot());
            timestamp = binding.leaderboardTvRecord;
            userId = binding.leaderboardTvUserId;
            buttonNum = binding.leaderboardTvButtonNum;
        }

        @Override
        public String toString(){return super.toString()+"'";}
    }
}
