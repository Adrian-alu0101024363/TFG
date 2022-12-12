package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.service.autofill.TextValueSanitizer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.db.DbHelper;
import com.example.myapplication.db.DbUsers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialChanges();
    }
    public void initialChanges() {
        TextView txtHello = (TextView)findViewById(R.id.welcomeMsg); // R refer to all resources
        //txtHello.setText("");
        DbHelper dbHelper = new DbHelper(MainActivity.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db != null) {
            DbUsers dbUsers = new DbUsers(MainActivity.this);
            Userinfo actualUser = dbUsers.getLastUser();
            txtHello.setText("Welcome back " + actualUser.getName());
        }
    }
    public void on_ar(View view) {
        Intent intent = new Intent(this,ar_screen.class);
        startActivity(intent);
    }
    public void onBtnClick(View view) {
        Intent intent = new Intent(this,UserRegister.class);
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.main_page:
                Intent main_intent = new Intent(this,MainActivity.class);
                startActivity(main_intent);
                return true;
            case R.id.sign_in:
                Intent sign_intent = new Intent(this,UserRegister.class);
                startActivity(sign_intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}