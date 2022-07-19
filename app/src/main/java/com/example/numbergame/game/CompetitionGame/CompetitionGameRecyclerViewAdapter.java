package com.example.numbergame.game.CompetitionGame;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numbergame.databinding.ObjectGameBinding;
import com.example.numbergame.game.GameButtonContent;
import com.example.numbergame.game.GameButtonOnClickListener;

import java.util.List;

public class CompetitionGameRecyclerViewAdapter extends RecyclerView.Adapter<CompetitionGameRecyclerViewAdapter.ViewHolder> {
    private GameButtonOnClickListener gameButtonOnClickListener;
    private List<GameButtonContent> data;

    public CompetitionGameRecyclerViewAdapter(List<GameButtonContent> data, GameButtonOnClickListener gameButtonOnClickListener) {
        this.data = data;
        this.gameButtonOnClickListener = gameButtonOnClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(ObjectGameBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CompetitionGameRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.gameButtonContent = data.get(position);
        if (holder.gameButtonContent.getButtonState() == GameButtonContent.ButtonState.ANIMATING) {
            Animation animation = AnimationUtils.loadAnimation(holder.bt_number.getContext(), com.example.numbergame.R.anim.buttonanim);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    holder.gameButtonContent.setButtonState(GameButtonContent.ButtonState.INVISIBLE);
                    notifyDataSetChanged();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            holder.bt_number.startAnimation(animation);

        } else if (holder.gameButtonContent.getButtonState() == GameButtonContent.ButtonState.VISIBLE) {
            holder.bt_number.setVisibility(View.VISIBLE);
        } else
            holder.bt_number.setVisibility(View.INVISIBLE);


        holder.bt_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameButtonOnClickListener.onButtonClicked(holder.gameButtonContent);
                if(holder.gameButtonContent.isClicked()){
                    holder.gameButtonContent.setButtonState(GameButtonContent.ButtonState.ANIMATING);
                }
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
