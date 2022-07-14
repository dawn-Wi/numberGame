package com.example.numbergame.game.PlayGame;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numbergame.databinding.ObjectGameBinding;
import com.example.numbergame.game.GameButtonContent;
import com.example.numbergame.game.GameButtonOnClickListener;

import java.util.List;

public class GameRecyclerViewAdapter extends RecyclerView.Adapter<GameRecyclerViewAdapter.ViewHolder> {

    private GameButtonOnClickListener gameButtonOnClickListener;
    private List<GameButtonContent> data;

    public GameRecyclerViewAdapter(List<GameButtonContent> data, GameButtonOnClickListener gameButtonOnClickListener) {
        this.data = data;
        this.gameButtonOnClickListener = gameButtonOnClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(ObjectGameBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.gameButtonContent = data.get(position);
        if (holder.gameButtonContent.isClicked())
            holder.bt_number.setVisibility(View.INVISIBLE);
        else
            holder.bt_number.setVisibility(View.VISIBLE);
        holder.bt_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameButtonOnClickListener.onButtonClicked(holder.gameButtonContent);
            }
        });
        holder.bt_number.setText(holder.gameButtonContent.getValue());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        GameButtonContent gameButtonContent;
        Button bt_number;

        public ViewHolder(ObjectGameBinding binding) {
            super(binding.getRoot());
            bt_number = binding.gameNumButton;
        }
    }
}
