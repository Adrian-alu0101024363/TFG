package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.service.autofill.TextValueSanitizer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.db.DbHelper;
import com.example.myapplication.db.DbUsers;

public class MainActivity extends BaseActivity {

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
            Userinfo currentUser = dbUsers.getLastUser();
            txtHello.setText("Welcome back " + currentUser.getName());
            Log.d("entro", currentUser.getName());
            if (currentUser.getName() != "") {
                Button userButton = findViewById(R.id.button);
                userButton.setVisibility(View.GONE);

            }
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

    public void on_AchievementButton(View view) {
        Intent intent = new Intent(this,Achievement.class);
        startActivity(intent);
    }

    public void on_WordList(View view) {
        Intent intent = new Intent(this,WordList.class);
        startActivity(intent);
    }
    public void onInfoUser(View view) {

    }
}