package com.example.gamecenter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonRegister;
    private Button buttonLogin;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

                // Dentro del método onClick del botón de login
                if (isLoginValid(username, password)) {
                    // Inicio de sesión exitoso
                    Toast.makeText(LoginActivity.this, "¡Inicio de sesión exitoso!", Toast.LENGTH_SHORT).show();

                    // Obtener la ruta de la imagen del usuario
                    DatabaseHelper dbHelper = new DatabaseHelper(LoginActivity.this);
                    String imagePath = dbHelper.getImagePathByUsername(username);

                    // Guardar el nombre de usuario y la ruta de la imagen en SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", username);
                    editor.putString("imagePath", imagePath);
                    editor.apply();

                    // Iniciar la actividad principal o GameCenterActivity
                    Intent intent = new Intent(LoginActivity.this, GameCenterActivity.class);
                    startActivity(intent);
                    finish(); // Esto evita que el usuario pueda volver atrás presionando el botón de retroceso
                }
                else {
                    // Nombre de usuario o contraseña incorrectos
                    Toast.makeText(LoginActivity.this, "Nombre de usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }

            }
        });
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir la actividad de registro cuando el usuario haga clic en "Registrarse"
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    private boolean isLoginValid(String username, String password) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Consulta para verificar el usuario y la contraseña en la base de datos
        String selection = "username = ? AND password = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query("users", null, selection, selectionArgs, null, null, null);
        boolean isValid = cursor.getCount() > 0;

        // Cerrar el cursor y la base de datos
        cursor.close();
        db.close();

        return isValid;
    }

}
