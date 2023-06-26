package com.example.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.myapplication.db.DbHelper;
import com.example.myapplication.db.DbUsers;

public class Achievement extends BaseActivity {

    int total = 6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);
        //Populate the array of imagesView
        ImageView[] images = createImageArray(total);
        //set all listener to the img scroll view
        for (int i = 0; i < images.length; i++) {
            //lambda need finals not locals as value passed
            int atImgNumb = i;
            //lambda recommended way (old java wont work)
            images[i].setOnClickListener(v -> changeImg(images[atImgNumb].getDrawable()));
        }
    }

    //Change the img according to drawable selected (used by lambda above) (callback)
    public void changeImg(Drawable drawableImg) {
        ImageView toShow = findViewById(R.id.imageViewPrincipal);
        toShow.setImageDrawable(drawableImg);
    }

    //Using the tags of the linear images ImgView children to create the img array
    private ImageView[] createImageArray(int total) {
        ImageView[] images = new ImageView[total];
        LinearLayout linear = findViewById(R.id.linear_images);
        for (int position = 1; position <= total; position++) {
            String imgTag = "img" + position;
            ImageView imageView = linear.findViewWithTag(imgTag);
            Drawable drawable = getDrawableForLevel(position);
            imageView.setImageDrawable(drawable);
            images[position-1] = imageView;
        }
        return images;
    }
    private Drawable getDrawableForLevel(int position) {

        int userLevel = 1;
        DbUsers dbUsers = new DbUsers(Achievement.this);
        Userinfo currentUser = dbUsers.getLastUser();
        if (currentUser.getName() != "") {
            userLevel = currentUser.getNivel();
        }
        if (userLevel >= position) {
            // El usuario ha alcanzado o superado el nivel correspondiente a la posición de la imagen.
            // Asigna el drawable adecuado para mostrar.
            switch (position) {
                case 1:
                    return getResources().getDrawable(R.drawable.castle, getTheme());
                case 6:
                    return getResources().getDrawable(R.drawable.view, getTheme());
                case 11:
                    return getResources().getDrawable(R.drawable.rasha, getTheme());
                case 16:
                    return getResources().getDrawable(R.drawable.street, getTheme());
                case 21:
                    return getResources().getDrawable(R.drawable.temple, getTheme());
                // Agrega más casos según sea necesario para cada nivel y su drawable correspondiente.
                default:
                    // Si no se cumple ninguna condición, devuelve el drawable predeterminado o nulo.
                    return getResources().getDrawable(R.drawable.grey, getTheme());
            }
        } else {
            // El usuario aún no ha alcanzado el nivel correspondiente a la posición de la imagen.
            return getResources().getDrawable(R.drawable.grey, getTheme());
        }
    }
}
