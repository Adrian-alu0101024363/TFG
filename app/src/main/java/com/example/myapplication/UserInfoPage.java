package com.example.myapplication;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.db.DbHelper;
import com.example.myapplication.db.DbUsers;

public class UserInfoPage  extends BaseActivity {
    private TextView textViewName;
    private TextView textViewEmail;
    private TextView textViewNivel;
    private TextView textViewExperiencia;
    private TextView textViewRango;
    private TextView textViewNative;
    private TextView textViewTarget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        // Asociar los TextView del layout con las variables
        textViewName = findViewById(R.id.textViewName);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewNivel = findViewById(R.id.textViewNivel);
        textViewExperiencia = findViewById(R.id.textViewExperiencia);
        textViewRango = findViewById(R.id.textViewRango);
        textViewNative = findViewById(R.id.textViewNative);
        textViewTarget = findViewById(R.id.textViewTarget);
        DbHelper dbHelper = new DbHelper(UserInfoPage.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db != null) {
            DbUsers dbUsers = new DbUsers(UserInfoPage.this);
            Userinfo currentUser = dbUsers.getLastUser();
            if (currentUser.getName() != "") {
                textViewName.setText("Name: " + currentUser.getName());
                textViewEmail.setText("Email: " + currentUser.getEmail());
                textViewNivel.setText("Nivel: " + currentUser.getNivel());
                textViewExperiencia.setText("Experiencia: " + currentUser.getExperiencia());
                textViewRango.setText("Rango: " + currentUser.getRango());
                textViewNative.setText("Native: " + currentUser.getMother());
                textViewTarget.setText("Target: " + currentUser.getTarget());
                DbWords dbWords = new DbWords(UserInfoPage.this);
                Wordinfo lastWord = dbWords.getLastWord();
                if (lastWord != null) {
                    ImageView imageView = findViewById(R.id.imageView);
                    TextView textViewCard = findViewById(R.id.textViewCard);

                    //los métodos correspondientes de lastWord para obtener la imagen y el texto
                    String imageUrl = lastWord.getImageUrl();
                    String text = lastWord.getText();
                    Glide.with(UserInfoPage.this).load(imageUrl).into(imageView);
                    textViewCard.setText(text);
                }
            }
        }
    }
}
