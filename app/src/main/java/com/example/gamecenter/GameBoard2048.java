package com.example.gamecenter;

import java.util.Random;


public class GameBoard2048 {

    private int[][] board;
    Random random;


    public GameBoard2048() {

        this.board = new int[4][4];
        this.random = new Random();
        this.generateRandomCell();
        this.generateRandomCell();
    }

    public boolean isValidOption(int i, int j) {
        return (i >= 0 && i < 4 && j >= 0 && j < 4 && this.board[i][j] == 0);
    }

    public void generateRandomCell() {
        // Verificar si hay celdas vacías disponibles
        boolean isEmptyCellAvailable = false;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 0) {
                    isEmptyCellAvailable = true;
                    break;
                }
            }
            if (isEmptyCellAvailable) {
                break;
            }
        }

        // Si no hay celdas vacías, salir sin generar nada
        if (!isEmptyCellAvailable) {
            return;
        }

        // Generar nuevas coordenadas aleatorias hasta que encuentres una celda vacía
        int x, y;
        do {
            x = random.nextInt(4);
            y = random.nextInt(4);
        } while (!isValidOption(x, y));

        // Colocar el nuevo número (actualmente siempre 2, podrías modificarlo para que sea aleatorio)
        board[x][y] = 2;
    }


    public void resetBoard() {
        // Reinicia todos los valores del tablero a 0
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = 0;
            }
        }
        // Genera dos nuevas celdas aleatorias
        generateRandomCell();
        generateRandomCell();
    }


    public int[][] getBoard() {
        return board;
    }
}
