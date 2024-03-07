package com.example.gamecenter;

public class Score {
    private int id;
    private int score;
    private String game;

    public Score(int id, int score, String game) {
        this.id = id;
        this.score = score;
        this.game = game;
    }

    public String getGame() {
        return game;
    }

    public int getScore() {
        return score;
    }
    // Agrega los getters y setters según sea necesario
}

