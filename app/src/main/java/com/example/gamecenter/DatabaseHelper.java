package com.example.gamecenter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "game2048.db";
    private static final int DATABASE_VERSION = 3;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla de usuarios si no existe
        String createUserTable = "CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT, " +
                "password TEXT, " +
                "photo_path TEXT)";
        db.execSQL(createUserTable);

        // Crear la tabla de puntajes si no existe
        String createScoreTable = "CREATE TABLE scores (" + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "score INTEGER, " + "game TEXT)";
        db.execSQL(createScoreTable);

    }
    public void insertUser(String username, String password, String photoPath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        values.put("photo_path", photoPath);
        db.insert("users", null, values);
        db.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Este método se ejecuta si hay una actualización en la base de datos
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS scores");
        onCreate(db);
    }

    // Método para insertar un puntaje en la tabla de scores
    public void insertScore(int score, String game) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("score", score);
        values.put("game", game);
        db.insert("scores", null, values);
        db.close();
    }

    public int getBestScore(String gameType) {
        SQLiteDatabase db = this.getReadableDatabase();
        int bestScore = 0;

        String query = "SELECT MAX(score) FROM scores WHERE game = ?";
        Cursor cursor = db.rawQuery(query, new String[]{gameType});

        if (cursor != null && cursor.moveToFirst()) {
            bestScore = cursor.getInt(0);
            cursor.close();
        }

        return bestScore;
    }

    public ArrayList<Score> getAllScores(String gameType) {
        ArrayList<Score> scoresList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Consulta para obtener todos los puntajes para un juego específico
        String query = "SELECT * FROM scores WHERE game = ?";
        Cursor cursor = db.rawQuery(query, new String[]{gameType});

        // Verificar si el cursor contiene datos y moverlo al primer elemento
        if (cursor != null && cursor.moveToFirst()) {
            // Obtener los índices de las columnas
            int idIndex = cursor.getColumnIndex("id");
            int scoreIndex = cursor.getColumnIndex("score");
            int gameIndex = cursor.getColumnIndex("game");

            // Verificar si los índices son válidos
            if (idIndex != -1 && scoreIndex != -1 && gameIndex != -1) {
                // Iterar a través del cursor y agregar los puntajes a la lista
                do {
                    int scoreId = cursor.getInt(idIndex);
                    int scoreValue = cursor.getInt(scoreIndex);
                    String game = cursor.getString(gameIndex);

                    // Crear un objeto Score y agregarlo a la lista
                    Score score = new Score(scoreId, scoreValue, game);
                    scoresList.add(score);
                } while (cursor.moveToNext());
            }

            cursor.close();
        }

        return scoresList;
    }
    public String getImagePathByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String imagePath = "";

        String[] projection = {"photo_path"};
        String selection = "username = ?";
        String[] selectionArgs = {username};



        Cursor cursor = db.query("users", projection, selection, selectionArgs, null, null, null);
        int index = cursor.getColumnIndex("photo_path");

        if (cursor != null && cursor.moveToFirst()) {

            if(index !=-1){
                imagePath = cursor.getString(index);
            }
            cursor.close();
        }

        db.close();

        return imagePath;
    }

}





