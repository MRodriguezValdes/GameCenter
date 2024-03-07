        package com.example.gamecenter;

        import android.content.DialogInterface;
        import android.os.Bundle;
        import android.os.CountDownTimer;
        import android.view.View;
        import android.widget.Button;
        import android.widget.GridLayout;
        import android.widget.TextView;

        import androidx.appcompat.app.AlertDialog;
        import androidx.appcompat.app.AppCompatActivity;

        public class SenkuActivity extends AppCompatActivity {
            SenkuScorer senkuScorer;
            SenkuGame senkuGame;
            TextView timerTextView;
            CountDownTimer countDownTimer;
            Button newGameButton;

            Button undoButton;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.layout_senku);

                // Obtiene una referencia al GridLayout
                GridLayout gridLayout = findViewById(R.id.gridLayout);

                // Inicializa el juego Senku
                this.senkuGame = new SenkuGame(gridLayout);
                this.senkuScorer=new SenkuScorer();

                // Inicializa los otros componentes de la interfaz de usuario
                timerTextView = findViewById(R.id.timer);
                newGameButton = findViewById(R.id.newGame);
                undoButton= findViewById(R.id.Undo);

                // Configura el temporizador y el botón Nuevo juego
                startTimer();
                newGameButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (countDownTimer != null) {
                            countDownTimer.cancel();
                        }
                        senkuGame.resetBoard();
                        gridLayout.invalidate(); // Invalida el GridLayout para redibujarlo
                        startTimer();
                    }
                });


                undoButton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        senkuGame.undoMove();
                        gridLayout.invalidate(); // Invalida el GridLayout para redibujarlo
                    }
                });
            }



            public void startTimer() {
                countDownTimer = new CountDownTimer(60000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        long seconds = millisUntilFinished / 1000;
                        long minutes = seconds / 60;
                        seconds = seconds % 60;
                        long hours = minutes / 60;
                        minutes = minutes % 60;
                        timerTextView.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
                    }

                    public void onFinish() {
                        timerTextView.setText("00:00:00");
                        showGameOverDialog();
                    }

                }.start();
            }

            private void showGameOverDialog() {
                // Obtener la puntuación actual del juego desde SenkuScorer
                int score = senkuScorer.calculateScore(senkuGame.senkuBoard);
                DatabaseHelper dbHelper = new DatabaseHelper(SenkuActivity.this);
                dbHelper.insertScore(score, "Senku");
                // Construir el mensaje del cuadro de diálogo
                String message = "Game Over\nYour Score: " + score;

                // Crear el cuadro de diálogo
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(false);
                builder.setMessage(message)
                        .setPositiveButton("New Game", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                senkuGame.resetBoard();
                                startTimer();
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }


            @Override
            protected void onDestroy() {
                super.onDestroy();
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
            }
        }
