package com.example.gamecenter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {

    private ArrayList<Score> scores;

    public ScoreAdapter(ArrayList<Score> scores) {
        this.scores = scores;
    }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_score, parent, false);
        return new ScoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {
        Score score = scores.get(position);
        holder.tvScore.setText(String.valueOf(score.getScore()));
        holder.tvGame.setText(score.getGame());
    }

    @Override
    public int getItemCount() {
        return scores.size();
    }

    public static class ScoreViewHolder extends RecyclerView.ViewHolder {
        TextView tvScore;
        TextView tvGame;

        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            tvScore = itemView.findViewById(R.id.tvScore);
            tvGame = itemView.findViewById(R.id.tvGame);
        }
    }
}

