package com.example.gamecenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Settings extends AppCompatActivity {

    private ImageView profileImageView;
    private Button editButton;
    private TextView usernameTextView; // TextView para mostrar el nombre de usuario

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        profileImageView = findViewById(R.id.profileImageView);
        editButton = findViewById(R.id.editButton);
        usernameTextView = findViewById(R.id.usernameTextView); // TextView para el nombre de usuario

        loadUserProfile();

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir la actividad de edición de perfil
                Intent intent = new Intent(Settings.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadUserProfile() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String imagePath = sharedPreferences.getString("imagePath", "");
        String username = sharedPreferences.getString("username", "");

        // Cargar la imagen utilizando Glide
        if (!imagePath.isEmpty()) {
            Glide.with(this)
                    .load(Uri.parse(imagePath)) // Parsea la ruta de la imagen a Uri
                    .placeholder(R.drawable.default_profile_image) // Imagen de placeholder mientras se carga
                    .error(R.drawable.error) // Imagen de error si falla la carga
                    .into(profileImageView);
        } else {
            // Si no hay una imagen guardada, cargar una imagen predeterminada o dejar el ImageView vacío
            profileImageView.setImageResource(R.drawable.default_profile_image);
        }

        // Mostrar el nombre de usuario en el TextView
        usernameTextView.setText(username);
    }



}
