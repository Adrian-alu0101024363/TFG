package com.example.myapplication;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.example.myapplication.db.DbHelper;
import com.example.myapplication.db.DbUsers;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialChanges();
        downloadFiles();
    }

    private void downloadFiles() {

        TranslatorOptions options = new TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.ENGLISH)
                .setTargetLanguage(TranslateLanguage.SPANISH)
                .build();
        Translator translator = Translation.getClient(options);
        DownloadConditions conditions = new DownloadConditions.Builder()
                .requireWifi()
                .build();
        translator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Model downloaded successfully. Okay to start translating.
                        // (Set a flag, unhide the translation UI, etc.)
                        //Toast.makeText(getApplicationContext(), "exito", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Model couldn't be downloaded or other internal error.
                        //Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                });
        TranslatorOptions options2 = new TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.JAPANESE)
                .setTargetLanguage(TranslateLanguage.ENGLISH)
                .build();
        Translator translator2 = Translation.getClient(options2);
        DownloadConditions conditions2 = new DownloadConditions.Builder()
                .requireWifi()
                .build();
        translator2.downloadModelIfNeeded(conditions2)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Model downloaded successfully. Okay to start translating.
                        // (Set a flag, unhide the translation UI, etc.)
                        //Toast.makeText(getApplicationContext(), "exito", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Model couldn't be downloaded or other internal error.
                        //Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                });
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
                Button userInfoButton = findViewById(R.id.InfoUser);
                userInfoButton.setVisibility(View.VISIBLE);
            } else {
                Button registerButton = findViewById(R.id.button);
                registerButton.setVisibility(View.VISIBLE);
                Button userInfoButton = findViewById(R.id.InfoUser);
                userInfoButton.setVisibility(View.GONE);
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
        Intent intent = new Intent(this,UserInfoPage.class);
        startActivity(intent);
    }
}