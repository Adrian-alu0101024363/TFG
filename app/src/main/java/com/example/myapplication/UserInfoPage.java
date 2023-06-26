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
        DbHelper dbHelper = new DbHelper(UserInfoPage.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db != null) {
            DbUsers dbUsers = new DbUsers(UserInfoPage.this);
            Userinfo currentUser = dbUsers.getLastUser();
            if (currentUser.getName() != "") {
                textViewName.setText("Nombre: " + currentUser.getName());
                textViewEmail.setText("Email: " + currentUser.getEmail());
                textViewNivel.setText("Nivel: " + currentUser.getNivel());
                textViewExperiencia.setText("Experiencia: " + currentUser.getExperiencia());
                textViewRango.setText("Rango: " + currentUser.getRango());
                DbWords dbWords = new DbWords(UserInfoPage.this);
                Wordinfo lastWord = dbWords.getLastWord();
                if (lastWord != null) {
                    ImageView imageView = findViewById(R.id.imageView);
                    TextView textViewCard = findViewById(R.id.textViewCard);

                    // Aquí puedes utilizar los métodos correspondientes de lastWord para obtener la imagen y el texto
                    String imageUrl = lastWord.getImageUrl();
                    String text = lastWord.getText();
                    Glide.with(UserInfoPage.this).load(imageUrl).into(imageView);
                    textViewCard.setText(text);
                }
            }
        }
    }
}
