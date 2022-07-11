package com.example.numbergame.game;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numbergame.databinding.ObjectGameBinding;

public class GameRecyclerViewAdapter extends RecyclerView.Adapter<GameRecyclerViewAdapter.ViewHolder> {

    private String[] numberString;
    private LayoutInflater mInflater;

    public GameRecyclerViewAdapter(Context context,String[] data){
        this.mInflater = LayoutInflater.from(context);
        this.numberString = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return new ViewHolder(ObjectGameBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.numberButton.setText(numberString[position]);
    }

    @Override
    public int getItemCount() {
        return numberString.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        Button numberButton;
        public ViewHolder(ObjectGameBinding binding){
            super(binding.getRoot());
            numberButton = binding.gameNumButton;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {

        }
        String getItem(int id){
            return numberString[id];
        }


    }
}
