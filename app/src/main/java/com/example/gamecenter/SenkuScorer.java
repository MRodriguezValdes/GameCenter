package com.example.gamecenter;

public class SenkuScorer {
    public static final int BASE_SCORE = 1024; // Puntuación base
    public static final int PENALTY_PER_PIECE = 32; // Penalización por cada pieza restante

    public int calculateScore(int[][] board) {
        int score = BASE_SCORE;

        // Cuenta la cantidad de piezas restantes en el tablero
        int remainingPieces = countRemainingPieces(board);

        // Aplica la penalización por piezas restantes
        score -= remainingPieces * PENALTY_PER_PIECE;

        // Asegúrate de que la puntuación no sea negativa
        score = Math.max(score, 0);

        return score;
    }

    private int countRemainingPieces(int[][] board) {
        int count = 0;
        for (int[] row : board) {
            for (int piece : row) {
                if (piece == 1) { // Pieza restante
                    count++;
                }
            }
        }
        return count;
    }
}