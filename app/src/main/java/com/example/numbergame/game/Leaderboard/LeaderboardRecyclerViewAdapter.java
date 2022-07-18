package com.example.numbergame.game.Leaderboard;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.numbergame.databinding.ObjectLeaderboardBinding;
import com.example.numbergame.game.GameRecord;

import java.util.ArrayList;
import java.util.Arrays;
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
        holder.rank.setText(String.valueOf(position+1));
        holder.userId.setText(recordList.get(position).getUserId());
        holder.buttonNumAndRecord.setText("ButtonNum: " + recordList.get(position).getButtonNum()+" / Time: " + Math.toIntExact(Long.parseLong("" + recordList.get(position).getTimestamp())));
    }


    @Override
    public int getItemCount(){return recordList.size();}

    public void setRecordList(List<GameRecord> newRecordList){
        recordList = newRecordList;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView rank;
        public final TextView userId;
        public final TextView buttonNumAndRecord;

        public ViewHolder(ObjectLeaderboardBinding binding){
            super(binding.getRoot());
            rank = binding.leaderboardTvRank;
            userId = binding.leaderboardTvName;
            buttonNumAndRecord = binding.leaderboardTvButtonNumAndRecord;
        }

        @Override
        public String toString(){return super.toString()+"'";}
    }
}
