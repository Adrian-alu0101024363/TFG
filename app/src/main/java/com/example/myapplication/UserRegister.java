package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.db.DbHelper;
import com.example.myapplication.db.DbUsers;

public class UserRegister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        initialChanges();
    }
    public void initialChanges() {
        TextView txtHello = (TextView)findViewById(R.id.textMessage); // R refer to all resources
        txtHello.setText("First name:\nSurname:\nEmail:\n");
        Button submitButton = findViewById(R.id.btnHello);
        submitButton.setText("Register");
    }
    public void onBtnClick(View view) {
        TextView txtHello = (TextView)findViewById(R.id.textMessage); // R refer to all resources
        txtHello.setText("First name:\nSurname:\nEmail:\n");
        EditText edtTxtName = findViewById(R.id.NameEdit);
        EditText edtTxtSurname = findViewById(R.id.SurnameEdit);
        EditText edtTxtEmail = findViewById(R.id.EmailEdit);
        String fullText = "First name: " + edtTxtName.getText().toString() + "\n";
        fullText += "Surname: " + edtTxtSurname.getText().toString() + "\n";
        fullText += "Email: " + edtTxtEmail.getText().toString() + "\n";
        txtHello.setText(fullText);
        DbHelper dbHelper = new DbHelper(UserRegister.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db != null) {
            DbUsers dbUsers = new DbUsers(UserRegister.this);
            long id = dbUsers.insertUser(edtTxtName.getText().toString(),edtTxtEmail.getText().toString());
            if (id > 0) {
                Toast.makeText(UserRegister.this,"Register done!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(UserRegister.this,"failed :/", Toast.LENGTH_LONG).show();
            }
        }
        edtTxtName.setText("");
        edtTxtSurname.setText("");
        edtTxtEmail.setText("");
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        //setContentView(R.layout.activity_user_register);
        //meter en base datos
        //cambiar escena
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