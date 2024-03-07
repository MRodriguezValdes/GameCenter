package com.example.gamecenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;


public class EditProfileActivity extends AppCompatActivity {

    private EditText editTextNewPassword;
    private EditText editTextConfirmPassword;
    private EditText editTextUsername;
    private Button buttonSaveChanges;
    private ImageView profileImageView;
    private TextView textViewUsername;
    private SharedPreferences sharedPreferences;
    private DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        dbHelper = new DatabaseHelper(this);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonSaveChanges = findViewById(R.id.buttonSaveChanges);
        profileImageView = findViewById(R.id.profileImageView);
        textViewUsername = findViewById(R.id.textViewUsername);
        editTextUsername = findViewById(R.id.editTextUsername);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Cargar el nombre de usuario y la foto de perfil
        String username = sharedPreferences.getString("username", "");
        String imagePath = sharedPreferences.getString("imagePath", "");

        // Mostrar la foto de perfil
        if (!imagePath.isEmpty()) {
            Glide.with(this)
                    .load(Uri.parse(imagePath)) // Parsea la ruta de la imagen a Url
                    .placeholder(R.drawable.default_profile_image)// Imagen de placeholder mientras se carga
                    .error(R.drawable.error) // Imagen de error si falla la carga
                    .into(profileImageView);
        } else {
            // Si no hay una imagen guardada, puedes cargar una imagen predeterminada o dejar el ImageView vacío
            profileImageView.setImageResource(R.drawable.default_profile_image);
        }

        // Mostrar el nombre de usuario
        textViewUsername.setText(username);

        buttonSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener la nueva contraseña y la confirmación de la contraseña
                String newPassword = editTextNewPassword.getText().toString().trim();
                String confirmPassword = editTextConfirmPassword.getText().toString().trim();
                // Obtener el nuevo nombre de usuario
                String newUsername = editTextUsername.getText().toString().trim();

                if (!newPassword.equals(confirmPassword)) {
                    // Si las contraseñas no coinciden, muestra un mensaje y no guardes los cambios
                    Toast.makeText(EditProfileActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Guardar la nueva contraseña y el nuevo nombre de usuario en SharedPreferences y en la base de datos
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("password", newPassword);
                editor.putString("username", newUsername); // Guardar el nuevo nombre de usuario en SharedPreferences
                editor.apply();

                // Actualizar la contraseña y el nombre de usuario en la base de datos
                updateUserData(newUsername, newPassword);

                // Volver a la actividad de ajustes después de guardar los cambios
                Intent intent = new Intent(EditProfileActivity.this, Settings.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private void updateUserData(String newUsername, String newPassword) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", newUsername);
        values.put("password", newPassword);
        db.update("users", values, null, null);
        db.close();
    }
}