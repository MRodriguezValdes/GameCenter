package com.example.gamecenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;


public class ScoreboardActivity extends AppCompatActivity {

    Button btn2048;
    Button btnSenku;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        btn2048 = findViewById(R.id.btn2048);
        btnSenku = findViewById(R.id.btnSenku);
        recyclerView = findViewById(R.id.recyclerView);

        btn2048.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar las puntuaciones del juego 2048
                showScores("Game2048");
            }
        });

        btnSenku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar las puntuaciones del juego Senku
                showScores("Senku");
            }
        });
    }

    private void showScores(String gameType) {
        // Obtener las puntuaciones del juego desde la base de datos
        DatabaseHelper dbHelper = new DatabaseHelper(ScoreboardActivity.this);
        // Aquí deberías tener registros en la base de datos para el juego Senku
        ArrayList<Score> scores = dbHelper.getAllScores(gameType);

        // Configurar el RecyclerView y su adaptador
        ScoreAdapter adapter = new ScoreAdapter(scores);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}


