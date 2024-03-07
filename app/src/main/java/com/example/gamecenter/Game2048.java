package com.example.gamecenter;

import android.content.Context;
import android.util.Log;

public class Game2048 {
    Context context;
    GameBoard2048 gameBoard;
    int currentScore;
    private int previousScore;
    private int[][] previousBoard;
    int bestScore;


    public Game2048(Context context) {

        this.gameBoard = new GameBoard2048();
        this.context = context;
        this.currentScore = 0;

    }
    private void savePreviousBoard() {
        previousBoard = new int[4][4];
        for (int i = 0; i < gameBoard.getBoard().length; i++) {
            for (int j = 0; j < gameBoard.getBoard()[0].length; j++) {
                previousBoard[i][j] = gameBoard.getBoard()[i][j];
            }
        }
        this.previousScore= this.currentScore;
    }
    public void undoMove() {
        if (previousBoard != null) {
            for (int i = 0; i < gameBoard.getBoard().length; i++) {
                for (int j = 0; j < gameBoard.getBoard()[0].length; j++) {
                    gameBoard.getBoard()[i][j] = previousBoard[i][j];
                }
            }
            this.currentScore = previousScore;
            ((Activity2048) context).fillTheGridLayout();
        }
    }
    public void moveLeft() {
        savePreviousBoard();
        for (int i = 0; i < gameBoard.getBoard().length; i++) {
            int lastMergedIndex = -1; // Variable para controlar la fusión de números

            for (int j = 1; j < gameBoard.getBoard()[i].length; j++) {
                if (gameBoard.getBoard()[i][j] != 0) {
                    int current = gameBoard.getBoard()[i][j];
                    int y = j - 1;

                    while (y > lastMergedIndex && gameBoard.getBoard()[i][y] == 0) {
                        y--;
                    }

                    if (y > lastMergedIndex && gameBoard.getBoard()[i][y] == current) {
                        gameBoard.getBoard()[i][y] *= 2;
                        this.currentScore += gameBoard.getBoard()[i][y];
                        gameBoard.getBoard()[i][j] = 0;
                        lastMergedIndex = y;
                    } else {
                        int temp = gameBoard.getBoard()[i][j];
                        gameBoard.getBoard()[i][j] = 0;
                        gameBoard.getBoard()[i][y + 1] = temp;
                    }
                    Log.d("MoveLeft", "i: " + i + ", j: " + j);
                }
            }

        }

        ((Activity2048) context).fillTheGridLayout();
    }


    public void moveRight() {
        savePreviousBoard();
        for (int i = 0; i < gameBoard.getBoard().length; i++) {
            int lastMergedIndex = gameBoard.getBoard()[i].length; // Comienza desde el final

            for (int j = gameBoard.getBoard()[i].length - 2; j >= 0; j--) {
                if (gameBoard.getBoard()[i][j] != 0) {
                    int current = gameBoard.getBoard()[i][j];
                    int y = j + 1;

                    while (y < lastMergedIndex && gameBoard.getBoard()[i][y] == 0) {
                        y++;
                    }

                    if (y < lastMergedIndex && gameBoard.getBoard()[i][y] == current) {
                        gameBoard.getBoard()[i][y] *= 2;
                        this.currentScore += gameBoard.getBoard()[i][y];
                        gameBoard.getBoard()[i][j] = 0;
                        lastMergedIndex = y;
                    } else {
                        int temp = gameBoard.getBoard()[i][j];
                        gameBoard.getBoard()[i][j] = 0;
                        gameBoard.getBoard()[i][y - 1] = temp;
                        Log.d("MoveRight", "i: " + i + ", j: " + j);
                    }
                }
            }
        }
        ((Activity2048) context).fillTheGridLayout();
    }


    public void moveUp() {
        savePreviousBoard();
        for (int j = 0; j < gameBoard.getBoard()[0].length; j++) {
            int lastMergedIndex = -1; // Comienza desde el principio

            for (int i = 1; i < gameBoard.getBoard().length; i++) {
                if (gameBoard.getBoard()[i][j] != 0) {
                    int current = gameBoard.getBoard()[i][j];
                    int x = i - 1;

                    while (x > lastMergedIndex && gameBoard.getBoard()[x][j] == 0) {
                        x--;
                    }

                    if (x > lastMergedIndex && gameBoard.getBoard()[x][j] == current) {
                        gameBoard.getBoard()[x][j] *= 2;
                        this.currentScore += gameBoard.getBoard()[x][j];
                        gameBoard.getBoard()[i][j] = 0;
                        lastMergedIndex = x;
                    } else {
                        int temp = gameBoard.getBoard()[i][j];
                        gameBoard.getBoard()[i][j] = 0;
                        gameBoard.getBoard()[x + 1][j] = temp;
                    }
                    Log.d("MoveUp", "i: " + i + ", j: " + j);
                }
            }
        }
        ((Activity2048) context).fillTheGridLayout();
    }

    public void moveDown() {
        savePreviousBoard();
        for (int j = 0; j < gameBoard.getBoard()[0].length; j++) {
            int lastMergedIndex = gameBoard.getBoard().length; // Comienza desde el final

            for (int i = gameBoard.getBoard().length - 2; i >= 0; i--) {
                if (gameBoard.getBoard()[i][j] != 0) {
                    int current = gameBoard.getBoard()[i][j];
                    int x = i + 1;

                    while (x < lastMergedIndex && gameBoard.getBoard()[x][j] == 0) {
                        x++;
                    }

                    if (x < lastMergedIndex && gameBoard.getBoard()[x][j] == current) {
                        gameBoard.getBoard()[x][j] *= 2;
                        this.currentScore += gameBoard.getBoard()[x][j];
                        gameBoard.getBoard()[i][j] = 0;
                        lastMergedIndex = x;
                    } else {
                        int temp = gameBoard.getBoard()[i][j];
                        gameBoard.getBoard()[i][j] = 0;
                        gameBoard.getBoard()[x - 1][j] = temp;
                    }
                }
                Log.d("MoveDown", "i: " + i + ", j: " + j);
            }
        }
        ((Activity2048) context).fillTheGridLayout();
    }
    public void resetGame() {
        gameBoard.resetBoard();
        currentScore = 0;
        ((Activity2048) context).fillTheGridLayout();
    }
    public boolean hasMoves() {
        // Verifica si hay movimientos posibles en cualquier dirección
        for (int i = 0; i < gameBoard.getBoard().length; i++) {
            for (int j = 0; j < gameBoard.getBoard()[i].length; j++) {
                // Verifica si la celda actual está vacía
                if (gameBoard.getBoard()[i][j] == 0) {
                    return true;
                }
                // Verifica si hay celdas adyacentes con el mismo valor en la misma fila
                if (j < gameBoard.getBoard()[i].length - 1 && gameBoard.getBoard()[i][j] == gameBoard.getBoard()[i][j + 1]) {
                    return true;
                }
                // Verifica si hay celdas adyacentes con el mismo valor en la misma columna
                if (i < gameBoard.getBoard().length - 1 && gameBoard.getBoard()[i][j] == gameBoard.getBoard()[i + 1][j]) {
                    return true;
                }


                Log.d("hasMove","Estoy aqui");
            }
        }
        // Si no se encontraron movimientos posibles, devuelve false
        return false;
    }


}
