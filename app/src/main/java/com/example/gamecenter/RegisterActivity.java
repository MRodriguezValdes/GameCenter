package com.example.gamecenter;// RegisterActivity.java
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class RegisterActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private Button buttonRegister;
    private Button buttonSelectPhoto;
    private static final int PICK_IMAGE_REQUEST = 1;

    private String selectedPhotoPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonRegister = findViewById(R.id.buttonRegister);
        buttonSelectPhoto = findViewById(R.id.buttonSelectPhoto);

        buttonSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String confirmPassword = editTextConfirmPassword.getText().toString().trim();

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (isUsernameExists(username)) {
                    Toast.makeText(RegisterActivity.this, "El nombre de usuario ya está registrado", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (selectedPhotoPath.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Por favor, seleccione una imagen", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Guardar la ruta de la imagen en SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("imagePath", selectedPhotoPath);
                editor.apply();

                DatabaseHelper dbHelper = new DatabaseHelper(RegisterActivity.this);
                dbHelper.insertUser(username, password, selectedPhotoPath);

                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            selectedPhotoPath = getRealPathFromURI(selectedImageUri);
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null);
        if (cursor == null) return null;
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(projection[0]);
        String path = cursor.getString(columnIndex);
        cursor.close();
        return path;
    }

    private boolean isUsernameExists(String username) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {"username"};
        String selection = "username = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query("users", projection, selection, selectionArgs, null, null, null);
        boolean usernameExists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return usernameExists;
    }
}
