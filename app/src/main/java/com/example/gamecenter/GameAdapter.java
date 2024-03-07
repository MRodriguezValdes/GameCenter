package com.example.gamecenter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {
    private List<GameItem> gameList;
    private OnItemClickListener mListener;

    public GameAdapter(List<GameItem> gameList) {
        this.gameList = gameList;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_game, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GameItem gameItem = gameList.get(position);
        holder.textViewGameName.setText(gameItem.getName());
        holder.imageGameIcon.setImageResource(gameItem.getIconResource()); // Cargar el icono del juego

        holder.itemView.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewGameName;
        public TextView textViewGameScore;
        public ImageView imageGameIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewGameName = itemView.findViewById(R.id.textViewGameName);
            textViewGameScore = itemView.findViewById(R.id.textViewGameScore);
            imageGameIcon = itemView.findViewById(R.id.imageGameIcon);
        }
    }
}
