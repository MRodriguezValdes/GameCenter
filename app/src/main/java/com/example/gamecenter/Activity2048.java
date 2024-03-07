package com.example.gamecenter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

public class Activity2048 extends AppCompatActivity {

    Game2048 game;
    GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_2048);

        game = new Game2048(this);

        this.fillTheGridLayout();

        this.updateBestScore();
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;


            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float diffX = e2.getX() - e1.getX();
                float diffY = e2.getY() - e1.getY();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            game.moveRight();
                            game.gameBoard.generateRandomCell();
                        } else {
                            game.moveLeft();
                            game.gameBoard.generateRandomCell();
                        }
                    }
                } else {
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            game.moveDown();
                            game.gameBoard.generateRandomCell();
                        } else {
                            game.moveUp();
                            game.gameBoard.generateRandomCell();
                        }
                    }
                }
                return super.onFling(e1, e2, velocityX, velocityY);

            }
        });

        Button playButton = findViewById(R.id.button);
        // Agregar un OnClickListener al botón "Play"
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reiniciar el juego al presionar el botón "Play"
                restartGame();
            }
        });

        Button undoButton = findViewById(R.id.undo_button);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.undoMove();
            }
        });
    }

    private void restartGame() {
        this.game.resetGame();
        this.updateBestScore();
    }

    private void updateBestScore() {
        // Obtener la mejor puntuación de la base de datos
        DatabaseHelper dbHelper = new DatabaseHelper(Activity2048.this);
        int bestScore = dbHelper.getBestScore("Game2048"); // Implementa este método en tu clase DatabaseHelper

        // Actualizar la etiqueta de la mejor puntuación en la interfaz de usuario
        TextView bestScoreTextView = findViewById(R.id.bestScore);
        bestScoreTextView.setText("Best: " + bestScore);
        Log.i("best score", "updateBestScore: " + bestScore);
    }


    public void fillTheGridLayout() {
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        TextView scoreTextView = findViewById(R.id.score);
        scoreTextView.setText("Score:" + "" + String.valueOf(game.currentScore));

        int numRows = gridLayout.getRowCount();
        int numColumns = gridLayout.getColumnCount();

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                int index = i * numColumns + j;
                TextView textView = (TextView) gridLayout.getChildAt(index);

                int cellValue = game.gameBoard.getBoard()[i][j];
                if (cellValue != 0) {
                    textView.setText(String.valueOf(cellValue));
                } else {
                    textView.setText("");
                }


                int color = getColorForValue(cellValue);
                textView.setBackgroundColor(color);
            }
        }
        if (!game.hasMoves()) {
            showGameOverDialog();
        }
    }

    private void showGameOverDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Over");
        builder.setMessage("No hay movimie                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  ntos posibles. ¿Deseas comenzar una nueva partida?");
        builder.setCancelable(false);
        DatabaseHelper dbHelper = new DatabaseHelper(Activity2048.this);
        dbHelper.insertScore(this.game.currentScore,"Game2048");

        builder.setPositiveButton("Nueva partida", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                game.resetGame();
                updateBestScore();
                fillTheGridLayout();
            }
        });

        AlertDialog gameOverDialog = builder.create();
        gameOverDialog.show();
    }


    private int getColorForValue(int value) {
        switch (value) {
            case 0:
                return 0xFFA35A8B;
            case 2:
                return 0xFFF1E9DB; // Color para el valor 2
            case 4:
                return 0xFFF3E8D9; // Color para el valor 4
            case 8:
                return 0xFFF5DFAA; // Color para el valor 8
            case 16:
                return 0xFFF8C98E; // Color para el valor 16
            case 32:
                return 0xFFF9B97C; // Color para el valor 32
            case 64:
                return 0xFFFAA860; // Color para el valor 64
            case 128:
                return 0xFFEDCE79; // Color para el valor 128
            case 256:
                return 0xFFEECB6B; // Color para el valor 256
            case 512:
                return 0xFFEEC75A; // Color para el valor 512
            case 1024:
                return 0xFFEEC447; // Color para el valor 1024
            case 2048:
                return 0xFFEEC233; // Color para el valor 2048
            default:
                return 0xFFCDC1B4; // Color predeterminado para otros valores
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }

}