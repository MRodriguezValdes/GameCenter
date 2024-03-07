package com.example.gamecenter;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GameCenterActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<GameItem> gameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_center);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar la lista de juegos
        gameList = new ArrayList<>();
        gameList.add(new GameItem(getString(R.string.app_name1), 0,R.drawable.senku));
        gameList.add(new GameItem(getString(R.string.app_name2), 0,R.drawable._2048)); // Aquí puedes cambiar por el nombre correcto
        gameList.add(new GameItem("Score",0,R.drawable.score));
        gameList.add(new GameItem("Settings",0,R.drawable.settings));
        // Configurar el adaptador
        GameAdapter adapter = new GameAdapter(gameList);
        recyclerView.setAdapter(adapter);

        // Configurar el listener para los clics en los elementos de la RecyclerView
        adapter.setOnItemClickListener(position -> {
            // Abrir la actividad correspondiente según el elemento seleccionado
            switch (position) {
                case 0:
                    startActivity(new Intent(GameCenterActivity.this, SenkuActivity.class));
                    break;
                case 1:
                    startActivity(new Intent(GameCenterActivity.this, Activity2048.class));
                    break;
                case 2:
                    startActivity(new Intent(GameCenterActivity.this, ScoreboardActivity.class));
                    break;
                case 3:
                    startActivity(new Intent(GameCenterActivity.this, Settings.class));
                    break;
            }
        });
    }
}
