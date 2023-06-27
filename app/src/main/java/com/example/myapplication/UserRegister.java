package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.db.DbHelper;
import com.example.myapplication.db.DbUsers;

import java.util.ArrayList;
import java.util.List;

public class UserRegister extends BaseActivity {
    private EditText edtTxtName;
    private EditText edtTxtSurname;
    private EditText edtTxtEmail;
    private Spinner nativeLanguageSpinner;
    private Spinner targetLanguageSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        initialChanges();
        setupSpinners();
        // Inicializar los campos y spinners
        edtTxtName = findViewById(R.id.NameEdit);
        edtTxtSurname = findViewById(R.id.SurnameEdit);
        edtTxtEmail = findViewById(R.id.EmailEdit);
        nativeLanguageSpinner = findViewById(R.id.nativeLanguageSpinner);
        targetLanguageSpinner = findViewById(R.id.targetLanguageSpinner);
    }
    public void initialChanges() {
        TextView txtHello = findViewById(R.id.textMessage);
        txtHello.setText("First name:\nSurname:\nEmail:");

        Button submitButton = findViewById(R.id.btnHello);
        submitButton.setText("Register");
    }
    public void onBtnClick(View view) {
        TextView txtHello = findViewById(R.id.textMessage);
        txtHello.setText("First name:\nSurname:\nEmail:");

        String firstName = edtTxtName.getText().toString();
        String surname = edtTxtSurname.getText().toString();
        String email = edtTxtEmail.getText().toString();

        String fullText = "First name: " + firstName + "\n";
        fullText += "Surname: " + surname + "\n";
        fullText += "Email: " + email + "\n";
        txtHello.setText(fullText);

        // Obtener los valores seleccionados de los spinners
        String nativeLanguage = nativeLanguageSpinner.getSelectedItem().toString();
        String targetLanguage = targetLanguageSpinner.getSelectedItem().toString();

        // Insertar en la base de datos
        DbHelper dbHelper = new DbHelper(UserRegister.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        if (db != null) {
            DbUsers dbUsers = new DbUsers(UserRegister.this);
            long id = dbUsers.insertUser(firstName, email, nativeLanguage, targetLanguage, 1);

            if (id > 0) {
                Toast.makeText(UserRegister.this, "Register done!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(UserRegister.this, "Failed :/", Toast.LENGTH_LONG).show();
            }
        }

        edtTxtName.setText("");
        edtTxtSurname.setText("");
        edtTxtEmail.setText("");

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void setupSpinners() {
        nativeLanguageSpinner = findViewById(R.id.nativeLanguageSpinner);
        targetLanguageSpinner = findViewById(R.id.targetLanguageSpinner);

        // Create a list of languages
        List<String> languages = new ArrayList<>();
        languages.add("English");
        languages.add("Japanese");
        languages.add("Spanish");

        // Create an ArrayAdapter using the languages list and a default spinner layout
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languages);

        // Set the drop-down view layout resource
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the spinner adapter for both spinners
        nativeLanguageSpinner.setAdapter(spinnerAdapter);
        targetLanguageSpinner.setAdapter(spinnerAdapter);
    }

    /*public void show(View view) {
        ImageView img = findViewById(R.id.dosb);
        if(img.getVisibility() == View.VISIBLE) {
            img.setVisibility(View.INVISIBLE);
        } else {
            img.setVisibility(View.VISIBLE);
        }
    }*/

}