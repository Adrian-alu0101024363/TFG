package com.example.myapplication;

import static com.example.myapplication.db.DbHelper.TABLE_USERS;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.db.DbHelper;
import com.example.myapplication.db.DbUsers;

public class LogIn extends BaseActivity {
    private EditText edtTxtName;
    private EditText edtTxtEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        initialChanges();
        // Inicializar los campos y spinners
        edtTxtName = findViewById(R.id.NameEdit);
        edtTxtEmail = findViewById(R.id.EmailEdit);
    }
    public void initialChanges() {
        TextView txtHello = findViewById(R.id.textMessage);
        txtHello.setText("First name:\nSurname:\nEmail:");

        Button submitButton = findViewById(R.id.btnHello);
    }
    public void onBtnClick(View view) {
        TextView txtHello = findViewById(R.id.textMessage);
        txtHello.setText("First name:\nSurname:\nEmail:");

        String firstName = edtTxtName.getText().toString();
        String email = edtTxtEmail.getText().toString();

        String fullText = "First name: " + firstName + "\n";
        fullText += "Email: " + email + "\n";
        txtHello.setText(fullText);

        // Insertar en la base de datos
        DbHelper dbHelper = new DbHelper(LogIn.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        if (db != null) {
            DbUsers dbUsers = new DbUsers(LogIn.this);
            Userinfo usuario = dbUsers.getUserById(firstName, email);
            // Establecer todos los campos "active" a 0
            ContentValues updateValues = new ContentValues();
            updateValues.put("active", 0);
            db.update(TABLE_USERS, updateValues, null, null);
            //Hacer update de active aqui
            ContentValues updateValues2 = new ContentValues();
            updateValues2.put("active", 1);
            String whereClause = "id = ?";
            String[] whereArgs = {String.valueOf(usuario.getId())};
            db.update(TABLE_USERS, updateValues2, whereClause, whereArgs);
            if (usuario != null) {
                Toast.makeText(LogIn.this, "Logged In!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(LogIn.this, "Failed :/", Toast.LENGTH_LONG).show();
            }
        }

        edtTxtName.setText("");
        edtTxtEmail.setText("");

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
